/**
 * InteractionConfig.java
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
package br.org.yacamim.ui.components;

import java.io.Serializable;


/**
 * Classe InteractionConfig TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class InteractionConfig implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 203672884007002681L;

	/**
	 * 
	 */
	private int resourceIDForInteraction = -1;
	
	/**
	 * 
	 */
	private Class<?> resourceTypeForInteraction = null;

	/**
	 * 
	 */
	public InteractionConfig() {
		super();
	}

	/**
	 * 
	 * @param resourceIDForInteraction If there is a CheckBox, Button or ImageView inside the row and interaction is required, then its identifier must be provided.
	 * @param resourceTypeForInteraction If there is a CheckBox, Button or ImageView inside the row and interaction is required, then its type must be provided.
	 */
	public InteractionConfig(int resourceIDForInteraction,
			Class<?> resourceTypeForInteraction) {
		super();
		this.resourceIDForInteraction = resourceIDForInteraction;
		this.resourceTypeForInteraction = resourceTypeForInteraction;
	}
	

	/**
	 * @return the resourceIDForInteraction
	 */
	public int getResourceIDForInteraction() {
		return resourceIDForInteraction;
	}

	/**
	 * @param resourceIDForInteraction the resourceIDForInteraction to set
	 */
	public void setResourceIDForInteraction(int resourceIDForInteraction) {
		this.resourceIDForInteraction = resourceIDForInteraction;
	}

	/**
	 * @return the resourceTypeForInteraction
	 */
	public Class<?> getResourceTypeForInteraction() {
		return resourceTypeForInteraction;
	}

	/**
	 * @param resourceTypeForInteraction the resourceTypeForInteraction to set
	 */
	public void setResourceTypeForInteraction(Class<?> resourceTypeForInteraction) {
		this.resourceTypeForInteraction = resourceTypeForInteraction;
	}

}
