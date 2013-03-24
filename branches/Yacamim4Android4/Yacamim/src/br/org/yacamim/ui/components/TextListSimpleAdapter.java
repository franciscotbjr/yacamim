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
	 * @param _activity
	 * @param _context
	 * @param _data
	 * @param _adapterConfig
	 */
	public TextListSimpleAdapter(final Activity _activity,
			final Context _context,
			final List<? extends Map<String, Object>> _data,
			final AdapterConfig _adapterConfig) {
		super(_context, _data, 0, null, null);
		this.activity = _activity;
		this.adapterConfig = _adapterConfig;
	}

	/**
	 *
	 * @see android.widget.SimpleAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int _position, View convertView, ViewGroup _parent) {
		try {
			final HashMap<String, Object> data = (HashMap<String, Object>) getItem(_position);
			final Object object = (Object) data.get(YConstants.OBJECT);

			final RowConfig rowConfig = this.selectRowConfig(_position, object);

			convertView = this.activity.getLayoutInflater().inflate(rowConfig.getResource(), null);

			if(object != null && rowConfig.getRowConfigItems() != null) {
				for(RowConfigItem rowConfigItem : rowConfig.getRowConfigItems()) {
					this.fillField(convertView, object, rowConfigItem);
				}
			}
		} catch (Exception _e) {
			Log.e("TextListSimpleAdapter.getView", _e.getMessage());
		}
		return convertView;
    }

	/**
	 * @param _position
	 * @param _object
	 * @return
	 */
	protected RowConfig selectRowConfig(int _position, final Object _object) {
		return this.adapterConfig.getRowCondition().selectRowConfig(_object, _position, this.adapterConfig.getRowConfigs());
	}

	/**
	 * @param _convertView
	 * @param _object
	 * @param _rowConfigItem
	 */
	private void fillField(View _convertView, final Object _object, RowConfigItem _rowConfigItem) {
		try {
			if(_rowConfigItem.getResourceIdTo() != -1 && _rowConfigItem.getGraphFrom() != null) {
				final TextView textView = (TextView) _convertView.findViewById(_rowConfigItem.getResourceIdTo());
				Object value = YUtilReflection.getPropertyValue(_rowConfigItem.getGraphFrom(), _object);
				String formmatedValue = format(_rowConfigItem, value);
				textView.setText(formmatedValue);
				textView.setPadding(0, 0, 0, 0);
			}
		} catch (Exception _e) {
			Log.e("TextListSimpleAdapter.fillField", _e.getMessage());
		}
	}

	/**
	 * @param _rowConfigItem
	 * @param _value
	 * @return
	 */
	private String format(final RowConfigItem _rowConfigItem, final Object _value) {
		String value = "";
		try {
			if(_value.getClass().equals(int.class)
					|| _value.getClass().equals(long.class)
					|| _value.getClass().equals(double.class)
					|| _value.getClass().equals(float.class)
					|| _value.getClass().equals(Integer.class)
					|| _value.getClass().equals(Long.class)
					|| _value.getClass().equals(Double.class)
					|| _value.getClass().equals(Float.class)
					|| _value.getClass().equals(String.class)) {
				value = _value.toString();
			}
			if(_value instanceof String) {
				if(!YUtilString.isEmptyString(value) && _rowConfigItem.getFormatingType() > 0) {
					if (_rowConfigItem.getFormatingType() == TextWatcherFormatter.TIPO_FORMATACAO_CPF) {
						value = YUtilFormatting.formatCpf(
								YUtilString.keepOnlyNumbers(
										value));
					} else if (_rowConfigItem.getFormatingType() == TextWatcherFormatter.TIPO_FORMATACAO_TELEFONE) {
						value = YUtilFormatting.formatTelefone(
								YUtilString.keepOnlyNumbers(
										value));
					} else if (_rowConfigItem.getFormatingType() == TextWatcherFormatter.TIPO_FORMATACAO_DATA) {
						value = YUtilFormatting.formatData(value);
					}
				}
			} else
			if(_value instanceof java.util.Date) {
				if (_rowConfigItem.getFormatingType() == TextWatcherFormatter.TIPO_FORMATACAO_DATA) {
					value = YUtilFormatting.formatData((java.util.Date)_value);
				}
			}
		} catch (Exception _e) {
			Log.e("TextListSimpleAdapter.format", _e.getMessage());
		}
		return value.trim();
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