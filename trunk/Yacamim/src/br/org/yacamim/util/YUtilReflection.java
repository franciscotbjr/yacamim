/**
 * YUtilReflection.java
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
package br.org.yacamim.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.Log;


/**
 *
 * Class YUtilReflection TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public strictfp abstract class YUtilReflection {

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
	private static final Object[] deafultParamArrayObjectReflection = new Object[]{};

	/**
	 *
	 */
	private static final Class<?>[] defaultParamArrayClassReflection = new Class[]{};

	/**
	 *
	 *
	 */
	private YUtilReflection() {
		super();
	}

	/**
	 *
	 * @param method
	 * @param objectoTo
	 * @param methodParams
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethod(Method method, Object objectoTo, Object... methodParams) throws Exception {
		return method.invoke(objectoTo, methodParams);
	}

	/**
	 *
	 * @param method
	 * @param objectTo
	 * @param methodParams
	 * @return
	 */
	public static String invokeMethodToString(Method method, Object objectTo, Object[] methodParams) {
		try {
			return method.invoke(objectTo, methodParams).toString();
		}
		catch(Exception e) {
			Log.e("YUtilReflection.invokeMethodToString", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param propertyName
	 * @param object
	 * @return
	 */
	public static Object getPropertyValue(String propertyName, Object object) {
		try {
			Object result  = null;
			if(propertyName.indexOf('.') == -1) {
				result = YUtilReflection.invokeMethodWithoutParams(
								YUtilReflection.getGetMethod(
										YUtilReflection.getGetMethodName(propertyName)
									, object.getClass()), object);
			} else {
				String[] properties = propertyName.split(REGEX_POINT_SEPARATOR);
				Object current = object;
				for(String propertie : properties) {
					current = getPropertyValue(propertie, current);
				}
				result = current;
			}
			return result;
		} catch(Exception e) {
			Log.e("YUtilReflection.getPropertyValue", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param propertyName
	 * @param propertyValue
	 * @param object
	 */
	public static void setValueToProperty(String propertyName, Object propertyValue, Object object) {
		Class<?> parameterClass = null;
		try {
			if (propertyValue != null) {
				if(propertyName.indexOf('.') == -1) {
					parameterClass = propertyValue.getClass();
					if (parameterClass.equals(Byte.class)) {
						parameterClass = byte.class;
					} else if (parameterClass.equals(Short.class)) {
						parameterClass = short.class;
					} else if (parameterClass.equals(Integer.class)) {
						parameterClass = int.class;
					} else if (parameterClass.equals(Long.class)) {
						parameterClass = long.class;
					} else if (parameterClass.equals(Float.class)) {
						parameterClass = float.class;
					} else if (parameterClass.equals(Double.class)) {
						parameterClass = double.class;
					} else if (parameterClass.equals(Boolean.class)) {
						parameterClass = boolean.class;
					}
					Method setMethod = YUtilReflection.getSetMethod(YUtilReflection.getSetMethodName(propertyName), object.getClass(), new Class<?>[]{parameterClass});

					YUtilReflection.invokeMethod(setMethod, object, propertyValue);
				} else {
					String recoveredProperty = propertyName.substring(0, propertyName.lastIndexOf(REGEX_POINT_SEPARATOR));
					Object currentObject = getPropertyValue(recoveredProperty, object);

					String propertyAttributes = propertyName.substring(propertyName.lastIndexOf(REGEX_POINT_SEPARATOR)+1);

					setValueToProperty(propertyAttributes, propertyValue, currentObject);
				}
			}
		} catch(Exception e) {
			Log.e("YUtilReflection.setValueToProperty", e.getMessage());
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<Method> getSetMethodList(Class<?> classType) {
		try {
			List<Method> setMethods = new ArrayList<Method>();

			Method[] methods = classType.getMethods();

			for(Method method : methods) {
				if(method.getName().startsWith(PREFIX_SET)) {
					setMethods.add(method);
				}
			}

			return setMethods;
		}
		catch(Exception e) {
			Log.e("YUtilReflection.getSetMethodList", e.getMessage());
			return new ArrayList<Method>();
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<Method> getGetMethodList(Class<?> classType) {
		try {
			List<Method> getMethods = new ArrayList<Method>();

			Method[] methods = classType.getMethods();

			for(Method method : methods) {
				if(method.getName().startsWith(PREFIX_GET) && !method.getName().equals(GET_CLASS_METHOD_NAME)) {
					getMethods.add(method);
				}
			}

			return getMethods;
		}
		catch(Exception e) {
			Log.e("YUtilReflection.getGetMethodList", e.getMessage());
			return new ArrayList<Method>();
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<Method> getGetMethodListSortedByName(Class<?> classType) {
		try {
			final List<Method> getMethods = getGetMethodList(classType);

			final List<String> getMethodNames = new ArrayList<String>();

			for(final Method method : getMethods) {
				getMethodNames.add(method.getName());
			}
			Collections.sort(getMethodNames);

			final List<Method> sortedGetMethods = new ArrayList<Method>();
			for(String methodName : getMethodNames) {
				for(Method method : getMethods) {
					if(method.getName().equals(methodName)) {
						sortedGetMethods.add(method);
						break;
					}
				}
			}
			return sortedGetMethods;
		} catch(Exception e) {
			Log.e("YUtilReflection.getGetMethodListSortedByName", e.getMessage());
			return new ArrayList<Method>();
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<String> getReadOnlyPropertyNameList(Class<?> classType) {
		try {
			List<String> propriedades = new ArrayList<String>();

			Method[] metodos = classType.getMethods();

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
		catch(Exception e) {
			Log.e("YUtilReflection.getReadOnlyPropertyNameList", e.getMessage());
			return new ArrayList<String>();
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<String> getReadOnlyPropertyNamesList(Class<?> classType) {
		try {
			List<String> properties = new ArrayList<String>();

			Method[] methods = classType.getMethods();

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
			Log.e("YUtilReflection.getReadOnlyPropertyNamesList", _e.getMessage());
			return new ArrayList<String>();
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<String> getReadAndWritePropertyNamesList(Class<?> classType) {
		try {
			List<String> readonlyProperties = getReadOnlyPropertyNameList(classType);

			List<String> writableProperties = getReadOnlyPropertyNamesList(classType);

			Set<String> propertiesSet = new HashSet<String>();

			propertiesSet.addAll(readonlyProperties);

			propertiesSet.addAll(writableProperties);

			List<String> properties = new ArrayList<String>();

			properties.addAll(propertiesSet);

			return properties;
		}
		catch(Exception e) {
			Log.e("YUtilReflection.getReadAndWritePropertyNamesList", e.getMessage());
			return new ArrayList<String>();
		}
	}

	/**
	 *
	 * @param setMethodName
	 * @return
	 */
	public static String convertToGetMethodName(String setMethodName) {
		return setMethodName.replaceFirst(REGEX_FIRST_CHARACTER_S, CHARACTER_G);
	}

	/**
	 *
	 * @param method
	 * @return
	 */
	public static String convertToGetMethodName(Method method) {
		if(method == null) {
			return null;
		}
		return method.getName().replaceFirst(REGEX_FIRST_CHARACTER_S, CHARACTER_G);
	}

	/**
	 *
	 * @param nomeMetodoGet
	 * @return
	 */
	public static String convertToSetMethodName(String nomeMetodoGet) {
		return nomeMetodoGet.replaceFirst(REGEX_FIRST_CHARACTER_G, CHARACTER_S);
	}

	/**
	 *
	 * @param method
	 * @return
	 */
	public static String convertToSetMethodName(Method method) {
		if(method == null) {
			return null;
		}
		return method.getName().replaceFirst(REGEX_FIRST_CHARACTER_G, CHARACTER_S);
	}

	/**
	 *
	 * @param propertyName
	 * @return
	 */
	public static String getGetMethodName(String propertyName) {
		return convertToMethodName(propertyName, PREFIX_GET);
	}

	/**
	 *
	 * @param propertyName
	 * @param classType
	 * @return
	 */
	public static String getGetMethodName(String propertyName, Class<?> classType) {
		String prefixo = PREFIX_GET;
		if(classType.equals(java.lang.Boolean.class)) {
			prefixo = PREFIX_IS;
		}
		return convertToMethodName(propertyName, prefixo);
	}

	/**
	 *
	 * @param propertyName
	 * @return
	 */
	public static String getSetMethodName(String propertyName) {
		return convertToMethodName(propertyName, PREFIX_SET);
	}

	/**
	 *
	 * @param propertyName
	 * @param _accessorMethodName
	 * @return
	 */
	private static String convertToMethodName(String propertyName, String _accessorMethodName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(_accessorMethodName);
		buffer.append(propertyName.substring(0, 1).toUpperCase());
		buffer.append(propertyName.substring(1));
		return buffer.toString();
	}

	/**
	 *
	 * @param getMethodName
	 * @param classType
	 * @return
	 */
	public static Method getGetMethod(String getMethodName, Class<?> classType) {
		try {
			return classType.getMethod(getMethodName, YUtilReflection.defaultParamArrayClassReflection);
		} catch(Exception e) {
			Log.e("YUtilReflection.getGetMethod", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param setMethodName
	 * @param classType
	 * @param params
	 * @return
	 */
	public static Method getSetMethod(String setMethodName, Class<?> classType, Class<?>[] params) {
		try {
			return classType.getMethod(setMethodName, params);
		} catch(Exception e) {
			Log.e("YUtilReflection.getSetMethod", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param setMethodName
	 * @param classType
	 * @return
	 */
	public static Method getSetMethod(String setMethodName, Class<?> classType) {
		try {
			return classType.getMethod(setMethodName, defaultParamArrayClassReflection);
		} catch(Exception _e) {
			Log.e("YUtilReflection.getSetMethod", _e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param classFullyQualifiedName
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> convertToClass(String classFullyQualifiedName) throws ClassNotFoundException {
		return Class.forName(classFullyQualifiedName);
	}

	/**
	 *
	 * @param fullyQualifiedName
	 * @return
	 */
	public static String getClassNameFromFullyQualifiedName(String fullyQualifiedName) {
		return fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf(".") + 1);
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static boolean implementsCollection(Class<?> classType) {
		try {
			if(classType.isPrimitive()) {
				return false;
			}
			Class<?>[] interfaces = classType.getInterfaces();
			for(Class<?> interfaceObj : interfaces) {
				if(interfaceObj.getName().indexOf("java.util.Collection") != -1) {
					return true;
				}
			}
			return false;
		}
		catch(Exception e) {
			Log.e("YUtilReflection.implementsCollection", e.getMessage());
			return false;
		}
	}

	/**
	 *
	 * @param classType
	 * @param className
	 * @return
	 */
	public static boolean isClassOfType(Class<?> classType, String className) {
		try {
			return YUtilReflection.getClassNameFromFullyQualifiedName(classType.toString()).equals(className);
		} catch(Exception e) {
			Log.e("YUtilReflection.isClassOfType", e.getMessage());
			return false;
		}
	}

	/**
	 *
	 * @param accessorMethodName
	 * @return
	 */
	public static String getPropertyName(String accessorMethodName) {
		try {
			if((accessorMethodName.startsWith(PREFIX_GET)) || (accessorMethodName.startsWith(PREFIX_SET)) || (accessorMethodName.startsWith(PREFIX_IS))) {

				accessorMethodName = accessorMethodName.replaceFirst(REGEX_ACCESSORS_GET_SET_IS, "");

				accessorMethodName = YUtilString.firstCharacterToLowerCase(accessorMethodName);

				return accessorMethodName;
			}

			return null;
		} catch (Exception e) {
			Log.e("YUtilReflection.getPropertyName", e.getMessage());
		}
		return null;
	}

	/**
	 *
	 * @param method
	 * @return
	 */
	public static String getPropertyName(Method method) {
		try {
			String methodName = method.getName();
			return YUtilReflection.getPropertyName(methodName);
		} catch (Exception e) {
			Log.e("YUtilReflection.getPropertyName", e.getMessage());
		}
		return null;
	}

    /**
     *
     * @param classType
     * @return
     */
    public static List<String> getPropertyList(Class<?> classType) {
        try {
            List<String> properties = new ArrayList<String>();

            Method[] methods = classType.getMethods();

            for(int i = 0; i < methods.length; i++) {
                Method method  = methods[i];
                if(method.getName().startsWith(PREFIX_GET) && !method.getName().equals(GET_CLASS_METHOD_NAME)) {
                    String methodName = method.getName().substring(3);
                    String propertyName = methodName.substring(0, 1).toLowerCase()+methodName.substring(1);
                    properties.add(propertyName);
                }
            }

            return properties;
        } catch (Exception e) {
        	Log.e("YUtilReflection.getPropertyList", e.getMessage());
            return new ArrayList<String>();
        }
    }

	/**
	 *
	 * @param collection
	 * @param value
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addToCollection(Object collection, Object value) {
		if(collection instanceof java.util.List) {
			((java.util.List)collection).add(value);
		} else if(collection instanceof java.util.Set) {
			((java.util.Set)collection).add(value);
		}
	}


	/**
	 *
	 * @param collectionType
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Object getCollectionInstance(Class<?> collectionType) {
		if("java.util.List".equals(collectionType.getName())) {
			return new java.util.ArrayList();
		} else if("java.util.Set".equals(collectionType.getName())) {
			return new java.util.HashSet();
		}
		return null;
	}

	/**
	 *
	 * @param collectionType
	 * @return
	 */
	public static boolean isCollection(Class<?> collectionType) {
		if(collectionType.getName().equals("java.util.List")
				|| java.util.Set.class.isInstance("java.util.Set")) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<Method> getDeclaredMethodsGetList(Class<?> classType) {
		try {
			List<Method> getMethods = new ArrayList<Method>();

			Method[] methods = classType.getDeclaredMethods();

			for(Method method : methods) {
				if(method.getName().startsWith(PREFIX_GET)) {
					getMethods.add(method);
				}
			}
			return getMethods;
		}
		catch(Exception e) {
			Log.e("YUtilReflection.getDeclaredMethodsGetList", e.getMessage());
			return new ArrayList<Method>();
		}
	}

	/**
	 *
	 * @param method
	 * @param objectTo
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethodWithoutParams(Method method, Object objectTo) throws Exception {
		return method.invoke(objectTo, deafultParamArrayObjectReflection);
	}

	/**
	 *
	 * @param _field
	 * @param classType
	 * @return
	 */
	public static Method getGetMethod(Field _field, Class<?> classType) {
		try {
			return classType.getMethod(_field.getName(), YUtilReflection.defaultParamArrayClassReflection);
		} catch(Exception e) {
			Log.e("YUtilReflection.getGetMethod", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static Map<Field, Method> buildDeclaredFieldMethodsMap(Class<?> classType) {
		Field[] declaredFields = classType.getDeclaredFields();
		if(declaredFields != null && declaredFields.length > 0) {
			Map<Field, Method> declaredFieldMethodsMap = new HashMap<Field, Method>();
			for(Field field : declaredFields) {
				Method getMethod = YUtilReflection.getGetMethod(field, classType);
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
	 * @param object
	 * @return
	 */
	public static Map<Field, Object> buildDeclaredFieldValuesMap(Object object) {
		try {
			Field[] declaredFields = object.getClass().getDeclaredFields();
			if(declaredFields != null && declaredFields.length > 0) {
				Map<Field, Object> declaredFieldValuesMap = new HashMap<Field, Object>();
				for(Field field : declaredFields) {
					Method getMethod = YUtilReflection.getGetMethod(field, object.getClass());
					if(getMethod != null) {
						Object value = YUtilReflection.invokeMethodWithoutParams(getMethod, object);
						declaredFieldValuesMap.put(field, value);
					}
				}
				return declaredFieldValuesMap;
			}
			return new HashMap<Field, Object>();
		} catch (Exception e) {
			Log.e("YUtilReflection.buildDeclaredFieldValuesMap", e.getMessage());
		}
		return new HashMap<Field, Object>();
	}

	/**
	 *
	 * @param object
	 * @return
	 */
	public static Map<Field, Method> buildDeclaredFieldNotNullMethodsMap(Object object) {
		try {
			Field[] declaredFields = object.getClass().getDeclaredFields();
			if(declaredFields != null && declaredFields.length > 0) {
				Map<Field, Method> declaredFieldNotNullMethodsMap = new HashMap<Field, Method>();
				for(Field field : declaredFields) {
					Method metodoGet = YUtilReflection.getGetMethod(field, object.getClass());
					if(metodoGet != null) {
						Object value = YUtilReflection.invokeMethodWithoutParams(metodoGet, object);
						if(value != null) {
							declaredFieldNotNullMethodsMap.put(field, metodoGet);
						}
					}
				}
				return declaredFieldNotNullMethodsMap;
			}
		} catch (Exception e) {
			Log.e("YUtilReflection.buildDeclaredFieldNotNullMethodsMap", e.getMessage());
		}
		return new HashMap<Field, Method>();
	}

	/**
	 *
	 * @param object
	 * @return
	 */
	public static Map<Field, Object> buildDeclaredFieldNotNullValuesMap(Object object) {
		try {
			Field[] declaredFields = object.getClass().getDeclaredFields();
			if(declaredFields != null && declaredFields.length > 0) {
				Map<Field, Object> declaredFieldNotNullValuesMap = new HashMap<Field, Object>();
				for(Field field : declaredFields) {
					Method getMethod = YUtilReflection.getGetMethod(field, object.getClass());
					if(getMethod != null) {
						Object value = YUtilReflection.invokeMethodWithoutParams(getMethod, object);
						if(value != null) {
							declaredFieldNotNullValuesMap.put(field, value);
						}
					}
				}
				return declaredFieldNotNullValuesMap;
			}
		} catch (Exception e) {
			Log.e("YUtilReflection.buildDeclaredFieldNotNullValuesMap", e.getMessage());
		}
		return new HashMap<Field, Object>();
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static Object getGenericSuperclassInstance(final Class<?> classType) {
		try {
			return getGenericSuperclassClass(classType).newInstance();
		} catch (InstantiationException e) {
			Log.e("YUtilReflection.getGenericSuperclassInstance", e.getMessage());
			return null;
		} catch (IllegalAccessException e) {
			Log.e("YUtilReflection.getGenericSuperclassInstance", e.getMessage());
			return null;
		}

	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static Class<?> getGenericSuperclassClass(final Class<?> classType) {
		try {
			final ParameterizedType parameterizedType = (ParameterizedType)classType.getGenericSuperclass();

			return (Class<?>) parameterizedType.getActualTypeArguments()[0];
		} catch (Exception e) {
			Log.e("YUtilReflection.getGenericSuperclassClass", e.getMessage());
			return null;
		}

	}

	/**
	 *
	 * @param object
	 * @param propertyName
	 */
	public static void setSingleWhiteSpaceBetweenWords(final Object object, final String propertyName) {

		YUtilReflection.setValueToProperty(
				propertyName,
				YUtilString.cleanMultipleWhiteSpaces((String)YUtilReflection.getPropertyValue(propertyName, object)),
				object
		);

	}

}