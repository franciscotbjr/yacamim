/**
 * YUtilAndroid.java
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
package br.org.yacamim.util;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.YacamimState;

/**
 * Class YUtilAndroid TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YUtilAndroid {
	
	private static final String TAG = YUtilAndroid.class.getSimpleName();

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
		} catch (Exception e) {
			Log.e(TAG + ".getImei", e.getMessage());
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
		} catch (Exception e) {
			Log.e(TAG + ".getMacAddress", e.getMessage());
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
		} catch (Exception e) {
			Log.e(TAG + ".getBluetoothMacAddress", e.getMessage());
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
		} catch (Exception e) {
			Log.e(TAG + ".getAndroidID", e.getMessage());
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
		} catch (Exception e) {
			Log.e(TAG + ".getIdCombinadoTemporal", e.getMessage());
		}
		return idCombinadoTemporal;
	}


	/**
	 *
	 * @param fileName
	 * @param pathImagens
	 * @param yBaseActivity
	 * @param refs
	 * @return
	 */
	public static Intent montaIntentParaCamera(final String fileName, final String pathImagens, final YBaseActivity yBaseActivity,  final List<File> refs) {
		final ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, fileName);
		values.put(MediaStore.Images.Media.DESCRIPTION, fileName);

		checkFilePath(pathImagens);

		final File fileImagem = new File(pathImagens + "/" + fileName);
		final Uri imageUri = Uri.fromFile(fileImagem);
		refs.add(fileImagem);

		final Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		return cameraIntent;
	}

	/**
	 * @param pathImagens
	 */
	protected static void checkFilePath(final String pathImagens) {
		final File fileCheckPath = new File(pathImagens);
		if(!fileCheckPath.exists()) {
			fileCheckPath.mkdirs();
		}
	}


	/**
	 *
	 * @param activity
	 * @return
	 */
	public static synchronized boolean checkInternetConnection(final Activity activity) {
		boolean wifi = false;
		try {
			final ConnectivityManager connectivityManager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
			if(connectivityManager != null
					&& connectivityManager.getActiveNetworkInfo() != null) {
				// handle wifi if the user wants to use only wifi connection
				if(YacamimState.getInstance().getPreferences(activity).useOnlyWifi) {
					wifi = true;
					final NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
					if (mWifi != null && mWifi.isConnected()) {
						return true;
					}
				} else {
					if(connectivityManager.getActiveNetworkInfo().isAvailable()
							&& connectivityManager.getActiveNetworkInfo().isConnected()) {
						return true;
					}
				}
			}
		} catch (Exception _e) {
			Log.e(TAG + ".checkInternetConnection", _e.getMessage());
		}
		handleDefaultDialogs(activity, wifi);
		return false;
	}

	/**
	 *
	 * @param activity
	 * @return
	 */
	public static synchronized boolean checkWifiConnection(final Activity activity) {
		try {
			final ConnectivityManager connectivityManager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
			if(connectivityManager != null
					&& connectivityManager.getActiveNetworkInfo() != null) {
				// Check if it was chosen to work only with wifi
				if(YacamimState.getInstance().getPreferences(activity).useOnlyWifi) {
					final NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
					if (mWifi != null && mWifi.isConnected()) {
						return true;
					}
				} else {
					// Wifi isn't the only way
					return true;
				}
			}
		} catch (Exception e) {
			Log.e(TAG + ".checkWifiConnection", e.getMessage());
		}
		return false;
	}

	/**
	 *
	 * @param activity
	 * @return
	 */
	public static boolean isOnLine(final Activity activity) {
		return checkInternetConnection(activity) || checkWifiConnection(activity);
	}


	/**
	 *
	 * @param activity
	 * @param wifi
	 */
	private static void handleDefaultDialogs(final Activity activity, final boolean wifi) {
		if(activity instanceof YBaseActivity) {
			final YBaseActivity yBaseActivity = (YBaseActivity)activity;
			yBaseActivity.clearProgressDialogStack();
			if(wifi) {
				yBaseActivity.displayDialogWifiAccess();
			} else {
				yBaseActivity.displayDialogNetworkAccess();
			}
		}
	}

}