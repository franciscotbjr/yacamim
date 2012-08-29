/**
 * YacamimClassMapping.java
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
 * 
 * Class YacamimClassMapping TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YacamimClassMapping {
	
	private List<KeyValuePair<String, String>> keyValues = new ArrayList<KeyValuePair<String,String>>();

	/**
	 * 
	 */
	public YacamimClassMapping() {
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
