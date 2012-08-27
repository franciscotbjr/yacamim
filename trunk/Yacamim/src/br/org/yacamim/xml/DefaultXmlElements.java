/**
 * DefaultXmlElements.java
 *
 * Copyright 2011 yacamim.org.br
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
