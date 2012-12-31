/**
 * YacamimInitializer.java
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

import android.util.Log;
import br.org.yacamim.util.YacamimClassMapping;
import br.org.yacamim.xml.YLoader;


/**
 * 
 * Class YacamimInitializer TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YacamimInitializer {
	
	private static final YacamimInitializer singleton = new YacamimInitializer();
	
	/**
	 * Reference to YacamimResources singleton.
	 */
	private YacamimResources yacamimResources = YacamimResources.getInstance();
	
	/**
	 * Reference to YacamimResources singleton.
	 */
	private YacamimClassMapping yacamimClassMapping = YacamimClassMapping.getInstance();
	
	
	
	private boolean initialized = false;

	/**
	 * 
	 */
	private YacamimInitializer() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static YacamimInitializer getInstance() {
		return YacamimInitializer.singleton;
	}
	
	/**
	 * 
	 */
	void initialize(final YBaseActivity yBaseActivity) {
		if(!this.isInitialized()) {
			
			this.loadConfigs(yBaseActivity);
			this.loadServiceURLs(yBaseActivity);
			this.loadClassMapping(yBaseActivity);
			this.loadParams(yBaseActivity);
			
			this.setInitialized(true);
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * 
	 * @param initialized
	 */
	private void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}
	
	
	/**
	 * ------------------------------------------------------------------------------------------------------------
	 * ------------------------------------------------------------------------------------------------------------
	 * ------------------------------------------------------------------------------------------------------------
	 * Loader Methods
	 * 
	 */
	
	
	/**
	 * 
	 * @param yBaseActivity
	 */
	private void loadConfigs(final YBaseActivity yBaseActivity) {
		try {
			if(!YacamimInitializer.getInstance().isInitialized()) {
				YLoader.loadConfigs(yBaseActivity);
			}
		} catch (Exception _e) {
			YacamimConfig.getInstance().clearEntities();
			Log.e("YacamimInitializer.loadConfigs", _e.getMessage());
		}
	}

	/**
	 * 
	 * @param yBaseActivity
	 */
	private void loadServiceURLs(final YBaseActivity yBaseActivity) {
		try {
			if(!YacamimState.getInstance().isServiceUrlsLoaded()) {
				YLoader.loadServiceURLs(yBaseActivity);
			}
		} catch (Exception _e) {
			Log.e("YacamimInitializer.loadServiceURLs", _e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param yBaseActivity
	 */
	private void loadClassMapping(final YBaseActivity yBaseActivity) {
		try {
			if(!YacamimState.getInstance().isClassMappingLoaded()) {
				YLoader.loadClassMapping(yBaseActivity, this.yacamimClassMapping);
			}
		} catch (Exception _e) {
			Log.e("YacamimInitializer.loadClassMapping", _e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param yBaseActivity
	 */
	private void loadParams(final YBaseActivity yBaseActivity) {
		try {
			if(!YacamimState.getInstance().isParamsLoaded()) {
				YLoader.loadParams(yBaseActivity);
			}
		} catch (Exception _e) {
			Log.e("YacamimInitializer.loadParams", _e.getMessage());
		}
	}
	
	
	
	
	/**
	 * ------------------------------------------------------------------------------------------------------------
	 * ------------------------------------------------------------------------------------------------------------
	 * ------------------------------------------------------------------------------------------------------------
	 * Delegates to YacamimResources setters methods.
	 */

	/**
	 * 
	 * @param idResourceServices
	 * @return
	 */
	public YacamimResources setIdResourceYServices(final int idResourceServices) {
		return yacamimResources.setIdResourceYServices(idResourceServices);
	}

	/**
	 * 
	 * @param idResourceYDbScript
	 * @return
	 */
	public YacamimResources setIdResourceYDbScript(final int idResourceYDbScript) {
		return yacamimResources.setIdResourceYDbScript(idResourceYDbScript);
	}

	/**
	 * 
	 * @param idResourceYClassMapping
	 * @return
	 */
	public YacamimResources setIdResourceYClassMapping(final int idResourceYClassMapping) {
		return yacamimResources
				.setIdResourceYClassMapping(idResourceYClassMapping);
	}

	/**
	 * 
	 * @param idResourceYParams
	 * @return
	 */
	public YacamimResources setIdResourceYParams(final int idResourceYParams) {
		return yacamimResources.setIdResourceYParams(idResourceYParams);
	}

	/**
	 * 
	 * @param idResourceYConfig
	 * @return
	 */
	public YacamimResources setIdResourceYConfig(final int idResourceYConfig) {
		return yacamimResources.setIdResourceYConfig(idResourceYConfig);
	}

	/**
	 * 
	 * @param idResourceMsgOK
	 * @return
	 */
	public YacamimResources setIdResourceMsgOK(final int idResourceMsgOK) {
		return yacamimResources.setIdResourceMsgOK(idResourceMsgOK);
	}

	/**
	 * 
	 * @param idResourceMsgSuccesfullyInserted
	 * @return
	 */
	public YacamimResources setIdResourceMsgSuccesfullyInserted(final int idResourceMsgSuccesfullyInserted) {
		return yacamimResources
				.setIdResourceMsgSuccesfullyInserted(idResourceMsgSuccesfullyInserted);
	}

	/**
	 * 
	 * @param idResourceMsgSuccesfullyUpdated
	 * @return
	 */
	public YacamimResources setIdResourceMsgSuccesfullyUpdated(final int idResourceMsgSuccesfullyUpdated) {
		return yacamimResources
				.setIdResourceMsgSuccesfullyUpdated(idResourceMsgSuccesfullyUpdated);
	}

	/**
	 * 
	 * @param idResourceMsgInvalidData
	 * @return
	 */
	public YacamimResources setIdResourceMsgInvalidData(final int idResourceMsgInvalidData) {
		return yacamimResources
				.setIdResourceMsgInvalidData(idResourceMsgInvalidData);
	}

	/**
	 * 
	 * @param idResourceMsgSelectAnItem
	 * @return
	 */
	public YacamimResources setIdResourceMsgSelectAnItem(final int idResourceMsgSelectAnItem) {
		return yacamimResources
				.setIdResourceMsgSelectAnItem(idResourceMsgSelectAnItem);
	}

	/**
	 * 
	 * @param idResourceMsgWait
	 * @return
	 */
	public YacamimResources setIdResourceMsgWait(int idResourceMsgWait) {
		return yacamimResources.setIdResourceMsgWait(idResourceMsgWait);
	}

	/**
	 * 
	 * @param idResourceMsgNoInformationFound
	 * @return
	 */
	public YacamimResources setIdResourceMsgNoInformationFound(final int idResourceMsgNoInformationFound) {
		return yacamimResources
				.setIdResourceMsgNoInformationFound(idResourceMsgNoInformationFound);
	}

	/**
	 * 
	 * @param idResourceMsgNoRecordsFoundForParameters
	 * @return
	 */
	public YacamimResources setIdResourceMsgNoRecordsFoundForParameters(final int idResourceMsgNoRecordsFoundForParameters) {
		return yacamimResources
				.setIdResourceMsgNoRecordsFoundForParameters(idResourceMsgNoRecordsFoundForParameters);
	}

	/**
	 * 
	 * @param idResourceMsgNoRecordSelected
	 * @return
	 */
	public YacamimResources setIdResourceMsgNoRecordSelected(final int idResourceMsgNoRecordSelected) {
		return yacamimResources
				.setIdResourceMsgNoRecordSelected(idResourceMsgNoRecordSelected);
	}

	/**
	 * 
	 * @param idResourceMsgNoConnectivityAvailable
	 * @return
	 */
	public YacamimResources setIdResourceMsgNoConnectivityAvailable(final int idResourceMsgNoConnectivityAvailable) {
		return yacamimResources
				.setIdResourceMsgNoConnectivityAvailable(idResourceMsgNoConnectivityAvailable);
	}

	/**
	 * 
	 * @param idResourceMsgNoWifiConnectivityAvailable
	 * @return
	 */
	public YacamimResources setIdResourceMsgNoWifiConnectivityAvailable(final int idResourceMsgNoWifiConnectivityAvailable) {
		return yacamimResources
				.setIdResourceMsgNoWifiConnectivityAvailable(idResourceMsgNoWifiConnectivityAvailable);
	}

	/**
	 * 
	 * @param idResourceMsgConstraintDependency
	 * @return
	 */
	public YacamimResources setIdResourceMsgConstraintDependency(final int idResourceMsgConstraintDependency) {
		return yacamimResources
				.setIdResourceMsgConstraintDependency(idResourceMsgConstraintDependency);
	}


}
