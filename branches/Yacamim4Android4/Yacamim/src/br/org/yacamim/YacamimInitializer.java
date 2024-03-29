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
	void initialize(final YApplication yApplication) {
		if(!this.isInitialized()) {
			
			this.loadConfigs(yApplication);

			// TODO - Revisar
//			this.loadServiceURLs(yBaseActivity);
//			this.loadClassMapping(yBaseActivity);
//			this.loadParams(yBaseActivity);
			
			this.setInitialized(true);
			
			if(YacamimConfig.getInstance().usesDBLoad()) {
				this.loadDBLoad(yApplication);
			}
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
	 * @param yApplication
	 */
	private void loadConfigs(final YApplication yApplication) {
		try {
			if(!YacamimInitializer.getInstance().isInitialized()) {
				YLoader.loadConfigs(yApplication);
			}
		} catch (Exception e) {
			YacamimConfig.getInstance().clearEntities();
			Log.e("YacamimInitializer.loadConfigs", e.getMessage());
		}
	}

	/**
	 * 
	 * @param yApplication
	 */
	private void loadDBLoad(final YApplication yApplication) {
		try {
			if(YacamimInitializer.getInstance().isInitialized()) {
				YLoader.loadDBLoad(yApplication);
			}
		} catch (Exception e) {
			YacamimConfig.getInstance().clearEntities();
			Log.e("YacamimInitializer.loadDBLoad", e.getMessage());
		}
	}

	/**
	 * 
	 * @param yApplication
	 */
	private void loadServiceURLs(final YApplication yApplication) {
		try {
			if(!YacamimState.getInstance().isServiceUrlsLoaded()) {
				YLoader.loadServiceURLs(yApplication);
			}
		} catch (Exception e) {
			Log.e("YacamimInitializer.loadServiceURLs", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param yApplication
	 */
	private void loadClassMapping(final YApplication yApplication) {
		try {
			if(!YacamimState.getInstance().isClassMappingLoaded()) {
				YLoader.loadClassMapping(yApplication, this.yacamimClassMapping);
			}
		} catch (Exception e) {
			Log.e("YacamimInitializer.loadClassMapping", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param yApplication
	 */
	private void loadParams(final YApplication yApplication) {
		try {
			if(!YacamimState.getInstance().isParamsLoaded()) {
				YLoader.loadParams(yApplication);
			}
		} catch (Exception e) {
			Log.e("YacamimInitializer.loadParams", e.getMessage());
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
