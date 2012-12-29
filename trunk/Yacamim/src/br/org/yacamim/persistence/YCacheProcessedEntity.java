/**
 * YCacheProcessedEntity.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*
* Class YCacheProcessedEntity TODO
*
* @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
* @version 1.0
* @since 1.0
*/
final class YCacheProcessedEntity {
	
	private static final YCacheProcessedEntity singleton = new YCacheProcessedEntity();
	
	private List<YProcessedEntity> processedEntities = new ArrayList<YProcessedEntity>();
	private Map<Class<?>, YProcessedEntity> processedEntitiesMap = new HashMap<Class<?>, YProcessedEntity>();

	private boolean terminated = false;

	/**
	 * 
	 */
	private YCacheProcessedEntity() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	static YCacheProcessedEntity getInstance() throws YCacheProcessedEntityTerminatedException {
		if(YCacheProcessedEntity.singleton.isTerminated()) {
			throw new YCacheProcessedEntityTerminatedException("CacheProcessedEntity has been terminated!");
		}
		return YCacheProcessedEntity.singleton;
	}
	
	/**
	 * 
	 * @param processedEntity
	 */
	void addProcessedEntity(final YProcessedEntity processedEntity) {
		this.processedEntities.add(processedEntity);
		this.processedEntitiesMap.put(processedEntity.getClass(), processedEntity);
	}
	
	/**
	 * 
	 * @return
	 */
	List<YProcessedEntity> getProcessedEntities() {
		return new ArrayList<YProcessedEntity>(this.processedEntities);
	}
	
	/**
	 * 
	 * @return
	 */
	YProcessedEntity getYProcessedEntity(final Class<?> classe) {
		return this.processedEntitiesMap.get(classe);
	}
	
	/**
	 * 
	 * @param classe
	 * @return
	 */
	boolean contains(final Class<?> classe) {
		return this.processedEntitiesMap.containsKey(classe);
	}
	
	/**
	 * 
	 */
	void terminate() {
		this.processedEntities = null;
		this.processedEntitiesMap = null;
		this.terminated = true;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isTerminated() {
		return terminated;
	}

}
