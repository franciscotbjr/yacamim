/**
 * ThreadResponseHandler.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim;

import android.os.Handler;
import android.os.Looper;

/**
 * 
 * Class ThreadResponseHandler TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class ThreadResponseHandler extends Handler {
	
	private Object data;

	/**
	 * 
	 */
	public ThreadResponseHandler() {
		super();
	}

	/**
	 * 
	 * @param _callback
	 */
	public ThreadResponseHandler(final Callback _callback) {
		super(_callback);
	}

	/**
	 * 
	 * @param _looper
	 * @param _callback
	 */
	public ThreadResponseHandler(final Looper _looper, final Callback _callback) {
		super(_looper, _callback);
	}

	/**
	 * 
	 * @param _looper
	 */
	public ThreadResponseHandler(final Looper _looper) {
		super(_looper);
	}

	/**
	 * 
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 
	 * @param _data
	 */
	public void setData(final Object _data) {
		this.data = _data;
	}

}
