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
public class YSQLBuilder {
	
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
	
	// No XML de mapeamento das classes colocar um sinalizar para indicar se o Script está sendo forneciodo ao se deverá ser criado com base nas entidades declaradas.
	
	private List<Method> getMethods;
	
	/**
	 * 
	 */
	public YSQLBuilder() {
		super();
	}
	
	public List<StringBuilder> buildCreateScript(final List<Class<?>> classes) {
		final List<StringBuilder> createScript = new ArrayList<StringBuilder>();
		try {
			this.processEntities(classes);
			
			final YDependencyOrderer yDependencyOrderer = new YDependencyOrderer();
			List<Class<?>> orderedList = yDependencyOrderer.order(classes);
			
			for(Class<?> classe : orderedList) {
				createScript.add(this.montaSQL(classe));
			}
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
					yProcessedEntity.setClasse(clazz);
					yProcessedEntity.setClassName(clazz.getSimpleName());
			
					initMethodsGet(clazz);
					
					for(final Method method : this.getMethods) {
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
			
			this.initMethodsGet(clazz);
			
			final List<StringBuilder> cols = new ArrayList<StringBuilder>();
			final List<StringBuilder> fks = new ArrayList<StringBuilder>();
			for(final Method method : this.getMethods) {
				final StringBuilder sqlCol = new StringBuilder();
				final StringBuilder sqlFK = new StringBuilder();
				final Class<?> returnedType = method.getReturnType();
				final String sqlType = this.getSqlType(returnedType); 
				
				if(sqlType != null) {
					
					final Column column = method.getAnnotation(Column.class);
					
					//---
					sqlCol.append(" " + this.getColumnName(column, method));
					sqlCol.append(" " + sqlType);
					
					if(this.isText(sqlType) && column != null) {
						sqlCol.append("(" + column.length() + ") ");
					}
					
					if(column != null) {
						if(this.isId(method)) {
							sqlCol.append(" PRIMARY KEY");
							if(this.isAutoincrement(method)) {
								sqlCol.append(" AUTOINCREMENT");
							}
							sqlCol.append(" NOT NULL");
						} else {
							if(!column.nullable()) {
								sqlCol.append(" NOT");
							}
							sqlCol.append(" NULL");
							if(column.unique()) {
								sqlCol.append(" UNIQUE");
							}
						}
					} else {
						// Não há anotação @Column
						if(this.isId(method)) {
							sqlCol.append(" PRIMARY KEY");
							if(this.isAutoincrement(method)) {
								sqlCol.append(" AUTOINCREMENT");
							}
							sqlCol.append(" NOT NULL");
						} else {
							// Não há anotação @Column e Nem @Id
							// se houver um método getId, então este será considerado a PK da enidade
							if(method.getName().equals("getId")) {
								sqlCol.append(" PRIMARY KEY");
								sqlCol.append(" AUTOINCREMENT");
								sqlCol.append(" NOT NULL");
							}
						}
					}
				} else {
					if(this.isEntity(returnedType)) {
						final Method[] methodsForeignEntity = returnedType.getMethods();
						Method methodColFK = null;
						for(final Method candidateMethodForFK : methodsForeignEntity) {
							if(candidateMethodForFK.getName().startsWith("get") 
									&& (this.isId(candidateMethodForFK)
											|| candidateMethodForFK.getName().equals("getId"))
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
								sqlCol.append(" " + fkName);
								sqlCol.append(" " + sqlTypeFK);
								
								sqlFK.append(" FOREIGN KEY(" + fkName+ ") REFERENCES " + processedEntityFK.getTableName() + "(" + processedEntityFK.getIdColumn() + ")");
							}
						}
					}
				}
				//---
				if(!YUtilString.isEmptyString(sqlCol)) {
					cols.add(sqlCol);
				}
				if(!YUtilString.isEmptyString(sqlFK)) {
					fks.add(sqlFK);
				}
			}
			cols.addAll(fks);
			for(int i = 0; i < cols.size()-1; i++) {
				cols.get(i).append(", ");
			}
			
			sqlCreate.append("CREATE TABLE");
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
	 * @param classe
	 */
	private void initMethodsGet(final Class<?> classe) {
		if(this.getMethods == null) {
			this.getMethods = YUtilReflection.getGetMethodList(classe);
		}
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
			if(column != null && this.isId(method)) { // há anotação @Column e há anotação @Id
				yProcessedEntity.setIdColumn(this.getColumnName(column, method));
				yProcessedEntity.setIdMethod(method.getName());
				idIdentificado = true;
			} else if(this.isId(method)) { // Não há anotação @Column, mas há anotação @Id
				yProcessedEntity.setIdColumn(this.getColumnName(null, method));
				yProcessedEntity.setIdMethod(method.getName());	
				idIdentificado = true;
			} else if(method.getName().equals("getId")) { // Não há nem @Column e nem @Id
				yProcessedEntity.setIdColumn(this.getColumnName(null, method));
				yProcessedEntity.setIdMethod("getId");										
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
		if(column != null && column.name() != null && !column.name().trim().equals("")) { 
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
		if(table != null && table.name() != null && !table.name().trim().equals("")) { 
			return table.name();
		}
		return this.toTableName(classe.getSimpleName());
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	private String toColumnName(final String name) {
		return name; // TODO Realizar transformação 
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	private String toTableName(final String name) {
		return name; // TODO Realizar transformação 
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
