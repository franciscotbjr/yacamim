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
