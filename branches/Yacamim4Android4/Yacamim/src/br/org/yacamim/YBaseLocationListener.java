/**
 * YBaseLocationListener.java
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

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import br.org.yacamim.entity.GpsLocationInfo;

/**
 * Class YBaseLocationListener TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YBaseLocationListener implements LocationListener {
	
	private LocationManager locationManager;
	
	private GpsLocationInfo gpsLocationInfo;
	
	private GpsLocationInfo currentGpsLocationInfo;
	
	private YBaseActivity yBaseActivity;
	
	/**
	 * 
	 * @param baseActivity
	 */
	public YBaseLocationListener(final YBaseActivity baseActivity) {
		super();
		this.yBaseActivity = baseActivity;
		this.locationManager = (LocationManager)this.yBaseActivity.getSystemService(Context.LOCATION_SERVICE);
	}

	/**
	 * 
	 * @param baseActivity
	 * @param locationManager
	 */
	public YBaseLocationListener(final YBaseActivity baseActivity, final LocationManager locationManager) {
		super();
		this.yBaseActivity = baseActivity;
		this.locationManager = locationManager;
	}

	/**
	 * 
	 *
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */
	@Override
	public void onLocationChanged(final Location location) {
		try {
			if(this.currentGpsLocationInfo == null) {
				this.currentGpsLocationInfo = new GpsLocationInfo();
			}
			
			// Sempre atualizado
			this.currentGpsLocationInfo.setLongitude(location.getLongitude());
			this.currentGpsLocationInfo.setLatitude(location.getLatitude());
			this.currentGpsLocationInfo.setAltitude(location.getAltitude());
			this.currentGpsLocationInfo.setAccuracy(location.getAccuracy());
			this.currentGpsLocationInfo.setTime(location.getTime());
			this.updateGpsLocationInfo();
			this.yBaseActivity.onUpdateGpsLocationInfo(this.currentGpsLocationInfo);
			
			// Apenas a primeira sez
			if(this.gpsLocationInfo == null) {
				this.gpsLocationInfo = new GpsLocationInfo();
				this.gpsLocationInfo.setLongitude(location.getLongitude());
				this.gpsLocationInfo.setLatitude(location.getLatitude());
				this.gpsLocationInfo.setAltitude(location.getAltitude());
				this.gpsLocationInfo.setAccuracy(location.getAccuracy());
				this.gpsLocationInfo.setTime(location.getTime());
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
	public void onProviderDisabled(final String provider) {
		try {

		} catch (Exception _e) {
			Log.e("YBaseLocationListener.onProviderDisabled", _e.getMessage());
		}
	}

	/**
	 * 
	 *
	 * @see android.location.LocationListener#onProviderEnabled(java.lang.String)
	 */
	@Override
	public void onProviderEnabled(final String provider) {
		try {

		} catch (Exception _e) {
			Log.e("YBaseLocationListener.onProviderEnabled", _e.getMessage());
		}
	}

	/**
	 * 
	 *
	 * @see android.location.LocationListener#onStatusChanged(java.lang.String, int, android.os.Bundle)
	 */
	@Override
	public void onStatusChanged(final String provider, final int status, final Bundle extras) {
		try {

		} catch (Exception _e) {
			Log.e("YBaseLocationListener.onStatusChanged", _e.getMessage());
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
	private void updateGpsLocationInfo() {
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
	 * @return the currentGpsLocationInfo
	 */
	public GpsLocationInfo getCurrentGpsLocationInfo() {
		if(this.currentGpsLocationInfo == null) {
			this.currentGpsLocationInfo = new GpsLocationInfo();
			final List<String> providers = this.locationManager.getProviders(true);

		    Location location = null;

		    for (int i = providers.size() - 1; i >= 0; i--) {
		    	location = this.locationManager.getLastKnownLocation(providers.get(i));
		        if (location != null) {
		        	this.currentGpsLocationInfo.setAccuracy(location.getAccuracy());
		        	this.currentGpsLocationInfo.setAltitude(location.getAltitude());
		        	this.currentGpsLocationInfo.setLatitude(location.getLatitude());
		        	this.currentGpsLocationInfo.setLongitude(location.getLongitude());
		        	this.currentGpsLocationInfo.setTime(location.getTime());
		        	this.updateGpsLocationInfo();
		            break;
		        }
		    }
		}
		return this.currentGpsLocationInfo;
	}

}