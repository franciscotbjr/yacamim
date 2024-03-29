/**
 * YCacheProcessedEntityTerminatedException.java
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
package br.org.yacamim.persistence;

/**
*
* Class YCacheProcessedEntityTerminatedException TODO
*
* @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
* @version 1.0
* @since 1.0
*/
public class YCacheProcessedEntityTerminatedException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1583963226948660090L;

	/**
	 * 
	 * @param message
	 */
	public YCacheProcessedEntityTerminatedException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param cause
	 */
	public YCacheProcessedEntityTerminatedException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public YCacheProcessedEntityTerminatedException(String message,
			Throwable cause) {
		super(message, cause);
	}

}
