/**
 * YUtilListView.java
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
