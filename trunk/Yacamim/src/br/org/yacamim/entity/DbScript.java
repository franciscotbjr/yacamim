/**
 * DbScript.java
 *
 * Copyright 2012 yacamim.org.br
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
package br.org.yacamim.entity;

import java.util.ArrayList;
import java.util.List;

import br.org.yacamim.persistence.Entity;

/**
 * Class DbScript TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
@Entity
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
