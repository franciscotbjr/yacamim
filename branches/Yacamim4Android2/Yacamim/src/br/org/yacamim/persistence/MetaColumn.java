/**
 * MetaColumn.java
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
package br.org.yacamim.persistence;

/**
 * 
 * Class MetaColumn TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class MetaColumn {
	
	private String name;
	private String type;
	private boolean nullable;
	private boolean unique;
	private boolean primaryKey;
	private boolean autoincrement;
	private int length;
	

	public MetaColumn() {
		super();
	}
	
	/**
	 * 
	 * @param name
	 * @param type
	 * @param nullable
	 * @param unique
	 * @param primaryKey
	 * @param autoincrement
	 * @param length
	 */
	public MetaColumn(String name, String type, boolean nullable,
			boolean unique, boolean primaryKey, boolean autoincremnt, int length) {
		super();
		this.name = name;
		this.type = type;
		this.nullable = nullable;
		this.unique = unique;
		this.primaryKey = primaryKey;
		this.autoincrement = autoincremnt;
		this.length = length;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * 
	 * @param nullable
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * 
	 * @param unique
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	/**
	 * 
	 * @param primaryKey
	 */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAutoincrement() {
		return autoincrement;
	}

	/**
	 * 
	 * @param autoincrement
	 */
	public void setAutoincrement(boolean autoincrement) {
		this.autoincrement = autoincrement;
	}

	/**
	 * 
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * 
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (autoincrement ? 1231 : 1237);
		result = prime * result + length;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (nullable ? 1231 : 1237);
		result = prime * result + (primaryKey ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (unique ? 1231 : 1237);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MetaColumn other = (MetaColumn) obj;
		if (autoincrement != other.autoincrement)
			return false;
		if (length != other.length)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nullable != other.nullable)
			return false;
		if (primaryKey != other.primaryKey)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (unique != other.unique)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		final StringBuilder sqlRow = new StringBuilder();
		sqlRow.append(" " + this.getName());
		sqlRow.append(" " + this.getType());
		if(EnumSQLiteTypes.isText(this.type)) {
			sqlRow.append("(" + getLength() + ") ");
		}
		if(this.isPrimaryKey()) {
			sqlRow.append(" PRIMARY KEY");
			if(this.isAutoincrement()) {
				sqlRow.append(" AUTOINCREMENT");
			}
			sqlRow.append(" NOT NULL");
		} else {
			if(!this.isNullable()) {
				sqlRow.append(" NOT");
			}
			sqlRow.append(" NULL");
			if(this.isUnique()) {
				sqlRow.append(" UNIQUE");
			}
		}
		return sqlRow.toString();
	}
}