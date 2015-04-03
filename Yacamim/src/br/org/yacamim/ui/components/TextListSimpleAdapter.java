/**
 * TextListSimpleAdapter.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import br.org.yacamim.util.YConstants;
import br.org.yacamim.util.YUtilFormatting;
import br.org.yacamim.util.YUtilReflection;
import br.org.yacamim.util.YUtilString;

/**
 * Class TextListSimpleAdapter TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class TextListSimpleAdapter extends SimpleAdapter {
	
	private static final String TAG = TextListSimpleAdapter.class.getSimpleName();

	/**
	 *
	 */
	private Activity activity;

	/**
	 *
	 */
	private AdapterConfig adapterConfig;

	/**
	 *
	 * @param activity
	 * @param context
	 * @param data
	 * @param adapterConfig
	 */
	public TextListSimpleAdapter(final Activity activity,
			final Context context,
			final List<? extends Map<String, Object>> data,
			final AdapterConfig adapterConfig) {
		super(context, data, 0, null, null);
		this.activity = activity;
		this.adapterConfig = adapterConfig;
	}

	/**
	 *
	 * @see android.widget.SimpleAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		try {
			final HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);
			final Object object = (Object) data.get(YConstants.OBJECT);

			final RowConfig rowConfig = this.selectRowConfig(position, object);

			convertView = this.activity.getLayoutInflater().inflate(rowConfig.getResource(), null);

			if(object != null && rowConfig.getRowConfigItems() != null) {
				for(RowConfigItem rowConfigItem : rowConfig.getRowConfigItems()) {
					this.fillField(convertView, object, rowConfigItem);
				}
			}
		} catch (Exception _e) {
			Log.e(TAG + ".getView", _e.getMessage());
		}
		return convertView;
    }

	/**
	 * @param position
	 * @param object
	 * @return
	 */
	protected RowConfig selectRowConfig(final int position, final Object object) {
		return this.adapterConfig.getRowCondition().selectRowConfig(object, position, this.adapterConfig.getRowConfigs());
	}

	/**
	 * @param convertView
	 * @param object
	 * @param rowConfigItem
	 */
	private void fillField(final View convertView, final Object object, final RowConfigItem rowConfigItem) {
		try {
			if(rowConfigItem.getResourceIdTo() != -1 && rowConfigItem.getGraphFrom() != null) {
				final TextView textView = (TextView) convertView.findViewById(rowConfigItem.getResourceIdTo());
				Object value = YUtilReflection.getPropertyValue(rowConfigItem.getGraphFrom(), object);
				String formmatedValue = format(rowConfigItem, value);
				textView.setText(formmatedValue);
				textView.setPadding(0, 0, 0, 0);
			}
		} catch (Exception e) {
			Log.e(TAG + ".fillField", e.getMessage());
		}
	}

	/**
	 * @param rowConfigItem
	 * @param value
	 * @return
	 */
	private String format(final RowConfigItem rowConfigItem, final Object value) {
		String strValue = "";
		try {
			if(value.getClass().equals(int.class)
					|| value.getClass().equals(long.class)
					|| value.getClass().equals(double.class)
					|| value.getClass().equals(float.class)
					|| value.getClass().equals(Integer.class)
					|| value.getClass().equals(Long.class)
					|| value.getClass().equals(Double.class)
					|| value.getClass().equals(Float.class)
					|| value.getClass().equals(String.class)) {
				strValue = value.toString();
			}
			if(value instanceof String) {
				if(!YUtilString.isEmptyString(strValue) && rowConfigItem.getFormatingType() > 0) {
					if (rowConfigItem.getFormatingType() == TextWatcherFormatter.TIPO_FORMATACAO_CPF) {
						strValue = YUtilFormatting.formatCpf(
								YUtilString.keepOnlyNumbers(
										strValue));
					} else if (rowConfigItem.getFormatingType() == TextWatcherFormatter.TIPO_FORMATACAO_TELEFONE) {
						strValue = YUtilFormatting.formatTelefone(
								YUtilString.keepOnlyNumbers(
										strValue));
					} else if (rowConfigItem.getFormatingType() == TextWatcherFormatter.TIPO_FORMATACAO_DATA) {
						strValue = YUtilFormatting.formatData(strValue);
					}
				}
			} else
			if(value instanceof java.util.Date) {
				if (rowConfigItem.getFormatingType() == TextWatcherFormatter.TIPO_FORMATACAO_DATA) {
					strValue = YUtilFormatting.formatData((java.util.Date)value);
				}
			}
		} catch (Exception e) {
			Log.e(TAG + ".format", e.getMessage());
		}
		return strValue.trim();
	}

	/**
	 * @return the activity
	 */
	protected Activity getActivity() {
		return activity;
	}

	/**
	 * @return the adapterConfig
	 */
	public AdapterConfig getAdapterConfig() {
		return this.adapterConfig;
	}

}