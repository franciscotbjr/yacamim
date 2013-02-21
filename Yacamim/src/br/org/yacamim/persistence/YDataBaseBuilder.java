/**
 * YSQLBuilder.java
 *
 * Copyright 2012 yacamim.org.br
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.org.yacamim.persistence;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.Log;
import br.org.yacamim.util.YUtilReflection;
import br.org.yacamim.util.YUtilString;

/**
 * 
 * Class YSQLBuilder TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
class YDataBaseBuilder {
	
	private static final Map<Class<?>, String> sqlTypeMap = new HashMap<Class<?>,String>();
	
	static {
		// Integer
		sqlTypeMap.put(byte.class, YUtilPersistence.SQL_INTEGER);
		sqlTypeMap.put(short.class, YUtilPersistence.SQL_INTEGER);
		sqlTypeMap.put(int.class, YUtilPersistence.SQL_INTEGER);
		sqlTypeMap.put(long.class, YUtilPersistence.SQL_INTEGER);
		sqlTypeMap.put(Byte.class, YUtilPersistence.SQL_INTEGER);
		sqlTypeMap.put(Short.class, YUtilPersistence.SQL_INTEGER);
		sqlTypeMap.put(Integer.class, YUtilPersistence.SQL_INTEGER);
		sqlTypeMap.put(Long.class, YUtilPersistence.SQL_INTEGER);
		
		// Text
		sqlTypeMap.put(String.class, YUtilPersistence.SQL_TEXT);
		sqlTypeMap.put(StringBuffer.class, YUtilPersistence.SQL_TEXT);
		sqlTypeMap.put(StringBuilder.class, YUtilPersistence.SQL_TEXT);
		
		// Floating Point
		sqlTypeMap.put(float.class, YUtilPersistence.SQL_REAL);
		sqlTypeMap.put(double.class, YUtilPersistence.SQL_REAL);
		sqlTypeMap.put(Float.class, YUtilPersistence.SQL_REAL);
		sqlTypeMap.put(Double.class, YUtilPersistence.SQL_REAL);
		
		// BLOB
		sqlTypeMap.put(InputStream.class, YUtilPersistence.SQL_BLOB);
		sqlTypeMap.put(byte[].class, YUtilPersistence.SQL_BLOB);
		
		// BLOB
		sqlTypeMap.put(boolean.class, YUtilPersistence.SQL_TEXT);
		sqlTypeMap.put(Boolean.class, YUtilPersistence.SQL_TEXT);
		
		// Date
		sqlTypeMap.put(Date.class, YUtilPersistence.SQL_TEXT);
		
	}
	
	/**
	 * 
	 */
	public YDataBaseBuilder() {
		super();
	}
	
	/**
	 * Creates the script to build the data base. Each StringBuilder instance inside the returned List represents a table creation script.<br/>
	 * 
	 * @param classes
	 * @return
	 */
	public List<StringBuilder> buildCreateScript(final List<Class<?>> classes) {
		final List<StringBuilder> createScript = new ArrayList<StringBuilder>();
		try {
			this.processEntities(classes);
			
			final YDependencyOrderer yDependencyOrderer = new YDependencyOrderer();
			List<Class<?>> orderedList = yDependencyOrderer.order(classes);
			
			for(Class<?> clazz : orderedList) {
				final Map<StringBuilder, Boolean> scriptMap = this.buildCreateSQL(clazz);
				final Set<StringBuilder> sqlScripts = scriptMap.keySet();
				for(final StringBuilder sqlScript : sqlScripts) { // Adds only regular tables
					if(!YUtilString.isEmptyString(sqlScript) 
							&& !scriptMap.get(sqlScript)) {
						createScript.add(sqlScript);
					}
				}
				for(final StringBuilder sqlScript : sqlScripts) { // Adds only join tables at the end of the list
					if(!YUtilString.isEmptyString(sqlScript) 
							&& scriptMap.get(sqlScript)) {
						createScript.add(sqlScript);
					}
				}
			}
			
			this.terminateYCacheProcessedEntity();
		} catch (Exception e) {
			Log.e("YSQLBuilder.buildCreateScript", e.getMessage());
		}
		return createScript;
	}
	

	/**
	 * 
	 * @param classes
	 */
	private void processEntities(final List<Class<?>> classes) {
		try {
			for(final Class<?> clazz : classes) {
				if(YUtilPersistence.isEntity(clazz)) {
					final YProcessedEntity yProcessedEntity = new YProcessedEntity();
					yProcessedEntity.setTableName(YUtilPersistence.getTableName(clazz));
					yProcessedEntity.setClassType(clazz);
					yProcessedEntity.setClassName(clazz.getSimpleName());
			
					final List<Method> getMethods = initMethodsGet(clazz);
					
					for(final Method method : getMethods) {
						final Class<?> returnedType = method.getReturnType();
						final String sqlType = this.getSqlType(returnedType);
						if(sqlType != null) {
							boolean idIdentificado = locateId(yProcessedEntity, method);
							if(idIdentificado) {
								break;
							}
						}
					}
					this.addProcessedEntity(yProcessedEntity);
				}
			}
		} catch (Exception e) {
			Log.e("YSQLBuilder.processEntities", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param sourceClassOfTableScript
	 * @return
	 * @throws BidirectionalOneToOneException 
	 */
	private Map<StringBuilder, Boolean> buildCreateSQL(final Class<?> sourceClassOfTableScript) throws BidirectionalOneToOneException {
		final Map<StringBuilder, Boolean> scriptMap = new HashMap<StringBuilder, Boolean>();
		final StringBuilder sqlCreate = new StringBuilder();
		final YProcessedEntity processedEntity = this.getYProcessedEntity(sourceClassOfTableScript);
		if(YUtilPersistence.isEntity(sourceClassOfTableScript) && processedEntity != null) { 
			
			final List<Method> getMethods = this.initMethodsGet(sourceClassOfTableScript);
			
			final StringBuilder idBuilder = new StringBuilder();
			final List<StringBuilder> cols = new ArrayList<StringBuilder>();
			final List<StringBuilder> fks = new ArrayList<StringBuilder>();
			final List<StringBuilder> fkConstarints = new ArrayList<StringBuilder>();
			for(final Method currentGetMethod : getMethods) {
				final StringBuilder sqlCol = new StringBuilder();
				final StringBuilder sqlFK = new StringBuilder();
				final StringBuilder sqlFKConstarint = new StringBuilder();
				final Class<?> currentReturnedType = currentGetMethod.getReturnType();
				final String sqlType = this.getSqlType(currentReturnedType); 
				
				if(sqlType != null) {
					
					if(YUtilPersistence.isId(currentGetMethod)) {
						idBuilder.append(processedEntity.getIdColumn());
						idBuilder.append(" " + sqlType);
						
						idBuilder.append(YUtilPersistence.SQL_WORD_PRIMARY_KEY);
						if(YUtilPersistence.isAutoincrement(currentGetMethod)) {
							idBuilder.append(YUtilPersistence.SQL_WORD_AUTOINCREMENT);
						}
						idBuilder.append(YUtilPersistence.SQL_WORD_NOT + YUtilPersistence.SQL_WORD_NULL);
					} else {
						final Column column = currentGetMethod.getAnnotation(Column.class);
						
						if(column != null) {
							defineColNameAndType(sqlCol, currentGetMethod, sqlType, column);
							
							if(YUtilPersistence.isText(sqlType) && column != null) {
								sqlCol.append("(" + column.length() + ") ");
							}
							
							if(!column.nullable()) {
								sqlCol.append(YUtilPersistence.SQL_WORD_NOT);
							}
							sqlCol.append(YUtilPersistence.SQL_WORD_NULL);
							if(column.unique()) {
								sqlCol.append(YUtilPersistence.SQL_WORD_UNIQUE);
							}
						}
					}

				} else {
					final Column column = currentGetMethod.getAnnotation(Column.class);
					if(YUtilPersistence.isEntity(currentReturnedType) && column != null) {
						final Method[] typeGetMethods = YUtilReflection.getGetMethodArray(currentReturnedType);
						Method methodColFK = null;
						for(final Method candidateMethodForFK : typeGetMethods) {
							if(YUtilPersistence.isId(candidateMethodForFK)) {
								methodColFK = candidateMethodForFK;
								break;
							}
						}
						if(methodColFK != null) {
							final Class<?> returnedTypeFK = methodColFK.getReturnType();
							final String sqlTypeFK = this.getSqlType(returnedTypeFK);
							if(sqlTypeFK != null) {
								final YProcessedEntity processedEntityFK = getYProcessedEntity(currentReturnedType);
								
								final String fkName = YUtilPersistence.getColumnName(column, methodColFK) + "_" + processedEntityFK.getTableName();
								sqlFK.append(" " + fkName);
								sqlFK.append(" " + sqlTypeFK);
								
								// JPA ----------------------------------------------------------
								// 2.10.3 Unidirectional Single-Valued Relationships
								// 2.10.3.1 Unidirectional OneToOne Relationships
								
								final OneToOne oneToOne = currentGetMethod.getAnnotation(OneToOne.class);
								final ManyToOne manyToOne = currentGetMethod.getAnnotation(ManyToOne.class);
								if(oneToOne != null) {
									// Checks if this is an Bidirectional Relationships
									if(YUtilPersistence.hashBidirectionalOneToOneItem(typeGetMethods, sourceClassOfTableScript, currentGetMethod)) { // It is an Bidirectional Relationship
										final Method bidirectionalOneToOneReferenceMethod = YUtilPersistence.getBidirectionalOneToOneOwnedMethod(typeGetMethods, sourceClassOfTableScript, currentGetMethod);
										if(bidirectionalOneToOneReferenceMethod == null) {
											final Method invalidBidirectionalOneToOneReferenceMethod = YUtilPersistence.getInvalidBidirectionalOneToOneOwnedMethod(typeGetMethods, sourceClassOfTableScript, currentGetMethod);
											if(invalidBidirectionalOneToOneReferenceMethod != null) {
												final OneToOne invalidOneToOne = invalidBidirectionalOneToOneReferenceMethod.getAnnotation(OneToOne.class);
												throw new BidirectionalOneToOneException("Invalid Bidirectional OneToOne relationship: mappedBy=" + invalidOneToOne.mappedBy());
											}
										}
									} else {
										if(YUtilPersistence.isOneToOneOwner(oneToOne)) {
											sqlFK.append(YUtilPersistence.SQL_WORD_UNIQUE);
										}
									}
								} else if (manyToOne != null) {
									
								}
								
								// 2.10.3.2 Unidirectional ManyToOne Relationships
								
								
								// -------------------------------------------------------------
								
								sqlFKConstarint.append(YUtilPersistence.SQL_WORD_FOREIGN_KEY + "(" + fkName+ ") " + YUtilPersistence.SQL_WORD_REFERENCES + processedEntityFK.getTableName() + "(" + processedEntityFK.getIdColumn() + ")");
							}
						}
					} else if (YUtilReflection.isList(currentReturnedType) && column != null) {
						final ManyToMany manyToMany = currentGetMethod.getAnnotation(ManyToMany.class);
						
						if(manyToMany != null) {
							if(YUtilPersistence.isManyToManyOwner(manyToMany)) { 
								// Finds the owned of relationship
								final Class<?> ownedType = YUtilReflection.getGenericType(sourceClassOfTableScript, currentGetMethod);
								if(ownedType != null) {
									final Method ownedMethod = YUtilPersistence.getBidirectionalManyToManyOwnedMethod(YUtilReflection.getGetMethodArray(ownedType), sourceClassOfTableScript, currentGetMethod);
									if(ownedMethod != null) {
										final YProcessedEntity processedOwnedEntity =  this.getYProcessedEntity(ownedType);
										final StringBuilder sqlCreateJoinTable = new StringBuilder();
										// Generate a join table that is named
										sqlCreateJoinTable.append(YUtilPersistence.SQL_WORD_CREATE + YUtilPersistence.SQL_WORD_TABLE);
										sqlCreateJoinTable.append(" " + processedEntity.getTableName() + "_" + processedOwnedEntity.getTableName() +  " (");
										
										final String ownerFKName = processedEntity.getTableName() + "_" + processedEntity.getIdColumn();
										final String ownedFKName = processedOwnedEntity.getTableName() + "_" + processedOwnedEntity.getIdColumn();
										
										// Columns
										sqlCreateJoinTable.append(
												ownerFKName + " " + this.getSqlType(YUtilPersistence.getGetIdMethod(sourceClassOfTableScript).getReturnType()) + YUtilPersistence.SQL_WORD_NOT + YUtilPersistence.SQL_WORD_NULL + ", "
												);
										sqlCreateJoinTable.append(
												ownedFKName + " " + this.getSqlType(YUtilPersistence.getGetIdMethod(ownedType).getReturnType()) + YUtilPersistence.SQL_WORD_NOT + YUtilPersistence.SQL_WORD_NULL + ", "
												);
										// FK Constraints
										sqlCreateJoinTable.append(
												sqlCreateJoinTable.append(YUtilPersistence.SQL_WORD_FOREIGN_KEY + "(" + ownerFKName  + ") " + YUtilPersistence.SQL_WORD_REFERENCES + processedEntity.getTableName() + "(" + processedEntity.getIdColumn() + "),")
												); // FK A
										sqlCreateJoinTable.append(
												sqlCreateJoinTable.append(YUtilPersistence.SQL_WORD_FOREIGN_KEY + "(" + ownedFKName + ") " + YUtilPersistence.SQL_WORD_REFERENCES + processedOwnedEntity.getTableName() + "(" + processedOwnedEntity.getIdColumn() + ")")
												); // FK B
										
										sqlCreateJoinTable.append(" ); ");
										
										
										scriptMap.put(sqlCreateJoinTable, true);
									} else {
										throw new BidirectionalOneToOneException("Invalid Bidirectional ManyToMany relationship: ownedMethod=" + ownedMethod);
									}
								} else {
									throw new BidirectionalOneToOneException("Invalid Bidirectional ManyToMany relationship: ownedType=" + ownedType);
								}
							}
						}
					}
				}
				//---
				if(!YUtilString.isEmptyString(sqlFK)) {
					fks.add(sqlFK);
				}
				if(!YUtilString.isEmptyString(sqlCol)) {
					cols.add(sqlCol);
				}
				if(!YUtilString.isEmptyString(sqlFKConstarint)) {
					fkConstarints.add(sqlFKConstarint);
				}
			}
			for(StringBuilder builderFKs : fks) {
				cols.add(0, builderFKs);
			}
			cols.add(0, idBuilder);
			cols.addAll(fkConstarints);
			for(int i = 0; i < cols.size()-1; i++) {
				cols.get(i).append(", ");
			}
			
			sqlCreate.append(YUtilPersistence.SQL_WORD_CREATE + YUtilPersistence.SQL_WORD_TABLE);
			sqlCreate.append(" " + processedEntity.getTableName() + " (");
			for(StringBuilder row : cols){
				sqlCreate.append(row);
			}
			sqlCreate.append(" ); ");
		}
		scriptMap.put(sqlCreate, false);
		return scriptMap;
	}

	/**
	 * 
	 * @param builder
	 * @param method
	 * @param sqlType
	 * @param column
	 */
	private void defineColNameAndType(final StringBuilder builder,
			final Method method, final String sqlType, final Column column) {
		//---
		builder.append(" " + YUtilPersistence.getColumnName(column, method));
		builder.append(" " + sqlType);
	}

	/**
	 * 
	 * @param classe
	 */
	private List<Method> initMethodsGet(final Class<?> classe) {
		List<Method> getMethods = YUtilReflection.getGetMethodList(classe);
		if(getMethods != null) {
			List<Method> excludeMethods = new ArrayList<Method>();
			for(Method getMethod : getMethods) {
				if(getMethod.getName().equals("getYError")
						|| getMethod.getName().equals("getYMessage")) {
					excludeMethods.add(getMethod);
				}
			}
			if(!excludeMethods.isEmpty()) {
				for(Method excludeMethod : excludeMethods) {
					getMethods.remove(excludeMethod);
				}
			}
		}
		return getMethods;
	}

	/**
	 * 
	 * @param classe
	 * @return
	 */
	private YProcessedEntity getYProcessedEntity(final Class<?> classe) {
		try {
			return YCacheProcessedEntity.getInstance().getYProcessedEntity(classe);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 */
	private void terminateYCacheProcessedEntity() {
		try {
			YCacheProcessedEntity.getInstance().terminate();
		} catch (YCacheProcessedEntityTerminatedException e) {
			Log.e("YSQLBuilder.terminateYCacheProcessedEntity", e.getMessage());
		}
	}

	/**
	 * 
	 * @param yProcessedEntity
	 */
	private void addProcessedEntity(final YProcessedEntity yProcessedEntity) {
		try {
			YCacheProcessedEntity.getInstance().addProcessedEntity(yProcessedEntity);
		} catch (YCacheProcessedEntityTerminatedException e) {
			Log.e("YSQLBuilder.addProcessedEntity", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param yProcessedEntity
	 * @param method
	 * @return
	 */
	private boolean locateId(final YProcessedEntity yProcessedEntity, final Method method) {
		boolean idIdentificado = false; 
		try {
			final Column column = method.getAnnotation(Column.class); 
			if(column != null && YUtilPersistence.isId(method)) { // There is @Column annotation and there is @Id annotation
				yProcessedEntity.setIdColumn(YUtilPersistence.getColumnName(column, method));
				yProcessedEntity.setIdMethod(method.getName());
				idIdentificado = true;
			} else if(YUtilPersistence.isId(method)) { // There is no @Column annotation, yet there is @Id annotation
				yProcessedEntity.setIdColumn(YUtilPersistence.getColumnName(null, method));
				yProcessedEntity.setIdMethod(method.getName());	
				idIdentificado = true;
			}
		} catch (Exception e) {
			Log.e("YSQLBuilder.locateId", e.getMessage());
		}
		return idIdentificado;
	}

	/**
	 * 
	 * @param returnedType
	 * @return
	 */
	private String getSqlType(Class<?> returnedType) {
		return sqlTypeMap.get(returnedType);
	}

}