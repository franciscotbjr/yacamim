/**
 * YUtilUIFields.java
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
package br.org.yacamim.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Class YUtilUIFields TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YUtilUIFields {
	
	private static final String TAG = YUtilUIFields.class.getSimpleName();

	/**
	 *
	 */
	private YUtilUIFields() {
		super();
	}

	/**
	 *
	 * @param activity
	 * @param idUIFields
	 */
	public static void clearUIFields(final Activity activity, final int[] idUIFields) {
		try {
			for(int i = 0; i < idUIFields.length; i++) {
				View view = activity.findViewById(idUIFields[i]);
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
		} catch (Exception e) {
			Log.e(TAG + ".clearUIFields", e.getMessage());
		}
	}

}
