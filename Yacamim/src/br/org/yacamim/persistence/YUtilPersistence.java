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
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.util.Log;
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
//	public static final String GET_ID_METHOD_NAME = GET_PREFIX + "Id";

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
	static final String SQL_WORD_SELECT = " SELECT ";
	static final String SQL_WORD_DISTINCT = " DISTINCT ";
	static final String SQL_WORD_FROM = " FROM ";
	static final String SQL_WORD_WHERE = " WHERE ";
	static final String SQL_WORD_AND = " AND ";
	static final String SQL_WORD_OR = " OR ";
	static final String SQL_WORD_MAX = " MAX ";

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
			if(isId(method)) {
				return method;
			}
		}
		return null;
	}
	

	/**
	 *
	 * @param type
	 * @return
	 */
	static Method getGetIdMethod(final Class<?> type) {
		Method idMethod = null;
		try {
			final List<Method> getMethods = YUtilReflection.getGetMethodList(type);

			for(final Method getMethod : getMethods) {
				Id id = getMethod.getAnnotation(Id.class);
				if(id != null) {
					idMethod = getMethod;
					break;
				}
			}
		} catch (Exception e) {
			Log.e("YUtilPersistence.getIdGetMethod", e.getMessage());
		}
		return idMethod;
	}

	/**
	 * 
	 * @param getMethods
	 * @return
	 */
	static String getGetIdColumnName(final List<Method> getMethods) {
		String idMethodName = null;
		for(Method method : getMethods) {
			if(isId(method)) {
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
	 * @param classType
	 * @return
	 */
	static String getIdColumnName(final Class<?> classType) {
		String idColumnName = null;
		try {
			final Method getMethod = YUtilPersistence.getGetIdMethod(classType);
			
			idColumnName = YUtilPersistence.getColumnName(getMethod.getAnnotation(Column.class), getMethod);
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getIdColumnName", e.getMessage());
		}
		return idColumnName;
	}
	
	/**
	 * 
	 * @param method
	 * @return
	 */
	static String getColumnName(final Method method) {
		return method.getAnnotation(Column.class).name();
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
	 * @throws YCacheProcessedEntityTerminatedException 
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
			String nomeColuna = YUtilPersistence.getColumnName(column, getMethod);
			if(YUtilPersistence.isEntity(getMethod.getReturnType())) {
				final Class<?> entityType = getMethod.getReturnType();
				final Method idGetMethod = YUtilPersistence.getGetIdMethod(entityType);
				nomeColuna = YUtilPersistence.getColumnName(idGetMethod.getAnnotation(Column.class), idGetMethod) + "_" + YUtilPersistence.getTableName(entityType);
			} else {
				nomeColuna = YUtilPersistence.getColumnName(column, getMethod);
			}
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
	
	/**
	 * 
	 * @param classType
	 * @return
	 */
	static Method[] getColumnGetMethodListSortedByNameAsArray(Class<?> classType) {
		try {
			final List<Method> getMethods = YUtilReflection.getGetMethodListSortedByName(classType);
			final List<Method> columnGetMethods = new ArrayList<Method>();
			for(final Method method : getMethods) {
				if(method.getAnnotation(Column.class) != null
						|| YUtilPersistence.isId(method)) {
					columnGetMethods.add(method);
				}
			}
			final Method[] sortedGetMethods = new Method[columnGetMethods.size()];
			for(int i = 0; i < columnGetMethods.size(); i++) {
				sortedGetMethods[i] = columnGetMethods.get(i);
			}
			return sortedGetMethods;
		} catch(Exception e) {
			Log.e("YUtilPersistence.getColumnGetMethodListSortedByNameAsArray", e.getMessage());
			return new Method[]{};
		}
	}
	

	/**
	 * 
	 * @param targetMethod
	 * @return
	 */
	static String builColumnName(final Method targetMethod) {
		final Class<?> returnType = targetMethod.getReturnType();
		final Column column = targetMethod.getAnnotation(Column.class);
		String columnName = null;
		if(YUtilPersistence.isEntity(returnType)) {
			final Method getFkMethod = YUtilPersistence.getGetIdMethod(returnType);
			if(getFkMethod != null) {
				columnName = buildFkColumnName(returnType, column, getFkMethod);
			}
		} else {
			columnName = YUtilPersistence.getColumnName(column, targetMethod);
		}
		return columnName;
	}

	/**
	 * 
	 * @param returnType
	 * @param column
	 * @param getFkMethod
	 * @return
	 */
	static String buildFkColumnName(final Class<?> returnType, final Column column, final Method getFkMethod) {
		return YUtilPersistence.getColumnName(column, getFkMethod) + "_" + YUtilPersistence.getTableName(returnType);
	}
	
	/**
	 * 
	 * @param referencedTypeGetMethods
	 * @param ownerType
	 * @param ownerGetMethod
	 * @return
	 */
	static boolean hashBidirectionalOneToOneItem(final Method[] referencedTypeGetMethods, final Class<?> ownerType, final Method ownerGetMethod) {
		boolean methodReferenceToWoner = false;
		for(final Method candidateMethodReferenceToWoner : referencedTypeGetMethods) {
			if(isInvalidBidirectionalOneToOneReferenceMethod(candidateMethodReferenceToWoner, ownerType, ownerGetMethod)) {
				methodReferenceToWoner = true;
				break;
			}
		}
		return methodReferenceToWoner;
	}
	
	/**
	 * 
	 * @param ownedTypeGetMethods
	 * @param ownerType
	 * @param ownerGetMethod
	 * @return
	 */
	static Method getBidirectionalManyToManyOwnedMethod(final Class<?> ownedType, final Class<?> ownerType, final Method ownerGetMethod) {
		Method ownedMethod = null;
		final Method[] ownedTypeGetMethods = YUtilReflection.getGetMethodArray(ownedType);
		if(ownedTypeGetMethods != null) {
			for(final Method candidateOwnedMethod : ownedTypeGetMethods) {
				if(YUtilPersistence.isBidirectionalManyToManyOwnedMethod(ownedType, candidateOwnedMethod, ownerType, ownerGetMethod)) {
					ownedMethod = candidateOwnedMethod;
					break;
				}
			}
		}
		return ownedMethod;
	}

	/**
	 * 
	 * @param ownedTypeGetMethods
	 * @param ownerType
	 * @param ownerGetMethod
	 * @return
	 */
	static Method getBidirectionalOneToOneOwnedMethod(final Method[] ownedTypeGetMethods, final Class<?> ownerType, final Method ownerGetMethod) {
		Method ownedMethod = null;
		for(final Method candidateOwnedMethod : ownedTypeGetMethods) {
			if(YUtilPersistence.isBidirectionalOneToOneOwnedMethod(candidateOwnedMethod, ownerType, ownerGetMethod)) {
				ownedMethod = candidateOwnedMethod;
				break;
			}
		}
		return ownedMethod;
	}

	/**
	 * 
	 * @param ownedTypeGetMethods
	 * @param ownerType
	 * @param ownerGetMethod
	 * @return
	 */
	static Method getInvalidBidirectionalOneToOneOwnedMethod(final Method[] ownedTypeGetMethods, final Class<?> ownerType, final Method ownerGetMethod) {
		Method ownedMethod = null;
		for(final Method candidateOwnedMethod : ownedTypeGetMethods) {
			if(YUtilPersistence.isInvalidBidirectionalOneToOneReferenceMethod(candidateOwnedMethod, ownerType, ownerGetMethod)) {
				ownedMethod = candidateOwnedMethod;
				break;
			}
		}
		return ownedMethod;
	}

	/**
	 * 
	 * @param ownerTypeGetMethods
	 * @param referencedType
	 * @param referencedGetMethod
	 * @return
	 */
	static Method getBidirectionalOneToOneOwnerMethod(final Method[] ownerTypeGetMethods, final Class<?> referencedType, final Method referencedGetMethod) {
		Method methodReferenceToReferenced = null;
		for(final Method candidateMethodWonerToReference : ownerTypeGetMethods) {
			if(YUtilPersistence.isBidirectionalOneToOneOwnerMethod(candidateMethodWonerToReference, referencedType, referencedGetMethod)) {
				methodReferenceToReferenced = candidateMethodWonerToReference;
				break;
			}
		}
		return methodReferenceToReferenced;
	}
	
	/**
	 * 
	 * @param method
	 * @param ownerType
	 * @param ownerGetMethod
	 * @return
	 */
	static boolean isBidirectionalOneToOneOwnedMethod(final Method method, final Class<?> ownerType, final Method ownerGetMethod) {
		OneToOne  oneToOne = null;
		return (((oneToOne = method.getAnnotation(OneToOne.class)) != null
						&& !YUtilString.isEmptyString(oneToOne.mappedBy())
						&& oneToOne.mappedBy().equals(YUtilReflection.getPropertyName(ownerGetMethod)))
				&& method.getReturnType().equals(ownerType));
	}

	/**
	 * 
	 * @param ownedType
	 * @param method
	 * @param ownerType
	 * @param ownerGetMethod
	 * @return
	 */
	static boolean isBidirectionalManyToManyOwnedMethod(final Class<?> ownedType, final Method method, final Class<?> ownerType, final Method ownerGetMethod) {
		ManyToMany  manyToMany = null;
		return (((manyToMany = method.getAnnotation(ManyToMany.class)) != null
				&& !YUtilString.isEmptyString(manyToMany.mappedBy())
				&& manyToMany.mappedBy().equals(YUtilReflection.getPropertyName(ownerGetMethod)))
				&& YUtilReflection.getGenericType(ownedType, method).equals(ownerType));
	}

	/**
	 * 
	 * @param method
	 * @param ownerType
	 * @param ownerGetMethod
	 * @return
	 */
	static boolean isInvalidBidirectionalOneToOneReferenceMethod(final Method method, final Class<?> ownerType, final Method ownerGetMethod) {
		OneToOne  oneToOneOwner = null;
		return (((oneToOneOwner = method.getAnnotation(OneToOne.class)) != null)
				&& YUtilString.isEmptyString(oneToOneOwner.mappedBy())
				&& method.getReturnType().equals(ownerType));
	}

	/**
	 * 
	 * @param method
	 * @return
	 */
	static boolean isInvalidBidirectionalOneToOneOwnerMethod(final Method method) {
		OneToOne  oneToOneOwner = null;
		return (((oneToOneOwner = method.getAnnotation(OneToOne.class)) != null)
				&& YUtilString.isEmptyString(oneToOneOwner.mappedBy()));
	}

	/**
	 * 
	 * @param method
	 * @param referencedType
	 * @param referencedGetMethod
	 * @return
	 */
	static boolean isBidirectionalOneToOneOwnerMethod(final Method method, final Class<?> referencedType, final Method referencedGetMethod) {
		OneToOne  oneToOneOwner = null;
		return (method.getAnnotation(Column.class) != null
				&& ((oneToOneOwner = method.getAnnotation(OneToOne.class)) != null
				&& YUtilString.isEmptyString(oneToOneOwner.mappedBy()))
				&& method.getReturnType().equals(referencedType));
	}
	
	/**
	 * 
	 * @param oneToOne
	 * @return
	 */
	static boolean isOneToOneOwner(final OneToOne  oneToOne) {
		return (YUtilString.isEmptyString(oneToOne.mappedBy()));
	}
	
	/**
	 * 
	 * @param manyToMany
	 * @return
	 */
	static boolean isManyToManyOwner(final ManyToMany  manyToMany) {
		return (YUtilString.isEmptyString(manyToMany.mappedBy()));
	}

	/**
	 * 
	 * @param classType
	 * @return
	 */
	public static List<Method> getGetColumnMethodList(final Class<?> classType) {
		try {
			final List<Method> getMethods = new ArrayList<Method>();

			final Method[] methods = classType.getMethods();

			for(final Method method : methods) {
				if(method.getName().startsWith(YUtilReflection.PREFIX_GET) 
						&& !method.getName().equals(YUtilReflection.GET_CLASS_METHOD_NAME)
						&& (method.getAnnotation(Column.class) != null
						|| YUtilPersistence.isId(method))) {
					getMethods.add(method);
				}
			}
			return getMethods;
		}
		catch(Exception e) {
			Log.e("YUtilReflection.getGetColumnMethodList", e.getMessage());
			return new ArrayList<Method>();
		}
	}

	/**
	 * 
	 * @param classType
	 * @param attributes
	 * @return
	 */
	public static List<Method> getGetColumnMethodList(final Class<?> classType, final String[] attributes) {
		try {
			final List<Method> getMethods = new ArrayList<Method>();
			
			final Method[] methods = classType.getMethods();
			final List<String> getMethodNames = YUtilReflection.getGetMethodNames(attributes);
			
			for(final Method method : methods) {
				if(method.getName().startsWith(YUtilReflection.PREFIX_GET) 
						&& !method.getName().equals(YUtilReflection.GET_CLASS_METHOD_NAME)
						&& getMethodNames.contains(method.getName())
						&& (method.getAnnotation(Column.class) != null
								|| YUtilPersistence.isId(method))) {
					getMethods.add(method);
				}
			}
			return getMethods;
		}
		catch(Exception e) {
			Log.e("YUtilReflection.getGetColumnMethodList", e.getMessage());
			return new ArrayList<Method>();
		}
	}
	
	/**
	 * 
	 * @param getMethods
	 * @return
	 */
	public static List<Method> filterManyToOneMethods(final List<Method> getMethods) {
		final List<Method> manyToOnemethods = new ArrayList<Method>();
		if(getMethods != null) {
			for(final Method method : getMethods) {
				if(method.getAnnotation(ManyToOne.class) != null) {
					manyToOnemethods.add(method);
				}
			}
		}
		return manyToOnemethods;
	}

}