/**
 * AdapterConfig.java
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

import java.util.List;

/**
 * Classe AdapterConfig TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class AdapterConfig {
	
	/**
	 * 
	 */
	private RowConfig[] rowConfigs;
	
	/**
	 * 
	 */
	private RowCondition rowCondition;
	
	/**
	 * 
	 */
	private List<Object> listModelSelection;

	/**
	 * 
	 */
	private int[] resourceHints;
	

	/**
	 * 
	 */
	public AdapterConfig() {
		super();
	}

	/**
	 * 
	 * @param rowConfigs
	 * @param rowCondition
	 * @param listModelSelection A simple list that stores the data of each selected row. Should be <tt>null</tt> if there is no <tt>CheckBox</tt>.
	 * @param resourceHints
	 */
	public AdapterConfig(RowConfig[] rowConfigs, RowCondition rowCondition,
			List<Object> listModelSelection, int[] resourceHints) {
		super();
		this.rowConfigs = rowConfigs;
		this.rowCondition = rowCondition;
		this.listModelSelection = listModelSelection;
		this.resourceHints = resourceHints;
	}


	/**
	 * @return the rowConfigs
	 */
	public RowConfig[] getRowConfigs() {
		return rowConfigs;
	}


	/**
	 * @param rowConfigs the rowConfigs to set
	 */
	public void setRowConfigs(RowConfig[] rowConfigs) {
		this.rowConfigs = rowConfigs;
	}


	/**
	 * @return the rowCondition
	 */
	public RowCondition getRowCondition() {
		return rowCondition;
	}


	/**
	 * @param rowCondition the rowCondition to set
	 */
	public void setRowCondition(RowCondition rowCondition) {
		this.rowCondition = rowCondition;
	}


	/**
	 * @return the listModelSelection
	 */
	public List<Object> getListModelSelection() {
		return listModelSelection;
	}


	/**
	 * @param listModelSelection the listModelSelection to set
	 */
	public void setListModelSelection(List<Object> listModelSelection) {
		this.listModelSelection = listModelSelection;
	}


	/**
	 * @return the resourceHints
	 */
	public int[] getResourceHints() {
		return resourceHints;
	}


	/**
	 * @param resourceHints the resourceHints to set
	 */
	public void setResourceHints(int[] resourceHints) {
		this.resourceHints = resourceHints;
	}
	

}