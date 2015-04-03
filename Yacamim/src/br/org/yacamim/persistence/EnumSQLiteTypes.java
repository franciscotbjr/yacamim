/**
 * EnumSQLiteTypes.java
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
* Class EnumSQLiteTypes TODO
*
* @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
* @version 1.0
* @since 1.0
*/
public enum EnumSQLiteTypes {
	
	
	SQL_NULL("NULL"),
	SQL_INTEGER("INTEGER"),
	SQL_TEXT("TEXT"),
	SQL_REAL("REAL"),
	SQL_BLOB("BLOB")
	
	;
	
	private String value;
	
	/**
	 * 
	 * @param value
	 */
	private EnumSQLiteTypes(String value) {
		this.value = value;
	}
	
	/**
	 * 
	 * 
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return this.value;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isText(final String value) {
		return SQL_TEXT.toString().equalsIgnoreCase(value);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isReal(final String value) {
		return SQL_REAL.toString().equalsIgnoreCase(value);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isInteger(final String value) {
		return SQL_INTEGER.toString().equalsIgnoreCase(value);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isBlob(final String value) {
		return SQL_BLOB.toString().equalsIgnoreCase(value);
	}

}