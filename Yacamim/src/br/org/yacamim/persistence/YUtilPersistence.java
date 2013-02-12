/**
 * YUtilPersistence.java
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

import java.lang.reflect.Method;
import java.util.List;

import android.annotation.SuppressLint;
import br.org.yacamim.util.YUtilReflection;
import br.org.yacamim.util.YUtilString;

/**
 * 
 * Class YUtilPersistence TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
final class YUtilPersistence {
	
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
	
	public static final String GET_PREFIX = "get";
	public static final String GET_ID_METHOD_NAME = GET_PREFIX + "Id";

	public static final String SQL_WORD_INSERT_INTO = " INSERT INTO ";
	public static final String SQL_WORD_VALUES = " VALUES ";

	static final String SQL_WORD_TABLE = " TABLE ";
	static final String SQL_WORD_CREATE = " CREATE ";
	static final String SQL_WORD_REFERENCES = " REFERENCES ";
	static final String SQL_WORD_FOREIGN_KEY = " FOREIGN KEY ";
	static final String SQL_WORD_NULL = " NULL ";
	static final String SQL_WORD_NOT = " NOT ";
	static final String SQL_WORD_UNIQUE = " UNIQUE ";
	static final String SQL_WORD_AUTOINCREMENT = " AUTOINCREMENT ";
	static final String SQL_WORD_PRIMARY_KEY = " PRIMARY KEY ";

	/**
	 * 
	 */
	private YUtilPersistence() {
		super();
	}
	
	
	/**
	 * 
	 * @param sqlType
	 * @return
	 */
	static boolean isText(final String sqlType) {
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
	static boolean isReal(final String sqlType) {
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
	static boolean isId(final Method method) {
		return method.getAnnotation(Id.class) != null;
	}

	/**
	 * 
	 * @param method
	 * @return
	 */
	static boolean isAutoincrement(final Method method) {
		return method.getAnnotation(Id.class).autoincrement();
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	static boolean isEntity(final Class<?> clazz) {
		return clazz.getAnnotation(Entity.class) != null;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	static Method getGetIdMethod(final List<Method> getMethods) {
		for(Method method : getMethods) {
			if(isId(method) || method.getName().equals(YUtilPersistence.GET_ID_METHOD_NAME)) {
				return method;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param getMethods
	 * @return
	 */
	static String getGetIdColumnName(final List<Method> getMethods) {
		String idMethodName = null;
		for(Method method : getMethods) {
			if(isId(method) || method.getName().equals(YUtilPersistence.GET_ID_METHOD_NAME)) {
				final Column column = method.getAnnotation(Column.class);
				idMethodName = getColumnName(column, method);
				break;
			}
		}
		return idMethodName;
	}

	/**
	 * 
	 * @param idMethod
	 * @return
	 */
	static String getGetIdColumnName(final Method idMethod) {
		final Column column = idMethod.getAnnotation(Column.class);
		return getColumnName(column, idMethod);
	}
	

	/**
	 * 
	 * @param column
	 * @param method
	 * @return
	 */
	static String getColumnName(final Column column, final Method method) {
		if(column != null && !YUtilString.isEmptyString(column.name())) { 
			return column.name();
		}
		return toColumnName(method.getName());
	}
	
	/**
	 * 
	 * @param column
	 * @param method
	 * @return
	 */
	static String getTableName(final Class<?> classe) {
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
	static String toColumnName(final String name) {
		if(!YUtilString.isEmptyString(name)) {
			return name.replaceFirst(GET_PREFIX, "");
		}
		return name;
	}
	
	/**
	 * 
	 * @param dbLoad
	 * @param row
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	static StringBuilder convertToColumnNames(final DbLoad dbLoad, final String row) {
		int start = row.toUpperCase().indexOf(YUtilPersistence.SQL_WORD_VALUES);
		String strCols = row.substring(0, start).trim();
		strCols = strCols.substring(1, strCols.length() - 1);
		final String[] arrCols = strCols.split("[\\,]");
		final StringBuilder builderCols = new StringBuilder();
		int count = 0;
		for(final String colName : arrCols) {
			final Method getMethod = YUtilReflection.getGetMethod(YUtilReflection.getGetMethodName(colName.trim()), dbLoad.getEntity());
			final Column column = getMethod.getAnnotation(Column.class);
			final String nomeColuna = YUtilPersistence.getColumnName(column, getMethod);
			builderCols.append(nomeColuna);
			if((count + 1) < arrCols.length) {
				builderCols.append(", ");
			}
			count++;
		}
		return builderCols;
	}
	
	/**
	 * 
	 * @param dbLoad
	 * @param row
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	static StringBuilder convertToInsert(final DbLoad dbLoad, final String row) {
		StringBuilder builderInsert = new StringBuilder();
		builderInsert.append(YUtilPersistence.SQL_WORD_INSERT_INTO);
		builderInsert.append(YUtilPersistence.getTableName(dbLoad.getEntity()));
		builderInsert.append(" (");
		builderInsert.append(YUtilPersistence.convertToColumnNames(dbLoad, row));
		builderInsert.append(") ");
		builderInsert.append(row.substring(row.toUpperCase().indexOf(YUtilPersistence.SQL_WORD_VALUES)));
		return builderInsert;
	}

}