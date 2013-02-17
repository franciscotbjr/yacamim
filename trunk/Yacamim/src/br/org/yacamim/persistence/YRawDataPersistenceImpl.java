/**
 * YRawDataPersistenceImpl.java
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

import br.org.yacamim.YRawData;

/**
 * Classe YRawDataPersistenceImpl TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
class YRawDataPersistenceImpl implements YRawData {
	
	private Map<String, Object> map = new HashMap<String, Object>();

	/**
	 * 
	 */
	public YRawDataPersistenceImpl() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getKeys() {
		return new ArrayList<String>(this.map.keySet());
	}

	/**
	 * 
	 * @see br.org.yacamim.YRawData#add(java.lang.String, java.lang.Object)
	 */
	void add(final String key, final Object value) {
		this.map.put(key, value);
	}

	/**
	 * 
	 * @see br.org.yacamim.YRawData#get(java.lang.String)
	 */
	@Override
	public Object get(final String key) {
		return this.map.get(key);
	}

}
