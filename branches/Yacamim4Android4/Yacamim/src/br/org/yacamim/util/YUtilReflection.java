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
import java.lang.reflect.InvocationTargetException;
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
	
	private static final String TAG = YUtilReflection.class.getSimpleName();

	/**
	 *
	 */
	private static final String REGEX_POINT_SEPARATOR = "[.]";

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
	public static final String GET_CLASS_METHOD_NAME = "getClass";
	
	/**
	 *
	 */
	public static final String PREFIX_GET = "get";

	/**
	 *
	 */
	public static final String SINGLETON_ACESS_METHOD_NAME = "getInstance";

	/**
	 *
	 */
	public static final Object[] DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION = new Object[]{};

	/**
	 *
	 */
	public static final Class<?>[] DEFAULT_PARAM_ARRAY_CLASS_REFLECTION = new Class[]{};

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
	public static Object invokeMethod(final Method method, final Object objectoTo, final Object... methodParams) throws Exception {
		return method.invoke(objectoTo, methodParams);
	}

	/**
	 *
	 * @param method
	 * @param objectTo
	 * @param methodParams
	 * @return
	 */
	public static String invokeMethodToString(final Method method, final Object objectTo, final Object[] methodParams) {
		try {
			return method.invoke(objectTo, methodParams).toString();
		}
		catch(Exception e) {
			Log.e(TAG + ".invokeMethodToString", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param propertyName
	 * @param object
	 * @return
	 */
	public static Object getPropertyValue(final String propertyName, final Object object) {
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
			Log.e(TAG + ".getPropertyValue", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param propertyName
	 * @param propertyValue
	 * @param object
	 */
	public static void setValueToProperty(final String propertyName, final Object propertyValue, final Object object) {
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
					} else if (parameterClass.equals(ArrayList.class)) {
						parameterClass = List.class;
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
			Log.e(TAG + ".setValueToProperty", e.getMessage());
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<Method> getSetMethodList(final Class<?> classType) {
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
			Log.e(TAG + ".getSetMethodList", e.getMessage());
			return new ArrayList<Method>();
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<Method> getGetMethodList(final Class<?> classType) {
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
			Log.e(TAG + ".getGetMethodList", e.getMessage());
			return new ArrayList<Method>();
		}
	}

	/**
	 * 
	 * @param classType
	 * @param attributes
	 * @return
	 */
	public static List<Method> getGetMethodList(final Class<?> classType, final String[] attributes) {
		try {
			final List<Method> getMethods = new ArrayList<Method>();
			
			final Method[] methods = classType.getMethods();
			final List<String> getMethodNames = getGetMethodNames(attributes);
			
			for(final Method method : methods) {
				if(method.getName().startsWith(PREFIX_GET) 
						&& !method.getName().equals(GET_CLASS_METHOD_NAME)
						&& getMethodNames.contains(method.getName())) {
					getMethods.add(method);
				}
			}
			return getMethods;
		}
		catch(Exception e) {
			Log.e(TAG + ".getGetMethodList", e.getMessage());
			return new ArrayList<Method>();
		}
	}

	/**
	 * 
	 * @param attributes
	 * @return
	 */
	public static List<String> getGetMethodNames(final String[] attributes) {
		final List<String> getMethodNames = new ArrayList<String>();
		for(final String attName : attributes) {
			getMethodNames.add(YUtilReflection.getGetMethodName(attName));
		}
		return getMethodNames;
	}

	/**
	 * 
	 * @param classType
	 * @return
	 */
	public static Method[] getGetMethodArray(final Class<?> classType) {
		try {
			final List<Method> getMethods = new ArrayList<Method>();
			
			final Method[] methods = classType.getMethods();
			
			for(Method method : methods) {
				if(method.getName().startsWith(PREFIX_GET) && !method.getName().equals(GET_CLASS_METHOD_NAME)) {
					getMethods.add(method);
				}
			}
			
			final Method[] arryGetMethods = new Method[getMethods.size()];
			for(int i = 0; i < getMethods.size(); i++) {
				arryGetMethods[i] = getMethods.get(i);
			}
			
			return arryGetMethods;
		}
		catch(Exception e) {
			Log.e(TAG + ".getGetMethodList", e.getMessage());
			return new Method[]{};
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<Method> getGetMethodListSortedByName(final Class<?> classType) {
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
			Log.e(TAG + ".getGetMethodListSortedByName", e.getMessage());
			return new ArrayList<Method>();
		}
	}
	
	/**
	 * 
	 * @param classType
	 * @return
	 */
	public static Method[] getGetMethodListSortedByNameAsArray(final Class<?> classType) {
		try {
			final List<Method> getMethods = getGetMethodList(classType);
			
			final List<String> getMethodNames = new ArrayList<String>();
			
			for(final Method method : getMethods) {
				getMethodNames.add(method.getName());
			}
			Collections.sort(getMethodNames);
			
			final Method[] sortedGetMethods = new Method[getMethodNames.size()];
			for(int i = 0; i < getMethodNames.size(); i++) {
				final String methodName = getMethodNames.get(i);
				for(Method method : getMethods) {
					if(method.getName().equals(methodName)) {
						sortedGetMethods[i] = method;
						break;
					}
				}
			}
			return sortedGetMethods;
		} catch(Exception e) {
			Log.e(TAG + ".getGetMethodListSortedByNameAsArray", e.getMessage());
			return new Method[]{};
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<String> getReadOnlyPropertyNameList(final Class<?> classType) {
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
			Log.e(TAG + ".getReadOnlyPropertyNameList", e.getMessage());
			return new ArrayList<String>();
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<String> getReadOnlyPropertyNamesList(final Class<?> classType) {
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
		catch(Exception e) {
			Log.e(TAG + ".getReadOnlyPropertyNamesList", e.getMessage());
			return new ArrayList<String>();
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<String> getReadAndWritePropertyNamesList(final Class<?> classType) {
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
			Log.e(TAG + ".getReadAndWritePropertyNamesList", e.getMessage());
			return new ArrayList<String>();
		}
	}

	/**
	 *
	 * @param setMethodName
	 * @return
	 */
	public static String convertToGetMethodName(final String setMethodName) {
		return setMethodName.replaceFirst(REGEX_FIRST_CHARACTER_S, CHARACTER_G);
	}

	/**
	 *
	 * @param method
	 * @return
	 */
	public static String convertToGetMethodName(final Method method) {
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
	public static String convertToSetMethodName(final String nomeMetodoGet) {
		return nomeMetodoGet.replaceFirst(REGEX_FIRST_CHARACTER_G, CHARACTER_S);
	}

	/**
	 *
	 * @param method
	 * @return
	 */
	public static String convertToSetMethodName(final Method method) {
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
	public static String getGetMethodName(final String propertyName) {
		return convertToMethodName(propertyName, PREFIX_GET);
	}

	/**
	 *
	 * @param propertyName
	 * @param classType
	 * @return
	 */
	public static String getGetMethodName(final String propertyName, final Class<?> classType) {
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
	public static String getSetMethodName(final String propertyName) {
		return convertToMethodName(propertyName, PREFIX_SET);
	}

	/**
	 *
	 * @param propertyName
	 * @param _accessorMethodName
	 * @return
	 */
	private static String convertToMethodName(final String propertyName, final String _accessorMethodName) {
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
	public static Method getGetMethod(final String getMethodName, final Class<?> classType) {
		try {
			return classType.getMethod(getMethodName, YUtilReflection.DEFAULT_PARAM_ARRAY_CLASS_REFLECTION);
		} catch(Exception e) {
			Log.e(TAG + ".getGetMethod", e.getMessage());
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
	public static Method getSetMethod(final String setMethodName, final Class<?> classType, final Class<?>[] params) {
		try {
			return classType.getMethod(setMethodName, params);
		} catch(Exception e) {
			Log.e(TAG + ".getSetMethod", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param setMethodName
	 * @param classType
	 * @return
	 */
	public static Method getSetMethod(final String setMethodName, final Class<?> classType) {
		try {
			return classType.getMethod(setMethodName, DEFAULT_PARAM_ARRAY_CLASS_REFLECTION);
		} catch(Exception _e) {
			Log.e(TAG + ".getSetMethod", _e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param classFullyQualifiedName
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> convertToClass(final String classFullyQualifiedName) throws ClassNotFoundException {
		return Class.forName(classFullyQualifiedName);
	}

	/**
	 *
	 * @param fullyQualifiedName
	 * @return
	 */
	public static String getClassNameFromFullyQualifiedName(final String fullyQualifiedName) {
		return fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf(".") + 1);
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static boolean implementsCollection(final Class<?> classType) {
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
			Log.e(TAG + ".implementsCollection", e.getMessage());
			return false;
		}
	}

	/**
	 *
	 * @param classType
	 * @param className
	 * @return
	 */
	public static boolean isClassOfType(final Class<?> classType, final String className) {
		try {
			return YUtilReflection.getClassNameFromFullyQualifiedName(classType.toString()).equals(className);
		} catch(Exception e) {
			Log.e(TAG + ".isClassOfType", e.getMessage());
			return false;
		}
	}

	/**
	 *
	 * @param accessorMethodName
	 * @return
	 */
	public static String getPropertyName(final String accessorMethodName) {
		String accessorMethodNameTemp = null;
		try {
			if((accessorMethodName.startsWith(PREFIX_GET)) || (accessorMethodName.startsWith(PREFIX_SET)) || (accessorMethodName.startsWith(PREFIX_IS))) {

				accessorMethodNameTemp = accessorMethodName.replaceFirst(REGEX_ACCESSORS_GET_SET_IS, "");

				accessorMethodNameTemp = YUtilString.firstCharacterToLowerCase(accessorMethodNameTemp);

				return accessorMethodNameTemp;
			}

			return null;
		} catch (Exception e) {
			Log.e(TAG + ".getPropertyName", e.getMessage());
		}
		return accessorMethodNameTemp;
	}

	/**
	 *
	 * @param method
	 * @return
	 */
	public static String getPropertyName(final Method method) {
		try {
			String methodName = method.getName();
			return YUtilReflection.getPropertyName(methodName);
		} catch (Exception e) {
			Log.e(TAG + ".getPropertyName", e.getMessage());
		}
		return null;
	}

    /**
     *
     * @param classType
     * @return
     */
    public static List<String> getPropertyList(final Class<?> classType) {
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
        	Log.e(TAG + ".getPropertyList", e.getMessage());
            return new ArrayList<String>();
        }
    }

	/**
	 *
	 * @param collection
	 * @param value
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addToCollection(final Object collection, final Object value) {
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
	public static Object getCollectionInstance(final Class<?> collectionType) {
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
	public static boolean isList(final Class<?> listClass) {
		return (listClass.equals(java.util.List.class)
				|| listClass.equals(java.util.ArrayList.class));
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static List<Method> getDeclaredMethodsGetList(final Class<?> classType) {
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
			Log.e(TAG + ".getDeclaredMethodsGetList", e.getMessage());
			return new ArrayList<Method>();
		}
	}

	/**
	 *
	 * @param method
	 * @param objectTo
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	public static Object invokeMethodWithoutParams(final Method method, final Object objectTo) 
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException  {
		return method.invoke(objectTo, DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
	}

	/**
	 *
	 * @param _field
	 * @param classType
	 * @return
	 */
	public static Method getGetMethod(final Field _field, final Class<?> classType) {
		try {
			return classType.getMethod(_field.getName(), YUtilReflection.DEFAULT_PARAM_ARRAY_CLASS_REFLECTION);
		} catch(Exception e) {
			Log.e(TAG + ".getGetMethod", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param classType
	 * @return
	 */
	public static Map<Field, Method> buildDeclaredFieldMethodsMap(final Class<?> classType) {
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
	public static Map<Field, Object> buildDeclaredFieldValuesMap(final Object object) {
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
			Log.e(TAG + ".buildDeclaredFieldValuesMap", e.getMessage());
		}
		return new HashMap<Field, Object>();
	}

	/**
	 *
	 * @param object
	 * @return
	 */
	public static Map<Field, Method> buildDeclaredFieldNotNullMethodsMap(final Object object) {
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
			Log.e(TAG + ".buildDeclaredFieldNotNullMethodsMap", e.getMessage());
		}
		return new HashMap<Field, Method>();
	}

	/**
	 *
	 * @param object
	 * @return
	 */
	public static Map<Field, Object> buildDeclaredFieldNotNullValuesMap(final Object object) {
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
			Log.e(TAG + ".buildDeclaredFieldNotNullValuesMap", e.getMessage());
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
			Log.e(TAG + ".getGenericSuperclassInstance", e.getMessage());
			return null;
		} catch (IllegalAccessException e) {
			Log.e(TAG + ".getGenericSuperclassInstance", e.getMessage());
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
			return (Class<?>) classType.getTypeParameters()[0].getGenericDeclaration();
//			final ParameterizedType parameterizedType = (ParameterizedType)classType.getGenericSuperclass();
//
//			return (Class<?>) parameterizedType.getActualTypeArguments()[0];
		} catch (Exception e) {
			Log.e(TAG + ".getGenericSuperclassClass", e.getMessage());
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
	
	public static Field getField(final Class<?> declaringClass, final Method targetMethod) {
		try {
			return declaringClass.getDeclaredField(YUtilReflection.getPropertyName(targetMethod));
		} catch (Exception e) {
			Log.e(TAG + ".getField", e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param declaringClass
	 * @param targetMethod
	 * @return
	 */
	public static Class<?> getGenericType(final Class<?> declaringClass, final Method targetMethod) {
		try {
			final Field targetField = getField(declaringClass, targetMethod);
			final ParameterizedType fieldListGenericType = (ParameterizedType) targetField.getGenericType();
			return (Class<?>) fieldListGenericType.getActualTypeArguments()[0];
		} catch (Exception e) {
			Log.e(TAG + ".getGenericType", e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param declaringClass
	 * @param declaringInterface
	 * @return
	 */
	public static boolean implementsInterface(final Class<?> declaringClass, final Class<?> declaringInterface) {
		boolean result = false;
		try {
			final Class<?>[] intarfaces = declaringClass.getInterfaces();
			if(intarfaces != null && intarfaces.length > 0) {
				for(Class<?> interf : intarfaces) {
					if(interf.equals(declaringInterface)) {
						result = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG + ".implementsInterface", e.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @param declaringClass
	 * @param declaringInterface
	 * @return
	 */
	public static boolean extendsClass(final Class<?> declaringClass, final Class<?> declaringInterface) {
		try {
			return declaringClass.getSuperclass().equals(declaringInterface);
		} catch (Exception e) {
			Log.e(TAG + ".extendsClass", e.getMessage());
			return false;
		}
	}
	

}