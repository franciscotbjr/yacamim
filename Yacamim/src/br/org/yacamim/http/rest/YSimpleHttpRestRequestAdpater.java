/**
 * YSimpleHttpRestRequestAdpater.java
 *
 * Copyright 2013 yacamim.org.br
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
package br.org.yacamim.http.rest;

import java.io.InputStream;
import java.util.Set;

import br.org.yacamim.http.YBodyTokenRecoverHandler;

/**
 * Class YSimpleHttpRestRequestAdpater TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public interface YSimpleHttpRestRequestAdpater {

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
	 * @param uri
	 * @return
	 */
	public YSimpleHttpRestRequestAdpater setUri(String uri);

	/**
	 * By default, it is "UTF-8".<br/>
	 *
	 * @param enconding
	 * @return
	 */
	public YSimpleHttpRestRequestAdpater setEncondig(String enconding);

	/**
	 * 
	 * @param content
	 */
	public YSimpleHttpRestRequestAdpater setContent(final String content);
	
	/**
	 * 
	 * @param content
	 */
	public YSimpleHttpRestRequestAdpater setContent(final StringBuilder content);
	
	/**
	 * 
	 * @param content
	 */
	public YSimpleHttpRestRequestAdpater setContent(final byte[] content);
	
	/**
	 * 
	 * @param content
	 */
	public YSimpleHttpRestRequestAdpater setContent(final InputStream content);

	/**
	 * Adds a token name witch represents a token that will exist as a part of the
	 * HTTP response. If the wanted token is a response header, no additional implementation
	 * will be necessary, because Yacamim will handle it. If it is inside the response body, than
	 * it will be necessary to provide your own solution, for that see the method <tt>setBodyTokenRecoverHandler</tt>.
	 *
	 * @param tokenName
	 * @return
	 */
	public YSimpleHttpRestRequestAdpater manageToken(final String tokenName);

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
	public YSimpleHttpRestRequestAdpater setBodyTokenRecoverHandler(YBodyTokenRecoverHandler yBodyTokenRecoverHandler);

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
	
	/**
	 * 
	 * @return
	 */
	public InputStream getContent();

}
