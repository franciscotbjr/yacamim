/**
 * UnidirectionalManyToManyException.java
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
package br.org.yacamim.persistence;

/**
*
* Class UnidirectionalManyToManyException TODO
*
* @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
* @version 1.0
* @since 1.0
*/
public class UnidirectionalManyToManyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9113122017951954353L;

	/**
	 * 
	 * @param message
	 */
	public UnidirectionalManyToManyException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param cause
	 */
	public UnidirectionalManyToManyException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public UnidirectionalManyToManyException(String message,
			Throwable cause) {
		super(message, cause);
	}

}
