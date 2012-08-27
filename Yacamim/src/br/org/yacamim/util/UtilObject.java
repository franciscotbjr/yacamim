/**
 * UtilObject.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class UtilObject TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public strictfp abstract class UtilObject {

	/**
	 * 
	 */
	private UtilObject() {
		super();
	}
	
	/**
	 * 
	 * @param _arrString
	 * @return
	 */
	public static String[] toStringArray(List<String> _arrString) {
		try {
			String[] classes = new String[_arrString.size()];
			int count = 0;
			for(String strValue : _arrString) {
				classes[count++] = strValue;
			}
			return classes;
		}
		catch(Exception _e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param _classNames
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Class[] toClassArray(List<String> _classNames) {
		try {
			Class[] classes = new Class[_classNames.size()];
			int count = 0;
			for(String strNomeClasse : _classNames) {
				classes[count++] = Class.forName(strNomeClasse);
			}
			return classes;
		}
		catch(Exception _e) {
			return null;
		}
	}

	/**
	 * 
	 * @param _classNames
	 * @return
	 */
	public static List<Class<?>> toClassList(List<String> _classNames) {
		try {
			List<Class<?>> classes = new ArrayList<Class<?>>();
			for(String strNomeClasse : _classNames) {
				classes.add(Class.forName(strNomeClasse));
			}
			return classes;
		}
		catch(Exception _e) {
			return null;
		}
	}

	/**
	 * 
	 * @param _value
	 * @return
	 */
	public static Integer toInteger(Object _value) {
		try {
			return Integer.valueOf(_value.toString());
		}
		catch(Exception _e) {
			return Integer.valueOf("0");
		}
	}

	/**
	 * 
	 * @param _value
	 * @return
	 */
	public static Boolean toBoolean(Object _value) {
		try {
			return Boolean.valueOf(_value.toString());
		}
		catch(Exception _e) {
			return Boolean.valueOf("false");
		}
	}
    
    /**
     * 
     * @param _value
     * @param _defaultValue
     * @return
     */
    public static int zeroToIntDefault(int _value, int _defaultValue) {
        if(_value <= 0) {
            return _defaultValue;
        }
        return _value;
    }
    
    /**
     * 
     * @param _value
     * @param _defaultValue
     * @return
     */
    public static long zeroToLongDefault(long _value, long _defaultValue) {
        if(_value <= 0) {
            return _defaultValue;
        }
        return _value;
    }   
    
    /**
     * 
     * @param _value
     * @return
     */
	public static String nullToStringVazia(Object _value) {
		if(_value == null) {
			return "";
		}
		return _value.toString();
	}

}
