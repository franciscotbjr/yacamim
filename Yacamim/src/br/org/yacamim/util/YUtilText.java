/**
 * YUtilText.java
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

import br.org.yacamim.YacamimState;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

/**
 *
 * Class YUtilText TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YUtilText {

	/**
	 *
	 */
	private YUtilText() {
		super();
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @return
	 */
	public static String getTextFromEditText(final Activity _activity, final int _viewId) {
		try {
			return ((EditText) _activity.findViewById(_viewId)).getText().toString().trim();
		} catch (Exception _e) {
			Log.e("YUtilText.getTextFromEditText", _e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @return
	 */
	public static Integer getIntegerFromEditText(final Activity _activity, final int _viewId) {
		try {
			return Integer.valueOf(((EditText) _activity.findViewById(_viewId)).getText().toString().trim());
		} catch (Exception _e) {
			Log.e("YUtilText.getIntegerFromEditText", _e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @return
	 */
	public static Double getDoubleFromEditText(final Activity _activity, final int _viewId) {
		try {
			return Double.valueOf(((EditText) _activity.findViewById(_viewId)).getText().toString().trim());
		} catch (Exception _e) {
			Log.e("YUtilText.getDoubleFromEditText", _e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @return
	 */
	public static Long getLongFromEditText(final Activity _activity, final int _viewId) {
		try {
			return Long.valueOf(((EditText) _activity.findViewById(_viewId)).getText().toString().trim());
		} catch (Exception _e) {
			Log.e("YUtilText.getLongFromEditText", _e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @param _text
	 * @return
	 */
	public static void setTextToEditText(final Activity _activity, final int _viewId, final String _text) {
		try {
			((EditText) _activity.findViewById(_viewId)).setText(_text);
		} catch (Exception _e) {
			Log.e("YUtilText.setTextToEditText", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _view
	 * @param _viewId
	 * @param _text
	 */
	public static void setTextToEditText(final View _view, final int _viewId, final String _text) {
		try {
			((EditText) _view.findViewById(_viewId)).setText(_text);
		} catch (Exception _e) {
			Log.e("YUtilText.setTextToEditText", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @param _value
	 */
	public static void setIntegerToEditText(final Activity _activity, final int _viewId, final Integer _value) {
		try {
			((EditText) _activity.findViewById(_viewId)).setText(_value.toString());
		} catch (Exception _e) {
			Log.e("YUtilText.setIntegerToEditText", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @param _value
	 */
	public static void setLongToEditText(final Activity _activity, final int _viewId, final Long _value) {
		try {
			((EditText) _activity.findViewById(_viewId)).setText(_value.toString());
		} catch (Exception _e) {
			Log.e("YUtilText.setLongToEditText", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @param _value
	 */
	public static void setDoubleToEditText(final Activity _activity, final int _viewId, final Double _value) {
		try {
			((EditText) _activity.findViewById(_viewId)).setText(_value.toString());
		} catch (Exception _e) {
			Log.e("YUtilText.setDoubleToEditText", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @param _text
	 * @return
	 */
	public static void cleanTextEditText(final Activity _activity, final int _viewId) {
		try {
			((EditText) _activity.findViewById(_viewId)).setText(null);
		} catch (Exception _e) {
			Log.e("YUtilText.cleanTextEditText", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @param _text
	 * @return
	 */
	public static void cleanTextTextView(final Activity _activity, final int _viewId) {
		try {
			((TextView) _activity.findViewById(_viewId)).setText(null);
		} catch (Exception _e) {
			Log.e("YUtilText.cleanTextTextView", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @return
	 */
	public static String getTextFromTextView(final Activity _activity, final int _viewId) {
		try {
			return ((TextView) _activity.findViewById(_viewId)).getText().toString();
		} catch (Exception _e) {
			Log.e("YUtilText.getTextFromTextView", _e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @return
	 */
	public static Integer getIntegerFromTextView(final Activity _activity, final int _viewId) {
		try {
			return Integer.valueOf(((TextView) _activity.findViewById(_viewId)).getText().toString());
		} catch (Exception _e) {
			Log.e("YUtilText.getIntegerFromTextView", _e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @param _value
	 */
	public static void setIntegerToTextView(final Activity _activity, final int _viewId, final Integer _value) {
		try {
			((TextView) _activity.findViewById(_viewId)).setText(_value.toString());
		} catch (Exception _e) {
			Log.e("YUtilText.setIntegerToTextView", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @param _text
	 */
	public static void setTextToTextView(final Activity _activity, final int _viewId, final String _text) {
		try {
			((TextView) _activity.findViewById(_viewId)).setText(_text);
		} catch (Exception _e) {
			Log.e("YUtilText.setTextToTextView", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @return
	 */
	public static String getBolStringFromRadioButton(final Activity _activity, final int _viewId) {
		String bolString = YConstants.NO;
		try {
			if(((RadioButton)_activity.findViewById(_viewId)).isChecked()) {
				bolString = YConstants.YES;
			}
		} catch (Exception _e) {
			Log.e("YUtilText.getBolStringFromRadioButton", _e.getMessage());
		}
		return bolString;
	}

	/**
	 *
	 * @param _activity
	 * @param _viewIdRadio1
	 * @param _viewIdRadio2
	 * @param _value
	 */
	public static void setBolStringFromRadioButton(final Activity _activity, final int _viewIdRadio1, final int _viewIdRadio2 , final String _value) {
		try {
			if(_value != null && _value.equalsIgnoreCase(YConstants.YES)) {
				((RadioButton)_activity.findViewById(_viewIdRadio1)).setChecked(true);
			} else {
				((RadioButton)_activity.findViewById(_viewIdRadio2)).setChecked(true);
			}
		} catch (Exception _e) {
			Log.e("YUtilText.setBolStringFromRadioButton", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @return
	 */
	public static String getTextFromSpinner(final Activity _activity, final int _viewId) {
		try {
			return ((Spinner) _activity.findViewById(_viewId)).getSelectedItem().toString();
		} catch (Exception _e) {
			Log.e("YUtilText.getTextFromSpinner", _e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @param _text
	 */
	public static void setTextToSpinner(final Activity _activity, final int _viewId, final String _text, final int _size) {
		try {
			final Spinner spinner = ((Spinner) _activity.findViewById(_viewId));
			for(int i = 0; i <= _size; i++) {
				if(spinner.getItemAtPosition(i).toString().equals(_text)) {
					spinner.setSelection(i);
					break;
				}
			}
		} catch (Exception _e) {
			Log.e("YUtilText.setTextToSpinner", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @return
	 */
	public static boolean isSpinnerSelected(final Activity _activity, final int _viewId) {
		try {
			return !((Spinner) _activity.findViewById(_viewId)).getSelectedItem().toString().equalsIgnoreCase(_activity.getResources().getString(YacamimState.getInstance().getYacamimResources().getIdResourceMsgSelectAnItem()));
		} catch (Exception _e) {
			Log.e("YUtilText.isSpinnerSelected", _e.getMessage());
			return false;
		}
	}

	/**
	 *
	 * @param _activity
	 * @param _viewId
	 * @return
	 */
	public static boolean isRadioButtonChecked(final Activity _activity, final int _viewId) {
		try {
			return ((RadioButton)_activity.findViewById(_viewId)).isChecked();
		} catch (Exception _e) {
			Log.e("YUtilText.isRadioButtonChecked", _e.getMessage());
			return false;
		}
	}
}
