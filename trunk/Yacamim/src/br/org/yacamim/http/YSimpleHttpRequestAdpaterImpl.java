/**
 * DefaultDataServiceHandler.java
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
package br.org.yacamim.http;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * Class BaseAsyncTask TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YSimpleHttpRequestAdpaterImpl implements YSimpleHttpRequestAdpater {
	
	private String enconding = "UTF-8";
	private String uri;
	private List<NameValuePair> parameters;
	private Set<String> tokens;
	private YBodyTokenRecoverHandler yBodyTokenRecoverHandler;

	public YSimpleHttpRequestAdpaterImpl() {
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
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#getParameters()
	 */
	@Override
	public List<NameValuePair> getParameters() {
		return new ArrayList<NameValuePair>(this.parameters);
	}

	/**
	 * 
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#setUri(java.lang.String)
	 */
	@Override
	public YSimpleHttpRequestAdpater setUri(String uri) {
		this.uri = uri;
		return this;
	}
	
	/**
	 * 
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#addParams(java.util.List)
	 */
	@Override
	public YSimpleHttpRequestAdpater addParams(final List<NameValuePair> params) {
		this.lazyParameters();
		this.parameters.addAll(params);
		return this;
	}

	/**
	 * 
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#addParam(org.apache.http.NameValuePair)
	 */
	@Override
	public YSimpleHttpRequestAdpater addParam(final NameValuePair param) {
		this.lazyParameters();
		this.parameters.add(param);
		return this;
	}

	/**
	 * 
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#addParam(java.lang.String, java.lang.String)
	 */
	@Override
	public YSimpleHttpRequestAdpater addParam(final String paramName, final String paramValue) {
		this.lazyParameters();
		this.parameters.add(new BasicNameValuePair(paramName, paramValue));
		return this;
	}

	/**
	 * 
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#setEncondig(java.lang.String)
	 */
	@Override
	public YSimpleHttpRequestAdpater setEncondig(final String enconding) {
		this.enconding = enconding;
		return this;
	}
	
	/**
	 * 
	 */
	private void lazyParameters() {
		if(this.parameters == null) {
			this.parameters = new ArrayList<NameValuePair>();
		}
	}

	/**
	 * 
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#manageToken(java.lang.String)
	 */
	@Override
	public YSimpleHttpRequestAdpater manageToken(String token) {
		this.lazyTokens();
		this.tokens.add(token);
		return this;
	}
	
	/**
	 * 
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#getTokens()
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
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#setBodyTokenRecoverHandler(br.org.yacamim.http.YBodyTokenRecoverHandler)
	 */
	@Override
	public YSimpleHttpRequestAdpater setBodyTokenRecoverHandler(
			YBodyTokenRecoverHandler yBodyTokenRecoverHandler) {
		this.yBodyTokenRecoverHandler = yBodyTokenRecoverHandler;
		return this;
	}

	/**
	 * 
	 * @see br.org.yacamim.http.YSimpleHttpRequestAdpater#getBodyTokenRecoverHandler()
	 */
	@Override
	public YBodyTokenRecoverHandler getBodyTokenRecoverHandler() {
		return this.yBodyTokenRecoverHandler;
	}

}
