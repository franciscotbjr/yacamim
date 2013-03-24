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
import java.util.List;

import org.apache.http.cookie.Cookie;

/**
 * A proxy that stores all active Cookies that have been received throw HTTP calls.<br/>
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YCookieProxy {

	private List<Cookie> cookies;

	private static final YCookieProxy singletonYCookieProxy = new YCookieProxy();

	/**
	 *
	 */
	private YCookieProxy() {
		super();
	}

	/**
	 *
	 * @return
	 */
	public static YCookieProxy getInstance() {
		return singletonYCookieProxy;
	}

	/**
	 *
	 * @return
	 */
	public List<Cookie> getCookies() {
		lazyCookies();
		return new ArrayList<Cookie>(cookies);
	}

	/**
	 *
	 * @param cookie
	 * @return
	 */
	public YCookieProxy addCookie(Cookie cookie) {
		lazyCookies();
		this.cookies.add(cookie);
		return this;
	}

	/**
	 *
	 * @param cookies
	 * @return
	 */
	public YCookieProxy addCookies(List<Cookie> cookies) {
		lazyCookies();
		this.cookies.addAll(cookies);
		return this;
	}

	/**
	 *
	 * @param cookie
	 * @return
	 */
	public YCookieProxy removeCookies(Cookie cookie) {
		lazyCookies();
		this.cookies.remove(cookie);
		return this;
	}

	/**
	 *
	 */
	private void lazyCookies() {
		if(this.cookies ==  null) {
			this.cookies = new ArrayList<Cookie>();
		}
	}

}
