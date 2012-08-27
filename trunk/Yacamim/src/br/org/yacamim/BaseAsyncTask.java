/**
 * BaseAsyncTask.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim;

import android.os.AsyncTask;
import android.util.Log;
import br.org.yacamim.util.Constants;
import br.org.yacamim.xml.DefaultDataServiceHandler;

/**
 * Class BaseAsyncTask TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseAsyncTask<Params, Result> extends AsyncTask<Params, Integer, Result> {

	private boolean errorWithoutConnectivity;
	
	private BaseActivity baseActivity;
	
	private Integer progress = -1;
	
	public BaseAsyncTask(final BaseActivity _baseActivity) {
		super();
		this.baseActivity = _baseActivity;
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
	protected void onProgressUpdate(final Integer... _progress) {
		this.progress = _progress[0];
	}
	
	/**
	 * 
	 * @param _resultado
	 */
	protected void onPostExecute(final Result _resultado) {
		try {
			if(this.errorWithoutConnectivity) {
				if(!DefaultDataServiceHandler.checkInternetConnection(this.baseActivity)) {
					this.baseActivity.showDialog(Constants.ERROR_NO_CONNECTIVITY_AVAILABLE);
				} else
				if(!DefaultDataServiceHandler.checkWifiConnection(this.baseActivity)) {
					this.baseActivity.showDialog(Constants.ERROR_NO_WIFI_CONNECTIVITY_AVAILABLE);
				} 
			}
		} catch (Exception _e) {
			Log.e("BaseAsyncTask.onPostExecute", _e.getMessage());
		}
	}
	
	/**
	 *
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Result doInBackground(final Params... _params) {
		try {
			if(!DefaultDataServiceHandler.checkInternetConnection(this.baseActivity)
					&& !DefaultDataServiceHandler.checkWifiConnection(this.baseActivity)) {
				this.errorWithoutConnectivity = true;
			}
		} catch (Exception _e) {
			Log.e("BaseAsyncTask.doInBackground", _e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @param _progress
	 */
	protected void updateProgress(final Integer _progress) {
		this.progress = _progress;
		super.publishProgress(this.progress);
	}

	/**
	 * @return the progress
	 */
	public Integer getProgress() {
		return this.progress;
	}

	/**
	 * @param _progress the progress to set
	 */
	public void setProgress(Integer _progress) {
		this.progress = _progress;
	}

	/**
	 * @return the context
	 */
	public BaseActivity getBaseActivity() {
		return this.baseActivity;
	}

	/**
	 * @param _baseActivity the context to set
	 */
	public void setBaseActivity(final BaseActivity _baseActivity) {
		this.baseActivity = _baseActivity;
	}

	/**
	 * @return the errorWithoutConnectivity
	 */
	public boolean isErrorWithoutConnectivity() {
		return this.errorWithoutConnectivity;
	}
	
}
