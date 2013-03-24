/**
 * YPersistenceInitializer.java
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
import java.util.List;

import android.util.Log;

/**
 * 
 * Class YPersistenceInitializer TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YPersistenceInitializer {
	
	private static final YPersistenceInitializer singleton = new YPersistenceInitializer();

	private List<String> entities = new ArrayList<String>();
	
	private boolean intialized = false;

	/**
	 * 
	 */
	private YPersistenceInitializer() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static YPersistenceInitializer getInstance() {
		return YPersistenceInitializer.singleton;
	}
	
	/**
	 * 
	 */
	public void runInitializer() {
		if(!this.intialized) {
			try {
				
			} catch (Exception e) {
				Log.e(YPersistenceInitializer.class.getName() + ".runInitializer", e.getMessage());
			}
		}
		this.intialized = true;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void addEntity(final String name) {
		if(!this.intialized) {
			this.entities.add(name);
		}
	}

}
