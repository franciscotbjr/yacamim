/**
 * DefaultAlertDialogBuilder.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.ui.componentes;

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
