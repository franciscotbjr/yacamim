/**
 * ConditionFactory.java
 *
 * Copyright 2013 yacamim.org.br
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
package br.org.yacamim.relationship.mapping.defaults.util;

import android.view.View;
import br.org.yacamim.ui.components.AdapterConfig;
import br.org.yacamim.ui.components.RowCondition;
import br.org.yacamim.ui.components.RowConfig;

/**
 * Classe ConditionFactory TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class ConditionFactory {

	/**
	 * 
	 */
	private ConditionFactory() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static RowCondition getSimpleRowCondition() {
		return new RowCondition() {
			
			@Override
			public RowConfig selectRowConfig(Object data, int position,
					AdapterConfig adapterConfig) {
				// TODO Auto-generated method stub
				return adapterConfig.getRowConfigs()[0];
			}

			@Override
			public void handleView(View view, Object data, int position,
					AdapterConfig adapterConfig) {
				// TODO Auto-generated method stub
				
			}

			
		
		};
	}
	
}