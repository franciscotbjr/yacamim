/**
 * DefaultAlertDialogBuilder.java
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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import br.org.yacamim.util.YUtilFormatting;
import br.org.yacamim.util.YUtilString;

/**
 *
 * Class TextWatcherFormatter TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class TextWatcherFormatter implements TextWatcher {

	/**
	 *
	 */
	public static final char DEAFULT_CHARACTER = '#';

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 */
	public static final int TIPO_FORMATACAO_CPF = 1;

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 */
	public static final int TIPO_FORMATACAO_TELEFONE = 2;

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 */
	public static final int TIPO_FORMATACAO_DATA = 3;

	/**
	 *
	 */
	private EditText editText = null;

	/**
	 *
	 */
	private int formattingType = 0;

	/**
	 *
	 */
	private int lenghtCountStart = 0;

	/**
	 *
	 */
	private boolean dynamicMask = false;

	/**
	 *
	 */
	private String mask = null;

	/**
	 *
	 * @param _editText
	 * @param _formattingType
	 * @param _lenghtCountStart
	 */
	public TextWatcherFormatter(final EditText _editText, final int _formattingType, final int _lenghtCountStart) {
		super();
		this.editText = _editText;
		this.formattingType = _formattingType;
		this.lenghtCountStart = _lenghtCountStart;
		this.editText.addTextChangedListener(this);
	}

	/**
	 *
	 * @param _editText
	 * @param _dynamicMask
	 * @param _mask
	 * @param _lenghtCountStart
	 */
	public TextWatcherFormatter(final EditText _editText, final boolean _dynamicMask, final String _mask, final int _lenghtCountStart) {
		super();
		this.editText = _editText;
		this.dynamicMask = _dynamicMask;
		this.lenghtCountStart = _lenghtCountStart;
		this.mask = _mask;
		this.editText.addTextChangedListener(this);
	}

	/**
	 *
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public void afterTextChanged(final Editable _editable) {

	}

	/**
	 *
	 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void beforeTextChanged(final CharSequence _charSequence, final int _start, final int _count, final int _after) {
		// TODO Auto-generated method stub

	}

	/**
	 *
	 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int, int, int)
	 */
	@Override
	public void onTextChanged(final CharSequence _charSequence, final int _start, final int _before, final int _count) {
		try {
        	this.editText.removeTextChangedListener(this);
        	if (filterLongEnough()) {

        		if(dynamicMask && !YUtilString.isEmptyString(this.mask) && this.mask.length() > 0) {
        			this.editText.setText(
        					YUtilFormatting.format(
	        					YUtilString.clearString(
	        							this.editText.getText().toString()), this.mask, DEAFULT_CHARACTER));

        		} else if (formattingType == TIPO_FORMATACAO_CPF) {
        			this.editText.setText(
        					YUtilFormatting.formatCpf(
        							YUtilString.keepOnlyNumbers(
        									this.editText.getText().toString())));
        		} else if (formattingType == TIPO_FORMATACAO_TELEFONE) {
        			this.editText.setText(
        					YUtilFormatting.formatTelefone(
        							YUtilString.keepOnlyNumbers(
        									this.editText.getText().toString())));
        		}
        	} else if (formattingType == TIPO_FORMATACAO_DATA) {
        		this.editText.setText(
        				YUtilFormatting.formatData(
        							this.editText.getText().toString()));
    		}
        	this.editText.addTextChangedListener(this);
        	this.editText.setSelection(this.editText.getText().length());
		} catch (Exception _e) {
			Log.e("TextWatcherFormatter.onTextChanged", _e.getMessage());
		}
	}

	/**
	 *
	 * @return
	 */
	private boolean filterLongEnough() {
		if(this.editText == null) {
			return false;
		}
        return this.editText.getText().toString().trim().length() > this.lenghtCountStart;
    }

}
