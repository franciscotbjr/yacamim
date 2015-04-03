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
import java.util.Set;

import org.apache.http.NameValuePair;

/**
 * When it is necessary to handle the HTTP response body to locate some element as a hidden
 * field that holds a TOKEN value, witch is not supported by Yacamim by default, its necessary
 * to provide a <tt>YBodyTokenRecoverHandler</tt> implementation.<br/><br/>
 *
 * See Yacamim Samples at SVN repository for a implementation reference.
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public interface YBodyTokenRecoverHandler {

	/**
	 * Call back method that will provide a way to obtain the desired data. It is meant to
	 * extract token values to enhance HTTP sessions management.<br/>
	 *
	 *
	 * @see Yacamim Samples at SVN repository for a implementation reference.
	 *
	 * @param body HTTP body
	 * @param tokenNames List of tokens to look for.
	 * @return The list of parsed tokens.
	 */
	public List<NameValuePair> recover(final StringBuilder body, final Set<String> tokenNames);

}
