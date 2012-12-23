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
import android.util.Log;
import br.org.yacamim.entity.DeviceInfo;
import br.org.yacamim.entity.Preferences;
import br.org.yacamim.entity.ServiceURL;
import br.org.yacamim.entity.Session;
import br.org.yacamim.entity.User;
import br.org.yacamim.persistence.PreferencesAdapter;
import br.org.yacamim.util.YacamimClassMapping;

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
	
	private YacamimClassMapping yacamimClassMapping = new YacamimClassMapping();
	
	private List<ServiceURL> serviceURLs = new ArrayList<ServiceURL>();
	
	private boolean serviceUrlsLoaded = false;

	private boolean classMappingLoaded = false;
	
	private boolean paramsLoaded = false;
	
	private Preferences preferences;
	
	private boolean logoff = false;
	
	private Session session;
	
	private BaseActivity currentActivity;
	
	private DeviceInfo deviceInfo;
	
	private User loggedUser = null;
	
	private String preferencesToken = null;

	private YacamimResources yacamimResources = new YacamimResources();

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
	 * @param _id
	 * @return
	 */
	public ServiceURL getUrlServico(final int _id) {
		for(ServiceURL serviceURL : serviceURLs) {
			if(serviceURL.id == _id) {
				return serviceURL;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param _activity
	 * @return
	 */
	public Preferences getPreferences(final Activity _activity) {
		try {
			if(YacamimState.getInstance().preferences == null) {
				
				final PreferencesAdapter preferenciasAdapter = new PreferencesAdapter(_activity);
				
				YacamimState.getInstance().setPreferences(preferenciasAdapter.get());
				
			}
		} catch (Exception _e) {
			Log.e("YacamimState.getPreferences", _e.getMessage());
		}
		return preferences;
	}

	/**
	 * 
	 * @param _preferences
	 */
	private void setPreferences(final Preferences _preferences) {
		this.preferences = _preferences;
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
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
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
	public void setLogoff(boolean _logoff) {
		this.logoff = _logoff;
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
	public void setServiceUrlsLoaded(boolean serviceUrlsLoaded) {
		this.serviceUrlsLoaded = serviceUrlsLoaded;
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
	protected void setSessao(final Session _sessao) {
		this.session = _sessao;
	}

	/**
	 * @return the currentActivity
	 */
	public BaseActivity getCurrentActivity() {
		return this.currentActivity;
	}

	/**
	 * @param currentActivity the currentActivity to set
	 */
	protected void setCurrentActivity(final BaseActivity currentActivity) {
		this.currentActivity = currentActivity;
	}

	/**
	 * @return the yacamimClassMapping
	 */
	public YacamimClassMapping getMapeamentoClasses() {
		return this.yacamimClassMapping;
	}

	/**
	 * @param _mapeamentoClasses the yacamimClassMapping to set
	 */
	public void setMapeamentoClasses(final YacamimClassMapping _mapeamentoClasses) {
		this.yacamimClassMapping = _mapeamentoClasses;
	}

	/**
	 * @return the classMappingLoaded
	 */
	public boolean isClassMappingLoaded() {
		return classMappingLoaded;
	}

	/**
	 * @param _classMappingLoaded the classMappingLoaded to set
	 */
	public void setClassMappingLoaded(boolean _classMappingLoaded) {
		this.classMappingLoaded = _classMappingLoaded;
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
	public void setParamsLoaded(boolean _paramsLoaded) {
		this.paramsLoaded = _paramsLoaded;
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
	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	/**
	 * @return the yacamimResources
	 */
	public YacamimResources getYacamimResources() {
		return this.yacamimResources;
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
	public void setPreferencesToken(String preferencesToken) {
		this.preferencesToken = preferencesToken;
	}
	
}