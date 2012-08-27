/**
 * BaseEntity.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.entidade;

import java.io.Serializable;

/**
 * 
 * Class BaseEntity TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable {
	
	protected long id;
	private String error;
	private String message;

	/**
	 * 
	 */
	public BaseEntity() {
		super();
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param _id the id to set
	 */
	public void setId(long _id) {
		this.id = _id;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param _error the error to set
	 */
	public void setError(String _error) {
		this.error = _error;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param _message the message to set
	 */
	public void setMessage(String _message) {
		this.message = _message;
	}

}
