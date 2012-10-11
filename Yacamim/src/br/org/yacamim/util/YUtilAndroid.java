/**
 * YUtilAndroid.java
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
package br.org.yacamim.util;

import java.io.File;
import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import br.org.yacamim.BaseActivity;
import br.org.yacamim.YacamimState;

/**
 * Class YUtilAndroid TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YUtilAndroid {

	/**
	 * 
	 */
	private YUtilAndroid() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getImei() {
		String imei = "";
		try {
			imei = ((TelephonyManager)YacamimState.getInstance().getCurrentActivity().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			if(imei == null) {
				imei = "";
			}
		} catch (Exception _e) {
			Log.e("YUtilAndroid.getImei", _e.getMessage());
		}
		return imei;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getMacAddress() {
		String macAddress = "";
		try {
			macAddress = ((WifiManager)YacamimState.getInstance().getCurrentActivity().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress();
			if(macAddress == null) {
				macAddress = "";
			}
		} catch (Exception _e) {
			Log.e("YUtilAndroid.getMacAddress", _e.getMessage());
		}
		return macAddress;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getBluetoothMacAddress() {
		String bluetoothMac = "";
		try {
	    	bluetoothMac =  BluetoothAdapter.getDefaultAdapter().getAddress();
	    	if(bluetoothMac == null) {
	    		bluetoothMac = "";
			}
		} catch (Exception _e) {
			Log.e("YUtilAndroid.getBluetoothMacAddress", _e.getMessage());
		}
		return bluetoothMac;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getAndroidID() {
		String androidID = "";
		try {
			androidID = Secure.getString(YacamimState.getInstance().getCurrentActivity().getContentResolver(), Secure.ANDROID_ID);
	    	if(androidID == null) {
	    		androidID = "";
			}
		} catch (Exception _e) {
			Log.e("YUtilAndroid.getAndroidID", _e.getMessage());
		}
		return androidID;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getInstalationID() {
		String idCombinadoTemporal = new String();
		try {
			final String idsConcatenadosTemporal = getImei() + getMacAddress() + getBluetoothMacAddress() + getAndroidID() +System.currentTimeMillis();
			idCombinadoTemporal = YUtilCryptographic.md5(idsConcatenadosTemporal);
		} catch (Exception _e) {
			Log.e("YUtilAndroid.getIdCombinadoTemporal", _e.getMessage());
		}
		return idCombinadoTemporal;
	}
	

	/**
	 * 
	 * @param _fileName
	 * @param _keyPathImagens
	 * @param _baseActivity
	 * @param _refs
	 * @return
	 */
	public static Intent montaIntentParaCamera(final String _fileName, final String _keyPathImagens, final BaseActivity _baseActivity,  final List<File> _refs) {
		final ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, _fileName);
		values.put(MediaStore.Images.Media.DESCRIPTION, _fileName);
		
		checkFilePath(_keyPathImagens);
		
		final File fileImagem = new File(YacamimState.getInstance().getParams().get(_keyPathImagens) + "/" + _fileName);
		final Uri imageUri = Uri.fromFile(fileImagem);
		_refs.add(fileImagem);
		
		final Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		return cameraIntent;
	}

	/**
	 * @param _keyPathImagens
	 */
	protected static void checkFilePath(final String _keyPathImagens) {
		final File fileCheckPath = new File(YacamimState.getInstance().getParams().get(_keyPathImagens));
		if(!fileCheckPath.exists()) {
			fileCheckPath.mkdirs();
		}
	}

}