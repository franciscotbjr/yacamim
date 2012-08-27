/**
 * ServiceURL.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.entidade;


/**
 * 
 * Class ServiceURL TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class ServiceURL extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4142735125146364845L;
	
	public int id;
	public String url;

	/**
	 * 
	 */
	public ServiceURL() {
		super();
	}
	
	@Override
	public String toString() {
		return url;
	}

}
