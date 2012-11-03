/**
 * DeviceInfo.java
 *
 * Copyright  2011 Yacamim.org
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