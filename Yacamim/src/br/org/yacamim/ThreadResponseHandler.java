/**
 * ThreadResponseHandler.java
 *
 * Copyright 2011 yacamim.org.br
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
