/**
 * DataAdapterHelper.java
 *
 * Copyright 2012 yacamim.org.br
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.org.yacamim.persistence;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;

import android.database.Cursor;
import br.org.yacamim.util.UtilDate;
import br.org.yacamim.util.UtilReflection;
import br.org.yacamim.util.UtilString;

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
	public static boolean treatRawData(final Cursor _cursor, Object _object,	final Method _getMethod, final String _columnName) throws ParseException {
		boolean rawData = false;
		if(_getMethod.getReturnType().equals(String.class)) {
			rawData = true;
			UtilReflection.setValueToProperty(
					UtilReflection.getPropertyName(_getMethod), 
					_cursor.getString(_cursor.getColumnIndex(_columnName)), 
					_object);
		} else if (_getMethod.getReturnType().equals(Integer.class) || _getMethod.getReturnType().equals(int.class)) {
			rawData = true;
			UtilReflection.setValueToProperty(
					UtilReflection.getPropertyName(_getMethod), 
					_cursor.getInt(_cursor.getColumnIndex(_columnName)), 
					_object);
		} else if (_getMethod.getReturnType().equals(Double.class) || _getMethod.getReturnType().equals(double.class)) {
			rawData = true;
			UtilReflection.setValueToProperty(
					UtilReflection.getPropertyName(_getMethod), 
					_cursor.getDouble(_cursor.getColumnIndex(_columnName)), 
					_object);
		} else if (_getMethod.getReturnType().equals(Long.class) || _getMethod.getReturnType().equals(long.class)) {
			rawData = true;
			UtilReflection.setValueToProperty(
					UtilReflection.getPropertyName(_getMethod), 
					_cursor.getLong(_cursor.getColumnIndex(_columnName)), 
					_object);
		} else if (_getMethod.getReturnType().equals(Date.class)) {
			final String strDate = _cursor.getString(_cursor.getColumnIndex(_columnName));
			rawData = true;
			if(!UtilString.isEmptyString(strDate)) {
				UtilReflection.setValueToProperty(
						UtilReflection.getPropertyName(_getMethod), 
						UtilDate.getSimpleDateFormatDateTime().parse(strDate), 
						_object);
			}
		}
		return rawData;
	}

	/**
	 * 
	 * @param getMethod
	 * @return
	 */
	public static boolean isOneToOne(Method getMethod) {
		return getMethod.getAnnotation(OneToOne.class) != null;
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
