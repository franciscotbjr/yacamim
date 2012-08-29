/**
 * UtilJSONDictionary.java
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

import java.util.ArrayList;
import java.util.List;

/**
 * Class UtilJSONDictionary TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class UtilJSONDictionary {
	
	private List<KeyValuePair<String, String>> keyValues = new ArrayList<KeyValuePair<String,String>>();
    
    private int countKey = 0;

	/**
	 * 
	 */
	public UtilJSONDictionary() {
		super();
	}
	
    /**
     *
     * @param _value
     * @return
     */
    public String add(final String _value) {
		String token = "";
		for(KeyValuePair<String, String> keyValuePair : this.keyValues) {
			if(keyValuePair.getValue().equals(_value)) {
				token = keyValuePair.getKey();
			}
		}
		if(UtilString.isEmptyString(token)) {
			token = next();
			this.keyValues.add(new KeyValuePair<String, String>(token, _value));
		}
		return token;
    }
    
    /**
     * 
     * @param _value
     * @return
     */
    public boolean contains(final String _value) {
	    for(KeyValuePair<String, String> keyValuePair : this.keyValues) {
			if(keyValuePair.getValue().equals(_value)) {
				return true;
			}
		}
	    return false;
    }
    
    /**
     *
     * @param _nomeItem
     * @return
     */
    public String get(final String _nomeItem) {
	    for(KeyValuePair<String, String> keyValuePair : this.keyValues) {
			if(keyValuePair.getKey().equals(_nomeItem)) {
				return keyValuePair.getValue();
			}
	    }
	    return null;
    }
    
    /**
     *
     * @return
     */
    public List<String> getKeys() {
		final List<String> keys = new ArrayList<String>();
		for(KeyValuePair<String, String> keyValuePair : this.keyValues) {
			keys.add(keyValuePair.getKey());
		}
		return keys;
    }
    
    /**
     *
     * @return
     */
    private String next() {
    	return this.countKey++ + "";
    }
    
 	/**
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String result = "";
		try {
			final JSONRPCBuilder jsonrpcBuilder = new JSONRPCBuilder();
			jsonrpcBuilder.startJsonRPCObject();
			List<String> keys = this.getKeys();
			for(final String key : keys) {
				jsonrpcBuilder.addItem(key, this.get(key));
			}
			jsonrpcBuilder.endJsonRPCObject();
			result = jsonrpcBuilder.toString();
		} catch (Exception _e) {
		}
		return result;
	}

}