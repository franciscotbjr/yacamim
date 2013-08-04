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

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import br.org.yacamim.YacamimConfig;
import br.org.yacamim.YacamimResources;

/**
 *
 * Class YUtilText TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YUtilText {
	
	private static final String TAG = YUtilText.class.getSimpleName();

	/**
	 *
	 */
	private YUtilText() {
		super();
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static String getTextFromEditText(final Activity activity, final int viewId) {
		try {
			return ((EditText) activity.findViewById(viewId)).getText().toString().trim();
		} catch (Exception e) {
			Log.e(TAG + ".getTextFromEditText", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static Integer getIntegerFromEditText(final Activity activity, final int viewId) {
		try {
			return Integer.valueOf(((EditText) activity.findViewById(viewId)).getText().toString().trim());
		} catch (Exception e) {
			Log.e(TAG + ".getIntegerFromEditText", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static Double getDoubleFromEditText(final Activity activity, final int viewId) {
		try {
			return Double.valueOf(((EditText) activity.findViewById(viewId)).getText().toString().trim());
		} catch (Exception e) {
			Log.e(TAG + ".getDoubleFromEditText", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static Long getLongFromEditText(final Activity activity, final int viewId) {
		try {
			return Long.valueOf(((EditText) activity.findViewById(viewId)).getText().toString().trim());
		} catch (Exception e) {
			Log.e(TAG + ".getLongFromEditText", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @param text
	 * @return
	 */
	public static void setTextToEditText(final Activity activity, final int viewId, final String text) {
		try {
			((EditText) activity.findViewById(viewId)).setText(text);
		} catch (Exception e) {
			Log.e(TAG + ".setTextToEditText", e.getMessage());
		}
	}

	/**
	 *
	 * @param view
	 * @param viewId
	 * @param text
	 */
	public static void setTextToEditText(final View view, final int viewId, final String text) {
		try {
			((EditText) view.findViewById(viewId)).setText(text);
		} catch (Exception e) {
			Log.e(TAG + ".setTextToEditText", e.getMessage());
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @param value
	 */
	public static void setIntegerToEditText(final Activity activity, final int viewId, final Integer value) {
		try {
			((EditText) activity.findViewById(viewId)).setText(value.toString());
		} catch (Exception e) {
			Log.e(TAG + ".setIntegerToEditText", e.getMessage());
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @param value
	 */
	public static void setLongToEditText(final Activity activity, final int viewId, final Long value) {
		try {
			((EditText) activity.findViewById(viewId)).setText(value.toString());
		} catch (Exception e) {
			Log.e(TAG + ".setLongToEditText", e.getMessage());
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @param value
	 */
	public static void setDoubleToEditText(final Activity activity, final int viewId, final Double value) {
		try {
			((EditText) activity.findViewById(viewId)).setText(value.toString());
		} catch (Exception e) {
			Log.e(TAG + ".setDoubleToEditText", e.getMessage());
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @param _text
	 * @return
	 */
	public static void cleanTextEditText(final Activity activity, final int viewId) {
		try {
			((EditText) activity.findViewById(viewId)).setText(null);
		} catch (Exception e) {
			Log.e(TAG + ".cleanTextEditText", e.getMessage());
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @param _text
	 * @return
	 */
	public static void cleanTextTextView(final Activity activity, final int viewId) {
		try {
			((TextView) activity.findViewById(viewId)).setText(null);
		} catch (Exception e) {
			Log.e(TAG + ".cleanTextTextView", e.getMessage());
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static String getTextFromTextView(final Activity activity, final int viewId) {
		try {
			return ((TextView) activity.findViewById(viewId)).getText().toString();
		} catch (Exception e) {
			Log.e(TAG + ".getTextFromTextView", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static Integer getIntegerFromTextView(final Activity activity, final int viewId) {
		try {
			return Integer.valueOf(((TextView) activity.findViewById(viewId)).getText().toString());
		} catch (Exception e) {
			Log.e(TAG + ".getIntegerFromTextView", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @param value
	 */
	public static void setIntegerToTextView(final Activity activity, final int viewId, final Integer value) {
		try {
			((TextView) activity.findViewById(viewId)).setText(value.toString());
		} catch (Exception e) {
			Log.e(TAG + ".setIntegerToTextView", e.getMessage());
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @param text
	 */
	public static void setTextToTextView(final Activity activity, final int viewId, final String text) {
		try {
			((TextView) activity.findViewById(viewId)).setText(text);
		} catch (Exception e) {
			Log.e(TAG + ".setTextToTextView", e.getMessage());
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static String getBolStringFromRadioButton(final Activity activity, final int viewId) {
		String bolString = YacamimConfig.getInstance().getYSqliteFalse();
		try {
			if(((RadioButton)activity.findViewById(viewId)).isChecked()) {
				bolString = YacamimConfig.getInstance().getYSqliteTrue();
			}
		} catch (Exception e) {
			Log.e(TAG + ".getBolStringFromRadioButton", e.getMessage());
		}
		return bolString;
	}

	/**
	 * 
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static boolean isRadioGroupChecked(final Activity activity, final int viewId) {
		boolean resultado = false;
		try {
			resultado = ((RadioGroup)activity.findViewById(viewId)).getCheckedRadioButtonId() != -1;
		} catch (Exception e) {
			Log.e(TAG + ".isRadioGroupChecked", e.getMessage());
		}
		return resultado;
	}

	/**
	 * 
	 * @param activity
	 * @param radioGroupId
	 * @return
	 */
	public static RadioButton getCheckedRadioFromRadioGroup(final Activity activity, final int radioGroupId) {
		RadioButton radioButton = null;
		try {
			radioButton = (RadioButton)activity.findViewById(((RadioGroup)activity.findViewById(radioGroupId)).getCheckedRadioButtonId());
		} catch (Exception e) {
			Log.e(TAG + ".getCheckedRadioFromRadioGroup", e.getMessage());
		}
		return radioButton;
	}

	/**
	 *
	 * @param activity
	 * @param viewIdRadio1
	 * @param viewIdRadio2
	 * @param value
	 */
	public static void setBolStringFromRadioButton(final Activity activity, final int viewIdRadio1, final int viewIdRadio2 , final String value) {
		try {
			if(value != null && value.equalsIgnoreCase(YacamimConfig.getInstance().getYSqliteTrue())) {
				((RadioButton)activity.findViewById(viewIdRadio1)).setChecked(true);
			} else {
				((RadioButton)activity.findViewById(viewIdRadio2)).setChecked(true);
			}
		} catch (Exception e) {
			Log.e(TAG + ".setBolStringFromRadioButton", e.getMessage());
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static String getTextFromSpinner(final Activity activity, final int viewId) {
		try {
			return ((Spinner) activity.findViewById(viewId)).getSelectedItem().toString();
		} catch (Exception e) {
			Log.e(TAG + ".getTextFromSpinner", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @param text
	 */
	public static void setTextToSpinner(final Activity activity, final int viewId, final String text, final int size) {
		try {
			final Spinner spinner = ((Spinner) activity.findViewById(viewId));
			for(int i = 0; i <= size; i++) {
				if(spinner.getItemAtPosition(i).toString().equals(text)) {
					spinner.setSelection(i);
					break;
				}
			}
		} catch (Exception e) {
			Log.e(TAG + ".setTextToSpinner", e.getMessage());
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static boolean isSpinnerSelected(final Activity activity, final int viewId) {
		try {
			return !((Spinner) activity.findViewById(viewId)).getSelectedItem().toString().equalsIgnoreCase(activity.getResources().getString(YacamimResources.getInstance().getIdResourceMsgSelectAnItem()));
		} catch (Exception e) {
			Log.e(TAG + ".isSpinnerSelected", e.getMessage());
			return false;
		}
	}

	/**
	 *
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static boolean isRadioButtonChecked(final Activity activity, final int viewId) {
		try {
			return ((RadioButton)activity.findViewById(viewId)).isChecked();
		} catch (Exception e) {
			Log.e(TAG + ".isRadioButtonChecked", e.getMessage());
			return false;
		}
	}
}
