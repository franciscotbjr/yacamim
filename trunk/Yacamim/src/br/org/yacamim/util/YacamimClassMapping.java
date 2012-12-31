/**
 * YacamimClassMapping.java
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
package br.org.yacamim.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Class YacamimClassMapping TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YacamimClassMapping {
	
	/**
	 * 
	 */
	private static final YacamimClassMapping singleton = new YacamimClassMapping();

	private List<YKeyValuePair<String, String>> keyValues = new ArrayList<YKeyValuePair<String,String>>();

	/**
	 *
	 */
	private YacamimClassMapping() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static YacamimClassMapping getInstance() {
		return YacamimClassMapping.singleton;
	}

	/**
	 *
	 * @param _key
	 * @param _value
	 */
	public void add(final String _key, final String _value) {
		for(YKeyValuePair<String, String> keyValuePair : this.keyValues) {
			if(keyValuePair.getKey().equals(_key)) {
				keyValuePair.setValue(_value);
				return;
			}
		}
		this.keyValues.add(new YKeyValuePair<String, String>(_key, _value));
	}

	/**
	 *
	 * @param _key
	 * @return
	 */
	public String get(final String _key) {
		for(YKeyValuePair<String, String> keyValuePair : this.keyValues) {
			if(keyValuePair.getKey().equals(_key)) {
				return keyValuePair.getValue();
			}
		}
		return null;
	}

}
