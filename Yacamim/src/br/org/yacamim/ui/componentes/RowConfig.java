/**
 * RowConfig.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.ui.componentes;

/**
 * Classe RowConfig TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class RowConfig {
	
	/**
	 * 
	 */
	private int[] resourcesHint;
	
	/**
	 * 
	 */
	private RowConfigItem[] rowConfigItems;

	/**
	 * 
	 */
	public RowConfig() {
		super();
	}
	
	/**
	 * 
	 * @param resourcesHint
	 * @param rowConfigItems
	 */
	public RowConfig(int[] resourcesHint, RowConfigItem[] rowConfigItems) {
		super();
		this.resourcesHint = resourcesHint;
		this.rowConfigItems = rowConfigItems;
	}


	/**
	 * @return the resourcesHint
	 */
	public int[] getResourcesHint() {
		return resourcesHint;
	}

	/**
	 * @param resourcesHint the resourcesHint to set
	 */
	public void setResourcesHint(int[] resourcesHint) {
		this.resourcesHint = resourcesHint;
	}

	/**
	 * @return the rowConfigItems
	 */
	public RowConfigItem[] getRowConfigItems() {
		return rowConfigItems;
	}

	/**
	 * @param rowConfigItems the rowConfigItems to set
	 */
	public void setRowConfigItems(RowConfigItem[] rowConfigItems) {
		this.rowConfigItems = rowConfigItems;
	}

}