/**
 * JSONDictionary.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class JSONDictionary TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class JSONDictionary {
	
	private List<KeyValuePair<String, String>> keyValues = new ArrayList<KeyValuePair<String,String>>();

	/**
	 * 
	 */
	public JSONDictionary() {
		super();
	}
	
	/**
	 * 
	 * @param _key
	 * @param _value
	 */
	public void add(final String _key, final String _value) {
		for(KeyValuePair<String, String> keyValuePair : this.keyValues) {
			if(keyValuePair.getKey().equals(_key)) {
				keyValuePair.setValue(_value);
				return;
			}
		}
		this.keyValues.add(new KeyValuePair<String, String>(_key, _value));
	}
	
	/**
	 * 
	 * @param _key
	 * @return
	 */
	public String get(final String _key) {
		for(KeyValuePair<String, String> keyValuePair : this.keyValues) {
			if(keyValuePair.getKey().equals(_key)) {
				return keyValuePair.getValue();
			}
		}
		return null;
	}

}
