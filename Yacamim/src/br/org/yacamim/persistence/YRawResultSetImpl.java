/**
 * YRawResultSetImpl.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe YRawResultSetImpl TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
class YRawResultSetImpl implements YRawResultSet {
	
	private List<Object> list = new ArrayList<Object>();
	
	private int rowLength;
	private int currentPosition;

	/**
	 * 
	 */
	public YRawResultSetImpl() {
		super();
	}

	/**
	 * 
	 * @see br.org.yacamim.persistence.YRawResultSet#hashNext()
	 */
	@Override
	public boolean hashNext() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 
	 * @see br.org.yacamim.persistence.YRawResultSet#get(int)
	 */
	public Object get(final int index) {
		return null;
	}
	
	/**
	 * 
	 * @see br.org.yacamim.persistence.YRawResultSet#getString(int)
	 */
	public String getString(final int index) {
		return null;
	}
	
	/**
	 * 
	 * @see br.org.yacamim.persistence.YRawResultSet#getInt(int)
	 */
	public int getInt(final int index) {
		return -1;
	}
	
	/**
	 * 
	 * @see br.org.yacamim.persistence.YRawResultSet#getLong(int)
	 */
	public long getLong(final int index) {
		return -1;
	}
	
	/**
	 * 
	 * @see br.org.yacamim.persistence.YRawResultSet#getDate(int)
	 */
	public Date getDate(final int index) {
		return null;
	}
	
	/**
	 * 
	 * @param rowLength
	 */
	void setRowLength(final int rowLength) {
		this.rowLength = rowLength;
	}

}
