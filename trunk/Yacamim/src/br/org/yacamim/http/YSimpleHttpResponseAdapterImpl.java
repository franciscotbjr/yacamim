package br.org.yacamim.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.cookie.Cookie;
import org.json.JSONException;
import org.json.JSONObject;

public class YSimpleHttpResponseAdapterImpl implements YSimpleHttpResponseAdapter {
	
	private StringBuilder body;
	private List<Cookie> cookies;
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
	public int getStatus() {
		return this.status;
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
	 */
	private void lazyCookies() {
		if(this.cookies == null) {
			this.cookies = new ArrayList<Cookie>();
		}
	}

}
