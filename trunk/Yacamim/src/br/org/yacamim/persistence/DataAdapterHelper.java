/**
 * DataAdapterHelper.java
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
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.util.Log;
import br.org.yacamim.util.YUtilReflection;
import br.org.yacamim.util.YUtilString;

/**
 * Classe DataAdapterHelper TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class DataAdapterHelper {

	/**
	 *
	 */
	private DataAdapterHelper() {
		super();
	}


	/**
	 *
	 * @param cursor
	 * @param object
	 * @param getMethod
	 * @param columnName
	 * @throws ParseException
	 */
	public static boolean treatRawData(final Cursor cursor, final Object object, final Method getMethod, final String columnName) throws ParseException {
		boolean rawData = false;
		if(getMethod.getReturnType().equals(String.class)) {
			rawData = true;
			YUtilReflection.setValueToProperty(
					YUtilReflection.getPropertyName(getMethod),
					cursor.getString(cursor.getColumnIndex(columnName)),
					object);
		} else if ((getMethod.getReturnType().equals(Byte.class) || getMethod.getReturnType().equals(byte.class))
				|| (getMethod.getReturnType().equals(Short.class) || getMethod.getReturnType().equals(short.class))) {
			rawData = true;
			YUtilReflection.setValueToProperty(
					YUtilReflection.getPropertyName(getMethod),
					cursor.getShort(cursor.getColumnIndex(columnName)),
					object);
		} else if (getMethod.getReturnType().equals(Integer.class) || getMethod.getReturnType().equals(int.class)) {
			rawData = true;
			YUtilReflection.setValueToProperty(
					YUtilReflection.getPropertyName(getMethod),
					cursor.getInt(cursor.getColumnIndex(columnName)),
					object);
		} else if (getMethod.getReturnType().equals(Long.class) || getMethod.getReturnType().equals(long.class)) {
			rawData = true;
			YUtilReflection.setValueToProperty(
					YUtilReflection.getPropertyName(getMethod),
					cursor.getLong(cursor.getColumnIndex(columnName)),
					object);
		} else if (getMethod.getReturnType().equals(Float.class) || getMethod.getReturnType().equals(float.class)) {
			rawData = true;
			YUtilReflection.setValueToProperty(
					YUtilReflection.getPropertyName(getMethod),
					cursor.getFloat(cursor.getColumnIndex(columnName)),
					object);
		} else if (getMethod.getReturnType().equals(Double.class) || getMethod.getReturnType().equals(double.class)) {
			rawData = true;
			YUtilReflection.setValueToProperty(
					YUtilReflection.getPropertyName(getMethod),
					cursor.getDouble(cursor.getColumnIndex(columnName)),
					object);
		} else if (getMethod.getReturnType().equals(Date.class)) {
			final long time = cursor.getLong(cursor.getColumnIndex(columnName));
			rawData = true;
			if(time > 0) {
				YUtilReflection.setValueToProperty(
						YUtilReflection.getPropertyName(getMethod),
						new Date(time),
						object);
			}
		}
		return rawData;
	}

	/**
	 * @param cursor
	 * @param object
	 * @param getMethod
	 * @param columnName
	 */
	@SuppressWarnings("rawtypes")
	public static void treatOneToOne(final Cursor cursor, final Object object,
			final Method getMethod, final String columnName) {
		try {
			String propertyOwner = "";
			long id = cursor.getLong(cursor.getColumnIndex(columnName));
			@SuppressWarnings("unchecked")
			DefaultDBAdapter<?> defaultDBAdapter = new DefaultDBAdapter(getMethod.getReturnType());
			Object entInstance = defaultDBAdapter.getByID(id);
			YUtilReflection.setValueToProperty(
					propertyOwner = YUtilReflection.getPropertyName(getMethod),
					entInstance,
					object);

			DataAdapterHelper.treatOneToOneOwned(object, propertyOwner, entInstance);

		} catch (Exception e) {
			Log.e("DataAdapterHelper.treatOneToOne", e.getMessage());
		}
	}

	/**
	 * @param object
	 * @param propertyOwner
	 * @param entInstance
	 */
	public static void treatOneToOneOwned(final Object object, final String propertyOwner, final Object entInstance) {
		try {
			final Class<?> entGenericClass = YUtilReflection.getGenericSuperclassClass(entInstance.getClass());

			final List<Method> getMethods = YUtilReflection.getGetMethodList(entGenericClass);
			if(getMethods != null) {
				for(Method getMethod : getMethods) {
					if(DataAdapterHelper.isOneToOneOwnedBy(getMethod, propertyOwner)) {
						YUtilReflection.setValueToProperty(
								YUtilReflection.getPropertyName(getMethod),
								object,
								entInstance);
					}
				}
			}
		} catch (Exception e) {
			Log.e("DataAdapterHelper.treatOneToOneOwned", e.getMessage());
		}
	}


	/**
	 *
	 * @param getMethod
	 * @return
	 */
	public static boolean isOneToOneOwner(final Method getMethod) {
		OneToOne oneToOne = getMethod.getAnnotation(OneToOne.class);
		return oneToOne != null && YUtilString.isEmptyString(oneToOne.mappedBy());
	}

	/**
	 *
	 * @param getMethod
	 * @return
	 */
	public static boolean isOneToOneOwned(final Method getMethod) {
		OneToOne oneToOne = getMethod.getAnnotation(OneToOne.class);
		return oneToOne != null && !YUtilString.isEmptyString(oneToOne.mappedBy());
	}

	/**
	 *
	 * @param getMethod
	 * @param owner
	 * @return
	 */
	public static boolean isOneToOneOwnedBy(final Method getMethod, final String owner) {
		boolean ownedBy = false;
		if(isOneToOneOwned(getMethod)) {
			OneToOne oneToOne = getMethod.getAnnotation(OneToOne.class);
			ownedBy = oneToOne.mappedBy().equals(owner);
		}
		return ownedBy;
	}

	/**
	 *
	 * @param getMethod
	 * @return
	 */
	public static boolean isOneToMany(final Method getMethod) {
		return getMethod.getAnnotation(OneToMany.class) != null;
	}

	/**
	 *
	 * @param getMethod
	 * @return
	 */
	public static boolean isManyToOne(final Method getMethod) {
		return getMethod.getAnnotation(ManyToOne.class) != null;
	}

	/**
	 *
	 * @param getMethod
	 * @return
	 */
	public static boolean isManyToMany(final Method getMethod) {
		return getMethod.getAnnotation(ManyToMany.class) != null;
	}

}
