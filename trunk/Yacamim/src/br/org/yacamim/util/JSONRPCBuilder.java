/**
 * JSONRPCBuilder.java
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
package br.org.yacamim.util;

/**
 * 
 * Class JSONRPCBuilder TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class JSONRPCBuilder {

	private final StringBuffer buffer = new StringBuffer();

	private boolean items = false;

	private boolean objects = false;

	/**
	 * 
	 */
	public JSONRPCBuilder() {
		super();
	}

	/**
	 * 
	 */
	public void startJsonRPCStruct() {
		this.items = false;
		this.objects = false;
		this.buffer.append("{");
	}

	/**
	 * 
	 */
	public void startJsonRPCObject() {
		this.items = false;
		if (this.objects) {
			this.buffer.append(",");
		}
		this.buffer.append("{");
	}

	/**
	 * 
	 * @param _objectName
	 */
	public void startJsonRPCObjectArray(String _objectName) {
		if (this.items) {
			this.buffer.append(",");
		}
		this.buffer.append("\"");
		this.buffer.append(_objectName);
		this.buffer.append("\"");
		this.buffer.append(":");
		this.buffer.append("[");
	}

	/**
	 * 
	 */
	public void startJsonRPCObjectArray() {
		this.buffer.append("[");
	}

	/**
	 * 
	 */
	public void endJsonRPCStruct() {
		this.buffer.append("}");
	}

	/**
	 * 
	 */
	public void endJsonRPCObjectArray() {
		this.buffer.append("]");
	}

	/**
	 * 
	 */
	public void endJsonRPCObject() {
		this.buffer.append("}");
		this.objects = true;
	}

	/**
	 * 
	 * @param _itemName
	 * @param _objectValue
	 */
	public void addItem(String _itemName, Object _objectValue) {
		if (this.items) {
			this.buffer.append(",");
		}
		this.buffer.append("\"");
		this.buffer.append(_itemName);
		this.buffer.append("\"");
		this.buffer.append(":");

		if(_objectValue != null){
			this.buffer.append("\"");
			this.buffer.append(_objectValue);
			this.buffer.append("\"");
		}

		this.items = true;
	}



	/**
	 * 
	 * @param _itemName
	 * @param _objectValue
	 */
	public void addItemValueNotString(String _itemName, Object _objectValue) {
		if (this.items) {
			this.buffer.append(",");
		}
		this.buffer.append("\"");
		this.buffer.append(_itemName);
		this.buffer.append("\"");
		this.buffer.append(":");

		if(_objectValue != null){
			this.buffer.append(_objectValue.toString());
		}else{
			this.buffer.append("null");
		}

		this.items = true;
	}

	/**
	 * 
	 * @param _itemName
	 * @param _builder
	 */
	public void addJSONRPCBuilder(String _itemName, JSONRPCBuilder _builder) {
		if (this.items) {
			this.buffer.append(",");
		}
		this.buffer.append("\"" + _itemName + "\"" + ":");
		this.buffer.append(_builder);
	}

	/**
	 * 
	 * @param _builder
	 */
	public void addJSONRPCBuilder(JSONRPCBuilder _builder) {
		if (this.items) {
			this.buffer.append(",");
		}
		this.buffer.append(_builder);
		this.items = true;
	}

	/**
	 * 
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.buffer.toString();
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasItems() {
		return this.items;
	}

}