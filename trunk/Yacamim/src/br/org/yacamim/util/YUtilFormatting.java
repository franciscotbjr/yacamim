/**
 * YUtilFormatting.java
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

import android.util.Log;

/**
 *
 * Class YUtilFormatting TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public strictfp abstract class YUtilFormatting {

	private static final String REGEX_0_9 = "[^0-9]";
	private static final String REGEX_0_9A_Z_A_Z = "[^0-9a-zA-Z]";
	private static final String REGEX_TEXT = "[^0-9|a-z|A-Z|âãáàäéèêëíìîïóòôõöúùûüç|ÂÃÁÀÄÉÈÊËÍÌÎÏÓÒÔÕÖÚÙÛÜÇ|\\s]";

	/**
	 *
	 */
	private YUtilFormatting() {
		super();
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param _value
	 * @return
	 */
	public static String formatCpfCnpj(String _value) {
		if (_value == null || _value.trim().length() < 11) {
			return "";
		}
		if (_value.length() == 11) { // CPF
			return YUtilFormatting.format(_value, "###.###.###-##", '#');
		}
		if (_value.length() == 14) { // CNPJ
			return YUtilFormatting.format(_value, "##.###.###/####-##", '#');
		}
		return "";
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param _value
	 * @return
	 */
	public static String formatCpf(String _value) {
		if (_value == null) {
			return "";
		}
		return YUtilFormatting.format(_value, "###.###.###-##", '#');
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param _value
	 * @return
	 */
	public static String formatData(String _value) {
		try {
			if (_value == null) {
				return "";
			}
			SimpleDateFormat simpleDateFormat = YUtilDate.getSimpleDateFormatData();
			return simpleDateFormat.format(simpleDateFormat.parse(_value));
		} catch (Exception _e) {
			Log.e("YUtilFormatting.formatData", _e.getMessage());
			return "";
		}
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param _value
	 * @return
	 */
	public static String formatData(Date _value) {
		try {
			if (_value == null) {
				return "";
			}
			SimpleDateFormat simpleDateFormat = YUtilDate.getSimpleDateFormatData();
			return simpleDateFormat.format(_value);
		} catch (Exception _e) {
			Log.e("YUtilFormatting.formatData", _e.getMessage());
			return "";
		}
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param _value
	 * @return
	 */
	public static String formatCEP(String _value) {
		String strReturn = "";
		if (_value.length() == 8) {
			strReturn = YUtilFormatting.format(_value, "#####-###", '#');
		}
		return strReturn;
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param _value
	 * @return
	 */
	public static String formatTelefone(String _value) {
		String strReturn = "";

		if (_value != null) {
			strReturn = YUtilFormatting.format(_value, "(##)####-####", '#');
		}

		return strReturn;
	}

	/**
	 *
	 * @param _value
	 * @param _pattern
	 * @param _patternChar
	 * @return
	 */
	public static String format(String _value, String _pattern, char _patternChar) {
		try {
			char[] valueArray = _value.toCharArray();
			char[] patternArray = _pattern.toCharArray();
			StringBuffer buffer = new StringBuffer();
			int control = 0;
			for (char patternChar : patternArray) {
				if (control < _value.length()) {
					if (patternChar == _patternChar) {
						buffer.append(valueArray[control]);
						control++;
					} else {
						buffer.append(patternChar);
					}
				}
			}
			return buffer.toString();
		} catch (Exception _e) {
			Log.e("YUtilFormatting.format", _e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param _value
	 * @param _pattern
	 * @param _patternChar
	 * @return
	 */
	public static String formatDatum(String _value, final String _pattern, final char _patternChar) {
		try {
			StringBuffer buffer = new StringBuffer(_pattern);
			int patternLength = _pattern.replaceAll("[^" + _patternChar + "]", "").length();
			int valueLength = _value.length();

			if (valueLength > patternLength) {
				_value = _value.substring(0, patternLength);
				valueLength = patternLength;
			}

			if (patternLength == 0 || valueLength == 0) {
				return "";
			}

			while (patternLength > valueLength) {
				if (buffer.length() == 0) {
					break;
				}
				if (buffer.charAt(0) == _patternChar) {
					patternLength--;
				}
				buffer.deleteCharAt(0);
			}

			if (buffer.length() > 0 && buffer.charAt(0) != _patternChar) {
				buffer.deleteCharAt(0);
			}

			return format(_value, buffer.toString(), _patternChar);
		} catch (Exception _e) {
			Log.e("YUtilFormatting.formatarDado", _e.getMessage());
			return "";
		}
	}

	/**
	 *
	 * @param _value
	 * @param _pattern
	 * @param _patternChar
	 * @return
	 */
	public static String formatDecimalDatum(String _value, final String _pattern, final char _patternChar) {
		try {
			StringBuffer buffer = new StringBuffer(_pattern);
			int padraoLength = _pattern.replaceAll("[^" + _patternChar + "]", "").length();


			int virgPadrao = _pattern.lastIndexOf(",");
			int pontoPadrao = _pattern.lastIndexOf(".");
			int poscDecimalPadrao = -1;
			int qtdDecimaisPadrao = -1;

			if((virgPadrao!= -1) || (pontoPadrao != -1)){
				if(virgPadrao > pontoPadrao){
					poscDecimalPadrao = virgPadrao;
				}else{
					poscDecimalPadrao = pontoPadrao;
				}
				qtdDecimaisPadrao = _pattern.length() - poscDecimalPadrao -1;
			}


			int indexDOT = _value.indexOf(".");
			int valueDecimalAmount = 0;
			if(indexDOT != -1){
				valueDecimalAmount = _value.length() - indexDOT -1;
				_value = _value.replace(".", "");
				if(valueDecimalAmount < qtdDecimaisPadrao){
					for (int i = 0; i < valueDecimalAmount; i++) {
						_value = _value + "0";
					}
				}

			}else if(valueDecimalAmount < qtdDecimaisPadrao){
				for (int i = 0; i < qtdDecimaisPadrao; i++) {
					_value = _value + "0";
				}
			}


			int valueLength = _value.length();

			if (valueLength > padraoLength) {
				_value = _value.substring(0, padraoLength);
				valueLength = padraoLength;
			}

			if (padraoLength == 0 || valueLength == 0) {
				return "";
			}

			while (padraoLength > valueLength) {
				if (buffer.length() == 0) {
					break;
				}
				if (buffer.charAt(0) == _patternChar) {
					padraoLength--;
				}
				buffer.deleteCharAt(0);
			}

			if (buffer.length() > 0 && buffer.charAt(0) != _patternChar) {
				buffer.deleteCharAt(0);
			}

			return format(_value, buffer.toString(), _patternChar);
		} catch (Exception _e) {
			Log.e("YUtilFormatting.formatarDadoDecimal", _e.getMessage());
			return "";
		}
	}

	/**
	 *
	 * @param _pattern
	 * @param _patternChar
	 * @return
	 */
	public static int sizeCharFormat(final String _pattern, char _patternChar) {
		try {
			int intSize = 0;
			for (int i = 0; i < _pattern.length(); i++) {
				if (_pattern.charAt(i) != _patternChar) {
					intSize++;
				}
			}
			return intSize;
		} catch (Exception _e) {
			Log.e("YUtilFormatting.sizeCharFormat", _e.getMessage());
			return -1;
		}
	}

	/**
	 *
	 * @param _value
	 * @return
	 */
	public static final String formataDecimal(double _value) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(YUtilDate.getLocaleBrasil());
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		return numberFormat.format(_value);
	}

	/**
	 *
	 * @param _value
	 * @param _fractionDigits
	 * @return
	 */
	public static final String formataDecimal(double _value, int _fractionDigits) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(YUtilDate.getLocaleBrasil());
		numberFormat.setMaximumFractionDigits(_fractionDigits);
		numberFormat.setMinimumFractionDigits(_fractionDigits);
		return numberFormat.format(_value);
	}

	/**
	 *
	 * @param _value
	 * @return
	 */
	public static final String formataDecimal(final Double _value) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(YUtilDate.getLocaleBrasil());
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		return numberFormat.format(_value);
	}

	/**
	 *
	 * @param _value
	 * @return
	 */
	public static final String formataDecimal(float _value) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(YUtilDate.getLocaleBrasil());
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		return numberFormat.format(_value);
	}

	/**
	 *
	 * @param _value
	 * @return
	 */
	public static final String formataDecimal(final Float _value) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(YUtilDate.getLocaleBrasil());
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		return numberFormat.format(_value);
	}

	/**
	 *
	 * @param _value
	 * @param _surrencySimble
	 * @return
	 */
	public static final String formataCurrency(double _value, final String _surrencySimble) {
		NumberFormat f = NumberFormat.getInstance(YUtilDate.getLocaleBrasil());
		if (f instanceof DecimalFormat) {
			((DecimalFormat) f).setDecimalSeparatorAlwaysShown(true);
		}
		((DecimalFormat) f).setMaximumFractionDigits(2);
		((DecimalFormat) f).setMinimumFractionDigits(2);
		Currency c = Currency.getInstance(YUtilDate.getLocaleBrasil());
		((DecimalFormat) f).setCurrency(c);
		return (_surrencySimble + f.format(_value));
	}

	/**
	 *
	 * @param _value
	 * @return
	 */
	public static String unformatValue(final String _value) {
		if (_value == null) {
			return "";
		}
		return _value.replaceAll(REGEX_0_9A_Z_A_Z, "");
	}

	/**
	 *
	 * @param _text
	 * @return
	 */
	public static String unformatText(final String _text) {
		if (_text == null) {
			return "";
		}
		return _text.replaceAll(REGEX_TEXT, "");
	}

	/**
	 *
	 * @param _value
	 * @return
	 */
	public static String unformatNumericValue(final String _value) {
		if (_value == null) {
			return "";
		}
		return _value.replaceAll(REGEX_0_9, "");
	}
}