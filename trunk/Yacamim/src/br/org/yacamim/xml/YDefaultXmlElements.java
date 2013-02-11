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
	ELEMENT_Y_ENTITY_MAPPING("y-entity-mapping"),
	ELEMENT_Y_PACKAGE("y-package"),
	ELEMENT_Y_ENTITY("y-entity"),
	ELEMENT_Y_DB_SCRIPT("y-db-script"),
	ELEMENT_Y_TABLES("y-tables"),
	ELEMENT_Y_CREATE_TABLE("y-create-table"),
	ELEMENT_Y_ALTER_TABLE("y-alter-table"),
	ELEMENT_Y_LOAD("y-load"),
	ELEMENT_Y_INSERT("y-insert"),
	ELEMENT_Y_UPDATE("y-update"),
	ELEMENT_Y_DELETE("y-delete"),
	ELEMENT_Y_ROW("y-row"),
	ELEMENT_Y_DB("y-db"),
	ELEMENT_Y_CLASS("y-class"),
	ELEMENT_Y_SERVICES("y-services"),
	ELEMENT_Y_SERVICE("y-service"),
	ELEMENT_Y_CONFIG("y-config"),
	ELEMENT_Y_CONFIG_ITEM("y-config-item"),
	ELEMENT_Y_PARAM("y-param"),
	ELEMENT_Y_PARAM_ITEM("y-param-item"),

	/* Attribute */
	ATTR_NAME("name"),
	ATTR_VALUE("value"),
	ATTR_VERSION("version"),
	ATTR_ID("id"),
	ATTR_URL("url"),
    ATTR_SERVICE_NAME("service-name"),
    ATTR_LOCAL_NAME("local-name"),
    ATTR_Y_ENTITY("y-entity")

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
