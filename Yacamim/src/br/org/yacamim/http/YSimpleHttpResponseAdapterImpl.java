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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.cookie.Cookie;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class YBaseAsyncTask TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YSimpleHttpResponseAdapterImpl implements YSimpleHttpResponseAdapter {

	private String requestPath;
	private StringBuilder body;
	private List<Cookie> cookies;
	private InputStream inputStream;
	public int status;
	

	/**
	 *
	 */
	public YSimpleHttpResponseAdapterImpl() {
		super();
	}

	/**
	 *
	 * @see br.org.yacamim.http.YSimpleHttpResponseAdapter#getBody()
	 */
	@Override
	public StringBuilder getBody() {
		return this.body;
	}

	/**
	 *
	 * @see br.org.yacamim.http.YSimpleHttpResponseAdapter#getJSONObject()
	 */
	@Override
	public JSONObject getJSONObject() throws JSONException {
		if(this.body == null) {
			throw new JSONException("Response body is null!");
		}
		return new JSONObject(this.getBody().toString());
	}

	/**
	 *
	 * @see br.org.yacamim.http.YSimpleHttpResponseAdapter#getCookies()
	 */
	@Override
	public List<Cookie> getCookies() {
		return this.cookies;
	}

	/**
	 *
	 * @see br.org.yacamim.http.YSimpleHttpResponseAdapter#getStatus()
	 */
	@Override
	public int getStatus() {
		return this.status;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public String getRequestPath() {
		return requestPath;
	}
	
	/**
	 *
	 * @see br.org.yacamim.http.YSimpleHttpResponseAdapter#setBody(java.lang.StringBuilder)
	 */
	public YSimpleHttpResponseAdapter setBody(final StringBuilder body) {
		this.body = body;
		return this;
	}

	/**
	 *
	 * @see br.org.yacamim.http.YSimpleHttpResponseAdapter#addCookies(java.util.List)
	 */
	public YSimpleHttpResponseAdapter addCookies(final List<Cookie> cookies) {
		this.lazyCookies();
		this.cookies.addAll(cookies);
		return this;
	}

	/**
	 *
	 * @see br.org.yacamim.http.YSimpleHttpResponseAdapter#addCookie(org.apache.http.cookie.Cookie)
	 */
	public YSimpleHttpResponseAdapter addCookie(final Cookie cookie) {
		this.lazyCookies();
		this.cookies.add(cookie);
		return this;
	}

	/**
	 *
	 * @see br.org.yacamim.http.YSimpleHttpResponseAdapter#setStatus(int)
	 */
	public YSimpleHttpResponseAdapter setStatus(final int status) {
		this.status = status;
		return this;
	}
	
	/**
	 * 
	 * @param requestPath
	 * @return
	 */
	public YSimpleHttpResponseAdapter setRequestPath(String requestPath) {
		this.requestPath = requestPath;
		return this;
	}

	/**
	 *
	 */
	private void lazyCookies() {
		if(this.cookies == null) {
			this.cookies = new ArrayList<Cookie>();
		}
	}

	@Override
	public YSimpleHttpResponseAdapter setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
		return this;
	}

	@Override
	public InputStream getInputStream() {
		return this.inputStream;
	}

}
