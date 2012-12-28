/**
 * YProcessedEntity.java
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
* Class YProcessedEntity TODO
*
* @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
* @version 1.0
* @since 1.0
*/
final class YProcessedEntity {
	
	private Class<?> classe;
	private String idMethod;
	private String className;
	private String idColumn;
	private String tableName;

	/**
	 * 
	 */
	YProcessedEntity() {
		super();
	}

	/**
	 * 
	 * @return
	 */
	Class<?> getClasse() {
		return classe;
	}

	/**
	 * 
	 * @param classe
	 */
	void setClasse(Class<?> classe) {
		this.classe = classe;
	}

	/**
	 * 
	 * @return
	 */
	String getIdMethod() {
		return idMethod;
	}

	/**
	 * 
	 * @param idMethod
	 */
	void setIdMethod(String idMethod) {
		this.idMethod = idMethod;
	}

	/**
	 * 
	 * @return
	 */
	String getClassName() {
		return className;
	}

	/**
	 * 
	 * @param className
	 */
	void setClassName(String className) {
		this.className = className;
	}

	/**
	 * 
	 * @return
	 */
	String getIdColumn() {
		return idColumn;
	}

	/**
	 * 
	 * @param idColumn
	 */
	void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	/**
	 * 
	 * @return
	 */
	String getTableName() {
		return tableName;
	}

	/**
	 * 
	 * @param tableName
	 */
	void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result
				+ ((idColumn == null) ? 0 : idColumn.hashCode());
		result = prime * result
				+ ((idMethod == null) ? 0 : idMethod.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}

	/**
	 * 
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		YProcessedEntity other = (YProcessedEntity) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (idColumn == null) {
			if (other.idColumn != null)
				return false;
		} else if (!idColumn.equals(other.idColumn))
			return false;
		if (idMethod == null) {
			if (other.idMethod != null)
				return false;
		} else if (!idMethod.equals(other.idMethod))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}

}