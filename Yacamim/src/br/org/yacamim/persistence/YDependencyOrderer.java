/**
 * YDependencyOrderer.java
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.org.yacamim.util.YUtilReflection;

/**
*
* Class YDependencyOrderer TODO
*
* @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
* @version 1.0
* @since 1.0
*/
class YDependencyOrderer {
	
	final int prime = 31;
	
	private Map<Class<?>, Long> mapEntityIds = new HashMap<Class<?>, Long>();

	/**
	 * 
	 */
	YDependencyOrderer() {
		super();
	}
	
	/**
	 * 
	 * @param unorderedList
	 * @return
	 */
	List<Class<?>> order(final List<Class<?>> unorderedList) {
		final List<Class<?>> orderedList = new ArrayList<Class<?>>();
		try {
			for(final Class<?> clazz : unorderedList) {
				if(!this.mapEntityIds.containsKey(clazz)) {
					this.mapEntityIds.put(clazz, this.getIdEntidade(clazz));
				}
			}
			orderedList.add(unorderedList.remove(0));
			for(final Class<?> clazz : unorderedList) {
				long entityId = this.mapEntityIds.get(clazz);
				boolean added = false;
				for(int i = 0; i < orderedList.size(); i++) {
					final long currentEntityId = this.mapEntityIds.get(orderedList.get(i));
					if(entityId <= currentEntityId) {
						orderedList.add(i, clazz);
						added = true;
						break;
					}
				}
				if(!added) {
					orderedList.add(clazz);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderedList;
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	private long getIdEntidade(final Class<?> clazz) {
		long idEntidade = 1;
		try {
			final List<Method> getMethods = YUtilReflection.getGetMethodList(clazz);
			for(final Method method : getMethods) {
				final Class<?> returnedType = method.getReturnType();
				if(returnedType.getAnnotation(Entity.class) != null && method.getAnnotation(Column.class) != null) {
					if(!this.mapEntityIds.containsKey(returnedType)) {
						this.mapEntityIds.put(returnedType, this.getIdEntidade(returnedType));
					}
					idEntidade = prime * idEntidade + this.mapEntityIds.get(returnedType);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idEntidade;
	}

}
