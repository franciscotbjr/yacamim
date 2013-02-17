/**
 * YList.java
 *
 * Copyright 2013 yacamim.org.br
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
import java.util.Iterator;

/**
 * Classe YList TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YList<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4800566635033691472L;

	@Override
	public int hashCode() {
		int hashCode = 1;
		Iterator<E> iterator = iterator();
		int count =  modCount > 0 ? modCount : 1;
		while (iterator.hasNext()) {
		    hashCode = 31 * hashCode + (count) + iterator.next().toString().hashCode();
		    count++;
		}
		return hashCode;
	}
	
}
