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
