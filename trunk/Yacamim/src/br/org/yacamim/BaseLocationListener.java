/**
 * BaseLocationListener.java
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

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import br.org.yacamim.entity.GpsLocationInfo;

/**
 * Class BaseLocationListener TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class BaseLocationListener implements LocationListener {
	
	private LocationManager locationManager;
	
	private GpsLocationInfo gpsLocationInfo;
	
	private GpsLocationInfo currentGpsLocationInfo;
	
	private BaseActivity baseActivity;

	/**
	 * 
	 */
	public BaseLocationListener(final BaseActivity _baseActivity) {
		super();
		this.baseActivity = _baseActivity;
	}

	/**
	 * 
	 *
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(Location _location) {
		try {
			if(this.currentGpsLocationInfo == null) {
				this.currentGpsLocationInfo = new GpsLocationInfo();
			}
			
			// Sempre atualizado
			this.currentGpsLocationInfo.setLongitude(_location.getLongitude());
			this.currentGpsLocationInfo.setLatitude(_location.getLatitude());
			this.currentGpsLocationInfo.setAltitude(_location.getAltitude());
			this.currentGpsLocationInfo.setAccuracy(_location.getAccuracy());
			this.currentGpsLocationInfo.setTime(_location.getTime());
			
			// Apenas a primeira sez
			if(this.gpsLocationInfo == null) {
				this.gpsLocationInfo = new GpsLocationInfo();
				this.gpsLocationInfo.setLongitude(_location.getLongitude());
				this.gpsLocationInfo.setLatitude(_location.getLatitude());
				this.gpsLocationInfo.setAltitude(_location.getAltitude());
				this.gpsLocationInfo.setAccuracy(_location.getAccuracy());
				this.gpsLocationInfo.setTime(_location.getTime());
				this.baseActivity.onUpdateGpsLocationInfo(this.gpsLocationInfo);
			}
			
		} catch (Exception _e) {
			Log.e("BaseLocationListenerActivity.onLocationChanged", _e.getMessage());
		}
	}

	/**
	 * 
	 *
	 * @see android.location.LocationListener#onProviderDisabled(java.lang.String)
	 */
	@Override
	public void onProviderDisabled(String provider) {
		try {

		} catch (Exception _e) {
			Log.e("BaseLocationListener.onProviderDisabled", _e.getMessage());
		}
	}

	/**
	 * 
	 *
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(String provider) {
		try {

		} catch (Exception _e) {
			Log.e("BaseLocationListener.onProviderEnabled", _e.getMessage());
		}
	}

	/**
	 * 
	 *
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		try {

		} catch (Exception _e) {
			Log.e("BaseLocationListener.onStatusChanged", _e.getMessage());
		}
	}

	/**
	 * @return the locationManager
	 */
	public LocationManager getLocationManager() {
		return this.locationManager;
	}

	/**
	 * @return the gpsLocationInfo
	 */
	public GpsLocationInfo getGpsLocationInfo() {
		if(this.gpsLocationInfo == null) {
			this.gpsLocationInfo = new GpsLocationInfo();
		}
		return this.gpsLocationInfo;
	}
	

	/**
	 * @return the gpsLocationInfo
	 */
	public void updatedGpsLocationInfo() {
		if(this.currentGpsLocationInfo != null) {
			this.gpsLocationInfo = new GpsLocationInfo();
			this.gpsLocationInfo.setLongitude(this.currentGpsLocationInfo.getLongitude());
			this.gpsLocationInfo.setLatitude(this.currentGpsLocationInfo.getLatitude());
			this.gpsLocationInfo.setAltitude(this.currentGpsLocationInfo.getAltitude());
			this.gpsLocationInfo.setAccuracy(this.currentGpsLocationInfo.getAccuracy());
			this.gpsLocationInfo.setTime(this.currentGpsLocationInfo.getTime());
		}
	}

	/**
	 * @return the curentGpsLocationInfo
	 */
	public GpsLocationInfo getCurentGpsLocationInfo() {
		if(this.currentGpsLocationInfo == null) {
			this.currentGpsLocationInfo = new GpsLocationInfo();
		}
		return this.currentGpsLocationInfo;
	}

}