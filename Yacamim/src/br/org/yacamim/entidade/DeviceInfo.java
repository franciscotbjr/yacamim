/**
 * DeviceInfo.java
 *
 * Copyright  2011 Yacamim.org (Francisco Tarcizo Bomfim Júnior)
 */
package br.org.yacamim.entidade;


/**
 * Class DeviceInfo TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class DeviceInfo extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4775895380781227510L;
	
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