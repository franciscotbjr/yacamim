/**
 * ThreadResponseHandler.java
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
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Class ThreadResponseHandler TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YInvocationHandlerProxy {
	
	private static final YInvocationHandlerProxy singleton = new YInvocationHandlerProxy();
	
	private Map<Class<?>, InvocationHandler> mapInvocationHandler = new HashMap<Class<?>, InvocationHandler>();

	/**
	 * 
	 */
	private YInvocationHandlerProxy() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static YInvocationHandlerProxy getInstance() {
		return YInvocationHandlerProxy.singleton;
	}
	
	/**
	 * 
	 * @param clazz
	 * @param invocationHandler
	 */
	public void add(final Class<?> clazz, final InvocationHandler invocationHandler) {
		this.mapInvocationHandler.put(clazz, invocationHandler);
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public InvocationHandler get(final Class<?> clazz) {
		return this.mapInvocationHandler.get(clazz);
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public boolean contains(final Class<?> clazz) {
		return this.mapInvocationHandler.containsKey(clazz);
	}
	
	/**
	 * 
	 * @param invocationHandler
	 * @return
	 */
	public boolean contains(final InvocationHandler invocationHandler) {
		return this.mapInvocationHandler.containsValue(invocationHandler);
	}
	
}
