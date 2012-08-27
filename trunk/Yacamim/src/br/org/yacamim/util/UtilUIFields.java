/**
 * UtilUIFields.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Class UtilUIFields TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class UtilUIFields {

	/**
	 * 
	 */
	private UtilUIFields() {
		super();
	}
	
	/**
	 * 
	 * @param _activity
	 * @param _idUIFields
	 */
	public static void clearUIFields(final Activity _activity, final int[] _idUIFields) {
		try {
			for(int i = 0; i < _idUIFields.length; i++) {
				View view = _activity.findViewById(_idUIFields[i]);
				if(view instanceof EditText) {
					((EditText)view).setText("");
				} else
				if(view instanceof TextView) {
					((TextView)view).setText("");
				} else
				if(view instanceof Spinner) {
					((Spinner)view).setSelection(0);
				}
			}
		} catch (Exception _e) {
			Log.e("UtilUIFields.clearUIFields", _e.getMessage());
		}
	}

}
