/**
 * RowConfigItem.java
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
package br.org.yacamim.ui.componentes;

import java.io.Serializable;

/**
 * Classe RowConfigItem TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class RowConfigItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5662063538709959966L;
	
	/**
	 * 
	 */
	private int formatingType = -1;
	
	/**
	 * 
	 */
	private String graphFrom;
	
	/**
	 * 
	 */
	private int resourceIdTo;
	
	/**
	 * 
	 */
	private int resourceIDForInteraction;
	
	/**
	 * 
	 */
	private Class<?> resourceTypeForInteraction;
	
	/**
	 * 
	 */
	private Condition condition = null;

	/**
	 * 
	 */
	public RowConfigItem() {
		super();
	}
	
	/**
	 * 
	 * 
	 * @param formatingType A formating type identifier supposed to format information displayed. When there is no formatting, then <tt>-1</tt> is the value to be provided.
	 * @param graphFrom A graph that will be scanned for the value to be displayed.
	 * @param resourceIdTo A resource identifier to which will be displayed the value obtained throw the scanned graph.
	 * @param resourceIDForInteraction If there is a CheckBox, Button or ImageView inside the row and interaction is required, then its identifier must be provided.
	 * @param resourceTypeForInteraction If there is a CheckBox, Button or ImageView inside the row and interaction is required, then its type must be provided.
	 * @param condition An instance of a class that implements <tt>br.org.yacamim.ui.componentes.Condition</tt> interface. This will be used to check if the item represented by the <tt>RowConfigItem</tt> instance will be displayed.
	 */
	public RowConfigItem(int formatingType, String graphFrom, int resourceIdTo,
			int resourceIDForInteraction, Class<?> resourceTypeForInteraction,
			Condition condition) {
		super();
		this.formatingType = formatingType;
		this.graphFrom = graphFrom;
		this.resourceIdTo = resourceIdTo;
		this.resourceIDForInteraction = resourceIDForInteraction;
		this.resourceTypeForInteraction = resourceTypeForInteraction;
		this.condition = condition;
	}

	/**
	 * @return the formatingType
	 */
	public int getFormatingType() {
		return formatingType;
	}

	/**
	 * @param formatingType the formatingType to set
	 */
	public void setFormatingType(int formatingType) {
		this.formatingType = formatingType;
	}

	/**
	 * @return the graphFrom
	 */
	public String getGraphFrom() {
		return graphFrom;
	}

	/**
	 * @param graphFrom the graphFrom to set
	 */
	public void setGraphFrom(String graphFrom) {
		this.graphFrom = graphFrom;
	}

	/**
	 * @return the resourceIdTo
	 */
	public int getResourceIdTo() {
		return resourceIdTo;
	}

	/**
	 * @param resourceIdTo the resourceIdTo to set
	 */
	public void setResourceIdTo(int resourceIdTo) {
		this.resourceIdTo = resourceIdTo;
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

	/**
	 * @return the condition
	 */
	public Condition getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}

}