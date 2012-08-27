/**
 * DefaultXmlElements.java
 *
 * Copyright 2011 yacamim.org.br
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
