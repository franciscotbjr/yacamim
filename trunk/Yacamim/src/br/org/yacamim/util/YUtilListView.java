/**
 * YUtilListView.java
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
import java.util.HashMap;
import java.util.List;

/**
 * Class YUtilListView TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YUtilListView {
	
	private YUtilListView() {
		super();
	}
	
	/**
	 * 
	 * @param dataList
	 * @return
	 */
	public static List<HashMap<String, Object>> buildListOfMappedData(final List<?> dataList) {
		final List<HashMap<String, Object>> listOfMappedData = new ArrayList<HashMap<String, Object>>();
		
		for(final Object data : dataList) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(YConstants.OBJECT, data);
			listOfMappedData.add(map);
		}
		
		return listOfMappedData;
	}

}
