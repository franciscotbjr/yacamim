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
	
	/**
	 * 
	 */
	public YSQLBuilder() {
		super();
	}
	
	
	private StringBuilder montaSQL(final Class<?> classe) {
		final StringBuilder sqlCreate = new StringBuilder();
		
		if(this.isEntity(classe)) { 
			// avaliar se as classes precisam ser Entity 
			// | Observação: avaliar também se não seria interesamente que as anotações como Column também sejam opcionais-> 
			// bastaria apenas mapear as classes no arquivo XML: qualquer classe.
			// sem anotações, tudo funcionaria no modo deafult
			
			final Method[] methods = classe.getMethods();
			final List<Method> getMethods = new ArrayList<Method>();
			for(Method method : methods) {
				if(method.getName().startsWith("get") && !method.getName().equals("getClass")) {
					getMethods.add(method);
				}
			}
			
			final List<StringBuilder> rows = new ArrayList<StringBuilder>();
			for(final Method method : getMethods) {
				final StringBuilder sqlRow = new StringBuilder();
				final Class<?> returnedType = method.getReturnType();
				final String sqlType = this.getSqlType(returnedType); 
				
				final Column column = method.getAnnotation(Column.class);
				
				//---
				sqlRow.append(" " + this.getColumnName(column, method));
				sqlRow.append(" " + sqlType);
				
				if(this.isText(sqlType) && column != null) {
					sqlRow.append("(" + column.length() + ") ");
				}

				if(column != null) {
					if(this.isId(method)) {
						sqlRow.append(" PRIMARY KEY");
						if(this.isAutoincrement(method)) {
							sqlRow.append(" AUTOINCREMENT");
						}
						sqlRow.append(" NOT NULL");
					} else {
						if(!column.nullable()) {
							sqlRow.append(" NOT");
						}
						sqlRow.append(" NULL");
						if(column.unique()) {
							sqlRow.append(" UNIQUE");
						}
					}
					if(this.isReal(sqlType)) {
//					// cancelado, não será usado nem precision e nem scale
//					// avaliar se os dados ficariam melhores todos com TEXT e tratados no código de forma específica
////					if(column.precision() > 0) {
////						
////					}
////					if(column.scale() > 0) {
////						
////					}
					}
				} else {
					// There is no @Column annotation
					if(this.isId(method)) {
						sqlRow.append(" PRIMARY KEY");
						if(this.isAutoincrement(method)) {
							sqlRow.append(" AUTOINCREMENT");
						}
						sqlRow.append(" NOT NULL");
					} else {
						// There is no @Column annotation neither @Id
						// if, there is a getId method, then it will be taken as the primery key for this entity
						if(method.getName().equals("getId")) {
							sqlRow.append(" PRIMARY KEY");
							sqlRow.append(" AUTOINCREMENT");
							sqlRow.append(" NOT NULL");
						}
					}
				}
				//---
				rows.add(sqlRow);
			}
			for(int i = 0; i < rows.size()-1; i++) {
				rows.get(i).append(", ");
			}
			
			sqlCreate.append("CREATE TABLE");
			sqlCreate.append(" " + this.getTableName(classe) + " (");
			for(StringBuilder row : rows){
				sqlCreate.append(row);
			}
			sqlCreate.append(" ); ");
		}
		return sqlCreate;
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
		if(column != null && column.name() != null && !column.name().trim().equals("")) { // TODO Trocar por isEmptyString
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
		if(table != null && table.name() != null && !table.name().trim().equals("")) { // TODO Trocar por isEmptyString
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
