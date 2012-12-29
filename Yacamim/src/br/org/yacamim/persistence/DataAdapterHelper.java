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
	 * @param _cursor
	 * @param _object
	 * @param _getMethod
	 * @param _columnName
	 * @throws ParseException
	 */
	public static boolean treatRawData(final Cursor _cursor, Object _object, final Method _getMethod, final String _columnName) throws ParseException {
		boolean rawData = false;
		if(_getMethod.getReturnType().equals(String.class)) {
			rawData = true;
			YUtilReflection.setValueToProperty(
					YUtilReflection.getPropertyName(_getMethod),
					_cursor.getString(_cursor.getColumnIndex(_columnName)),
					_object);
		} else if ((_getMethod.getReturnType().equals(Byte.class) || _getMethod.getReturnType().equals(byte.class))
				|| (_getMethod.getReturnType().equals(Short.class) || _getMethod.getReturnType().equals(short.class))) {
			rawData = true;
			YUtilReflection.setValueToProperty(
					YUtilReflection.getPropertyName(_getMethod),
					_cursor.getShort(_cursor.getColumnIndex(_columnName)),
					_object);
		} else if (_getMethod.getReturnType().equals(Integer.class) || _getMethod.getReturnType().equals(int.class)) {
			rawData = true;
			YUtilReflection.setValueToProperty(
					YUtilReflection.getPropertyName(_getMethod),
					_cursor.getInt(_cursor.getColumnIndex(_columnName)),
					_object);
		} else if (_getMethod.getReturnType().equals(Long.class) || _getMethod.getReturnType().equals(long.class)) {
			rawData = true;
			YUtilReflection.setValueToProperty(
					YUtilReflection.getPropertyName(_getMethod),
					_cursor.getLong(_cursor.getColumnIndex(_columnName)),
					_object);
		} else if (_getMethod.getReturnType().equals(Float.class) || _getMethod.getReturnType().equals(float.class)) {
			rawData = true;
			YUtilReflection.setValueToProperty(
					YUtilReflection.getPropertyName(_getMethod),
					_cursor.getFloat(_cursor.getColumnIndex(_columnName)),
					_object);
		} else if (_getMethod.getReturnType().equals(Double.class) || _getMethod.getReturnType().equals(double.class)) {
			rawData = true;
			YUtilReflection.setValueToProperty(
					YUtilReflection.getPropertyName(_getMethod),
					_cursor.getDouble(_cursor.getColumnIndex(_columnName)),
					_object);
		} else if (_getMethod.getReturnType().equals(Date.class)) {
			final long time = _cursor.getLong(_cursor.getColumnIndex(_columnName));
			rawData = true;
			if(time > 0) {
				YUtilReflection.setValueToProperty(
						YUtilReflection.getPropertyName(_getMethod),
						new Date(time),
						_object);
			}
		}
		return rawData;
	}

	/**
	 * @param _cursor
	 * @param _object
	 * @param _getMethod
	 * @param _columnName
	 */
	@SuppressWarnings("rawtypes")
	public static void treatOneToOne(final Cursor _cursor, Object _object,
			final Method _getMethod, final String _columnName) {
		try {
			String propertyOwner = "";
			long id = _cursor.getLong(_cursor.getColumnIndex(_columnName));
			Class<?> tipo = _getMethod.getReturnType();
			DefaultDBAdapter<?> defaultDBAdapter = new DefaultDBAdapter();
			Object entInstance = defaultDBAdapter.getByID(id, tipo);
			YUtilReflection.setValueToProperty(
					propertyOwner = YUtilReflection.getPropertyName(_getMethod),
					entInstance,
					_object);

			DataAdapterHelper.treatOneToOneOwned(_object, propertyOwner, entInstance);

		} catch (Exception _e) {
			Log.e("DataAdapterHelper.treatOneToOne", _e.getMessage());
		}
	}

	/**
	 * @param _object
	 * @param _propertyOwner
	 * @param _entInstance
	 */
	public static void treatOneToOneOwned(Object _object, String _propertyOwner, Object _entInstance) {
		try {
			final Class<?> entGenericClass = YUtilReflection.getGenericSuperclassClass(_entInstance.getClass());

			final List<Method> getMethods = YUtilReflection.getGetMethodList(entGenericClass);
			if(getMethods != null) {
				for(Method getMethod : getMethods) {
					if(DataAdapterHelper.isOneToOneOwnedBy(getMethod, _propertyOwner)) {
						YUtilReflection.setValueToProperty(
								YUtilReflection.getPropertyName(getMethod),
								_object,
								_entInstance);
					}
				}
			}
		} catch (Exception _e) {
			Log.e("DataAdapterHelper.treatOneToOneOwned", _e.getMessage());
		}
	}


	/**
	 *
	 * @param getMethod
	 * @return
	 */
	public static boolean isOneToOneOwner(Method getMethod) {
		OneToOne oneToOne = getMethod.getAnnotation(OneToOne.class);
		return oneToOne != null && YUtilString.isEmptyString(oneToOne.mappedBy());
	}

	/**
	 *
	 * @param getMethod
	 * @return
	 */
	public static boolean isOneToOneOwned(Method getMethod) {
		OneToOne oneToOne = getMethod.getAnnotation(OneToOne.class);
		return oneToOne != null && !YUtilString.isEmptyString(oneToOne.mappedBy());
	}

	/**
	 *
	 * @param getMethod
	 * @param _owner
	 * @return
	 */
	public static boolean isOneToOneOwnedBy(Method getMethod, String _owner) {
		boolean ownedBy = false;
		if(isOneToOneOwned(getMethod)) {
			OneToOne oneToOne = getMethod.getAnnotation(OneToOne.class);
			ownedBy = oneToOne.mappedBy().equals(_owner);
		}
		return ownedBy;
	}

	/**
	 *
	 * @param getMethod
	 * @return
	 */
	public static boolean isOneToMany(Method getMethod) {
		return getMethod.getAnnotation(OneToMany.class) != null;
	}

	/**
	 *
	 * @param getMethod
	 * @return
	 */
	public static boolean isManyToOne(Method getMethod) {
		return getMethod.getAnnotation(ManyToOne.class) != null;
	}

	/**
	 *
	 * @param getMethod
	 * @return
	 */
	public static boolean isManyToMany(Method getMethod) {
		return getMethod.getAnnotation(ManyToMany.class) != null;
	}

	/**
	 *
	 * @param getMethod
	 * @return
	 */
	public static boolean isTransiente(Method getMethod) {
		return getMethod.getAnnotation(Transiente.class) != null;
	}

}