/**
 * RowConfig.java
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	private List<RowConfigItem> rowConfigItems;
	
	/**
	 * 
	 */
	private int resource;

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
	 * @param resource The identifier of a XML file layout that defines the appearance of its rows. 
	 */
	public RowConfig(int[] resourcesHint, List<RowConfigItem> rowConfigItems,
			int resource) {
		super();
		this.resourcesHint = resourcesHint;
		this.rowConfigItems = rowConfigItems;
		this.resource = resource;
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
	public List<RowConfigItem> getRowConfigItems() {
		return rowConfigItems;
	}

	/**
	 * @param rowConfigItems the rowConfigItems to set
	 */
	public void setRowConfigItems(List<RowConfigItem> rowConfigItems) {
		this.rowConfigItems = rowConfigItems;
	}

	/**
	 * @return the resource
	 */
	public int getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(int resource) {
		this.resource = resource;
	}
	
	/**
	 * 
	 * @param rowConfigItem
	 */
	public void addRowConfigItem(RowConfigItem rowConfigItem) {
		if(this.rowConfigItems == null) {
			this.rowConfigItems = new ArrayList<RowConfigItem>();
		}
		this.rowConfigItems.add(rowConfigItem);
	}
	
	/**
	 * 
	 * @param rowConfigItem
	 * @return
	 */
	public boolean removeRowConfigItem(RowConfigItem rowConfigItem) {
		if(this.rowConfigItems == null) {
			this.rowConfigItems = new ArrayList<RowConfigItem>();
		}
		return this.rowConfigItems.remove(rowConfigItem);
	}


	/**
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + resource;
		result = prime * result + Arrays.hashCode(resourcesHint);
		result = prime * result
				+ ((rowConfigItems == null) ? 0 : rowConfigItems.hashCode());
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
		if (!(obj instanceof RowConfig)) {
			return false;
		}
		RowConfig other = (RowConfig) obj;
		if (resource != other.resource) {
			return false;
		}
		if (!Arrays.equals(resourcesHint, other.resourcesHint)) {
			return false;
		}
		if (rowConfigItems == null) {
			if (other.rowConfigItems != null) {
				return false;
			}
		} else if (!rowConfigItems.equals(other.rowConfigItems)) {
			return false;
		}
		return true;
	}

}