/**
 * YBaseActivity.java
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

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import br.org.yacamim.entity.GpsLocationInfo;
import br.org.yacamim.ui.components.OnDialogDoneListener;
import br.org.yacamim.ui.components.YAlertDialogFragment;
import br.org.yacamim.ui.components.YDialogEvent;
import br.org.yacamim.ui.components.YProgressDialogFragment;
import br.org.yacamim.util.YConstants;
import br.org.yacamim.util.YUtilUIFields;

/**
 * 
 * Class YBaseActivity TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public abstract class YBaseActivity extends Activity implements Callback, OnDialogDoneListener {
	
	private static final String TAG = YBaseActivity.class.getSimpleName();
	
	private YBaseLocationListener mYBaseLocationListener;
	
	private StringBuilder mMessage;
	
	private static boolean mActive;
	
	private static final List<YProgressDialogFragment> mYProgressDialogFragmentStack = new ArrayList<YProgressDialogFragment>();
	
	/**
	 * 
	 */
	public YBaseActivity() {
		super();
		YacamimState.getInstance().setCurrentContext(this);
	}
	
	/**
	 *
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.dismissCurrentProgressDialog();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mActive = true;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mActive = false;
	}
	
	/**
	 * 
	 * @return
	 */
	public static boolean isActive() {
		return mActive;
	}

	/**
	 * 
	 */
	protected void initGPS() {
		try {
			this.mYBaseLocationListener = new YBaseLocationListener(this);
			this.mYBaseLocationListener.getLocationManager().requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mYBaseLocationListener);
		} catch (Exception e) {
			Log.e(TAG + ".initGPS", e.getMessage());
		}
	}

    /**
     * 
     */
	protected void keepScreenOn() {
		try {
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		} catch (Exception e) {
			Log.e(TAG + ".keepScreenOn", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param uiFieldNames
	 * @param messageErrorType
	 */
	protected void buildMessage(final List<String> uiFieldNames, final int messageErrorType) {
		try {
			clearMessage();
			this.getMessage().append(getText(messageErrorType));
			for(final String nomeCampo : uiFieldNames) {
				this.getMessage().append("\n");
				this.getMessage().append(" - " + nomeCampo);
			}
			
		} catch (Exception e) {
			Log.e(TAG + ".buildMessage", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param uiFieldNames
	 */
	protected void buildMessage(final List<String> uiFieldNames) {
		try {
			clearMessage();
			for(final String nomeCampo : uiFieldNames) {
				this.getMessage().append("\n");
				this.getMessage().append(" - " + nomeCampo);
			}
			
		} catch (Exception e) {
			Log.e(TAG + "._uiFieldNames", e.getMessage());
		}
	}
	

	/**
	 * 
	 */
	protected void clearMessage() {
		this.mMessage = null;
	}


	/**
	 * 
	 * @return
	 */
	public StringBuilder getMessage() {
		if(this.mMessage == null) {
			this.mMessage = new StringBuilder();
		}
		return mMessage;
	}
	
	/**
	 * 
	 * @param titleResourceID
	 * @param messageResourceID
	 */
	public void displayProgressDialog(final int titleResourceID, final int messageResourceID) {
		try {
			progressDialog(titleResourceID, messageResourceID);
		} catch (Exception e) {
			Log.e(TAG + ".displayProgressDialog(int titleResourceID, int messageResourceID)", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param titleResourceID
	 */
	public void displayProgressDialog(final int titleResourceID) {
		try {
			progressDialog(titleResourceID, YacamimResources.getInstance().getIdResourceMsgWait());
		} catch (Exception e) {
			Log.e(TAG + ".displayProgressDialog(int messageResourceID)", e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void displayProgressDialog() {
		try {
			progressDialog(0, YacamimResources.getInstance().getIdResourceMsgWait());
		} catch (Exception e) {
			Log.e(TAG + ".displayProgressDialog()", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param titleResourceID
	 * @param messageResourceID
	 */
	private void progressDialog(final int titleResourceID,
			final int messageResourceID) {
		final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		
		YProgressDialogFragment yProgressDialogFragment = YProgressDialogFragment.newInstance(
				YConstants.DIALOG_WAIT, 
				titleResourceID, 
				messageResourceID);
		
		mYProgressDialogFragmentStack.add(yProgressDialogFragment);

		fragmentTransaction.add(yProgressDialogFragment, YConstants.Y_PROGRESS_DIALOG_FRAGMENT_PREFIX + YConstants.DIALOG_WAIT);
		
		fragmentTransaction.commit();
		
		fragmentTransaction.show(yProgressDialogFragment);
	}
	
	/**
	 * 
	 */
	public void dismissCurrentProgressDialog() {
		try {
			for(YProgressDialogFragment yProgressDialogFragment : mYProgressDialogFragmentStack) {
				yProgressDialogFragment.dismiss();
			}
			mYProgressDialogFragmentStack.clear();
		} catch (final Exception e) {
			Log.e(TAG + ".dimissCurrentProgressDialog()", e.getMessage());
		}
	}
	
	/**
	 *
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if(YacamimState.getInstance().isLogoff()) {
				this.finish();
				return;
			}
			switch (resultCode) {
				case YConstants.ERROR_NO_INFORMATION_FOUND:
					this.showDialogNoInformationFound();
					break;
				case YConstants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS:
					this.showDialogNoRecordFound();
					break;
			}
		} catch (Exception e) {
			Log.e(TAG + ".onActivityResult", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @see br.org.yacamim.ui.components.OnDialogDoneListener#onDialogClick(br.org.yacamim.ui.components.YDialogEvent)
	 */
	@Override
	public void onDialogClick(final YDialogEvent dialogEvent) {
		switch(dialogEvent.getDialogId()) {
			case YConstants.ERROR_NO_INFORMATION_FOUND:
				// TODO
				break;
			case YConstants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS:
				// TODO
				break;
			case YConstants.ERROR_NO_RECORD_SELECTED:
				// TODO
				break;
			case YConstants.ERROR_NO_CONNECTIVITY_AVAILABLE:
				// TODO
				break;
			case YConstants.ERROR_NO_WIFI_CONNECTIVITY_AVAILABLE:
				// TODO
				break;
			case YConstants.ERROR_CONSTRAINT_DEPENDENCY:
				// TODO
				break;
			case YConstants.INFO_DATA_SUCCESSFULLY_INSERTED:
				// TODO
				break;
			case YConstants.INFO_DATA_SUCCESSFULLY_UPDATED:
				// TODO
				break;
			case YConstants.ERROR_INVALID_DATA:
				// TODO
				break;
			case YConstants.ERROR_REQUIRED_FIELDS:
				// TODO
				break;
		}
		
	}
	
	/**
	 * 
	 */
	protected void showDialogNoInformationFound() {
		this.buildSimplePositiveAlertDialog(YConstants.ERROR_NO_INFORMATION_FOUND, 0, 
				YacamimResources.getInstance().getIdResourceMsgNoInformationFound(), 
				YacamimResources.getInstance().getIdResourceMsgOK());
	}
	
	/**
	 * 
	 */
	protected void finishForNoInformationFound() {
		super.setResult(YConstants.ERROR_NO_INFORMATION_FOUND);
		super.finish();
	}
	
	/**
	 * 
	 */
	protected void showDialogNoRecordFound() {
		this.buildSimplePositiveAlertDialog(YConstants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS, 0, 
				YacamimResources.getInstance().getIdResourceMsgNoRecordsFoundForParameters(), 
				YacamimResources.getInstance().getIdResourceMsgOK());
	}
	
	/**
	 * 
	 */
	protected void finishForNoRecordFound() {
		super.setResult(YConstants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS);
		super.finish();
	}
	
	/**
	 * 
	 */
	protected void showDialogNoRecordSelected() {
		this.buildSimplePositiveAlertDialog(YConstants.ERROR_NO_RECORD_SELECTED, 0, 
				YacamimResources.getInstance().getIdResourceMsgNoRecordSelected(), 
				YacamimResources.getInstance().getIdResourceMsgOK());
	}
	
	/**
	 * 
	 */
	protected void finishForNoRecordSelected() {
		super.setResult(YConstants.ERROR_NO_RECORD_SELECTED);
		super.finish();
	}
	
	
	/**
	 * 
	 */
	public final void displayDialogNetworkAccess() {
		this.buildSimplePositiveAlertDialog(YConstants.ERROR_NO_CONNECTIVITY_AVAILABLE, 0, 
				YacamimResources.getInstance().getIdResourceMsgNoConnectivityAvailable(), 
				YacamimResources.getInstance().getIdResourceMsgOK());
	}
	
	/**
	 * 
	 */
	public final void displayDialogWifiAccess() {
		this.buildSimplePositiveAlertDialog(YConstants.ERROR_NO_WIFI_CONNECTIVITY_AVAILABLE, 0, 
				YacamimResources.getInstance().getIdResourceMsgNoWifiConnectivityAvailable(), 
				YacamimResources.getInstance().getIdResourceMsgOK());
	}
	
	/**
	 * 
	 */
	protected void showDialogConstraintDependency() {
		this.buildSimplePositiveAlertDialog(YConstants.ERROR_CONSTRAINT_DEPENDENCY, 0, 
				YacamimResources.getInstance().getIdResourceMsgConstraintDependency(), 
				YacamimResources.getInstance().getIdResourceMsgOK());
	}
	
	/**
	 * 
	 */
	protected void showDialogDataSuccessfullyInserted() {
		this.buildSimplePositiveAlertDialog(YConstants.INFO_DATA_SUCCESSFULLY_INSERTED, 0, 
				YacamimResources.getInstance().getIdResourceMsgSuccesfullyInserted(), 
				YacamimResources.getInstance().getIdResourceMsgOK());
	}
	
	/**
	 * 
	 */
	protected void showDialogDataSuccessfullyUpdated() {
		this.buildSimplePositiveAlertDialog(YConstants.INFO_DATA_SUCCESSFULLY_UPDATED, 0, 
				YacamimResources.getInstance().getIdResourceMsgSuccesfullyUpdated(), 
				YacamimResources.getInstance().getIdResourceMsgOK());
	}
	
	/**
	 * 
	 */
	protected void showDialogInvalidData() {
		this.buildSimplePositiveAlertDialog(YConstants.ERROR_INVALID_DATA, 0, 
				YacamimResources.getInstance().getIdResourceMsgInvalidData(), 
				YacamimResources.getInstance().getIdResourceMsgOK());
	}
	
	/**
	 * 
	 */
	protected void showDialogErrorRequiredFields() {
		this.buildSimplePositiveAlertDialog(YConstants.ERROR_REQUIRED_FIELDS, 0, 
				YacamimResources.getInstance().getIdResourceMsgInvalidData(), 
				YacamimResources.getInstance().getIdResourceMsgOK());
	}
	
	/**
	 * 
	 * @param dialogId
	 * @param titleResource
	 * @param mensageResource
	 * @param positiveButtonresource
	 */
	public void buildSimplePositiveAlertDialog(final int dialogId, final int titleResource, 
			final int mensageResource, final int positiveButtonresource) {
		final YAlertDialogFragment yAlertDialogFragment = 
				YAlertDialogFragment.newInstance(dialogId, 
						titleResource, 
						mensageResource, 
						positiveButtonresource);
		yAlertDialogFragment.show(this.getFragmentManager(), TAG);
	}

	/**
	 * 
	 * @param dialogId
	 * @param title
	 * @param mensage
	 * @param positiveButtonresource
	 */
	public void buildSimplePositiveAlertDialog(final int dialogId, final String title, 
			final String mensage, final int positiveButtonresource) {
		final YAlertDialogFragment yAlertDialogFragment = 
				YAlertDialogFragment.newInstance(dialogId, 
						title, 
						mensage, 
						positiveButtonresource);
		yAlertDialogFragment.show(this.getFragmentManager(), TAG);
	}

	/**
	 *
	 * @see android.os.Handler.Callback#handleMessage(android.os.Message)
	 */
	@Override
	public boolean handleMessage(final Message message) {
		switch(message.what){
	        case YConstants.ERROR_NO_WIFI_CONNECTIVITY_AVAILABLE:
	        	this.dismissCurrentProgressDialog();
	        	this.displayDialogWifiAccess();
	        	return true;
	        case YConstants.ERROR_NO_CONNECTIVITY_AVAILABLE:
	        	this.dismissCurrentProgressDialog();
    			this.displayDialogNetworkAccess();
	    		return true;
	        default:
	            return false;
		}
	}
	
	/**
	 * 
	 */
	protected void vibrar() {
		try {
			((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(new long[]{0, 150}, -1);
		} catch (Exception e) {
			Log.e(TAG + ".vibrar", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected int[] getFieldsForWindowsCleanup() {
		return new int[]{};
	}
	
	/**
	 * 
	 */
	public void cleanupWindowUIFields() {
		try {
			YUtilUIFields.clearUIFields(this, this.getFieldsForWindowsCleanup());
		} catch (Exception e) {
			Log.e(TAG + ".limpaTela", e.getMessage());
		}
	}

	/**
	 * 
	 * 
	 * @return
	 */
	protected GpsLocationInfo getBestLocation() {
		return this.mYBaseLocationListener.getCurrentGpsLocationInfo();
	}
	
	/**
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Runtime.getRuntime().gc();
		if(super.isFinishing()) {
			
		}
	}
	
	protected  void onFinishing() {
		
	}
	
	
	/**
	 * 
	 * @param gpsLocationInfo
	 */
	public void onUpdateGpsLocationInfo(GpsLocationInfo gpsLocationInfo) {
		
	}
}