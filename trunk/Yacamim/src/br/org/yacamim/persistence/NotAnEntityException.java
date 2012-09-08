/**
 * NotAnEntityException.java
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
package br.org.yacamim.persistence;

/**
 * Classe NotAnEntityException TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class NotAnEntityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4388691218085997790L;

	/**
	 * 
	 */
	public NotAnEntityException() {
		super();
	}

	/**
	 * @param detailMessage
	 */
	public NotAnEntityException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * @param throwable
	 */
	public NotAnEntityException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * @param detailMessage
	 * @param throwable
	 */
	public NotAnEntityException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
