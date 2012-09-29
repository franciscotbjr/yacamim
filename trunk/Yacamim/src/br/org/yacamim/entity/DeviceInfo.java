/**
 * DeviceInfo.java
 *
 * Copyright  2011 Yacamim.org 
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
package br.org.yacamim.entity;

import br.org.yacamim.persistence.Entity;


/**
 * Class DeviceInfo TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
@Entity
public class DeviceInfo extends BaseEntity {
	
	private String imei;
	private String macAddress;
	private String bluetoothMacAddress;
	private String androidId;
	private String instalationId;

	/**
	 * 
	 */
	public DeviceInfo() {
		super();
	}

	/**
	 * @return the imei
	 */
	public String getImei() {
		return imei;
	}

	/**
	 * @param _imei the imei to set
	 */
	public void setImei(String _imei) {
		this.imei = _imei;
	}

	/**
	 * @return the macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}

	/**
	 * @param _macAddress the macAddress to set
	 */
	public void setMacAddress(String _macAddress) {
		this.macAddress = _macAddress;
	}

	/**
	 * @return the bluetoothMacAddress
	 */
	public String getBluetoothMacAddress() {
		return bluetoothMacAddress;
	}

	/**
	 * @param _bluetoothMacAddress the bluetoothMacAddress to set
	 */
	public void setBluetoothMacAddress(String _bluetoothMacAddress) {
		this.bluetoothMacAddress = _bluetoothMacAddress;
	}

	/**
	 * @return the androidId
	 */
	public String getAndroidId() {
		return androidId;
	}

	/**
	 * @param _androidId the androidId to set
	 */
	public void setAndroidId(String _androidId) {
		this.androidId = _androidId;
	}

	/**
	 * @return the instalationId
	 */
	public String getInstalationId() {
		return instalationId;
	}

	/**
	 * @param _instalationId the instalationId to set
	 */
	public void setInstalationId(String _instalationId) {
		this.instalationId = _instalationId;
	}
	
}