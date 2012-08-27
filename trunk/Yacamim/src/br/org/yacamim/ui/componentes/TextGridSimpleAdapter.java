/**
 * TextGridSimpleAdapter.java
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
package br.org.yacamim.ui.componentes;

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
import br.org.yacamim.util.Constants;
import br.org.yacamim.util.UtilFormatting;
import br.org.yacamim.util.UtilReflection;
import br.org.yacamim.util.UtilString;

/**
 * Class TextGridSimpleAdapter TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class TextGridSimpleAdapter extends SimpleAdapter {
	
	/**
	 * 
	 */
	private Activity activity;
	
	/**
	 * 
	 */
	private String[] attributeNames;
	
	/**
	 * 
	 */
	private int[] uiFieldResourceIds;
	
	/**
	 * 
	 */
	private int[] formattingTypes;
	
	/**
	 * 
	 */
	private int resource;

	/**
	 * @param _context
	 * @param _data
	 * @param _resource
	 * @param _from
	 * @param _to
	 */
	public TextGridSimpleAdapter(final Activity _activity, final int[] _tipoFormatacoes, 
			final Context _context, final List<? extends Map<String, Object>> _data, final int _resource, final String[] _from, final int[] _to) {
		super(_context, _data, _resource, _from, _to);
		this.activity = _activity;
		this.attributeNames = _from;
		this.uiFieldResourceIds = _to;
		this.formattingTypes = _tipoFormatacoes;
		this.resource = _resource;
	}

	/**
	 *
	 * @see android.widget.SimpleAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int _position, View convertView, ViewGroup _parent) {
		super.getView(_position, convertView, _parent);
		try {
			if (convertView == null) {
				convertView = this.activity.getLayoutInflater().inflate(this.resource, null);
			}
			 
			final HashMap<String, Object> data = (HashMap<String, Object>) getItem(_position);
			 
			final Object object = (Object) data.get(Constants.OBJECT);
			 
			if(object != null && this.attributeNames != null && this.uiFieldResourceIds != null) {
				for(int i = 0; i < this.uiFieldResourceIds.length; i++) {
					this.preencheCampo(convertView, object, i);
				}
			}
		} catch (Exception _e) {
			Log.e("TextGridSimpleAdapter.getView", _e.getMessage());
		}
		return convertView;
    }

	/**
	 * @param _convertView
	 * @param _object
	 * @param _count
	 */
	private void preencheCampo(View _convertView, final Object _object, int _count) {
		try {
			final TextView textView = (TextView) _convertView.findViewById(this.uiFieldResourceIds[_count]);
			Object value = UtilReflection.getPropertyValue(this.attributeNames[_count], _object);
			String formmatedValue = format(_count, value);
			textView.setText("- " + formmatedValue);
			textView.setPadding(0, 0, 0, 0);
		} catch (Exception _e) {
			Log.e("TextGridSimpleAdapter.preencheCampo", _e.getMessage());
		}
	}

	/**
	 * @param _count
	 * @param _value
	 * @return
	 */
	private String format(final int _count, final Object _value) {
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
				if(!UtilString.isEmptyString(value) && formattingTypes != null) {
					if (formattingTypes[_count] == TextWatcherFormatter.TIPO_FORMATACAO_CPF) {
						value = UtilFormatting.formatCpf(
								UtilString.keepOnlyNumbers(
										value));
					} else if (formattingTypes[_count] == TextWatcherFormatter.TIPO_FORMATACAO_TELEFONE) {
						value = UtilFormatting.formatTelefone(
								UtilString.keepOnlyNumbers(
										value));
					} else if (formattingTypes[_count] == TextWatcherFormatter.TIPO_FORMATACAO_DATA) {
						value = UtilFormatting.formatData(value);
					}
				}
			} else 
			if(_value instanceof java.util.Date) {
				if (formattingTypes[_count] == TextWatcherFormatter.TIPO_FORMATACAO_DATA) {
					value = UtilFormatting.formatData((java.util.Date)_value);
				}
			}
		} catch (Exception _e) {
			Log.e("TextGridSimpleAdapter.format", _e.getMessage());
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
	 * @return the attributeNames
	 */
	protected String[] getAttributeNames() {
		return attributeNames;
	}

	/**
	 * @return the uiFieldResourceIds
	 */
	protected int[] getUiFieldResourceIds() {
		return uiFieldResourceIds;
	}

	/**
	 * @return the formattingTypes
	 */
	protected int[] getFormattingTypes() {
		return formattingTypes;
	}
	
	/**
	 * @param _tipoFormatacoes the formattingTypes to set
	 */
	protected void setTipoFormatacoes(int[] _tipoFormatacoes) {
		this.formattingTypes = _tipoFormatacoes;
	}

	/**
	 * @return the resource
	 */
	protected int getResource() {
		return resource;
	}
}