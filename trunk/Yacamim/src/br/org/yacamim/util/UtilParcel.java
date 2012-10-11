/**
 * UtilParcel.java
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
package br.org.yacamim.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import br.org.yacamim.entity.YWriteToParcel;

/**
 * 
 * Class BaseEntity TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class UtilParcel {

	/**
	 * 
	 */
	private UtilParcel() {
		super();
	}
	
	/**
	 * 
	 * @param yParcel
	 * @param parcel
	 */
	public static void fillValueFromParcel(final YParcel yParcel, final Parcel parcel) {
		try {
			final Object object = Class.forName(parcel.readString()).newInstance();
			fillAttributesFromParcel(object, parcel);
			yParcel.setValue(object);
		} catch (Exception e) {
			Log.e("UtilParcel.fillValueFromParcel", e.getMessage());
		}
	}
	

	/**
	 * 
	 * @param object
	 * @param parcel
	 */
	public static void fillAttributesFromParcel(final Object object, final Parcel parcel) {
		try {
			List<Method> getMethodList = UtilReflection.getGetMethodListSortedByName(object.getClass());
			if(getMethodList != null) {
				for(Method getMethod : getMethodList) {
					String propertyName = UtilReflection.getPropertyName(getMethod);
					if(getMethod.getReturnType().equals(String.class)) {
						UtilReflection.setValueToProperty(
								propertyName, 
								parcel.readString(), 
								object);
					} else if (getMethod.getReturnType().equals(Byte.class) || getMethod.getReturnType().equals(byte.class)) {
						UtilReflection.setValueToProperty(
								propertyName, 
								parcel.readByte(), 
								object);
					} else if (getMethod.getReturnType().equals(Short.class) || getMethod.getReturnType().equals(short.class)) {
						UtilReflection.setValueToProperty(
								propertyName, 
								Short.valueOf(parcel.readInt()+""), 
								object);
					} else if (getMethod.getReturnType().equals(Integer.class) || getMethod.getReturnType().equals(int.class)) {
						UtilReflection.setValueToProperty(
								propertyName, 
								parcel.readInt(), 
								object);
					} else if (getMethod.getReturnType().equals(Long.class) || getMethod.getReturnType().equals(long.class)) {
						UtilReflection.setValueToProperty(
								propertyName, 
								parcel.readLong(), 
								object);
					} else if (getMethod.getReturnType().equals(Float.class) || getMethod.getReturnType().equals(float.class)) {
						UtilReflection.setValueToProperty(
								propertyName, 
								parcel.readFloat(), 
								object);
					} else if (getMethod.getReturnType().equals(Double.class) || getMethod.getReturnType().equals(double.class)) {
						UtilReflection.setValueToProperty(
								propertyName, 
								parcel.readDouble(), 
								object);
					} else if (getMethod.getReturnType().equals(Boolean.class) || getMethod.getReturnType().equals(boolean.class)) {
						UtilReflection.setValueToProperty(
								propertyName, 
								parcel.readInt() == 1, 
								object);
					} else if (getMethod.getReturnType().equals(Date.class)) {
						UtilReflection.setValueToProperty(
								propertyName, 
								new Date(parcel.readLong()), 
								object);
					} else if (getMethod.getReturnType().equals(Bundle.class)) {
						UtilReflection.setValueToProperty(
								propertyName, 
								parcel.readBundle(), 
								object);
					} else if (getMethod.getReturnType().equals(Serializable.class)) {
						UtilReflection.setValueToProperty(
								propertyName, 
								parcel.readSerializable(), 
								object);
					}
					
				}
			}
		} catch (Exception e) {
			Log.e("UtilParcel.fillAttributesFromParcel", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	public static Bundle getAttributesAsBundle(Object object) {
		Bundle bundle = null;
		try {
			final Class<?> clazz = object.getClass();
			List<Method> getMethodList = UtilReflection.getGetMethodListSortedByName(clazz);
			if(getMethodList != null) {
				bundle = new Bundle(clazz.getClassLoader());
				for(Method getMethod : getMethodList) {
					final YWriteToParcel yWriteToParcel =  getMethod.getAnnotation(YWriteToParcel.class);
					if(yWriteToParcel == null || (yWriteToParcel != null && yWriteToParcel.value())) {
						addToBundle(object, bundle, getMethod);
					}
				}
			}
		} catch (Exception e) {
			Log.e("UtilParcel.getAttributesAsBundle", e.getMessage());
			bundle = new Bundle();
		}
		return bundle;
	}


	/**
	 * @param object
	 * @param bundle
	 * @param getMethod
	 * @return
	 * @throws Exception
	 */
	protected static void addToBundle(Object object, Bundle bundle,
			Method getMethod) throws Exception {
		String propertyName = UtilReflection.getPropertyName(getMethod);
		Object propertyValue = UtilReflection.invokeMethodWithoutParams(getMethod, object);
		if(getMethod.getReturnType().equals(String.class)) {
			bundle.putString(propertyName, (String)propertyValue);
		} else if (getMethod.getReturnType().equals(Byte.class) || getMethod.getReturnType().equals(byte.class)) {
			bundle.putByte(propertyName, (Byte)propertyValue);
		} else if (getMethod.getReturnType().equals(Short.class) || getMethod.getReturnType().equals(short.class)) {
			bundle.putShort(propertyName, (Short)propertyValue);
		} else if (getMethod.getReturnType().equals(Integer.class) || getMethod.getReturnType().equals(int.class)) {
			bundle.putInt(propertyName, (Integer)propertyValue);
		} else if (getMethod.getReturnType().equals(Long.class) || getMethod.getReturnType().equals(long.class)) {
			bundle.putLong(propertyName, (Long)propertyValue);
		} else if (getMethod.getReturnType().equals(Float.class) || getMethod.getReturnType().equals(float.class)) {
			bundle.putFloat(propertyName, (Float)propertyValue);
		} else if (getMethod.getReturnType().equals(Double.class) || getMethod.getReturnType().equals(double.class)) {
			bundle.putDouble(propertyName, (Double)propertyValue);
		} else if (getMethod.getReturnType().equals(Boolean.class) || getMethod.getReturnType().equals(boolean.class)) {
			bundle.putBoolean(propertyName, (Boolean)propertyValue);
		} else if (getMethod.getReturnType().equals(Date.class)) {
			Date date = (Date)propertyValue;
			bundle.putLong(propertyName, date.getTime());
		} else if (getMethod.getReturnType().equals(Parcelable.class)) {
			bundle.putParcelable(propertyName, (Parcelable)propertyValue);
		} else if (getMethod.getReturnType().equals(Bundle.class)) {
			bundle.putBundle(propertyName, (Bundle)propertyValue);
		} else if (getMethod.getReturnType().equals(Serializable.class)) {
			bundle.putSerializable(propertyName, (Serializable)propertyValue);
		} else if (getMethod.getReturnType().equals(String[].class)) {
			bundle.putStringArray(propertyName, (String[])propertyValue);
		} else if (getMethod.getReturnType().equals(byte[].class)) {
			bundle.putByteArray(propertyName, (byte[])propertyValue);
		} else if (getMethod.getReturnType().equals(short[].class)) {
			bundle.putShortArray(propertyName, (short[])propertyValue);
		} else if (getMethod.getReturnType().equals(int[].class)) {
			bundle.putIntArray(propertyName, (int[])propertyValue);
		} else if (getMethod.getReturnType().equals(long[].class)) {
			bundle.putLongArray(propertyName, (long[])propertyValue);
		} else if (getMethod.getReturnType().equals(float[].class)) {
			bundle.putFloatArray(propertyName, (float[])propertyValue);
		} else if (getMethod.getReturnType().equals(double[].class)) {
			bundle.putDoubleArray(propertyName, (double[])propertyValue);
		} else if (getMethod.getReturnType().equals(boolean[].class)) {
			bundle.putBooleanArray(propertyName, (boolean[])propertyValue);
		} else if (getMethod.getReturnType().equals(Parcelable[].class)) {
			bundle.putParcelable(propertyName, (Parcelable)propertyValue);
		}
	}

	/**
	 * 
	 * @param object
	 * @param parcel
	 * @return
	 */
	public static Bundle writeToParcel(Object object, Parcel parcel) {
		Bundle bundle = null;
		try {
			final Class<?> clazz = object.getClass();
			List<Method> getMethodList = UtilReflection.getGetMethodListSortedByName(clazz);
			if(getMethodList != null) {
				bundle = new Bundle(clazz.getClassLoader());
				for(Method getMethod : getMethodList) {
					final YWriteToParcel yWriteToParcel =  getMethod.getAnnotation(YWriteToParcel.class);
					if(yWriteToParcel == null || (yWriteToParcel != null && yWriteToParcel.value())) {
						writeToParcel(object, parcel, getMethod);
					}
				}
			}
		} catch (Exception e) {
			Log.e("UtilParcel.getAttributesAsBundle", e.getMessage());
			bundle = new Bundle();
		}
		return bundle;
	}

	/**
	 * 
	 * @param object
	 * @param parcel
	 * @param getMethod
	 * @throws Exception
	 */
	public static void writeToParcel(Object object, Parcel parcel, Method getMethod) throws Exception {
		Object propertyValue = UtilReflection.invokeMethodWithoutParams(getMethod, object);
		if(getMethod.getReturnType().equals(String.class)) {
			parcel.writeString((String)propertyValue);
		} else if (getMethod.getReturnType().equals(Byte.class) || getMethod.getReturnType().equals(byte.class)) {
			parcel.writeByte((Byte)propertyValue);
		} else if (getMethod.getReturnType().equals(Short.class) || getMethod.getReturnType().equals(short.class)) {
			parcel.writeInt((Short)propertyValue);
		} else if (getMethod.getReturnType().equals(Integer.class) || getMethod.getReturnType().equals(int.class)) {
			parcel.writeInt((Integer)propertyValue);
		} else if (getMethod.getReturnType().equals(Long.class) || getMethod.getReturnType().equals(long.class)) {
			parcel.writeLong((Long)propertyValue);
		} else if (getMethod.getReturnType().equals(Float.class) || getMethod.getReturnType().equals(float.class)) {
			parcel.writeFloat((Float)propertyValue);
		} else if (getMethod.getReturnType().equals(Double.class) || getMethod.getReturnType().equals(double.class)) {
			parcel.writeDouble((Double)propertyValue);
		} else if (getMethod.getReturnType().equals(Boolean.class) || getMethod.getReturnType().equals(boolean.class)) {
			parcel.writeInt(((Boolean)propertyValue ? 1 : 0));
		} else if (getMethod.getReturnType().equals(Date.class)) {
			Date date = (Date)propertyValue;
			parcel.writeLong(date.getTime());
		} else if (getMethod.getReturnType().equals(Bundle.class)) {
			parcel.writeBundle((Bundle)propertyValue);
		} else if (getMethod.getReturnType().equals(Serializable.class)) {
			parcel.writeSerializable((Serializable)propertyValue);
		}
	}

}
