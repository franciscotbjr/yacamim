/**
 * YInvocationHandler.java
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
package br.org.yacamim.dex;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import br.org.yacamim.YRawData;
import br.org.yacamim.util.YList;
import br.org.yacamim.util.YUtilReflection;

import com.google.dexmaker.stock.ProxyBuilder;

/**
 * 
 * Class YInvocationHandler TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public abstract class YInvocationHandler implements InvocationHandler {
	
	private YMethodFilter yMethodFilter;

	public YInvocationHandler(final YMethodFilter yMethodFilter) {
		super();
		this.yMethodFilter = yMethodFilter;
	}

	/**
	 * 
	 * 
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object invoke(final Object proxyTargetObject, final Method targetMethod, final Object[] args) throws Throwable {
		Object result = ProxyBuilder.callSuper(proxyTargetObject, targetMethod, args);
		if(!this.yMethodFilter.accept(targetMethod)) {
			return result;
		}
		if (result == null) {
			final Class<?> realClass = proxyTargetObject.getClass().getSuperclass();
			final Class<?> clazzEntity = targetMethod.getReturnType();
			final Long longId = getTargetObjectId(proxyTargetObject);
			if(checkTypeConstraint(clazzEntity)) {
				
					final YRawData targetObjectRawData= this.getTargetObjectYRawData(realClass, longId, targetMethod);
					final YRawData childRawData = this.getChildYRawData(clazzEntity, targetObjectRawData, targetMethod, realClass, longId);

					result = ProxyBuilder.forClass(clazzEntity)
							.handler(this)
							.build();
					
					this.fillChild(result, childRawData);
				
			} else if(YUtilReflection.isList(clazzEntity)) {
				result = new YList();
				final List<YRawData> childListRawData = this.getChildListYRawData(YUtilReflection.getGenericType(realClass, targetMethod), realClass, longId);
				if(childListRawData != null) {
					final Class<?> genericType = YUtilReflection.getGenericType(realClass, targetMethod);
					@SuppressWarnings("unchecked")
					final List<Object> resultAsList = (List<Object>)result;
					
					for(final YRawData rawData : childListRawData) {
						final Object child = ProxyBuilder.forClass(genericType)
								.handler(this)
								.build();
						this.fillChild(child, rawData);
						resultAsList.add(child);
					}
				}
			}
			
			this.invokeSet(proxyTargetObject, targetMethod, result, clazzEntity);
			
			this.handlePosConstruction(proxyTargetObject, targetMethod, args, result);
		}
		return result;
	}

	/**
	 * 
	 * @param proxyTargetObject
	 * @param targetMethod
	 * @param result
	 * @param clazzEntity
	 */
	private void invokeSet(final Object proxyTargetObject, final Method targetMethod, Object result, final Class<?> clazzEntity) {
		try {
			final Method setMethod = YUtilReflection.getSetMethod(YUtilReflection.getSetMethodName(YUtilReflection.getPropertyName(targetMethod)), proxyTargetObject.getClass(), new Class<?>[]{clazzEntity});
			YUtilReflection.invokeMethod(setMethod, proxyTargetObject, result);
		} catch (Exception e) {
			Log.e("YInvocationHandler.invokeSet", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param proxyTargetObject
	 * @param targetMethod
	 * @param args
	 * @param result
	 * @return
	 */
	protected abstract void handlePosConstruction(final Object proxyTargetObject, final Method targetMethod, final Object[] args, final Object result);

	/**
	 * 
	 * @param entityClass
	 * @return
	 */
	protected abstract boolean checkTypeConstraint(final Class<?> entityClass);

	/**
	 * 
	 * @param realClass
	 * @param id
	 * @param targetGetMethod
	 * @return
	 */
	protected abstract YRawData getTargetObjectYRawData(final Class<?> realClass, final Long id, final Method targetGetMethod);
	
	/**
	 * 
	 * @param parenrawData
	 * @param result
	 * @return
	 */
	protected abstract YRawData getChildYRawData(final Class<?> entityClass, final YRawData parenrawData, final Method targetGetMethod, final Class<?> parentClass, final long parentId);
	
	/**
	 * 
	 * @param entityClass
	 * @param parentClass
	 * @param parenrawId
	 * @param targetGetMethod
	 * @return
	 */
	protected abstract List<YRawData> getChildListYRawData(final Class<?> entityClass, final Class<?> parentClass, final long parenrawId);
	
	/**
	 * 
	 * @param result
	 * @param childRawData
	 */
	protected abstract void fillChild(Object result, YRawData childRawData);
	
	/**
	 * 
	 * @param proxyTargetObject
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected abstract Long getTargetObjectId(final Object proxyTargetObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

}
