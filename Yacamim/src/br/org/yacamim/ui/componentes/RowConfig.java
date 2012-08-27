/**
 * RowConfig.java
 *
 * Copyright 2011 yacamim.org.br
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