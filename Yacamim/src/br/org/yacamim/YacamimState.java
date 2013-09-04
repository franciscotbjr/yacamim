/**
 * YacamimState.java
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
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import br.org.yacamim.entity.DeviceInfo;
import br.org.yacamim.entity.Preferences;
import br.org.yacamim.entity.ServiceURL;
import br.org.yacamim.entity.Session;
import br.org.yacamim.entity.User;
import br.org.yacamim.persistence.PreferencesAdapter;

/**
 * 
 * Class YacamimState TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YacamimState {

	private static final YacamimState singletonYacamimState = new YacamimState();
	
	private List<ServiceURL> serviceURLs = new ArrayList<ServiceURL>();
	
	private boolean serviceUrlsLoaded = false;

	private boolean classMappingLoaded = false;
	
	private boolean useDBScriptResource = false;
	
	private boolean paramsLoaded = false;
	
	private Preferences preferences;
	
	private boolean logoff = false;
	
	private Session session;
	
	private Context mCurrentContext;
	
	private DeviceInfo deviceInfo;
	
	private User loggedUser = null;
	
	private String preferencesToken = null;

	/**
	 * 
	 */
	private YacamimState() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static YacamimState getInstance() {
		return YacamimState.singletonYacamimState;
	}

	/**
	 * 
	 * @return
	 */
	public List<ServiceURL> getUrlServicos() {
		return serviceURLs;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ServiceURL getUrlServico(final int id) {
		for(ServiceURL serviceURL : serviceURLs) {
			if(serviceURL.id == id) {
				return serviceURL;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param activity
	 * @return
	 */
	public Preferences getPreferences(final Activity activity) {
		try {
			if(YacamimState.getInstance().preferences == null) {
				
				final PreferencesAdapter preferenciasAdapter = new PreferencesAdapter(activity);
				
				YacamimState.getInstance().setPreferences(preferenciasAdapter.get());
				
			}
		} catch (Exception _e) {
			Log.e("YacamimState.getPreferences", _e.getMessage());
		}
		return preferences;
	}

	/**
	 * 
	 * @param preferences
	 */
	private void setPreferences(final Preferences preferences) {
		this.preferences = preferences;
	}

	/**
	 * @return the deviceInfo
	 */
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	/**
	 * @param deviceInfo the deviceInfo to set
	 */
	public YacamimState setDeviceInfo(final DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isLogoff() {
		return logoff;
	}

	/**
	 * 
	 * @param _logoff
	 */
	public YacamimState setLogoff(final boolean _logoff) {
		this.logoff = _logoff;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isServiceUrlsLoaded() {
		return serviceUrlsLoaded;
	}

	/**
	 * 
	 * @param serviceUrlsLoaded
	 */
	public YacamimState setServiceUrlsLoaded(final boolean serviceUrlsLoaded) {
		// May be changed only once
		if(!this.serviceUrlsLoaded) {
			this.serviceUrlsLoaded = serviceUrlsLoaded;
		}
		return this;
	}

	/**
	 * @return the session
	 */
	protected Session getSessao() {
		return session;
	}

	/**
	 * @param _sessao the session to set
	 */
	protected YacamimState setSessao(final Session _sessao) {
		this.session = _sessao;
		return this;
	}

	/**
	 * @return the currentActivity
	 */
	public Context getCurrentContext() {
		return this.mCurrentContext;
	}

	/**
	 * @param currentContext the currentActivity to set
	 */
	protected YacamimState setCurrentContext(final Context currentContext) {
		this.mCurrentContext = currentContext;
		return this;
	}

	/**
	 * @return the classMappingLoaded
	 */
	public boolean isClassMappingLoaded() {
		return classMappingLoaded;
	}

	/**
	 * @param classMappingLoaded the classMappingLoaded to set
	 */
	public YacamimState setClassMappingLoaded(final boolean classMappingLoaded) {
		// May be changed only once
		if(!this.classMappingLoaded) {
			this.classMappingLoaded = classMappingLoaded;
		}
		return this;
	}

	/**
	 * @return the paramsLoaded
	 */
	public boolean isParamsLoaded() {
		return paramsLoaded;
	}

	/**
	 * @param paramsLoaded the paramsLoaded to set
	 */
	public YacamimState setParamsLoaded(final boolean paramsLoaded) {
		// May be changed only once
		if(!this.paramsLoaded) {
			this.paramsLoaded = paramsLoaded;
		}
		return this;
	}

	/**
	 * @return the loggedUser
	 */
	public User getLoggedUser() {
		return this.loggedUser;
	}

	/**
	 * @param loggedUser the loggedUser to set
	 */
	public YacamimState setLoggedUser(final User loggedUser) {
		this.loggedUser = loggedUser;
		return this;
	}

	/**
	 * @return the preferencesToken
	 */
	public String getPreferencesToken() {
		return preferencesToken;
	}

	/**
	 * @param preferencesToken the preferencesToken to set
	 */
	public YacamimState setPreferencesToken(final String preferencesToken) {
		this.preferencesToken = preferencesToken;
		return this;
	}
	
}