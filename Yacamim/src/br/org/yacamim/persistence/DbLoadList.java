/**
 * DbLoadList.java
 *
 * Copyright 2013 yacamim.org.br
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
package br.org.yacamim.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe DbLoadList TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class DbLoadList {
	
	private List<DbLoad> inserts;
	private List<DbLoad> updates;
	private List<DbLoad> deletes;
	
	/**
	 * 
	 */
	public DbLoadList() {
		super();
	}
	
	/**
	 * 
	 * @param entity
	 * @param row
	 * @return
	 */
	public DbLoadList addRowToInsert(final Class<?> entity, final String row) {
		if(this.inserts == null) {
			this.inserts = new ArrayList<DbLoad>();
		}
		if(!this.inserts.contains(entity)) {
			this.inserts.add(new DbLoad(entity));
		}
		for(final DbLoad dbLoad : this.inserts) {
			dbLoad.addRow(row);
		}
		return this;
	}
	
	/**
	 * 
	 * @param entity
	 * @param row
	 * @return
	 */
	public DbLoadList addRowToUpdate(final Class<?> entity, final String row) {
		if(this.updates == null) {
			this.updates = new ArrayList<DbLoad>();
		}
		if(!this.updates.contains(entity)) {
			this.updates.add(new DbLoad(entity));
		}
		for(final DbLoad dbLoad : this.updates) {
			dbLoad.addRow(row);
		}
		return this;
	}
	
	/**
	 * 
	 * @param entity
	 * @param row
	 * @return
	 */
	public DbLoadList addRowToDelete(final Class<?> entity, final String row) {
		if(this.deletes == null) {
			this.deletes = new ArrayList<DbLoad>();
		}
		if(!this.deletes.contains(entity)) {
			this.deletes.add(new DbLoad(entity));
		}
		for(final DbLoad dbLoad : this.deletes) {
			dbLoad.addRow(row);
		}
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public List<DbLoad> getInserts() {
		return inserts;
	}

	/**
	 * 
	 * @return
	 */
	public List<DbLoad> getUpdates() {
		return updates;
	}

	/**
	 * 
	 * @return
	 */
	public List<DbLoad> getDeletes() {
		return deletes;
	}

}
