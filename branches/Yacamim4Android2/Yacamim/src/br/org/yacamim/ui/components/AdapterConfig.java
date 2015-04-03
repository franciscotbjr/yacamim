/**
 * AdapterConfig.java
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
package br.org.yacamim.ui.components;

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
	public AdapterConfig() {
		super();
	}

	/**
	 *
	 * @param rowConfigs
	 * @param rowCondition
	 * @param listModelSelection A simple list that stores the data of each selected row. Should be <tt>null</tt> if there is no <tt>CheckBox</tt>.
	 */
	public AdapterConfig(final RowConfig[] rowConfigs, final RowCondition rowCondition,
			final List<Object> listModelSelection) {
		super();
		this.rowConfigs = rowConfigs;
		this.rowCondition = rowCondition;
		this.listModelSelection = listModelSelection;
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
	public void setRowConfigs(final RowConfig[] rowConfigs) {
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
	public void setRowCondition(final RowCondition rowCondition) {
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
	public void setListModelSelection(final List<Object> listModelSelection) {
		this.listModelSelection = listModelSelection;
	}

}