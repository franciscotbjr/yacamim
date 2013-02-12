/**
 * YSQLBuild.java
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

import br.org.yacamim.util.YUtilReflection;
import br.org.yacamim.util.YUtilString;

/**
 * 
 * Class YSQLBuild TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
class YSQLBuilder {
	
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
	public YSQLBuilder() {
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
				final StringBuilder sqlScript = this.montaSQL(clazz);
				if(!YUtilString.isEmptyString(sqlScript)) {
					createScript.add(sqlScript);
				}
			}
			
			this.terminateYCacheProcessedEntity();
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	private StringBuilder montaSQL(final Class<?> clazz) {
		final StringBuilder sqlCreate = new StringBuilder();
		final YProcessedEntity processedEntity = this.getYProcessedEntity(clazz);
		if(YUtilPersistence.isEntity(clazz) && processedEntity != null) { 
			
			final List<Method> getMethods = this.initMethodsGet(clazz);
			
			final StringBuilder idBuilder = new StringBuilder();
			final List<StringBuilder> cols = new ArrayList<StringBuilder>();
			final List<StringBuilder> fks = new ArrayList<StringBuilder>();
			final List<StringBuilder> fkConstarints = new ArrayList<StringBuilder>();
			for(final Method method : getMethods) {
				final StringBuilder sqlCol = new StringBuilder();
				final StringBuilder sqlFK = new StringBuilder();
				final StringBuilder sqlFKConstarint = new StringBuilder();
				final Class<?> returnedType = method.getReturnType();
				final String sqlType = this.getSqlType(returnedType); 
				
				if(sqlType != null) {
					
					final Column column = method.getAnnotation(Column.class);
					
					if(column != null) {
						if(YUtilPersistence.isId(method)) {
							defineColNameAndType(idBuilder, method, sqlType, column);
							
							idBuilder.append(YUtilPersistence.SQL_WORD_PRIMARY_KEY);
							if(YUtilPersistence.isAutoincrement(method)) {
								idBuilder.append(YUtilPersistence.SQL_WORD_AUTOINCREMENT);
							}
							idBuilder.append(YUtilPersistence.SQL_WORD_NOT + YUtilPersistence.SQL_WORD_NULL);
						} else {
							defineColNameAndType(sqlCol, method, sqlType, column);
							
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
					} else {
						// Não há anotação @Column
						if(YUtilPersistence.isId(method)) {
							defineColNameAndType(idBuilder, method, sqlType, column);
							
							idBuilder.append(YUtilPersistence.SQL_WORD_PRIMARY_KEY);
							if(YUtilPersistence.isAutoincrement(method)) {
								idBuilder.append(YUtilPersistence.SQL_WORD_AUTOINCREMENT);
							}
							idBuilder.append(YUtilPersistence.SQL_WORD_NOT + YUtilPersistence.SQL_WORD_NULL);
						} else {
							// Não há anotação @Column e Nem @Id
							// se houver um método getId, então este será considerado a PK da enidade
							if(method.getName().equals(YUtilPersistence.GET_ID_METHOD_NAME)) {
								defineColNameAndType(idBuilder, method, sqlType, column);
								
								idBuilder.append(YUtilPersistence.SQL_WORD_PRIMARY_KEY);
								idBuilder.append(YUtilPersistence.SQL_WORD_AUTOINCREMENT);
								idBuilder.append(YUtilPersistence.SQL_WORD_NOT + YUtilPersistence.SQL_WORD_NULL);
							}
						}
					}
				} else {
					if(YUtilPersistence.isEntity(returnedType)) {
						final Method[] methodsForeignEntity = returnedType.getMethods();
						Method methodColFK = null;
						for(final Method candidateMethodForFK : methodsForeignEntity) {
							if(candidateMethodForFK.getName().startsWith(YUtilPersistence.GET_PREFIX) 
									&& (YUtilPersistence.isId(candidateMethodForFK)
											|| candidateMethodForFK.getName().equals(YUtilPersistence.GET_ID_METHOD_NAME))
									) {
								methodColFK = candidateMethodForFK;
							}
						}
						if(methodColFK != null) {
							final Column column = method.getAnnotation(Column.class);
							final Class<?> returnedTypeFK = methodColFK.getReturnType();
							final String sqlTypeFK = this.getSqlType(returnedTypeFK);
							if(sqlTypeFK != null) {
								final YProcessedEntity processedEntityFK = getYProcessedEntity(returnedType);
								
								final String fkName = YUtilPersistence.getColumnName(column, methodColFK) + "_" + processedEntityFK.getTableName();
								sqlFK.append(" " + fkName);
								sqlFK.append(" " + sqlTypeFK);
								
								sqlFKConstarint.append(YUtilPersistence.SQL_WORD_FOREIGN_KEY + "(" + fkName+ ") " + YUtilPersistence.SQL_WORD_REFERENCES + processedEntityFK.getTableName() + "(" + processedEntityFK.getIdColumn() + ")");
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
		return sqlCreate;
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			} else if(method.getName().equals(YUtilPersistence.GET_ID_METHOD_NAME)) { // There is neither @Column annotation nor @Id annotation
				yProcessedEntity.setIdColumn(YUtilPersistence.getColumnName(null, method));
				yProcessedEntity.setIdMethod(YUtilPersistence.GET_ID_METHOD_NAME);										
				idIdentificado = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
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