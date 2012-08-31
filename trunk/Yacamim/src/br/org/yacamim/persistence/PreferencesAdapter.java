/**
 * PreferencesAdapter.java
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
package br.org.yacamim.persistence;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import br.org.yacamim.YacamimState;
import br.org.yacamim.entity.DeviceInfo;
import br.org.yacamim.entity.Preferences;
import br.org.yacamim.util.UtilAndroid;
import br.org.yacamim.util.UtilString;

/**
 * 
 * Class PreferencesAdapter TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class PreferencesAdapter {
	
	/**
	 * Mapeamento das preferências
	 */
	// "__y_Prefs_#_token_@_yakamim.org.br_::"
	private static final String PREF_YACAMIM_PREFS = YacamimState.getInstance().getPreferencesToken();

	private static final String PREF_KEEP_CACHE = "_y_keep_cache";
	private static final String PREF_KEEP_USER_LOGIN = "_y_keep_user_login";
	private static final String PREF_USER_LOGIN = "_y_user_login";
	private static final String PREF_DISPLAY_OPEN_MESSAGE = "_y_display_open_message";
	private static final String PREF_USE_ONLY_WIFI = "_y_use_only_wifi";
	
	
	private static final String PREF_INSTALATION_ID = "__y_instalation_";
	
	private SharedPreferences sharedPreferences;
	
	private Context context;
	
	/**
	 * 
	 * @param _context
	 */
	public PreferencesAdapter(final Context _context) {
		super();
		this.context = _context;
		this.init();
	}
	
	/**
	 * 
	 * @param _preferencias
	 * @return
	 */
	private void init() {
		try {
			this.sharedPreferences = this.context.getSharedPreferences(PREF_YACAMIM_PREFS, Activity.MODE_PRIVATE);
			
			if (!this.sharedPreferences.contains(this.getKeyToken(PREF_KEEP_CACHE))) {
				final Editor editor = this.sharedPreferences.edit();
				editor.putBoolean(this.getKeyToken(PREF_KEEP_CACHE), true);
				editor.putBoolean(this.getKeyToken(PREF_KEEP_USER_LOGIN), false);
				editor.putString(this.getKeyToken(PREF_USER_LOGIN), "");
				editor.putBoolean(this.getKeyToken(PREF_DISPLAY_OPEN_MESSAGE), true);
				editor.putBoolean(this.getKeyToken(PREF_USE_ONLY_WIFI), false);
								
				editor.commit();
			}
		} catch (Exception _e) {
			Log.e("PreferenciasAdapter.init", _e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param _context
	 * @param deviceInfo
	 */
	public void initDeviceInfo(final DeviceInfo deviceInfo) {
		try {
			if (!this.sharedPreferences.contains(this.getKeyToken(PREF_INSTALATION_ID))) {
				final Editor editor = this.sharedPreferences.edit();
				
				editor.putString(this.getKeyToken(PREF_INSTALATION_ID), deviceInfo.getInstalationId());
				
				editor.commit();
			}
		} catch (Exception _e) {
			Log.e("PreferenciasAdapter.initDeviceInfo", _e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param _context
	 * @return
	 */
	public boolean isInstalled() {
		boolean installed = false;
		try {
			if (this.sharedPreferences.contains(this.getKeyToken(PREF_INSTALATION_ID))) {
				if(!UtilString.isEmptyString(sharedPreferences.getString(this.getKeyToken(PREF_INSTALATION_ID), ""))) {
					installed = true;
				}
			}
		} catch (Exception _e) {
			Log.e("PreferenciasAdapter.isInstalled", _e.getMessage());
		}
		return installed;
	}
	
	/**
	 * 
	 * @param _preferences
	 * @return
	 */
	public boolean update(final Preferences _preferences) {
		try {
			final Editor editor = this.sharedPreferences.edit();
			editor.putBoolean(this.getKeyToken(PREF_KEEP_CACHE), _preferences.keepCache);
			editor.putBoolean(this.getKeyToken(PREF_KEEP_USER_LOGIN), _preferences.keepUserLogin);
			editor.putString(this.getKeyToken(PREF_USER_LOGIN), _preferences.keepUser);
			editor.putBoolean(this.getKeyToken(PREF_DISPLAY_OPEN_MESSAGE), _preferences.displayOpenMessage);
			editor.putBoolean(this.getKeyToken(PREF_USE_ONLY_WIFI), _preferences.useOnlyWifi);
			return editor.commit();
		} catch (Exception _e) {
			Log.e("PreferenciasAdapter.update", _e.getMessage());
			return false;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Preferences get() {
		return this.build();
	}

	/**
	 * 
	 * @return
	 */
	private Preferences build() {
		
		final Preferences preferences = new Preferences();

		preferences.keepCache = this.sharedPreferences.getBoolean(this.getKeyToken(PREF_KEEP_CACHE), false);
		preferences.keepUserLogin = sharedPreferences.getBoolean(this.getKeyToken(PREF_KEEP_USER_LOGIN), false);;
		preferences.keepUser = sharedPreferences.getString(this.getKeyToken(PREF_USER_LOGIN), null);
		preferences.displayOpenMessage = sharedPreferences.getBoolean(this.getKeyToken(PREF_DISPLAY_OPEN_MESSAGE), false);
		preferences.useOnlyWifi = sharedPreferences.getBoolean(this.getKeyToken(PREF_USE_ONLY_WIFI), false);
		
		return preferences;
	}
	
	/**
	 * 
	 * @return
	 */
	public DeviceInfo getDeviceInfo() {
		
		final DeviceInfo deviceInfo = new DeviceInfo();
		
		deviceInfo.setInstalationId(sharedPreferences.getString(this.getKeyToken(PREF_INSTALATION_ID), null));
		deviceInfo.setAndroidId(UtilAndroid.getAndroidID());
		deviceInfo.setBluetoothMacAddress(UtilAndroid.getBluetoothMacAddress());
		deviceInfo.setImei(UtilAndroid.getImei());
		deviceInfo.setMacAddress(UtilAndroid.getMacAddress());
		
		return deviceInfo;
	}

	/**
	 * 
	 * @param _sufixo
	 * @return
	 */
	private String getKeyToken(final String _sufixo) {
		return PREF_YACAMIM_PREFS + _sufixo;
	}
	
}
