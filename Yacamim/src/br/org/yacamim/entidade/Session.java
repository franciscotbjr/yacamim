/**
 * Session.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.entidade;

import br.org.yacamim.persistencia.Column;
import br.org.yacamim.persistencia.Entity;
import br.org.yacamim.persistencia.PK;
import br.org.yacamim.persistencia.Table;



/**
 * 
 * Class Session TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="session")
public class Session extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3491760419509126643L;
	
	private long id;
	private String sessionId;
	
	/**
	 * 
	 */
	public Session() {
		super();
	}

	/**
	 * 
	 */
	public Session(final String _sessionId) {
		super();
		this.sessionId = _sessionId;
	}

	/**
	 * @return the id
	 */
	@PK()
	@Column(name="id_session")
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the sessionId
	 */
	@Column(name="ds_sessionid")
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	/**
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
		return result;
	}

	/**
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Session)) {
			return false;
		}
		Session other = (Session) obj;
		if (getId() != other.getId()) {
			return false;
		}
		return true;
	}

}
