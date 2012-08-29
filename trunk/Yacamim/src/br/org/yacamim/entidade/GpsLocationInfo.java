/**
 * GpsLocationInfo.java
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
package br.org.yacamim.entidade;

import android.util.Log;



/**
 * 
 * Class GpsLocationInfo TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class GpsLocationInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5548446327048768000L;
	
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
