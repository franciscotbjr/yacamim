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

import java.util.List;

import org.apache.http.cookie.Cookie;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class BaseAsyncTask TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
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
