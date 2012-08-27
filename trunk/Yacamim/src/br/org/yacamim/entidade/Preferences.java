/**
 * Preferences.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.entidade;



/**
 * 
 * Class Preferences TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class Preferences extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5616227882215035953L;

	public boolean keepCache;
	public boolean keepUserLogin;
	public String keepUser;
	public boolean displayOpenMessage;
	public boolean useOnlyWifi;
	
	/**
	 * 
	 */
	public Preferences() {
		super();
	}
	
	public String toString() {
		return super.toString();
	}

}
