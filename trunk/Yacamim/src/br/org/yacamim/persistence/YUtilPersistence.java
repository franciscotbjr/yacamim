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
			if(isId(method) || method.getName().equals(YSQLBuilder.GET_ID_METHOD_NAME)) {
				return method;
			}
		}
		return null;
	}

}