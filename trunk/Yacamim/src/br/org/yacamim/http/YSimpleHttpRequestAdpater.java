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
import java.util.Set;

import org.apache.http.NameValuePair;

/**
 * Class BaseAsyncTask TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public interface YSimpleHttpRequestAdpater {
	
	/**
	 * 
	 * @return
	 */
	public String getEnconding();
	
	/**
	 * 
	 * @return
	 */
	public String getUri();
	
	/**
	 * 
	 * @return
	 */
	public List<NameValuePair> getParameters();
	
	/**
	 * 
	 * @param uri
	 * @return
	 */
	public YSimpleHttpRequestAdpater setUri(String uri);
	
	/**
	 * By default, it is "UTF-8".<br/>
	 * 
	 * @param enconding
	 * @return
	 */
	public YSimpleHttpRequestAdpater setEncondig(String enconding);
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public YSimpleHttpRequestAdpater addParams(final List<NameValuePair> params);
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	public YSimpleHttpRequestAdpater addParam(NameValuePair param);

	/**
	 * 
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public YSimpleHttpRequestAdpater addParam(final String paramName, final String paramValue);
	
	/**
	 * Adds a token name witch represents a token that will exist as a part of the 
	 * HTTP response. If the wanted token is a response header, no additional implementation 
	 * will be necessary, because Yacamim will handle it. If it is inside the response body, than 
	 * it will be necessary to provide your own solution, for that see the method <tt>setBodyTokenRecoverHandler</tt>.  
	 * 
	 * @param tokenName
	 * @return
	 */
	public YSimpleHttpRequestAdpater manageToken(final String tokenName);
	
	/**
	 * As long as it is very difficult to predict the format of a response body,
	 * Yacamim does not try to find his way to a wanted token. To do that you must 
	 * provide your own solution by implementing <tt>YBodyTokenRecoverHandler</tt>.<br/>
	 * 
	 * Check Yacamim Samples at SVN repository.<br/>
	 * 
	 * @see br.org.yacamim.http.YBodyTokenRecoverHandler

	 * @param yBodyTokenRecoverHandler
	 * @return
	 * 
	 */
	public YSimpleHttpRequestAdpater setBodyTokenRecoverHandler(YBodyTokenRecoverHandler yBodyTokenRecoverHandler);
	
	/**
	 * 
	 * @return
	 */
	public YBodyTokenRecoverHandler getBodyTokenRecoverHandler();
	
	/**
	 * 
	 * @return
	 */
	public Set<String> getTokens();

}
