/**
 * YBaseAsyncTask.java
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

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import br.org.yacamim.util.YConstants;
import br.org.yacamim.util.YUtilAndroid;

/**
 * Class YBaseAsyncTask TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public abstract class YBaseAsyncTask<Params, Result> extends AsyncTask<Params, Integer, Result> {
	
	private static final String TAG = YBaseAsyncTask.class.getSimpleName();

	private boolean errorWithoutConnectivity;
	
	private Activity activity;
	
	private Integer progress = -1;
	
	/**
	 * 
	 * @param activity
	 */
	public YBaseAsyncTask(final Activity activity) {
		super();
		this.activity = activity;
	}
	
	/**
	 *
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	protected void onPreExecute() {
		this.progress = 0;
	}
	
	/**
	 *
	 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
	 */
	protected void onProgressUpdate(final Integer... progress) {
		this.progress = progress[0];
	}
	
	/**
	 * 
	 * @param resultado
	 */
	protected void onPostExecute(final Result resultado) {
		try {
			if(this.errorWithoutConnectivity) {
				if(!YUtilAndroid.checkInternetConnection(this.activity)) {
					this.activity.showDialog(YConstants.ERROR_NO_CONNECTIVITY_AVAILABLE);
				} else
				if(!YUtilAndroid.checkWifiConnection(this.activity)) {
					this.activity.showDialog(YConstants.ERROR_NO_WIFI_CONNECTIVITY_AVAILABLE);
				} 
			}
		} catch (Exception e) {
			Log.e(TAG + ".onPostExecute", e.getMessage());
		}
	}
	
	/**
	 *
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Result doInBackground(final Params... params) {
		try {
			if(!YUtilAndroid.checkInternetConnection(this.activity)
					&& !YUtilAndroid.checkWifiConnection(this.activity)) {
				this.errorWithoutConnectivity = true;
			}
		} catch (Exception e) {
			Log.e(TAG + ".doInBackground", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @param progress
	 */
	protected void updateProgress(final Integer progress) {
		this.progress = progress;
		super.publishProgress(this.progress);
	}

	/**
	 * @return the progress
	 */
	public Integer getProgress() {
		return this.progress;
	}

	/**
	 * @param progress the progress to set
	 */
	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	/**
	 * @return the context
	 */
	public Activity getActivity() {
		return this.activity;
	}

	/**
	 * @param activity the context to set
	 */
	public void setActivity(final Activity activity) {
		this.activity = activity;
	}

	/**
	 * @return the errorWithoutConnectivity
	 */
	public boolean isErrorWithoutConnectivity() {
		return this.errorWithoutConnectivity;
	}
	
}
