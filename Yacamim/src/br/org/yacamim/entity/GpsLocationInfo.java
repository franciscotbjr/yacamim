/**
 * GpsLocationInfo.java
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
package br.org.yacamim.entity;

import br.org.yacamim.persistence.Entity;
import android.util.Log;



/**
 *
 * Class GpsLocationInfo TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
@Entity
public class GpsLocationInfo extends BaseEntity {

	private double longitude;
	private double latitude;
	private double altitude;
	private double accuracy;
	private long time;

	/**
	 *
	 */
	public GpsLocationInfo() {
		super();
	}

	/**
	 *
	 * @param _gpsLocationInfo
	 */
	public GpsLocationInfo(GpsLocationInfo _gpsLocationInfo) {
		super();
		init(_gpsLocationInfo);
	}

	/**
	 * @param _gpsLocationInfo
	 */
	protected void init(final GpsLocationInfo _gpsLocationInfo) {
		try {
			this.longitude = _gpsLocationInfo.getLongitude();
			this.latitude = _gpsLocationInfo.getLatitude();
			this.altitude = _gpsLocationInfo.getAltitude();
			this.accuracy = _gpsLocationInfo.getAccuracy();
			this.time = _gpsLocationInfo.getTime();
		} catch (Exception _e) {
			Log.e("GpsLocationInfo.init", _e.getMessage());
		}
	}

	@Override
	public String toString() {
		return longitude + ", " + latitude + ", " + altitude + ", " + accuracy + ", " + time;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param _longitude the longitude to set
	 */
	public void setLongitude(double _longitude) {
		this.longitude = _longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param _latitude the latitude to set
	 */
	public void setLatitude(double _latitude) {
		this.latitude = _latitude;
	}

	/**
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}

	/**
	 * @param _altitude the altitude to set
	 */
	public void setAltitude(double _altitude) {
		this.altitude = _altitude;
	}

	/**
	 * @return the accuracy
	 */
	public double getAccuracy() {
		return accuracy;
	}

	/**
	 * @param _accuracy the accuracy to set
	 */
	public void setAccuracy(double _accuracy) {
		this.accuracy = _accuracy;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param _time the time to set
	 */
	public void setTime(long _time) {
		this.time = _time;
	}

}
