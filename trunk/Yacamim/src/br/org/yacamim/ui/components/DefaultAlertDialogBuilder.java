/**
 * DefaultAlertDialogBuilder.java
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
