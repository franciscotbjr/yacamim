/**
 * ThreadResponseHandler.java
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