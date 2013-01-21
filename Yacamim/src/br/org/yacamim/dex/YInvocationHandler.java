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
import java.util.List;

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
			final Class<?> superClass = proxyTargetObject.getClass().getSuperclass();
			final List<Method> getMethods = YUtilReflection.getGetMethodListSortedByName(superClass);
			for(final Method getMethod : getMethods) {
				if(getMethod.getName().equals(method.getName())) {
					final Class<?> clazzEntity = getMethod.getReturnType();
					if(checkTypeConstraint(clazzEntity)) {
						final Long longId = getParentId(proxyTargetObject);
						final YRawData parentrawData= this.getParentYRawData(proxyTargetObject, longId);
						final YRawData childRawData = this.getChildYRawData(clazzEntity, parentrawData);
						
						result = ProxyBuilder.forClass(clazzEntity)
								.handler(this)
								.build();
						
						this.fillChild(result, childRawData);
					}
				}
			}
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
	 * @param proxyTargetObject 
	 * @param id
	 * @return
	 */
	protected abstract YRawData getParentYRawData(final Object proxyTargetObject, final Long id);
	
	/**
	 * 
	 * @param parenrawData
	 * @param result
	 * @return
	 */
	protected abstract YRawData getChildYRawData(final Class<?> clazzEntity, final YRawData parenrawData);
	
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
	private Long getParentId(final Object proxyTargetObject)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		final Class<?> proxyClass = proxyTargetObject.getClass();
		final Method getIDMethod = proxyClass.getMethod("getId");
		final Object oLongId = getIDMethod.invoke(proxyTargetObject, new Object[]{});
		return (Long)oLongId;
	}


}
