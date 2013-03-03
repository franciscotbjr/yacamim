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
		final YInnerRawDBAdapter yInnerRawDBAdapter = new YInnerRawDBAdapter(realClass);
		yInnerRawDBAdapter.open();
		final YRawData yRawData = yInnerRawDBAdapter.getRawDataById(id, new Method[]{targetGetMethod});
		yInnerRawDBAdapter.close();
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
		final YInnerRawDBAdapter yInnerRawDBAdapter = new YInnerRawDBAdapter(entityClass);
		YRawData yRawData = null;
		if(targetGetMethod.getAnnotation(Column.class) != null) {
			yInnerRawDBAdapter.open();
			yRawData = yInnerRawDBAdapter.getRawDataById((Long)parenrawData.get(propertyName), YUtilPersistence.getColumnGetMethodListSortedByNameAsArray(entityClass));
			yInnerRawDBAdapter.close();
		} else {
			final OneToOne oneToOne = targetGetMethod.getAnnotation(OneToOne.class);
			if(oneToOne != null && !YUtilString.isEmptyString(oneToOne.mappedBy())) {
				yInnerRawDBAdapter.open();
				@SuppressWarnings("unchecked")
				final List<YRawData> yRawDataList = yInnerRawDBAdapter.selectRawData(parentClass, parentId);
				yInnerRawDBAdapter.close();
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
	 * @see br.org.yacamim.dex.YInvocationHandler#getChildListYRawData(java.lang.reflect.Method, java.lang.Class, java.lang.Class, long)
	 */
	@Override
	protected List<YRawData> getChildListYRawData(final Method targetMethod, final Class<?> desiredEntityClass, final Class<?> parentClass, final long parenId) {
		List<YRawData> yRawDataList = null;
		if(YUtilReflection.isList(targetMethod.getReturnType())) {
			if(targetMethod.getAnnotation(ManyToMany.class) != null) {
				yRawDataList = this.handlesManyToMany(targetMethod, desiredEntityClass, parentClass, parenId);
			} else if (targetMethod.getAnnotation(OneToMany.class) != null) {
				final Method ownedMethod = YUtilPersistence.getBidirectionalOneToManyOwnedMethod(parentClass, desiredEntityClass, targetMethod);
				if(ownedMethod != null) {
					yRawDataList = handlesOneToMany(desiredEntityClass, parentClass, parenId);
				} else {
					yRawDataList = handlesOneToManyJoinWithTable(desiredEntityClass, parentClass, parenId);
				}
			}
		}
		return yRawDataList;
	}

	/**
	 * 
	 * @param desiredEntityClass
	 * @param parentClass
	 * @param parenId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<YRawData> handlesOneToMany(final Class<?> desiredEntityClass,
			final Class<?> parentClass, final long parenId) {
		List<YRawData> yRawDataList;
		final YInnerRawDBAdapter yInnerRawDBAdapter = new YInnerRawDBAdapter(desiredEntityClass);
		yInnerRawDBAdapter.open();
		yRawDataList = yInnerRawDBAdapter.selectRawData(parentClass, parenId);
		yInnerRawDBAdapter.close();
		return yRawDataList;
	}

	/**
	 * 
	 * @param desiredEntityClass
	 * @param parentClass
	 * @param parenId
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	private List<YRawData> handlesOneToManyJoinWithTable(final Class<?> desiredEntityClass, final Class<?> parentClass, final long parenId) {
		List<YRawData> yRawDataList;
		final YInnerRawDBAdapter yInnerRawDBAdapter = new YInnerRawDBAdapter(desiredEntityClass);
		yInnerRawDBAdapter.open();
		yRawDataList = yInnerRawDBAdapter.selectRawDataWithJoinTable(parentClass, desiredEntityClass, parenId, 0);
		yInnerRawDBAdapter.close();
		return yRawDataList;
	}

	/**
	 * 
	 * @param targetMethod
	 * @param desiredEntityClass
	 * @param parentClass
	 * @param parenId
	 * @param yRawDataList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<YRawData> handlesManyToMany(final Method targetMethod,
			final Class<?> desiredEntityClass, final Class<?> parentClass,
			final long parenId) {
		List<YRawData> yRawDataList = null;
		final ManyToMany manyToMany;
		if((manyToMany = targetMethod.getReturnType().getAnnotation(ManyToMany.class)) != null) {
			final YInnerRawDBAdapter yInnerRawDBAdapter = new YInnerRawDBAdapter(desiredEntityClass);
			yInnerRawDBAdapter.open();
			if(YUtilPersistence.isManyToManyOwner(manyToMany)) { 
				yRawDataList = yInnerRawDBAdapter.selectRawDataWithJoinTable(parentClass, desiredEntityClass, parenId, 0);
			} else {
				yRawDataList = yInnerRawDBAdapter.selectRawDataWithJoinTable(desiredEntityClass, parentClass, 0, parenId);
			}
			yInnerRawDBAdapter.close();
		}
		return yRawDataList;
	}

}
