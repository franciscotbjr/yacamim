/**
 * PopupMenuItem.java
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
package br.org.yacamim;

import java.io.Serializable;

/**
 * Class PopupMenuItem TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class PopupMenuItem implements Serializable {
	
	private Integer action;
	private Integer descriptionResourceId;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4796571119887028734L;

	/**
	 * 
	 */
	public PopupMenuItem() {
		super();
	}
	
	/**
	 * 
	 * @param _action
	 * @param _descricao
	 */
	public PopupMenuItem(final Integer _action, final Integer _idDescriptionResource) {
		super();
		this.action = _action;
		this.descriptionResourceId = _idDescriptionResource;
	}

	/**
	 * @return the action
	 */
	public Integer getAction() {
		return action;
	}

	/**
	 * @param _action the action to set
	 */
	public void setAction(Integer _action) {
		this.action = _action;
	}

	/**
	 * @return the descriptionResourceId
	 */
	public Integer getDescriptionResourceId() {
		return descriptionResourceId;
	}

	/**
	 * @param descriptionResourceId the descriptionResourceId to set
	 */
	public void setDescriptionResourceId(Integer _descriptionResourceId) {
		this.descriptionResourceId = _descriptionResourceId;
	}

	/**
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		return result;
	}

	/**
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PopupMenuItem)) {
			return false;
		}
		final PopupMenuItem other = (PopupMenuItem) obj;
		if (this.action == null || other.action == null) {
				return false;
		} else if (this.action.intValue() != other.action.intValue()) {
			return false;
		}
		return true;
	}


}
