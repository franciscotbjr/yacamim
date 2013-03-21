/**
 * YUtilString.java
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

import java.nio.charset.Charset;

import android.util.Log;

/**
 *
 * Class YUtilString TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final strictfp class YUtilString {
	
	private static final String TAG = YUtilString.class.getSimpleName();

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
	public static final String REGEX_WHITE_SPACE = "[" + YUtilString.WHITE_SPACE + "]";

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
	private YUtilString() {
		super();
	}

	/**
	 *
	 * @param string
	 * @return
	 */
	public static final boolean isEmptyString(final String string) {
		if(string !=  null && !string.trim().equals("")) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * @param string
	 * @return
	 */
	public static final boolean isEmptyString(final CharSequence string) {
		if(string !=  null && !string.toString().trim().equals("")) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * @param string
	 * @return
	 */
	public static String keepOnlyNumbers(final String string) {
		if ((string == null) || string.trim().equals("")) {
			return "";
		}
		char[] st = string.toCharArray();
		StringBuffer noNumberBuffer = new StringBuffer();
		for (int i = 0; i < st.length; i++) {
			if (YUtilString.isNumeric(new String(new char[] { st[i] }))) {
				noNumberBuffer.append(st[i]);
			}
		}
		return noNumberBuffer.toString();
	}

	/**
	 *
	 * @param string
	 * @return
	 */
	public static boolean isNumeric(final String string) {
		if (YUtilString.isEmptyString(string)) {
			return false;
		}
		return string.matches(YUtilString.REGEX_FLOATING_POINT);
	}

	/**
	 *
	 * @param string
	 * @return
	 */
	public static String nullToEmptyString(final String string) {
		if(YUtilString.isEmptyString(string)) {
			return "";
		}
		return string;
	}

	/**
	 *
	 * @param string
	 * @return
	 */
	public static String cleanMultipleWhiteSpaces(final String string) {
		if(YUtilString.isEmptyString(string)) {
			return null;
		}
		return string.replaceAll(" {2,}", " ");
	}

	/**
	 *
	 * @param string
	 * @return
	 */
	public static String firstCharacterToLowerCase(final String string) {
		if (YUtilString.isEmptyString(string)) {
			return "";
		}
		try {
			String stringMod = "";
			String firstCharacter = new String(new char[] { string.toCharArray()[0] }).toLowerCase();
			stringMod = firstCharacter + string.substring(1);
			return stringMod;
		} catch (Exception e) {
			Log.e(TAG + ".firstCharacterToLowerCase", e.getMessage());
			return "";
		}
	}

	/**
	 *
	 * @param string
	 * @return
	 */
	public static String firstCharacterToUpperCase(final String string) {
		if (YUtilString.isEmptyString(string)) {
			return "";
		}
		try {
			String stringMod = "";
			String firstCharacter = new String(new char[] { string.toCharArray()[0] }).toUpperCase();
			stringMod = firstCharacter + string.substring(1);
			return stringMod;
		} catch (Exception e) {
			Log.e(TAG + ".firstCharacterToUpperCase", e.getMessage());
			return "";
		}
	}

	/**
	 *
	 * @param string
	 * @return
	 */
	public static String clearString(final String string) {
		if (YUtilString.isEmptyString(string)) {
			return "";
		}
		try {
			return string.replaceAll("[^A-Za-z0-9]", "");
		} catch (Exception e) {
			Log.e(TAG + ".clearString", e.getMessage());
			return "";
		}
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static boolean isInteger(final String value) {
		if (YUtilString.isEmptyString(value)) {
			return false;
		}
		return value.matches(YUtilString.REGEX_INTEGER);
	}
}