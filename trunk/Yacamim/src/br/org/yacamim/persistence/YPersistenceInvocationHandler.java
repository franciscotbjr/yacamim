/**
 * YPersistenceInvocationHandler.java
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.util.Log;
import br.org.yacamim.YRawData;
import br.org.yacamim.dex.YInvocationHandler;
import br.org.yacamim.dex.YMethodFilter;
import br.org.yacamim.util.YUtilReflection;
import br.org.yacamim.util.YUtilString;

/**
 * 
 * Class YPersistenceInvocationHandler TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YPersistenceInvocationHandler extends YInvocationHandler {

	/**
	 * 
	 * @param yMethodFilter
	 */
	public YPersistenceInvocationHandler(final YMethodFilter yMethodFilter) {
		super(yMethodFilter);
	}

	/**
	 * 
	 * @see br.org.yacamim.dex.YInvocationHandler#checkTypeConstraint(java.lang.Class)
	 */
	@Override
	protected boolean checkTypeConstraint(final Class<?> entityClass) {
		return YUtilPersistence.isEntity(entityClass);
	}

	/**
	 * 
	 * @see br.org.yacamim.dex.YInvocationHandler#getTargetObjectYRawData(java.lang.Class, java.lang.Long, java.lang.reflect.Method)
	 */
	@Override
	protected YRawData getTargetObjectYRawData(final Class<?> realClass, final Long id, final Method targetGetMethod) {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(realClass);
		defaultDBAdapter.open();
		final YRawData yRawData = defaultDBAdapter.getRawDataById(id, new Method[]{targetGetMethod});
		defaultDBAdapter.close();
		return yRawData;
	}

	/**
	 * 
	 * @see br.org.yacamim.dex.YInvocationHandler#getChildYRawData(java.lang.Class, br.org.yacamim.YRawData, java.lang.reflect.Method, java.lang.Class, long)
	 */
	@Override
	protected YRawData getChildYRawData(final Class<?> entityClass, final YRawData parenrawData, final Method targetGetMethod,
			final Class<?> parentClass, final long parentId) {
		final String propertyName = YUtilReflection.getPropertyName(targetGetMethod);
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(entityClass);
		YRawData yRawData = null;
		if(targetGetMethod.getAnnotation(Column.class) != null) {
			defaultDBAdapter.open();
			yRawData = defaultDBAdapter.getRawDataById((Long)parenrawData.get(propertyName), YUtilPersistence.getColumnGetMethodListSortedByNameAsArray(entityClass));
			defaultDBAdapter.close();
		} else {
			final OneToOne oneToOne = targetGetMethod.getAnnotation(OneToOne.class);
			if(oneToOne != null && !YUtilString.isEmptyString(oneToOne.mappedBy())) {
				defaultDBAdapter.open();
				@SuppressWarnings("unchecked")
				final List<YRawData> yRawDataList = defaultDBAdapter.selectRawData(parentClass, parentId);
				defaultDBAdapter.close();
				if(yRawDataList != null) {
					yRawData = yRawDataList.get(0);
				}
			}
		}
		return yRawData;
	}

	/**
	 * 
	 * @see br.org.yacamim.dex.YInvocationHandler#fillChild(java.lang.Object, br.org.yacamim.YRawData)
	 */
	@Override
	protected void fillChild(final Object result, final YRawData childRawData) {
		final List<String> keys = childRawData.getKeys();
		for(final String propertyName : keys) {
			final Class<?> returnType = YUtilReflection.getGetMethod(YUtilReflection.getGetMethodName(propertyName), result.getClass()).getReturnType();
			if(!YUtilPersistence.isEntity(returnType)) {
				YUtilReflection.setValueToProperty(
						propertyName,
						childRawData.get(propertyName),
						result);
			}
		}
	}

	/**
	 * 
	 * @see br.org.yacamim.dex.YInvocationHandler#getTargetObjectId(java.lang.Object)
	 */
	@Override
	protected Long getTargetObjectId(final Object proxyTargetObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		final Method getIDMethod = YUtilPersistence.getGetIdMethod(proxyTargetObject.getClass().getSuperclass());
		final Object oLongId = getIDMethod.invoke(proxyTargetObject, new Object[]{});
		return (Long)oLongId;
	}

	/**
	 * 
	 */
	@Override
	protected void handlePosConstruction(final Object proxyTargetObject, final Method targetMethod, final Object[] args, final Object result) {
		final Class<?> realClass = proxyTargetObject.getClass().getSuperclass();
		final Method[] typeGetMethods = YUtilReflection.getGetMethodArray(result.getClass().getSuperclass());
		// Bidirectional OneToOne      
		this.handleBidirectionalOneToOne(proxyTargetObject, targetMethod, result, realClass, typeGetMethods);
		
	}

	/**
	 * 
	 * @param proxyTargetObject
	 * @param targetMethod
	 * @param result
	 * @param realClass
	 * @param typeGetMethods
	 */
	private void handleBidirectionalOneToOne(final Object proxyTargetObject,
			final Method targetMethod, final Object result, final Class<?> realClass,
			final Method[] typeGetMethods) {
		final Method bidirectionalOneToOneReferenceMethod = YUtilPersistence.getBidirectionalOneToOneOwnedMethod(typeGetMethods, realClass, targetMethod);
		if(bidirectionalOneToOneReferenceMethod != null) { // It is an Bidirectional Relationship
			this.executeSet(
					YUtilReflection.getSetMethodName(YUtilReflection.getPropertyName(bidirectionalOneToOneReferenceMethod)), 
					result.getClass().getSuperclass(), 
					realClass, 
					result, 
					proxyTargetObject);
		} else {
			for(final Method typeGetMethod : typeGetMethods) {
				if(YUtilPersistence.isInvalidBidirectionalOneToOneOwnerMethod(typeGetMethod)) {
					final String propertyName = YUtilReflection.getPropertyName(typeGetMethod);
					final OneToOne oneToOne = targetMethod.getAnnotation(OneToOne.class);
					if(oneToOne != null 
							&& !YUtilString.isEmptyString(oneToOne.mappedBy())
							&& !YUtilString.isEmptyString(propertyName)
							&& oneToOne.mappedBy().equals(propertyName)) {
						this.executeSet(
								YUtilReflection.getSetMethodName(YUtilReflection.getPropertyName(typeGetMethod)), 
								result.getClass().getSuperclass(), 
								realClass, 
								result, 
								proxyTargetObject);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param setMethodName
	 * @param targetClass
	 * @param paramClass
	 * @param targetObject
	 * @param valueObject
	 */
	private void executeSet(final String setMethodName, final Class<?> targetClass, final Class<?> paramClass, final Object targetObject, final Object valueObject) {
		try {
			final Method setMethod = YUtilReflection.getSetMethod(
												setMethodName, 
												targetClass,
												new Class[]{paramClass}
												);
			YUtilReflection.invokeMethod(setMethod, targetObject, valueObject);
		} catch (Exception e) {
			Log.e("YPersistenceInvocationHandler.executeSet", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @see br.org.yacamim.dex.YInvocationHandler#getChildListYRawData(java.lang.Class, java.lang.Class, long)
	 */
	@Override
	protected List<YRawData> getChildListYRawData(final Class<?> entityClass, final Class<?> parentClass, final long parenId) {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(entityClass);
		defaultDBAdapter.open();
		@SuppressWarnings("unchecked")
		final List<YRawData> yRawDataList = defaultDBAdapter.selectRawData(parentClass, parenId);
		defaultDBAdapter.close();
		return yRawDataList;
	}

}
