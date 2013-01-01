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
	
	private static final String SQL_WORD_TABLE = " TABLE ";
	private static final String SQL_WORD_CREATE = " CREATE ";
	private static final String SQL_WORD_REFERENCES = " REFERENCES ";
	private static final String SQL_WORD_FOREIGN_KEY = " FOREIGN KEY ";
	private static final String SQL_WORD_NULL = " NULL ";
	private static final String SQL_WORD_NOT = " NOT ";
	private static final String SQL_WORD_UNIQUE = " UNIQUE ";
	private static final String SQL_WORD_AUTOINCREMENT = " AUTOINCREMENT ";
	private static final String SQL_WORD_PRIMARY_KEY = " PRIMARY KEY ";
	
	private static final String GET_PREFIX = "get";
	private static final String GET_ID_METHOD_NAME = GET_PREFIX + "Id";
	
	
	public static final int NULL = 0;
	public static final int INTEGER = 1;
	public static final int TEXT = 2;
	public static final int REAL = 3;
 	public static final int BLOB = 4;
 	public static final int BOOLEAN = 5;

	public static final String SQL_NULL = "NULL";
	public static final String SQL_INTEGER = "INTEGER";
	public static final String SQL_TEXT = "TEXT";
	public static final String SQL_REAL = "REAL";
	public static final String SQL_BLOB = "BLOB";
	
	private static final Map<Class<?>, String> sqlTypeMap = new HashMap<Class<?>,String>();
	
	static {
		// Integer
		sqlTypeMap.put(byte.class, SQL_INTEGER);
		sqlTypeMap.put(short.class, SQL_INTEGER);
		sqlTypeMap.put(int.class, SQL_INTEGER);
		sqlTypeMap.put(long.class, SQL_INTEGER);
		sqlTypeMap.put(Byte.class, SQL_INTEGER);
		sqlTypeMap.put(Short.class, SQL_INTEGER);
		sqlTypeMap.put(Integer.class, SQL_INTEGER);
		sqlTypeMap.put(Long.class, SQL_INTEGER);
		
		// Text
		sqlTypeMap.put(String.class, SQL_TEXT);
		sqlTypeMap.put(StringBuffer.class, SQL_TEXT);
		sqlTypeMap.put(StringBuilder.class, SQL_TEXT);
		
		// Floating Point
		sqlTypeMap.put(float.class, SQL_REAL);
		sqlTypeMap.put(double.class, SQL_REAL);
		sqlTypeMap.put(Float.class, SQL_REAL);
		sqlTypeMap.put(Double.class, SQL_REAL);
		
		// BLOB
		sqlTypeMap.put(InputStream.class, SQL_BLOB);
		sqlTypeMap.put(byte[].class, SQL_BLOB);
		
		// BLOB
		sqlTypeMap.put(boolean.class, SQL_TEXT);
		sqlTypeMap.put(Boolean.class, SQL_TEXT);
		
		// Date
		sqlTypeMap.put(Date.class, SQL_TEXT);
		
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
				if(this.isEntity(clazz)) {
					final YProcessedEntity yProcessedEntity = new YProcessedEntity();
					yProcessedEntity.setTableName(this.getTableName(clazz));
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
		if(this.isEntity(clazz) && processedEntity != null) { 
			// avaliar se as classes precisam ser Entity 
			// | Observação: avaliar também se não seria interesamente que as anotações como Column também sejam opcionais-> 
			// bastaria apenas mapear as classes no arquivo XML: qualquer classe.
			// sem anotações, tudo funcionaria no modo deafult
			
			final List<Method> getMethods = this.initMethodsGet(clazz);
			
			final StringBuilder id = new StringBuilder();
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
						if(this.isId(method)) {
							defineColNameAndType(id, method, sqlType, column);
							
							id.append(SQL_WORD_PRIMARY_KEY);
							if(this.isAutoincrement(method)) {
								id.append(SQL_WORD_AUTOINCREMENT);
							}
							id.append(SQL_WORD_NOT + SQL_WORD_NULL);
						} else {
							defineColNameAndType(sqlCol, method, sqlType, column);
							
							if(this.isText(sqlType) && column != null) {
								sqlCol.append("(" + column.length() + ") ");
							}
							
							if(!column.nullable()) {
								sqlCol.append(SQL_WORD_NOT);
							}
							sqlCol.append(SQL_WORD_NULL);
							if(column.unique()) {
								sqlCol.append(SQL_WORD_UNIQUE);
							}
						}
					} else {
						// Não há anotação @Column
						if(this.isId(method)) {
							defineColNameAndType(id, method, sqlType, column);
							
							id.append(SQL_WORD_PRIMARY_KEY);
							if(this.isAutoincrement(method)) {
								id.append(SQL_WORD_AUTOINCREMENT);
							}
							id.append(SQL_WORD_NOT + SQL_WORD_NULL);
						} else {
							// Não há anotação @Column e Nem @Id
							// se houver um método getId, então este será considerado a PK da enidade
							if(method.getName().equals(GET_ID_METHOD_NAME)) {
								defineColNameAndType(id, method, sqlType, column);
								
								id.append(SQL_WORD_PRIMARY_KEY);
								id.append(SQL_WORD_AUTOINCREMENT);
								id.append(SQL_WORD_NOT + SQL_WORD_NULL);
							}
						}
					}
				} else {
					if(this.isEntity(returnedType)) {
						final Method[] methodsForeignEntity = returnedType.getMethods();
						Method methodColFK = null;
						for(final Method candidateMethodForFK : methodsForeignEntity) {
							if(candidateMethodForFK.getName().startsWith(GET_PREFIX) 
									&& (this.isId(candidateMethodForFK)
											|| candidateMethodForFK.getName().equals(GET_ID_METHOD_NAME))
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
								
								final String fkName = this.getColumnName(column, methodColFK) + "_" + processedEntityFK.getTableName();
								sqlFK.append(" " + fkName);
								sqlFK.append(" " + sqlTypeFK);
								
								sqlFKConstarint.append(SQL_WORD_FOREIGN_KEY + "(" + fkName+ ") " + SQL_WORD_REFERENCES + processedEntityFK.getTableName() + "(" + processedEntityFK.getIdColumn() + ")");
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
			cols.add(0, id);
			cols.addAll(fkConstarints);
			for(int i = 0; i < cols.size()-1; i++) {
				cols.get(i).append(", ");
			}
			
			sqlCreate.append(SQL_WORD_CREATE + SQL_WORD_TABLE);
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
		builder.append(" " + this.getColumnName(column, method));
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
			if(column != null && this.isId(method)) { // There is @Column annotation and there is @Id annotation
				yProcessedEntity.setIdColumn(this.getColumnName(column, method));
				yProcessedEntity.setIdMethod(method.getName());
				idIdentificado = true;
			} else if(this.isId(method)) { // There is no @Column annotation, yet there is @Id annotation
				yProcessedEntity.setIdColumn(this.getColumnName(null, method));
				yProcessedEntity.setIdMethod(method.getName());	
				idIdentificado = true;
			} else if(method.getName().equals(GET_ID_METHOD_NAME)) { // There is neither @Column annotation nor @Id annotation
				yProcessedEntity.setIdColumn(this.getColumnName(null, method));
				yProcessedEntity.setIdMethod(GET_ID_METHOD_NAME);										
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
	
	/**
	 * 
	 * @param column
	 * @param method
	 * @return
	 */
	private String getColumnName(final Column column, final Method method) {
		if(column != null && !YUtilString.isEmptyString(column.name())) { 
			return column.name();
		}
		return this.toColumnName(method.getName());
	}
	
	/**
	 * 
	 * @param column
	 * @param method
	 * @return
	 */
	private String getTableName(final Class<?> classe) {
		final Table table = classe.getAnnotation(Table.class);
		if(table != null && !YUtilString.isEmptyString(table.name())) { 
			return table.name();
		}
		return classe.getSimpleName();
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	private String toColumnName(final String name) {
		if(!YUtilString.isEmptyString(name)) {
			return name.replaceFirst(GET_PREFIX, "");
		}
		return name;
	}
	
	/**
	 * 
	 * @param sqlType
	 * @return
	 */
	private boolean isText(final String sqlType) {
		if(sqlType == null) {
			return false;
		}
		return SQL_TEXT.equals(sqlType);
	}

	/**
	 * 
	 * @param sqlType
	 * @return
	 */
	private boolean isReal(final String sqlType) {
		if(sqlType == null) {
			return false;
		}
		return SQL_REAL.equals(sqlType);
	}

	/**
	 * 
	 * @param method
	 * @return
	 */
	private boolean isId(final Method method) {
		return method.getAnnotation(Id.class) != null;
	}

	/**
	 * 
	 * @param method
	 * @return
	 */
	private boolean isAutoincrement(final Method method) {
		return method.getAnnotation(Id.class).autoincrement();
	}
	
	/**
	 * 
	 * @param classe
	 * @return
	 */
	private boolean isEntity(final Class<?> classe) {
		return classe.getAnnotation(Entity.class) != null;
	}

}
