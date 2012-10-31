/**
 * BaseAsyncTask.java
 *
 * Copyright 2012 yacamim.org.br
 * 
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
package br.org.yacamim;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import br.org.yacamim.util.YConstants;
import br.org.yacamim.util.YUtilAndroid;

/**
 * Class BaseAsyncTask TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseAsyncTask<Params, Result> extends AsyncTask<Params, Integer, Result> {

	private boolean errorWithoutConnectivity;
	
	private Activity activity;
	
	private Integer progress = -1;
	
	/**
	 * 
	 * @param activity
	 */
	public BaseAsyncTask(final Activity activity) {
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
			Log.e("BaseAsyncTask.onPostExecute", e.getMessage());
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
			Log.e("BaseAsyncTask.doInBackground", e.getMessage());
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
