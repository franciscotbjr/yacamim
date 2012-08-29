/**
 * UtilString.java
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
package br.org.yacamim.util;

import java.nio.charset.Charset;

import android.util.Log;

/**
 * 
 * Class UtilString TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final strictfp class UtilString {
	
	/**
	 * 
	 */
	public static final String TAB = "\t";

	/**
	 * 
	 */
	public static final String ENTER = "\n";

	/**
	 * 
	 */
	public static final char WHITE_SPACE = '\u0020';

	/**
	 * 
	 */
	public static final String REGEX_WHITE_SPACE = "[" + UtilString.WHITE_SPACE + "]";

	/**
	 * 
	 */
	public static final String HTML_SPACE = "&nbsp;";

	/**
	 * 
	 */
	public static final String REGEX_HTML_TAG = "\\<[^\\>]*\\>";

	/**
	 * 
	 */
	public static final String CPF_REGEX = "[0-9]{3}[\\.|\\s]?[0-9]{3}[\\.|\\s]?[0-9]{3}[\\-|\\s]?[0-9]{2}";

	/**
	 * 
	 */
	public static final String CNPJ_REGEX = "[0-9]{2}[\\.|\\s]?[0-9]{3}[\\.|\\s]?[0-9]{3}[\\/|\\s]?[0-9]{4}[\\-|\\s]?[0-9]{2}";
	
	/**
	 * 
	 */
	public static final String ZIPCODE_REGEX = "[0-9]{5}\\-?[0-9]{3}|[0-9]{2}\\.[0-9]{3}\\-?[0-9]{3}";
	
	/**
	 * 
	 */
	public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	
	/**
	 * 
	 */
	public static final String PHONE_REGEX = "\\([0-9]{2}\\)[0-9]{4}\\-[0-9]{4}";
	
	/**
	 * 
	 */
	public static final String URL_REGEX = "^((https?|ftp|news):\\/\\/)?([a-zA-Z]([a-zA-Z0-9\\-]*\\.)+([a-zA-Z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\\/[a-zA-Z0-9_\\-\\.~]+)*(\\/([a-zA-Z0-9_\\-\\.]*)(\\?[a-zA-Z0-9+_\\-\\.%=&]*)?)?(#[a-zA-Z][a-zA-Z0-9_]*)?$";
	
	/**
	 * 
	 */
	public static final String IP_REGEX = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

	/**
	 * 
	 */
	public static final String PROPERTY_REGEX = "^([a-zA-Z]+)([a-zA-Z]+|\\.{1}[a-zA-Z]+)+(\\.{1}[a-zA-Z]+|[a-zA-Z]+)$";

	/**
	 * 
	 */
	public static final String REGEX_INTEGER = "[0-9]+";

	/**
	 * 
	 */
	public static final String REGEX_FLOATING_POINT = "[0-9]+|[0-9]+\\.{1}[0-9]+";

	/**
	 * 
	 */
	public static final String REGEX_FILE_SYSTEM_PATH = "^((([a-zA-Z]{1}):(/|\\\\)(.+))|(/(.+)))";

	/**
	 * 
	 */
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	/**
	 * 
	 */
	private UtilString() {
		super();
	}
	
	/**
	 * 
	 * @param _string
	 * @return
	 */
	public static final boolean isEmptyString(final String _string) {
		if(_string !=  null && !_string.trim().equals("")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param _string
	 * @return
	 */
	public static final boolean isEmptyString(final CharSequence _string) {
		if(_string !=  null && !_string.toString().trim().equals("")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param _datum
	 * @return
	 */
	public static String keepOnlyNumbers(String _datum) {
		if ((_datum == null) || _datum.trim().equals("")) {
			return "";
		}
		char[] st = _datum.toCharArray();
		StringBuffer noNumberBuffer = new StringBuffer();
		for (int i = 0; i < st.length; i++) {
			if (UtilString.isNumeric(new String(new char[] { st[i] }))) {
				noNumberBuffer.append(st[i]);
			}
		}
		return noNumberBuffer.toString();
	}
	
	/**
	 * 
	 * @param _value
	 * @return
	 */
	public static boolean isNumeric(final String _value) {
		if (UtilString.isEmptyString(_value)) {
			return false;
		}
		return _value.matches(UtilString.REGEX_FLOATING_POINT);
	}

	/**
	 * 
	 * @param _string
	 * @return
	 */
	public static String nullToEmptyString(final String _string) {
		if(UtilString.isEmptyString(_string)) {
			return "";
		}
		return _string;
	}
	
	/**
	 * 
	 * @param _string
	 * @return
	 */
	public static String cleanMultipleWhiteSpaces(final String _string) {
		if(UtilString.isEmptyString(_string)) {
			return null;
		}
		return _string.replaceAll(" {2,}", " ");
	}

	/**
	 * 
	 * @param _string
	 * @return
	 */
	public static String firstCharacterToLowerCase(final String _string) {
		if (UtilString.isEmptyString(_string)) {
			return "";
		}
		try {
			String string = "";
			String firstCharacter = new String(new char[] { _string.toCharArray()[0] }).toLowerCase();
			string = firstCharacter + _string.substring(1);
			return string;
		} catch (Exception _e) {
			Log.e("UtilString.firstCharacterToLowerCase", _e.getMessage());
			return "";
		}
	}

	/**
	 * 
	 * @param _string
	 * @return
	 */
	public static String firstCharacterToUpperCase(final String _string) {
		if (UtilString.isEmptyString(_string)) {
			return "";
		}
		try {
			String string = "";
			String firstCharacter = new String(new char[] { _string.toCharArray()[0] }).toUpperCase();
			string = firstCharacter + _string.substring(1);
			return string;
		} catch (Exception _e) {
			Log.e("UtilString.firstCharacterToUpperCase", _e.getMessage());
			return "";
		}
	}
	
	/**
	 * 
	 * @param _string
	 * @return
	 */
	public static String clearString(final String _string) {
		if (UtilString.isEmptyString(_string)) {
			return "";
		}
		try {
			return _string.replaceAll("[^A-Za-z0-9]", "");
		} catch (Exception _e) {
			Log.e("UtilString.clearString", _e.getMessage());
			return "";
		}
	}

	/**
	 * 
	 * @param _value
	 * @return
	 */
	public static boolean isInteger(final String _value) {
		if (UtilString.isEmptyString(_value)) {
			return false;
		}
		return _value.matches(UtilString.REGEX_INTEGER);
	}
}