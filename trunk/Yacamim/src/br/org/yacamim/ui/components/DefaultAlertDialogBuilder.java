/**
 * DefaultAlertDialogBuilder.java
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
package br.org.yacamim.ui.components;

import android.app.AlertDialog;
import android.content.Context;

/**
 *
 * Class DefaultAlertDialogBuilder TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class DefaultAlertDialogBuilder extends AlertDialog.Builder {

	/**
	 *
	 * @param _context
	 */
	public DefaultAlertDialogBuilder(final Context _context) {
		super(_context);
		this.setTitle("");
	}

	/**
	 *
	 * @param _context
	 * @param _message
	 * @param _cancelable
	 */
	public DefaultAlertDialogBuilder(final Context _context, final CharSequence _message, final boolean _cancelable) {
		super(_context);
		this.setTitle("");
		this.setMessage(_message);
		this.setCancelable(_cancelable);
	}

	/**
	 *
	 * @param _context
	 * @param _title
	 * @param _message
	 * @param _cancelable
	 */
	public DefaultAlertDialogBuilder(final Context _context, final CharSequence _title, final CharSequence _message, final boolean _cancelable) {
		super(_context);
		this.setTitle(_title);
		this.setMessage(_message);
		this.setCancelable(_cancelable);
	}

	/**
	 *
	 * @param _context
	 * @param _message
	 * @param _cancelable
	 */
	public DefaultAlertDialogBuilder(final Context _context, final int _message, final boolean _cancelable) {
		super(_context);
		this.setTitle("");
		this.setMessage(_message);
		this.setCancelable(_cancelable);
	}

}
