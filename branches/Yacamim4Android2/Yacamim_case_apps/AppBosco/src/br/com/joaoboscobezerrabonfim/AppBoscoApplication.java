/**
 * AppBoscoApplication.java
 *
 * Copyright 2013 br.com.joaoboscobezerrabonfim
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
package br.com.joaoboscobezerrabonfim;

import android.util.Log;
import br.org.yacamim.YApplication;
import br.org.yacamim.YacamimInitializer;
import br.org.yacamim.YacamimState;

/**
 * 
 * Class AppBoscoApplication TODO
 * 
 * @author br.com.joaoboscobezerrabonfim (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class AppBoscoApplication extends YApplication {
	
	private static final String TAG = AppBoscoApplication.class.getSimpleName();

	/**
	 * 
	 */
	public AppBoscoApplication() {
		super();
	}

	/**
	 * 
	 * @see br.org.yacamim.YApplication#initApp()
	 */
	@Override
	protected void initApp() {
		try {
    		YacamimState.getInstance().setPreferencesToken("__y_prefs_#_token_@_joaoboscobezerrabonfim.com.br_::");

    		YacamimInitializer.getInstance()
    		.setIdResourceMsgOK(R.string.y_MsgOK)
    		.setIdResourceMsgSuccesfullyInserted(R.string.y_MsgSuccesfullyInserted)
    		.setIdResourceMsgSuccesfullyUpdated(R.string.y_MsgSuccesfullyUpdated)
    		.setIdResourceMsgInvalidData(R.string.y_MsgInvalidData)
    		.setIdResourceMsgWait(R.string.y_MsgWait)
    		.setIdResourceMsgSelectAnItem(R.string.y_MsgSelectAnItem)
    		.setIdResourceMsgNoInformationFound(R.string.y_MsgNoInformationFound)
    		.setIdResourceMsgNoRecordsFoundForParameters(R.string.y_MsgNoRecordsFoundForParameters)
    		.setIdResourceMsgNoRecordSelected(R.string.y_MsgNoRecordSelected)
    		.setIdResourceMsgNoConnectivityAvailable(R.string.y_MsgNoConnectivityAvailable)
    		.setIdResourceMsgNoWifiConnectivityAvailable(R.string.y_MsgNoWifiConnectivityAvailable)
    		.setIdResourceMsgConstraintDependency(R.string.y_MsgConstraintDependency)
    		;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

}
