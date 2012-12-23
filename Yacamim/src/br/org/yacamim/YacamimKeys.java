/**
 * YacamimKeys.java
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
package br.org.yacamim;

/**
 * Class YacamimKeys TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public enum YacamimKeys {


	YACAMIM_USES_PERSISTENCE("YACAMIM_USES_PERSISTENCE"),
	YACAMIM_DIR_CAPTURED_IMAGES("YACAMIM_DIR_CAPTURED_IMAGES"),
	YACAMIM_SQLITE_BOOLEAN_TRUE("YACAMIM_SQLITE_BOOLEAN_TRUE"),
    YACAMIM_SQLITE_BOOLEAN_FALSE("YACAMIM_SQLITE_BOOLEAN_FALSE")
	;

	private String value;

	private YacamimKeys(final String _strTag) {
		this.value = _strTag;
	}

	/**
	 *
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return this.value;
	}

}
