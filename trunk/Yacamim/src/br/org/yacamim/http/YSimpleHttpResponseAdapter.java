package br.org.yacamim.http;

import java.util.List;

import org.apache.http.cookie.Cookie;
import org.json.JSONException;
import org.json.JSONObject;

public interface YSimpleHttpResponseAdapter {
	
	public int getStatus();
	
	public StringBuilder getBody();
	
	public JSONObject getJSONObject() throws JSONException;
	
	public List<Cookie> getCookies();
	
	public YSimpleHttpResponseAdapter setStatus(int status);
	
	public YSimpleHttpResponseAdapter setBody(StringBuilder body);
	
	public YSimpleHttpResponseAdapter addCookies(List<Cookie> cookies);
	
	public YSimpleHttpResponseAdapter addCookie(Cookie cookie);

}
