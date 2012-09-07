/**
 * YacamimState.java
 *
 * Copyright 2012 yacamim.org.br
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.org.yacamim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public final class YacamimState implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6841969142735431809L;

	private static final YacamimState singletonYacamimState = new YacamimState();
	
	private YacamimClassMapping yacamimClassMapping = new YacamimClassMapping();
	
	private List<ServiceURL> serviceURLs = new ArrayList<ServiceURL>();
	
	private Map<String, String> params = new HashMap<String, String>();
	
	private boolean serviceUrlsLoaded = false;

	private boolean classMappingLoaded = false;
	
	private boolean paramsLoaded = false;
	
	private Preferences preferences;
	
	private boolean logoff = false;
	
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
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
	public Preferences getPreferencias(final Activity _activity) {
		try {
			if(YacamimState.getInstance().preferences == null) {
				
				final PreferencesAdapter preferenciasAdapter = new PreferencesAdapter(_activity);
				
				YacamimState.getInstance().setPreferencias(preferenciasAdapter.get());
				
			}
		} catch (Exception _e) {
			Log.e("YacamimState.getPreferencias", _e.getMessage());
		}
		return preferences;
	}

	/**
	 * 
	 * @param _preferencias
	 */
	private void setPreferencias(final Preferences _preferencias) {
		this.preferences = _preferencias;
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
	 * 
	 * @param _chave
	 * @param _objeto
	 */
	public void addAttribute(final String _chave, final Object _objeto) {
		try {
			this.attributes.remove(_chave);
			this.attributes.put(_chave, _objeto);
		} catch (Exception _e) {
			Log.e("YacamimState.addAttribute", _e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param _chave
	 */
	public void removeAttribute(final String _chave) {
		try {
			this.attributes.remove(_chave);
		} catch (Exception _e) {
			Log.e("YacamimState.removeAttribute", _e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param _chave
	 */
	public Object getAttribute(final String _chave) {
		try {
			return this.attributes.get(_chave);
		} catch (Exception _e) {
			Log.e("YacamimState.getAttribute", _e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @param _chave
	 */
	public void clearAttributes() {
		try {
			this.attributes.clear();
		} catch (Exception _e) {
			Log.e("YacamimState.clearAttributes", _e.getMessage());
		}
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
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
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