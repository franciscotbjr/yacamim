/**
 * InsertEmployeeActivity.java
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
package br.org.yacamim.relationship.mapping.defaults.bidirectionalOneToOneRelationships;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import br.org.yacamim.YBaseListActivity;
import br.org.yacamim.persistence.DefaultDBAdapter;
import br.org.yacamim.relationship.mapping.defaults.R;
import br.org.yacamim.relationship.mapping.defaults.bidirectionalOneToOneRelationships.entity.Cubicle;
import br.org.yacamim.relationship.mapping.defaults.bidirectionalOneToOneRelationships.entity.Employee;
import br.org.yacamim.relationship.mapping.defaults.util.ConditionFactory;
import br.org.yacamim.ui.components.AdapterConfig;
import br.org.yacamim.ui.components.ComplexListSimpleAdapter;
import br.org.yacamim.ui.components.RowConfig;
import br.org.yacamim.ui.components.RowConfigItem;
import br.org.yacamim.util.YUtilListView;

/**
 * Classe InsertEmployeeActivity TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class InsertEmployeeActivity extends YBaseListActivity {
	
	private static final String TAG = InsertEmployeeActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bidirectional_one_to_one);
		this.init();
	}
	
	/**
	 * 
	 */
	protected void init() {
		try {
			final Employee employee = new Employee();
			employee.setName("Employee 1 ");
			
			final Cubicle cubicle = new Cubicle();
			cubicle.setName("Cubicle 1 ");
			cubicle.setResidentEmployee(employee);
			
			employee.setAssignedCubicle(cubicle);
			
			final DefaultDBAdapter<Employee> defaultDBAdapter = new DefaultDBAdapter<Employee>(Employee.class);
			defaultDBAdapter.open();
			defaultDBAdapter.insert(employee);
			List<Employee> employees = defaultDBAdapter.list();
			defaultDBAdapter.close();
			
			this.initList(employees);
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param employees
	 */
    private void initList(final List<Employee> employees) {
    	try {
			List<HashMap<String, Object>> listOfMappedData = YUtilListView.buildListOfMappedData(employees);
			
			final AdapterConfig adapterConfig = this.buildAdapterConfig();
			
			final ComplexListSimpleAdapter complexListSimpleAdapter = new ComplexListSimpleAdapter(
					this, 
					listOfMappedData, 
					adapterConfig);
			setListAdapter(complexListSimpleAdapter);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
    }

	/**
	 * @return
	 */
	protected AdapterConfig buildAdapterConfig() {
		
		final RowConfig rowConfig = new RowConfig()
			.setResource(R.layout.list_bidirectional_one_to_one)
			.setResourcesHint(new int[]{})
			.addRowConfigItem(new RowConfigItem("assignedCubicle.residentEmployee.name", R.id.txtv_employee_name))
			.addRowConfigItem(new RowConfigItem("assignedCubicle.name", R.id.txtv_cubicle_name))
			;
		
		final RowConfig[] rowConfigs = {rowConfig} ;
		
		final AdapterConfig adapterConfig = new AdapterConfig(rowConfigs , ConditionFactory.getSimpleRowCondition(), null);
		return adapterConfig;
	}

	/**
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_bidirectional_one_to_one,
				menu);
		return true;
	}

}
