package br.org.yacamim.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.cookie.Cookie;

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
