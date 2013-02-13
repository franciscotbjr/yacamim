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

import br.org.yacamim.YRawData;
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
	@Override
	public Object invoke(final Object proxyTargetObject, final Method method, final Object[] args) throws Throwable {
		Object result = ProxyBuilder.callSuper(proxyTargetObject, method, args);
		if(!this.yMethodFilter.accept(method)) {
			return result;
		}
		if (result == null) {
			final Class<?> realClass = proxyTargetObject.getClass().getSuperclass();
//			final List<Method> getMethods = YUtilReflection.getGetMethodListSortedByName(realClass);
//			for(final Method getMethod : getMethods) {
//				if(getMethod.getName().equals(method.getName())) {
//					final Class<?> clazzEntity = getMethod.getReturnType();
					final Class<?> clazzEntity = method.getReturnType();
					if(checkTypeConstraint(clazzEntity)) {
						final Long longId = getTargetObjectId(proxyTargetObject);
						final YRawData targetObjectRawData= this.getTargetObjectYRawData(realClass, longId, method);
						final YRawData childRawData = this.getChildYRawData(clazzEntity, targetObjectRawData, method);
						
						result = ProxyBuilder.forClass(clazzEntity)
								.handler(this)
								.build();
						
						this.fillChild(result, childRawData);
						
						try {
							final Method setMethod = YUtilReflection.getSetMethod(YUtilReflection.getSetMethodName(YUtilReflection.getPropertyName(method)), proxyTargetObject.getClass(), new Class<?>[]{clazzEntity});
							YUtilReflection.invokeMethod(setMethod, proxyTargetObject, result);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
//				}
//			}
		}
		return result;
	}

	/**
	 * 
	 * @param clazzEntity
	 * @return
	 */
	protected abstract boolean checkTypeConstraint(final Class<?> clazzEntity);

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
	protected abstract YRawData getChildYRawData(final Class<?> clazzEntity, final YRawData parenrawData, final Method targetGetMethod);
	
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
