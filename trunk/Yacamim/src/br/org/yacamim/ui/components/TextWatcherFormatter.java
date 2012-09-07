/**
 * DefaultAlertDialogBuilder.java
 *
 * Copyright 2012 yacamim.org.br
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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import br.org.yacamim.util.UtilFormatting;
import br.org.yacamim.util.UtilString;

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
            	
        		if(dynamicMask && !UtilString.isEmptyString(this.mask) && this.mask.length() > 0) {
        			this.editText.setText(
        					UtilFormatting.format(
	        					UtilString.clearString(
	        							this.editText.getText().toString()), this.mask, DEAFULT_CHARACTER));
        			
        		} else if (formattingType == TIPO_FORMATACAO_CPF) {
        			this.editText.setText(
        					UtilFormatting.formatCpf(
        							UtilString.keepOnlyNumbers(
        									this.editText.getText().toString())));
        		} else if (formattingType == TIPO_FORMATACAO_TELEFONE) {
        			this.editText.setText(
        					UtilFormatting.formatTelefone(
        							UtilString.keepOnlyNumbers(
        									this.editText.getText().toString())));
        		}
        	} else if (formattingType == TIPO_FORMATACAO_DATA) {
        		this.editText.setText(
        				UtilFormatting.formatData(
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
