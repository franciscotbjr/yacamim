/**
 * YacamimConfig.java
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
package br.org.yacamim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

/**
 * 
 * Class YacamimState TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YacamimConfig {
	
	private static final YacamimConfig singleton = new YacamimConfig();

	private Map<String, String> mConfigItems = new HashMap<String, String>();
	
	private List<Class<?>> entities = new ArrayList<Class<?>>();
	
	private boolean configItemsLoaded = false;

	/**
	 * 
	 */
	private YacamimConfig() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static YacamimConfig getInstance() {
		return YacamimConfig.singleton;
	}
	
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getConfigItem(final String key) {
		if(this.isConfigItemsLoaded()) {
			return this.getConfigItems().get(key);
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, String> getConfigItems() {
		// Returns a clone
		return new HashMap<String, String>(this.mConfigItems);
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void add(final String key, final String value) {
		if(!this.isConfigItemsLoaded()) {
			this.mConfigItems.put(key, value);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getYSqliteTrue() {
		return this.getConfigItems().get(YacamimKeys.YACAMIM_SQLITE_BOOLEAN_TRUE.toString());
	}
	
	/**
	 * 
	 * @return
	 */
	public String getYSqliteFalse() {
		return this.getConfigItems().get(YacamimKeys.YACAMIM_SQLITE_BOOLEAN_FALSE.toString());
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPathToDirCapturedImages() {
		return this.getConfigItems().get(YacamimKeys.YACAMIM_DIR_CAPTURED_IMAGES.toString());
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean usesPersistence() {
		final String value = this.getConfigItems().get(YacamimKeys.YACAMIM_USES_PERSISTENCE.toString());
		return (value != null && Boolean.valueOf(value));
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean usesLazyProxy() {
		final String value = this.getConfigItems().get(YacamimKeys.YACAMIM_USES_LAZY_PROXY.toString());
		return (value != null && Boolean.valueOf(value));
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean usesDBScript() {
		final String value = this.getConfigItems().get(YacamimKeys.YACAMIM_USES_DB_SCRIPT.toString());
		return (value != null && Boolean.valueOf(value));
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean usesDBLoad() {
		final String value = this.getConfigItems().get(YacamimKeys.YACAMIM_USES_DB_LOAD.toString());
		return (value != null && Boolean.valueOf(value));
	}
	
	/**
	 * 
	 * @return
	 */
	public int getDbVersion() {
		try {
			final String value = this.getConfigItems().get(YacamimKeys.YACAMIM_DB_VERSION.toString());
			if(value != null) {
				return Integer.valueOf(value);
			}
		} catch (Exception e) {
			Log.e("YacamimConfig.getDbVersion", e.getMessage());
		}
		return -1;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDbName() {
		return this.getConfigItems().get(YacamimKeys.YACAMIM_DB_NAME.toString());
	}

	/**
	 * 
	 * @return
	 */
	public boolean isConfigItemsLoaded() {
		return configItemsLoaded;
	}

	/**
	 * 
	 * @param configItemsLoaded
	 */
	public void setConfigItemsLoaded(boolean configItemsLoaded) {
		// May be changed only once
		if(!this.configItemsLoaded) {
			this.configItemsLoaded = configItemsLoaded;
		}
	}
	
	/**
	 * 
	 * @param clazz
	 */
	public void addEntity(final Class<?> clazz) {
		this.entities.add(clazz);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Class<?>> getEntities() {
		return new ArrayList<Class<?>>(this.entities);
	}

	/**
	 * 
	 */
	void clearEntities() {
		this.entities.clear();
	}
}
