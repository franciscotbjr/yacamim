/**
 * YJSONRPCBuilder.java
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
package br.org.yacamim.util;

/**
 *
 * Class YJSONRPCBuilder TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YJSONRPCBuilder {

	private final StringBuffer buffer = new StringBuffer();

	private boolean items = false;

	private boolean objects = false;

	/**
	 *
	 */
	public YJSONRPCBuilder() {
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
	public void addJSONRPCBuilder(String _itemName, YJSONRPCBuilder _builder) {
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
	public void addJSONRPCBuilder(YJSONRPCBuilder _builder) {
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