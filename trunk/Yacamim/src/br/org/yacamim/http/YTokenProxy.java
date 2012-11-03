/**
 * DefaultDataServiceHandler.java
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
public class YTokenProxy {

	private Set<NameValuePair> tokens;

	private static final YTokenProxy singletonYTokenProxy = new YTokenProxy();

	/**
	 *
	 */
	private YTokenProxy() {
		super();
	}

	/**
	 *
	 * @return
	 */
	public static YTokenProxy getInstance() {
		return singletonYTokenProxy;
	}

	/**
	 *
	 * @param tokenName
	 * @return
	 */
	public String getToken(final String tokenName) {
		lazyTokens();
		for(NameValuePair nameValuePair : this.tokens) {
			if(nameValuePair.getName().equals(tokenName)) {
				return nameValuePair.getValue();
			}
		}
		return null;
	}

	/**
	 *
	 * @return
	 */
	public List<NameValuePair> getTokens() {
		lazyTokens();
		return new ArrayList<NameValuePair> (this.tokens);
	}

	/**
	 *
	 * @param tokenName
	 * @return
	 */
	public YTokenProxy addToken(final String tokenName, final String tokenValue) {
		lazyTokens();
		removeToken(tokenName);
		this.tokens.add(new BasicNameValuePair(tokenName, tokenValue));
		return this;
	}

	public YTokenProxy addToken(final NameValuePair token) {
		lazyTokens();
		if(token != null) {
			this.removeToken(token.getName());
			this.tokens.add(token);
		}
		return this;
	}

	/**
	 *
	 * @param tokens
	 * @return
	 */
	public YTokenProxy addTokens(final List<NameValuePair> tokens) {
		if(tokens != null) {
			lazyTokens();
			for(final NameValuePair token : tokens) {
				this.addToken(token);
			}
		}
		return this;
	}

	/**
	 *
	 * @param tokenName
	 * @return
	 */
	public YTokenProxy removeToken(final String tokenName) {
		lazyTokens();
		for(final NameValuePair token : this.tokens) {
			if(token.getName().equals(tokenName)) {
				this.tokens.remove(token);
			}
		}
		return this;
	}

	/**
	 *
	 */
	private void lazyTokens() {
		if(this.tokens ==  null) {
			this.tokens = new HashSet<NameValuePair>();
		}
	}

}
