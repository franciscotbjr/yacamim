/**
 * DbScript.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.entidade;

import java.util.ArrayList;
import java.util.List;

/**
 * Class DbScript TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class DbScript extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3601855060801282327L;

	private Integer dbVersion = 0;
	
	final List<String> createTables = new ArrayList<String>();
	
	final List<String> alterTables = new ArrayList<String>();
	
	final List<String> inserts = new ArrayList<String>();
	
	final List<String> updates = new ArrayList<String>();

	/**
	 * 
	 */
	public DbScript() {
		super();
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getCreateTables() {
		return this.createTables;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getAlterTables() {
		return this.alterTables;
	}


	/**
	 * 
	 * @return
	 */
	public List<String> getInserts() {
		return this.inserts;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getUpdates() {
		return this.updates;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getDbVersion() {
		return this.dbVersion;
	}

	/**
	 * 
	 * @param _dbVersion
	 */
	public void setDbVersion(final Integer _dbVersion) {
		this.dbVersion = _dbVersion;
	}
	
}
