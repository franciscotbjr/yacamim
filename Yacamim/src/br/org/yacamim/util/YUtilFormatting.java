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
	
	private static final String TAG = YUtilFormatting.class.getSimpleName();

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
	 * @param value
	 * @return
	 */
	public static String formatCpfCnpj(final String value) {
		if (value == null || value.trim().length() < 11) {
			return "";
		}
		if (value.length() == 11) { // CPF
			return YUtilFormatting.format(value, "###.###.###-##", '#');
		}
		if (value.length() == 14) { // CNPJ
			return YUtilFormatting.format(value, "##.###.###/####-##", '#');
		}
		return "";
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param value
	 * @return
	 */
	public static String formatCpf(final String value) {
		if (value == null) {
			return "";
		}
		return YUtilFormatting.format(value, "###.###.###-##", '#');
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param _value
	 * @return
	 */
	public static String formatData(final String value) {
		try {
			if (value == null) {
				return "";
			}
			SimpleDateFormat simpleDateFormat = YUtilDate.getSimpleDateFormat();
			return simpleDateFormat.format(simpleDateFormat.parse(value));
		} catch (Exception e) {
			Log.e(TAG + ".formatData", e.getMessage());
			return "";
		}
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param value
	 * @return
	 */
	public static String formatData(final Date value) {
		try {
			if (value == null) {
				return "";
			}
			SimpleDateFormat simpleDateFormat = YUtilDate.getSimpleDateFormat();
			return simpleDateFormat.format(value);
		} catch (Exception e) {
			Log.e(TAG + ".formatData", e.getMessage());
			return "";
		}
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param value
	 * @return
	 */
	public static String formatCEP(final String value) {
		String strReturn = "";
		if (value.length() == 8) {
			strReturn = YUtilFormatting.format(value, "#####-###", '#');
		}
		return strReturn;
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param value
	 * @return
	 */
	public static String formatTelefone(final String value) {
		String strReturn = "";

		if (value != null) {
			strReturn = YUtilFormatting.format(value, "(##)####-####", '#');
		}

		return strReturn;
	}

	/**
	 *
	 * @param value
	 * @param pattern
	 * @param patternChar
	 * @return
	 */
	public static String format(final String value, final String pattern, final char patternChar) {
		try {
			char[] valueArray = value.toCharArray();
			char[] patternArray = pattern.toCharArray();
			StringBuffer buffer = new StringBuffer();
			int control = 0;
			for (char patternCharItem : patternArray) {
				if (control < value.length()) {
					if (patternCharItem == patternChar) {
						buffer.append(valueArray[control]);
						control++;
					} else {
						buffer.append(patternCharItem);
					}
				}
			}
			return buffer.toString();
		} catch (Exception e) {
			Log.e(TAG + ".format", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param value
	 * @param pattern
	 * @param patternChar
	 * @return
	 */
	public static String formatDatum(final String value, final String pattern, final char patternChar) {
		try {
			String valueTemp = value;
			StringBuffer buffer = new StringBuffer(pattern);
			int patternLength = pattern.replaceAll("[^" + patternChar + "]", "").length();
			int valueLength = valueTemp.length();

			if (valueLength > patternLength) {
				valueTemp = valueTemp.substring(0, patternLength);
				valueLength = patternLength;
			}

			if (patternLength == 0 || valueLength == 0) {
				return "";
			}

			while (patternLength > valueLength) {
				if (buffer.length() == 0) {
					break;
				}
				if (buffer.charAt(0) == patternChar) {
					patternLength--;
				}
				buffer.deleteCharAt(0);
			}

			if (buffer.length() > 0 && buffer.charAt(0) != patternChar) {
				buffer.deleteCharAt(0);
			}

			return format(valueTemp, buffer.toString(), patternChar);
		} catch (Exception e) {
			Log.e(TAG + ".formatDatum", e.getMessage());
			return "";
		}
	}

	/**
	 *
	 * @param value
	 * @param pattern
	 * @param patternChar
	 * @return
	 */
	public static String formatDecimalDatum(final String value, final String pattern, final char patternChar) {
		try {
			String valueTemp = value;
			StringBuffer buffer = new StringBuffer(pattern);
			int padraoLength = pattern.replaceAll("[^" + patternChar + "]", "").length();


			int virgPadrao = pattern.lastIndexOf(",");
			int pontoPadrao = pattern.lastIndexOf(".");
			int poscDecimalPadrao = -1;
			int qtdDecimaisPadrao = -1;

			if((virgPadrao!= -1) || (pontoPadrao != -1)){
				if(virgPadrao > pontoPadrao){
					poscDecimalPadrao = virgPadrao;
				}else{
					poscDecimalPadrao = pontoPadrao;
				}
				qtdDecimaisPadrao = pattern.length() - poscDecimalPadrao -1;
			}


			int indexDOT = valueTemp.indexOf(".");
			int valueDecimalAmount = 0;
			if(indexDOT != -1){
				valueDecimalAmount = valueTemp.length() - indexDOT -1;
				valueTemp = valueTemp.replace(".", "");
				if(valueDecimalAmount < qtdDecimaisPadrao){
					for (int i = 0; i < valueDecimalAmount; i++) {
						valueTemp = valueTemp + "0";
					}
				}

			}else if(valueDecimalAmount < qtdDecimaisPadrao){
				for (int i = 0; i < qtdDecimaisPadrao; i++) {
					valueTemp = valueTemp + "0";
				}
			}


			int valueLength = valueTemp.length();

			if (valueLength > padraoLength) {
				valueTemp = valueTemp.substring(0, padraoLength);
				valueLength = padraoLength;
			}

			if (padraoLength == 0 || valueLength == 0) {
				return "";
			}

			while (padraoLength > valueLength) {
				if (buffer.length() == 0) {
					break;
				}
				if (buffer.charAt(0) == patternChar) {
					padraoLength--;
				}
				buffer.deleteCharAt(0);
			}

			if (buffer.length() > 0 && buffer.charAt(0) != patternChar) {
				buffer.deleteCharAt(0);
			}

			return format(valueTemp, buffer.toString(), patternChar);
		} catch (Exception e) {
			Log.e(TAG + ".formatDecimalDatum", e.getMessage());
			return "";
		}
	}

	/**
	 *
	 * @param pattern
	 * @param patternChar
	 * @return
	 */
	public static int sizeCharFormat(final String pattern, final char patternChar) {
		try {
			int intSize = 0;
			for (int i = 0; i < pattern.length(); i++) {
				if (pattern.charAt(i) != patternChar) {
					intSize++;
				}
			}
			return intSize;
		} catch (Exception e) {
			Log.e(TAG + ".sizeCharFormat", e.getMessage());
			return -1;
		}
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static final String formataDecimal(final double value) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(YUtilDate.getLocaleBrasil());
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		return numberFormat.format(value);
	}

	/**
	 *
	 * @param value
	 * @param fractionDigits
	 * @return
	 */
	public static final String formataDecimal(final double value, final int fractionDigits) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(YUtilDate.getLocaleBrasil());
		numberFormat.setMaximumFractionDigits(fractionDigits);
		numberFormat.setMinimumFractionDigits(fractionDigits);
		return numberFormat.format(value);
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static final String formataDecimal(final Double value) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(YUtilDate.getLocaleBrasil());
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		return numberFormat.format(value);
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static final String formataDecimal(final float value) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(YUtilDate.getLocaleBrasil());
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		return numberFormat.format(value);
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static final String formataDecimal(final Float value) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(YUtilDate.getLocaleBrasil());
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		return numberFormat.format(value);
	}

	/**
	 *
	 * @param value
	 * @param surrencySimble
	 * @return
	 */
	public static final String formataCurrency(final double value, final String surrencySimble) {
		NumberFormat f = NumberFormat.getInstance(YUtilDate.getLocaleBrasil());
		if (f instanceof DecimalFormat) {
			((DecimalFormat) f).setDecimalSeparatorAlwaysShown(true);
		}
		((DecimalFormat) f).setMaximumFractionDigits(2);
		((DecimalFormat) f).setMinimumFractionDigits(2);
		Currency c = Currency.getInstance(YUtilDate.getLocaleBrasil());
		((DecimalFormat) f).setCurrency(c);
		return (surrencySimble + f.format(value));
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static String unformatValue(final String value) {
		if (value == null) {
			return "";
		}
		return value.replaceAll(REGEX_0_9A_Z_A_Z, "");
	}

	/**
	 *
	 * @param text
	 * @return
	 */
	public static String unformatText(final String text) {
		if (text == null) {
			return "";
		}
		return text.replaceAll(REGEX_TEXT, "");
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static String unformatNumericValue(final String value) {
		if (value == null) {
			return "";
		}
		return value.replaceAll(REGEX_0_9, "");
	}
}