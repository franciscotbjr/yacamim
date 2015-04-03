/**
 * YBasicHttpRestEntity.java
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
package br.org.yacamim.http.rest;

import java.io.ByteArrayInputStream;

import org.apache.http.entity.BasicHttpEntity;

/**
 * Constructs an Http Entity designed to be a JSON content place holder.<br/>
 * By default its Content Type is "application/json" and its Content Encoding is "UTF-8"<br/><br/>
 * Also provides additional methods for placing JSON content by overloading <tt>setContent</tt> 
 * with <tt>String</tt>, <tt>StringBuilder</tt> and <tt>byte[]</tt> signatures.<br/>
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YBasicHttpRestEntity extends BasicHttpEntity {

	/**
	 * 
	 */
	public YBasicHttpRestEntity() {
		super();
		this.setContentType("application/json");
		this.setContentEncoding("UTF-8");
	}
	
	/**
	 * 
	 * @param content
	 */
	public void setContent(final String content) {
		setContent(content.getBytes());
	}

	/**
	 * 
	 * @param content
	 */
	public void setContent(final StringBuilder content) {
		setContent(content.toString().getBytes());
	}

	/**
	 * 
	 * @param content
	 */
	public void setContent(final byte[] content) {
		super.setContent(new ByteArrayInputStream(content));
	}

}
