/**
 * YUtilObject.java
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

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * Class YUtilObject TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public strictfp abstract class YUtilObject {
	
	private static final String TAG = YUtilObject.class.getSimpleName();

	/**
	 *
	 */
	private YUtilObject() {
		super();
	}

	/**
	 *
	 * @param arrString
	 * @return
	 */
	public static String[] toStringArray(final List<String> arrString) {
		try {
			String[] classes = new String[arrString.size()];
			int count = 0;
			for(String strValue : arrString) {
				classes[count++] = strValue;
			}
			return classes;
		}
		catch(Exception e) {
			Log.e(TAG + ".toStringArray", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param classNames
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Class[] toClassArray(final List<String> classNames) {
		try {
			Class[] classes = new Class[classNames.size()];
			int count = 0;
			for(String strNomeClasse : classNames) {
				classes[count++] = Class.forName(strNomeClasse);
			}
			return classes;
		}
		catch(Exception e) {
			Log.e(TAG + ".toClassArray", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param classNames
	 * @return
	 */
	public static List<Class<?>> toClassList(final List<String> classNames) {
		try {
			List<Class<?>> classes = new ArrayList<Class<?>>();
			for(String strNomeClasse : classNames) {
				classes.add(Class.forName(strNomeClasse));
			}
			return classes;
		}
		catch(Exception e) {
			Log.e(TAG + ".toClassList", e.getMessage());
			return null;
		}
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static Integer toInteger(final  Object value) {
		try {
			return Integer.valueOf(value.toString());
		}
		catch(Exception e) {
			Log.e(TAG + ".toInteger", e.getMessage());
			return Integer.valueOf("0");
		}
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static Boolean toBoolean(final Object value) {
		try {
			return Boolean.valueOf(value.toString());
		}
		catch(Exception e) {
			Log.e(TAG + ".toBoolean", e.getMessage());
			return Boolean.valueOf("false");
		}
	}

    /**
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static int zeroToIntDefault(final int value, final int defaultValue) {
        if(value <= 0) {
            return defaultValue;
        }
        return value;
    }

    /**
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static long zeroToLongDefault(final long value, final long defaultValue) {
        if(value <= 0) {
            return defaultValue;
        }
        return value;
    }

    /**
     *
     * @param value
     * @return
     */
	public static String nullToStringVazia(final Object value) {
		if(value == null) {
			return "";
		}
		return value.toString();
	}

}
