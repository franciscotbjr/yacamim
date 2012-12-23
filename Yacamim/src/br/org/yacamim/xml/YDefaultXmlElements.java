/**
 * YDefaultXmlElements.java
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
public enum YDefaultXmlElements {

	/* Elements */
	ELEMENT_ENTITY_MAPPING("y-entity-mapping"),
	ELEMENT_PACKAGE("y-package"),
	ELEMENT_ENTITY("y-entity"),
	ELEMENT_DB_SCRIPT("y-db-script"),
	ELEMENT_TABLES("y-tables"),
	ELEMENT_CREATE_TABLE("y-create-table"),
	ELEMENT_ALTER_TABLE("y-alter-table"),
	ELEMENT_CHARGE("y-charge"),
	ELEMENT_INSERT("y-insert"),
	ELEMENT_UPDATE("y-update"),
	ELEMENT_DB("y-db"),
	ELEMENT_CLASS("y-class"),
	ELEMENT_SERVICES("y-services"),
	ELEMENT_SERVICE("y-service"),
	ELEMENT_CONFIG("y-config"),
	ELEMENT_Y_CONFIG_ITEM("y-config-item"),
	ELEMENT_NAME("y-name"),
	ELEMENT_VALUE("y-value"),


	/* Attribute */
	ATTR_NAME("name"),
	ATTR_VERSION("version"),
	ATTR_ID("id"),
	ATTR_URL("url"),
    ATTR_SERVICE_NAME("service-name"),
    ATTR_LOCAL_NAME("local-name")

	;

	private String value;

	private YDefaultXmlElements(final String _strTag) {
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
