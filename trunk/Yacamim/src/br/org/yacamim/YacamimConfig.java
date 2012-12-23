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

import java.util.HashMap;
import java.util.Map;

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

	private Map<String, String> configItems = new HashMap<String, String>();

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
	 * @return the params
	 */
	public Map<String, String> getConfigItems() {
		return configItems;
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

}
