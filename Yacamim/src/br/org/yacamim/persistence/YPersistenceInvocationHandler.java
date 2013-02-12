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

import br.org.yacamim.YRawData;
import br.org.yacamim.dex.YInvocationHandler;
import br.org.yacamim.dex.YMethodFilter;

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
	protected boolean checkTypeConstraint(final Class<?> clazzEntity) {
		return YUtilPersistence.isEntity(clazzEntity);
	}

	/**
	 * 
	 * @see br.org.yacamim.dex.YInvocationHandler#getTargetObjectYRawData(java.lang.Class, java.lang.Long, java.lang.reflect.Method)
	 */
	@Override
	protected YRawData getTargetObjectYRawData(final Class<?> realClass, final Long id, final Method targetGetMethod) {
		final DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(realClass);
		defaultDBAdapter.open();
		defaultDBAdapter.getByID(id);
		defaultDBAdapter.close();
		return null;
	}

	/**
	 * 
	 * @see br.org.yacamim.dex.YInvocationHandler#getChildYRawData(java.lang.Class, br.org.yacamim.YRawData)
	 */
	@Override
	protected YRawData getChildYRawData(final Class<?> clazzEntity, final YRawData parenrawData) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @see br.org.yacamim.dex.YInvocationHandler#fillChild(java.lang.Object, br.org.yacamim.YRawData)
	 */
	@Override
	protected void fillChild(final Object result, final YRawData childRawData) {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 * @see br.org.yacamim.dex.YInvocationHandler#getTargetObjectId(java.lang.Object, java.util.List)
	 */
	@Override
	protected Long getTargetObjectId(final Object proxyTargetObject, final List<Method> getMethods) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		final Method getIDMethod = YUtilPersistence.getGetIdMethod(getMethods);
		final Object oLongId = getIDMethod.invoke(proxyTargetObject, new Object[]{});
		return (Long)oLongId;
	}

}
