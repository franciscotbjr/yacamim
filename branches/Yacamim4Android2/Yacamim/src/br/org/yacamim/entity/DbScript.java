/**
 * DbScript.java
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
public class DbScript extends YBaseEntity {

	private Integer dbVersion = 0;

	final List<String> createTables = new ArrayList<String>();

	final List<String> alterTables = new ArrayList<String>();

	final List<String> inserts = new ArrayList<String>();

	final List<String> updates = new ArrayList<String>();
	
	final List<String> entities = new ArrayList<String>();

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
