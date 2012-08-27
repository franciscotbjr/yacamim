/**
 * RowConfigItem.java
 *
 * Copyright 2011 yacamim.org.br
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
	 * 
	 * @param graphFrom
	 * @param resourceIdTo
	 * @param resourceIDForInteraction
	 * @param resourceTypeForInteraction
	 */
	public RowConfigItem(String graphFrom, int resourceIdTo,
			int resourceIDForInteraction, Class<?> resourceTypeForInteraction) {
		super();
		this.graphFrom = graphFrom;
		this.resourceIdTo = resourceIdTo;
		this.resourceIDForInteraction = resourceIDForInteraction;
		this.resourceTypeForInteraction = resourceTypeForInteraction;
	}

	/**
	 * 
	 * 
	 * 
	 * @param formatingType
	 * @param graphFrom
	 * @param resourceIdTo
	 * @param resourceIDForInteraction
	 * @param resourceTypeForInteraction
	 * @param condition
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