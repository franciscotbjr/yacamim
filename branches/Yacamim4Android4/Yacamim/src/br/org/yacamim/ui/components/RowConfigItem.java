/**
 * RowConfigItem.java
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
package br.org.yacamim.ui.components;

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
	private String graphFrom = null;

	/**
	 *
	 */
	private int resourceIdTo = -1;

	/**
	 *
	 */
	private Condition condition = null;

	/**
	 *
	 */
	private InteractionConfig interactionConfig = null;

	/**
	 *
	 */
	public RowConfigItem() {
		super();
	}

	/**
	 *
	 *
	 * @param graphFrom A graph that will be scanned for the value to be displayed.
	 * @param resourceIdTo A resource identifier to which will be displayed the value obtained throw the scanned graph.
	 */
	public RowConfigItem(
			final String graphFrom, 
			final int resourceIdTo) {
		super();
		this.graphFrom = graphFrom;
		this.resourceIdTo = resourceIdTo;
	}

	/**
	 *
	 *
	 * @param graphFrom A graph that will be scanned for the value to be displayed.
	 * @param resourceIdTo A resource identifier to which will be displayed the value obtained throw the scanned graph.
	 * @param formatingType A formating type identifier supposed to format information displayed. When there is no formatting, then <tt>-1</tt> is the value to be provided.
	 */
	public RowConfigItem(
			final String graphFrom, 
			final int resourceIdTo, 
			final int formatingType) {
		super();
		this.graphFrom = graphFrom;
		this.resourceIdTo = resourceIdTo;
		this.formatingType = formatingType;
	}

	/**
	 *
	 *
	 * @param graphFrom A graph that will be scanned for the value to be displayed.
	 * @param resourceIdTo A resource identifier to which will be displayed the value obtained throw the scanned graph.
	 * @param formatingType A formating type identifier supposed to format information displayed. When there is no formatting, then <tt>-1</tt> is the value to be provided.
	 * @param condition An instance of a class that implements <tt>br.org.yacamim.ui.componentes.Condition</tt> interface. This will be used to check if the item represented by the <tt>RowConfigItem</tt> instance will be displayed.
	 */
	public RowConfigItem(
			final String graphFrom, 
			final int resourceIdTo,
			final int formatingType,
			final Condition condition) {
		super();
		this.graphFrom = graphFrom;
		this.resourceIdTo = resourceIdTo;
		this.formatingType = formatingType;
		this.condition = condition;
	}

	/**
	 *
	 *
	 * @param graphFrom A graph that will be scanned for the value to be displayed.
	 * @param resourceIdTo A resource identifier to which will be displayed the value obtained throw the scanned graph.
	 * @param formatingType A formating type identifier supposed to format information displayed. When there is no formatting, then <tt>-1</tt> is the value to be provided.
	 * @param condition An instance of a class that implements <tt>br.org.yacamim.ui.componentes.Condition</tt> interface. This will be used to check if the item represented by the <tt>RowConfigItem</tt> instance will be displayed.
	 * @param interactionConfig
	 */
	public RowConfigItem(
			final String graphFrom, 
			final int resourceIdTo,
			final int formatingType,
			final Condition condition,
			final InteractionConfig interactionConfig) {
		super();
		this.graphFrom = graphFrom;
		this.resourceIdTo = resourceIdTo;
		this.formatingType = formatingType;
		this.condition = condition;
		this.interactionConfig = interactionConfig;
	}
	
	/**
	 *
	 *
	 * @param graphFrom A graph that will be scanned for the value to be displayed.
	 * @param resourceIdTo A resource identifier to which will be displayed the value obtained throw the scanned graph.
	 * @param condition An instance of a class that implements <tt>br.org.yacamim.ui.componentes.Condition</tt> interface. This will be used to check if the item represented by the <tt>RowConfigItem</tt> instance will be displayed.
	 */
	public RowConfigItem(
			final String graphFrom, 
			final int resourceIdTo,
			final Condition condition) {
		super();
		this.graphFrom = graphFrom;
		this.resourceIdTo = resourceIdTo;
		this.condition = condition;
	}

	/**
	 *
	 *
	 * @param graphFrom A graph that will be scanned for the value to be displayed.
	 * @param resourceIdTo A resource identifier to which will be displayed the value obtained throw the scanned graph.
	 * @param condition An instance of a class that implements <tt>br.org.yacamim.ui.componentes.Condition</tt> interface. This will be used to check if the item represented by the <tt>RowConfigItem</tt> instance will be displayed.
	 * @param interactionConfig
	 */
	public RowConfigItem(
			final String graphFrom, 
			final int resourceIdTo, 
			final Condition condition,
			final InteractionConfig interactionConfig) {
		super();
		this.graphFrom = graphFrom;
		this.resourceIdTo = resourceIdTo;
		this.condition = condition;
		this.interactionConfig = interactionConfig;
	}

	/**
	 *
	 *
	 * @param condition An instance of a class that implements <tt>br.org.yacamim.ui.componentes.Condition</tt> interface. This will be used to check if the item represented by the <tt>RowConfigItem</tt> instance will be displayed.
	 * @param interactionConfig
	 */
	public RowConfigItem(
			final Condition condition,
			final InteractionConfig interactionConfig) {
		super();
		this.condition = condition;
		this.interactionConfig = interactionConfig;
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

	/**
	 * @return the interactionConfig
	 */
	public InteractionConfig getInteractionConfig() {
		return interactionConfig;
	}

	/**
	 * @param interactionConfig the interactionConfig to set
	 */
	public void setInteractionConfig(InteractionConfig interactionConfig) {
		this.interactionConfig = interactionConfig;
	}

	/**
	 *
	 * @return
	 */
	public boolean isInteraction() {
		return this.getInteractionConfig() != null;
	}

	/**
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + formatingType;
		result = prime * result
				+ ((graphFrom == null) ? 0 : graphFrom.hashCode());
		result = prime
				* result
				+ ((interactionConfig == null) ? 0 : interactionConfig
						.hashCode());
		result = prime * result + resourceIdTo;
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
		if (!(obj instanceof RowConfigItem)) {
			return false;
		}
		RowConfigItem other = (RowConfigItem) obj;
		if (condition == null) {
			if (other.condition != null) {
				return false;
			}
		} else if (!condition.equals(other.condition)) {
			return false;
		}
		if (formatingType != other.formatingType) {
			return false;
		}
		if (graphFrom == null) {
			if (other.graphFrom != null) {
				return false;
			}
		} else if (!graphFrom.equals(other.graphFrom)) {
			return false;
		}
		if (interactionConfig == null) {
			if (other.interactionConfig != null) {
				return false;
			}
		} else if (!interactionConfig.equals(other.interactionConfig)) {
			return false;
		}
		if (resourceIdTo != other.resourceIdTo) {
			return false;
		}
		return true;
	}

}