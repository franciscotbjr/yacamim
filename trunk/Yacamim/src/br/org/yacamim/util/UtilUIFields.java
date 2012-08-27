/**
 * UtilUIFields.java
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
