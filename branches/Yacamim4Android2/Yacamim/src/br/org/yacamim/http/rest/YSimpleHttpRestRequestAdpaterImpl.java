/**
 * YSimpleHttpRestRequestAdpaterImpl.java
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
package br.org.yacamim.http.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import br.org.yacamim.http.YBodyTokenRecoverHandler;

/**
 * Class YSimpleHttpRestRequestAdpaterImpl TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YSimpleHttpRestRequestAdpaterImpl implements YSimpleHttpRestRequestAdpater {

	private String enconding = "UTF-8";
	private String uri;
	private InputStream content;
	private Set<String> tokens;
	private YBodyTokenRecoverHandler yBodyTokenRecoverHandler;

	public YSimpleHttpRestRequestAdpaterImpl() {
		super();
	}

	/**
	 *
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#getUri()
	 */
	@Override
	public String getUri() {
		return this.uri;
	}

	/**
	 *
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#getEnconding()
	 */
	@Override
	public String getEnconding() {
		return this.enconding;
	}

	/**
	 *
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#setUri(java.lang.String)
	 */
	@Override
	public YSimpleHttpRestRequestAdpater setUri(String uri) {
		this.uri = uri;
		return this;
	}


	/**
	 *
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#setEncondig(java.lang.String)
	 */
	@Override
	public YSimpleHttpRestRequestAdpater setEncondig(final String enconding) {
		this.enconding = enconding;
		return this;
	}

	/**
	 * 
	 * @see br.org.yacamim.http.rest.YSimpleHttpRestRequestAdpater#manageToken(java.lang.String)
	 */
	@Override
	public YSimpleHttpRestRequestAdpater manageToken(String token) {
		this.lazyTokens();
		this.tokens.add(token);
		return this;
	}

	/**
	 * 
	 * @see br.org.yacamim.http.rest.YSimpleHttpRestRequestAdpater#getTokens()
	 */
	public Set<String> getTokens() {
		lazyTokens();
		return new HashSet<String>(tokens);
	}

	/**
	 *
	 */
	private void lazyTokens() {
		if(this.tokens ==  null) {
			this.tokens = new HashSet<String>();
		}
	}

	/**
	 * 
	 * @see br.org.yacamim.http.rest.YSimpleHttpRestRequestAdpater#setBodyTokenRecoverHandler(br.org.yacamim.http.YBodyTokenRecoverHandler)
	 */
	@Override
	public YSimpleHttpRestRequestAdpater setBodyTokenRecoverHandler(
			YBodyTokenRecoverHandler yBodyTokenRecoverHandler) {
		this.yBodyTokenRecoverHandler = yBodyTokenRecoverHandler;
		return this;
	}

	/**
	 * 
	 * @see br.org.yacamim.http.rest.YSimpleHttpRestRequestAdpater#getBodyTokenRecoverHandler()
	 */
	@Override
	public YBodyTokenRecoverHandler getBodyTokenRecoverHandler() {
		return this.yBodyTokenRecoverHandler;
	}

	/**
	 * 
	 * @see br.org.yacamim.http.rest.YSimpleHttpRestRequestAdpater#setContent(java.lang.String)
	 */
	@Override
	public YSimpleHttpRestRequestAdpater setContent(String content) {
		this.content = new ByteArrayInputStream(content.getBytes());
		return this;
	}

	/**
	 * 
	 * @see br.org.yacamim.http.rest.YSimpleHttpRestRequestAdpater#setContent(java.lang.StringBuilder)
	 */
	@Override
	public YSimpleHttpRestRequestAdpater setContent(StringBuilder content) {
		this.content = new ByteArrayInputStream(content.toString().getBytes());
		return this;
	}

	/**
	 * 
	 * @see br.org.yacamim.http.rest.YSimpleHttpRestRequestAdpater#setContent(byte[])
	 */
	@Override
	public YSimpleHttpRestRequestAdpater setContent(byte[] content) {
		this.content = new ByteArrayInputStream(content);
		return this;
	}

	/**
	 * 
	 * @see br.org.yacamim.http.rest.YSimpleHttpRestRequestAdpater#setContent(java.io.InputStream)
	 */
	@Override
	public YSimpleHttpRestRequestAdpater setContent(InputStream content) {
		this.content = content;
		return this;
	}

	/**
	 * 
	 * @see br.org.yacamim.http.rest.YSimpleHttpRestRequestAdpater#getContent()
	 */
	@Override
	public InputStream getContent() {
		return this.content;
	}

}
