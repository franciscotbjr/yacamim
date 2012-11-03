/**
 * DefaultXmlElements.java
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
package br.org.yacamim.xml;

/**
 * Class DefaultXmlElements TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public enum DefaultXmlElements {

	/* Elements */
	ELEMENT_DB_SCRIPT("db-script"),
	ELEMENT_TABLES("tables"),
	ELEMENT_CREATE_TABLE("create-table"),
	ELEMENT_ALTER_TABLE("alter-table"),
	ELEMENT_CHARGE("charge"),
	ELEMENT_INSERT("insert"),
	ELEMENT_UPDATE("update"),
	ELEMENT_DB("db"),
	ELEMENT_CLASS("class"),
	ELEMENT_SERVICES("services"),
	ELEMENT_SERVICE("service"),
	ELEMENT_PARAMS("params"),
	ELEMENT_PARAM("param"),
	ELEMENT_NAME("name"),
	ELEMENT_VALUE("value"),


	/* Attribute */
	ATTR_VERSION("version"),
	ATTR_ID("id"),
	ATTR_URL("url"),
    ATTR_SERVICE_NAME("service-name"),
    ATTR_LOCAL_NAME("local-name")

	;

	private String value;

	private DefaultXmlElements(final String _strTag) {
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
