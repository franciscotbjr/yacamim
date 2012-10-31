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
