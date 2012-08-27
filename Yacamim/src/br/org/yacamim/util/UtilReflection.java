/**
 * UtilReflection.java
 *
 * Copyright 2011 yacamim.org.br
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.Log;


/**
 * 
 * Class UtilReflection TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public strictfp abstract class UtilReflection {
	
	/**
	 * 
	 */
	private static final String REGEX_POINT_SEPARATOR = "[.]";

	/**
	 * 
	 */
	private static final String GET_CLASS_METHOD_NAME = "getClass";

	/**
	 * 
	 */
	private static final String REGEX_ACCESSORS_GET_SET_IS = "get|set|is";

	/**
	 * 
	 */
	private static final String CHARACTER_S = "s";

	/**
	 * 
	 */
	private static final String REGEX_FIRST_CHARACTER_G = "[g]";

	/**
	 * 
	 */
	private static final String CHARACTER_G = "g";

	/**
	 * 
	 */
	private static final String REGEX_FIRST_CHARACTER_S = "[s]";

	/**
	 * 
	 */
	private static final String PREFIX_SET = "set";

	/**
	 * 
	 */
	private static final String PREFIX_IS = "is";

	/**
	 * 
	 */
	private static final String PREFIX_GET = "get";

	/**
	 * 
	 */
	public static final String SINGLETON_ACESS_METHOD_NAME = "getInstance";

	/**
	 * 
	 */
	protected static final Object[] deafultParamArrayObjectReflection = new Object[]{};

	/**
	 * 
	 */
	protected static final Class<?>[] defaultParamArrayClassReflection = new Class[]{};

	/**
	 * 
	 *
	 */
	private UtilReflection() {
		super();
	}

	/**
	 * 
	 * @param _method
	 * @param _objectoTo
	 * @param _methodParams
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethod(Method _method, Object _objectoTo, Object... _methodParams) throws Exception {
		return _method.invoke(_objectoTo, _methodParams);
	}

	/**
	 * 
	 * @param _method
	 * @param _objectTo
	 * @param _methodParams
	 * @return
	 */
	public static String invokeMethodToString(Method _method, Object _objectTo, Object[] _methodParams) {
		try {
			return _method.invoke(_objectTo, _methodParams).toString();
		}
		catch(Exception _e) {
			Log.e("UtilReflection.invokeMethodToString", _e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @param _propertyName
	 * @param _object
	 * @return
	 */
	public static Object getPropertyValue(String _propertyName, Object _object) {
		try {
			Object result  = null;
			if(_propertyName.indexOf('.') == -1) {
				result = UtilReflection.invokeMethodWithoutParams(
								UtilReflection.getGetMethod(
										UtilReflection.getGetMethodName(_propertyName)
									, _object.getClass()), _object);
			} else {
				String[] properties = _propertyName.split(REGEX_POINT_SEPARATOR);
				Object current = _object;
				for(String propertie : properties) {
					current = getPropertyValue(propertie, current);
				}
				result = current;
			}
			return result;
		} catch(Exception _e) {
			Log.e("UtilReflection.getPropertyValue", _e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @param _propertyName
	 * @param _propertyValue
	 * @param _object
	 */
	public static void setValueToProperty(String _propertyName, Object _propertyValue, Object _object) {
		Class<?> parameterClass = null;
		try {
			if (_propertyValue != null) {
				if(_propertyName.indexOf('.') == -1) {
					if (_propertyValue.getClass().equals(Long.class)) {
						parameterClass = long.class;
					} else if (_propertyValue.getClass().equals(Double.class)) {
						parameterClass = double.class;
					} else if (_propertyValue.getClass().equals(Integer.class)) {
						parameterClass = int.class;
					} else {
						parameterClass = _propertyValue.getClass();
					}
	
					UtilReflection.invokeMethod(
							UtilReflection.getSetMethod(
									UtilReflection.getSetMethodName(_propertyName)
								, _object.getClass(), new Class<?>[] {parameterClass}), _object, _propertyValue);
				} else {
					String recoveredProperty = _propertyName.substring(0, _propertyName.lastIndexOf(REGEX_POINT_SEPARATOR));
					Object currentObject = getPropertyValue(recoveredProperty, _object);
					
					String propertyAttributes = _propertyName.substring(_propertyName.lastIndexOf(REGEX_POINT_SEPARATOR)+1);
					
					setValueToProperty(propertyAttributes, _propertyValue, currentObject);
				}
			}
		} catch(Exception _e) {
			Log.e("UtilReflection.setValueToProperty", _e.getMessage());
		}
	}

	/**
	 * 
	 * @param _classType
	 * @return
	 */
	public static List<Method> getSetMethodList(Class<?> _classType) {
		try {
			List<Method> setMethods = new ArrayList<Method>();

			Method[] methods = _classType.getMethods();

			for(Method method : methods) {
				if(method.getName().startsWith(PREFIX_SET)) {
					setMethods.add(method);
				}
			}

			return setMethods;
		}
		catch(Exception _e) {
			Log.e("UtilReflection.getSetMethodList", _e.getMessage());
			return new ArrayList<Method>();
		}
	}

	/**
	 * 
	 * @param _classType
	 * @return
	 */
	public static List<Method> getGetMethodList(Class<?> _classType) {
		try {
			List<Method> getMethods = new ArrayList<Method>();

			Method[] methods = _classType.getMethods();

			for(Method method : methods) {
				if(method.getName().startsWith(PREFIX_GET) && !method.getName().equals(GET_CLASS_METHOD_NAME)) {
					getMethods.add(method);
				}
			}

			return getMethods;
		}
		catch(Exception _e) {
			Log.e("UtilReflection.getGetMethodList", _e.getMessage());
			return new ArrayList<Method>();
		}
	}

	/**
	 * 
	 * @param _classType
	 * @return
	 */
	public static List<String> getReadOnlyPropertyNameList(Class<?> _classType) {
		try {
			List<String> propriedades = new ArrayList<String>();

			Method[] metodos = _classType.getMethods();

			for(Method metodo : metodos) {
				if(metodo.getName().startsWith(PREFIX_GET) && !metodo.getName().equals(GET_CLASS_METHOD_NAME)) {
					String nomeMetodo = metodo.getName().substring(3);
					String nomePropriedade = nomeMetodo.substring(0, 1).toLowerCase()
							+ nomeMetodo.substring(1);
					propriedades.add(nomePropriedade);
				}
			}

			return propriedades;
		}
		catch(Exception _e) {
			Log.e("UtilReflection.getReadOnlyPropertyNameList", _e.getMessage());
			return new ArrayList<String>();
		}
	}

	/**
	 * 
	 * @param _classType
	 * @return
	 */
	public static List<String> getReadOnlyPropertyNamesList(Class<?> _classType) {
		try {
			List<String> properties = new ArrayList<String>();

			Method[] methods = _classType.getMethods();

			for(Method method : methods) {
				if(method.getName().startsWith(PREFIX_SET)) {
					String methodName = method.getName().substring(3);
					String propertyName = methodName.substring(0, 1).toLowerCase()
							+ methodName.substring(1);
					properties.add(propertyName);
				}
			}

			return properties;
		}
		catch(Exception _e) {
			Log.e("UtilReflection.getReadOnlyPropertyNamesList", _e.getMessage());
			return new ArrayList<String>();
		}
	}

	/**
	 * 
	 * @param _classType
	 * @return
	 */
	public static List<String> getReadAndWritePropertyNamesList(Class<?> _classType) {
		try {
			List<String> readonlyProperties = getReadOnlyPropertyNameList(_classType);

			List<String> writableProperties = getReadOnlyPropertyNamesList(_classType);

			Set<String> propertiesSet = new HashSet<String>();

			propertiesSet.addAll(readonlyProperties);

			propertiesSet.addAll(writableProperties);

			List<String> properties = new ArrayList<String>();

			properties.addAll(propertiesSet);

			return properties;
		}
		catch(Exception _e) {
			Log.e("UtilReflection.getReadAndWritePropertyNamesList", _e.getMessage());
			return new ArrayList<String>();
		}
	}

	/**
	 * 
	 * @param _setMethodName
	 * @return
	 */
	public static String convertToGetMethodName(String _setMethodName) {
		return _setMethodName.replaceFirst(REGEX_FIRST_CHARACTER_S, CHARACTER_G);
	}

	/**
	 * 
	 * @param _method
	 * @return
	 */
	public static String convertToGetMethodName(Method _method) {
		if(_method == null) {
			return null;
		}
		return _method.getName().replaceFirst(REGEX_FIRST_CHARACTER_S, CHARACTER_G);
	}

	/**
	 * 
	 * @param _nomeMetodoGet
	 * @return
	 */
	public static String convertToSetMethodName(String _nomeMetodoGet) {
		return _nomeMetodoGet.replaceFirst(REGEX_FIRST_CHARACTER_G, CHARACTER_S);
	}

	/**
	 * 
	 * @param _method
	 * @return
	 */
	public static String convertToSetMethodName(Method _method) {
		if(_method == null) {
			return null;
		}
		return _method.getName().replaceFirst(REGEX_FIRST_CHARACTER_G, CHARACTER_S);
	}

	/**
	 * 
	 * @param _propertyName
	 * @return
	 */
	public static String getGetMethodName(String _propertyName) {
		return convertToMethodName(_propertyName, PREFIX_GET);
	}

	/**
	 * 
	 * @param _nomePropriedade
	 * @param _class
	 * @return
	 */
	public static String getGetMethodName(String _nomePropriedade, Class<?> _class) {
		String prefixo = PREFIX_GET;
		if(_class.equals(java.lang.Boolean.class)) {
			prefixo = PREFIX_IS;
		}
		return convertToMethodName(_nomePropriedade, prefixo);
	}

	/**
	 * 
	 * @param _nomePropriedade
	 * @return
	 */
	public static String getSetMethodName(String _nomePropriedade) {
		return convertToMethodName(_nomePropriedade, PREFIX_SET);
	}

	/**
	 * 
	 * @param _propertyName
	 * @param _nomeAccessorMethod
	 * @return
	 */
	private static String convertToMethodName(String _propertyName, String _nomeAccessorMethod) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(_nomeAccessorMethod);
		buffer.append(_propertyName.substring(0, 1).toUpperCase());
		buffer.append(_propertyName.substring(1));
		return buffer.toString();
	}

	/**
	 * 
	 * @param _getMethodName
	 * @param _classType
	 * @return
	 */
	public static Method getGetMethod(String _getMethodName, Class<?> _classType) {
		try {
			return _classType.getMethod(_getMethodName, UtilReflection.defaultParamArrayClassReflection);
		} catch(Exception _e) {
			Log.e("UtilReflection.getGetMethod", _e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @param _setMethodName
	 * @param _classType
	 * @param _params
	 * @return
	 */
	public static Method getSetMethod(String _setMethodName, Class<?> _classType, Class<?>[] _params) {
		try {
			return _classType.getMethod(_setMethodName, _params);
		} catch(Exception _e) {
			Log.e("UtilReflection.getSetMethod", _e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @param _classFullyQualifiedName
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> convertToClass(String _classFullyQualifiedName) throws ClassNotFoundException {
		return Class.forName(_classFullyQualifiedName);
	}

	/**
	 * 
	 * @param _fullyQualifiedName
	 * @return
	 */
	public static String getClassNameFromFullyQualifiedName(String _fullyQualifiedName) {
		return _fullyQualifiedName.substring(_fullyQualifiedName.lastIndexOf(".") + 1);
	}

	/**
	 * 
	 * @param _tipo
	 * @return
	 */
	public static boolean implementsCollection(Class<?> _tipo) {
		try {
			if(_tipo.isPrimitive()) {
				return false;
			}
			Class<?>[] interfaces = _tipo.getInterfaces();
			for(Class<?> interfaceObj : interfaces) {
				if(interfaceObj.getName().indexOf("java.util.Collection") != -1) {
					return true;
				}
			}
			return false;
		}
		catch(Exception _e) {
			Log.e("UtilReflection.implementsCollection", _e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param _classType
	 * @param _className
	 * @return
	 */
	public static boolean isClassOfType(Class<?> _classType, String _className) {
		try {
			return UtilReflection.getClassNameFromFullyQualifiedName(_classType.toString()).equals(_className);
		} catch(Exception _e) {
			Log.e("UtilReflection.isClassOfType", _e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param _accessorMethodName
	 * @return
	 */
	public static String getPropertyName(String _accessorMethodName) {
		try {
			if((_accessorMethodName.startsWith(PREFIX_GET)) || (_accessorMethodName.startsWith(PREFIX_SET)) || (_accessorMethodName.startsWith(PREFIX_IS))) {

				_accessorMethodName = _accessorMethodName.replaceFirst(REGEX_ACCESSORS_GET_SET_IS, "");

				_accessorMethodName = UtilString.firstCharacterToLowerCase(_accessorMethodName);

				return _accessorMethodName;
			}

			return null;
		} catch (Exception _e) {
			Log.e("UtilReflection.getPropertyName", _e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param _method
	 * @return
	 */
	public static String getPropertyName(Method _method) {
		try {
			String methodName = _method.getName();
			return UtilReflection.getPropertyName(methodName);
		} catch (Exception _e) {
			Log.e("UtilReflection.getPropertyName", _e.getMessage());
		}
		return null;
	}

    /**
     * 
     * @param _classType
     * @return
     */
    public static List<String> getPropertyList(Class<?> _classType) {
        try {
            List<String> properties = new ArrayList<String>();

            Method[] methods = _classType.getMethods();

            for(int i = 0; i < methods.length; i++) {
                Method method  = methods[i];
                if(method.getName().startsWith(PREFIX_GET) && !method.getName().equals(GET_CLASS_METHOD_NAME)) {
                    String methodName = method.getName().substring(3);
                    String propertyName = methodName.substring(0, 1).toLowerCase()+methodName.substring(1);
                    properties.add(propertyName);
                }
            }

            return properties;
        } catch (Exception _e) {
        	Log.e("UtilReflection.getPropertyList", _e.getMessage());
            return new ArrayList<String>();
        }
    }
    
	/**
	 * 
	 * @param _collection
	 * @param _value
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addToCollection(Object _collection, Object _value) {
		if(_collection instanceof java.util.List) {
			((java.util.List)_collection).add(_value);
		} else if(_collection instanceof java.util.Set) {
			((java.util.Set)_collection).add(_value);
		}
	}


	/**
	 * 
	 * @param _tipoCollection
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Object getCollectionInstance(Class<?> _tipoCollection) {
		if("java.util.List".equals(_tipoCollection.getName())) {
			return new java.util.ArrayList();
		} else if("java.util.Set".equals(_tipoCollection.getName())) {
			return new java.util.HashSet();
		}
		return null;
	}
	
	/**
	 * 
	 * @param _tipoCollection
	 * @return
	 */
	public static boolean isCollection(Class<?> _tipoCollection) {
		if(_tipoCollection.getName().equals("java.util.List") 
				|| java.util.Set.class.isInstance("java.util.Set")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param _classType
	 * @return
	 */
	public static List<Method> getDeclaredMethodsGetList(Class<?> _classType) {
		try {
			List<Method> getMethods = new ArrayList<Method>();
			
			Method[] methods = _classType.getDeclaredMethods();
			
			for(Method method : methods) {
				if(method.getName().startsWith(PREFIX_GET)) {
					getMethods.add(method);
				}
			}
			return getMethods;
		}
		catch(Exception _e) {
			Log.e("UtilReflection.getDeclaredMethodsGetList", _e.getMessage());
			return new ArrayList<Method>();
		}
	}

	/**
	 * 
	 * @param _method
	 * @param _objectTo
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethodWithoutParams(Method _method, Object _objectTo) throws Exception {
		return _method.invoke(_objectTo, deafultParamArrayObjectReflection);
	}

	/**
	 * 
	 * @param _field
	 * @param _classType
	 * @return
	 */
	public static Method getGetMethod(Field _field, Class<?> _classType) {
		try {
			return _classType.getMethod(_field.getName(), UtilReflection.defaultParamArrayClassReflection);
		} catch(Exception _e) {
			Log.e("UtilReflection.getGetMethod", _e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param _class
	 * @return
	 */
	public static Map<Field, Method> buildDeclaredFieldMethodsMap(Class<?> _class) {
		Field[] declaredFields = _class.getDeclaredFields();
		if(declaredFields != null && declaredFields.length > 0) {
			Map<Field, Method> declaredFieldMethodsMap = new HashMap<Field, Method>();
			for(Field field : declaredFields) {
				Method getMethod = UtilReflection.getGetMethod(field, _class);
				if(getMethod != null) {
					declaredFieldMethodsMap.put(field, getMethod);
				}
			}
			return declaredFieldMethodsMap;
		}
		return new HashMap<Field, Method>();
	}
	
	/**
	 * 
	 * @param _object
	 * @return
	 */
	public static Map<Field, Object> buildDeclaredFieldValuesMap(Object _object) {
		try {
			Field[] declaredFields = _object.getClass().getDeclaredFields();
			if(declaredFields != null && declaredFields.length > 0) {
				Map<Field, Object> declaredFieldValuesMap = new HashMap<Field, Object>();
				for(Field field : declaredFields) {
					Method getMethod = UtilReflection.getGetMethod(field, _object.getClass());
					if(getMethod != null) {
						Object value = UtilReflection.invokeMethodWithoutParams(getMethod, _object);
						declaredFieldValuesMap.put(field, value);
					}
				}
				return declaredFieldValuesMap;
			}
			return new HashMap<Field, Object>();
		} catch (Exception _e) {
			Log.e("UtilReflection.buildDeclaredFieldValuesMap", _e.getMessage());
		}
		return new HashMap<Field, Object>();
	}
	
	/**
	 * 
	 * @param _object
	 * @return
	 */
	public static Map<Field, Method> buildDeclaredFieldNotNullMethodsMap(Object _object) {
		try {
			Field[] declaredFields = _object.getClass().getDeclaredFields();
			if(declaredFields != null && declaredFields.length > 0) {
				Map<Field, Method> declaredFieldNotNullMethodsMap = new HashMap<Field, Method>();
				for(Field field : declaredFields) {
					Method metodoGet = UtilReflection.getGetMethod(field, _object.getClass());
					if(metodoGet != null) {
						Object value = UtilReflection.invokeMethodWithoutParams(metodoGet, _object);
						if(value != null) {
							declaredFieldNotNullMethodsMap.put(field, metodoGet);
						}
					}
				}
				return declaredFieldNotNullMethodsMap;
			}
		} catch (Exception _e) {
			Log.e("UtilReflection.buildDeclaredFieldNotNullMethodsMap", _e.getMessage());
		}
		return new HashMap<Field, Method>();
	}
	
	/**
	 * 
	 * @param _object
	 * @return
	 */
	public static Map<Field, Object> buildDeclaredFieldNotNullValuesMap(Object _object) {
		try {
			Field[] declaredFields = _object.getClass().getDeclaredFields();
			if(declaredFields != null && declaredFields.length > 0) {
				Map<Field, Object> declaredFieldNotNullValuesMap = new HashMap<Field, Object>();
				for(Field field : declaredFields) {
					Method getMethod = UtilReflection.getGetMethod(field, _object.getClass());
					if(getMethod != null) {
						Object value = UtilReflection.invokeMethodWithoutParams(getMethod, _object);
						if(value != null) {
							declaredFieldNotNullValuesMap.put(field, value);
						}
					}
				}
				return declaredFieldNotNullValuesMap;
			}
		} catch (Exception _e) {
			Log.e("UtilReflection.buildDeclaredFieldNotNullValuesMap", _e.getMessage());
		}
		return new HashMap<Field, Object>();
	}
	
	/**
	 * 
	 * @param _classe
	 * @return
	 */
	public static Object getGenericSuperclassInstance(final Class<?> _classe) {
		try {
			return getGenericSuperclassClass(_classe).newInstance();
		} catch (InstantiationException _e) {
			Log.e("UtilReflection.getGenericSuperclassInstance", _e.getMessage());
			return null;
		} catch (IllegalAccessException _e) {
			Log.e("UtilReflection.getGenericSuperclassInstance", _e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * 
	 * @param _classe
	 * @return
	 */
	public static Class<?> getGenericSuperclassClass(final Class<?> _classe) {
		try {
			final ParameterizedType parameterizedType = (ParameterizedType)_classe.getGenericSuperclass();
			
			return (Class<?>) parameterizedType.getActualTypeArguments()[0];
		} catch (Exception _e) {
			Log.e("UtilReflection.getGenericSuperclassClass", _e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * 
	 * @param _object
	 * @param _propertyName
	 */
	public static void setSingleWhiteSpaceBetweenWords(final Object _object, final String _propertyName) {
		
		UtilReflection.setValueToProperty(
				_propertyName,
				UtilString.cleanMultipleWhiteSpaces((String)UtilReflection.getPropertyValue(_propertyName, _object)),
				_object
		);
		
	}
	
}