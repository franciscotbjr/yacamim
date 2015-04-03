/**
 * User.java
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
package br.org.yacamim.entity;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;
import br.org.yacamim.YacamimConfig;
import br.org.yacamim.persistence.Column;
import br.org.yacamim.persistence.Entity;
import br.org.yacamim.persistence.Id;
import br.org.yacamim.persistence.Table;


/**
 *
 * Class User TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "user")
public class User extends YBaseEntity {

	private String login;
	private String password;
	private String newPassword;
	private String question;
	private String answer;
	private Session session;
	private String firstAccess;
	private String logonFail;
	private Date lastAcces;

	/**
	 * Construtor da classe.
	 */
	public User() {
		super();
		this.firstAccess = YacamimConfig.getInstance().getYSqliteFalse();
		this.logonFail = YacamimConfig.getInstance().getYSqliteFalse();
	}
	
	/**
	 *
	 */
	public User(Parcel parcel) {
		super(parcel);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param _login
	 * @param _question
	 * @param _answer
	 */
	public User(final String _login, final String _question, final String _answer) {
		super();
		this.login = _login;
		this.question = _question;
		this.answer = _answer;

		this.firstAccess = YacamimConfig.getInstance().getYSqliteFalse();
		this.logonFail = YacamimConfig.getInstance().getYSqliteFalse();
	}


	/**
	 * @return the id
	 */
	@Id()
	@Column(name="id_user")
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
	 * @return the login
	 */
	@Column(name="ds_login")
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * @return the firstAccess
	 */
	public String getFirstAccess() {
		return firstAccess;
	}

	/**
	 * @param firstAccess the firstAccess to set
	 */
	public void setFirstAccess(String firstAccess) {
		this.firstAccess = firstAccess;
	}

	/**
	 * @return the logonFail
	 */
	public String getLogonFail() {
		return logonFail;
	}

	/**
	 * @param logonFail the logonFail to set
	 */
	public void setLogonFail(String logonFail) {
		this.logonFail = logonFail;
	}

	/**
	 * @return the lastAcces
	 */
	public Date getLastAcces() {
		return lastAcces;
	}

	/**
	 * @param lastAcces the lastAcces to set
	 */
	public void setLastAcces(Date lastAcces) {
		this.lastAcces = lastAcces;
	}
	
	/**
	 * 
	 */
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel pc) {
            return new User(pc);
        }
        public User[] newArray(int size) {
            return new User[size];
        }
	};
	
	/**
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (login == null) {
			if (other.login != null) {
				return false;
			}
		} else if (!login.equals(other.login)) {
			return false;
		}
		return true;
	}



}