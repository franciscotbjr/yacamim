/**
 * InteractionConfig.java
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
	private int mResourceIDForInteraction = -1;

	/**
	 *
	 */
	private Class<?> mResourceTypeForInteraction = null;
	
	/**
	 * 
	 */
	private Condition mCondition;

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
	public InteractionConfig(final int resourceIDForInteraction,
			final Class<?> resourceTypeForInteraction) {
		super();
		mResourceIDForInteraction = resourceIDForInteraction;
		mResourceTypeForInteraction = resourceTypeForInteraction;
	}
	
	/**
	 *
	 * @param resourceIDForInteraction If there is a CheckBox, Button or ImageView inside the row and interaction is required, then its identifier must be provided.
	 * @param resourceTypeForInteraction If there is a CheckBox, Button or ImageView inside the row and interaction is required, then its type must be provided.
	 */
	public InteractionConfig(final int resourceIDForInteraction,
			final Class<?> resourceTypeForInteraction,
			final Condition condition) {
		super();
		mResourceIDForInteraction = resourceIDForInteraction;
		mResourceTypeForInteraction = resourceTypeForInteraction;
		mCondition = condition;
	}


	/**
	 * @return the resourceIDForInteraction
	 */
	public int getResourceIDForInteraction() {
		return mResourceIDForInteraction;
	}

	/**
	 * @param resourceIDForInteraction the resourceIDForInteraction to set
	 */
	public void setResourceIDForInteraction(int resourceIDForInteraction) {
		mResourceIDForInteraction = resourceIDForInteraction;
	}

	/**
	 * @return the resourceTypeForInteraction
	 */
	public Class<?> getResourceTypeForInteraction() {
		return mResourceTypeForInteraction;
	}

	/**
	 * @param resourceTypeForInteraction the resourceTypeForInteraction to set
	 */
	public void setResourceTypeForInteraction(Class<?> resourceTypeForInteraction) {
		mResourceTypeForInteraction = resourceTypeForInteraction;
	}

	/**
	 * 
	 * @return
	 */
	public Condition getCondition() {
		return mCondition;
	}
	
	/**
	 * 
	 * @param mCondition
	 */
	public void setCondition(Condition mCondition) {
		this.mCondition = mCondition;
	}
}
