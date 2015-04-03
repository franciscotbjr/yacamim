/**
 * DbLoad.java
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
 * Classe DbLoad TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class DbLoad {
	
	private Class<?> entity;
	private List<String> rows;

	/**
	 * 
	 * @param entity
	 */
	public DbLoad(final Class<?> entity) {
		super();
		this.entity = entity;
	}

	/**
	 * 
	 * @return
	 */
	public Class<?> getEntity() {
		return entity;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getRows() {
		return rows;
	}
	
	/**
	 * 
	 * @param row
	 * @return
	 */
	public DbLoad addRow(final String row) {
		if(this.rows == null) {
			this.rows = new ArrayList<String>();
		}
		this.rows.add(row);
		return this;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((rows == null) ? 0 : rows.hashCode());
		return result;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DbLoad other = (DbLoad) obj;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (rows == null) {
			if (other.rows != null)
				return false;
		} else if (!rows.equals(other.rows))
			return false;
		return true;
	}
	
}