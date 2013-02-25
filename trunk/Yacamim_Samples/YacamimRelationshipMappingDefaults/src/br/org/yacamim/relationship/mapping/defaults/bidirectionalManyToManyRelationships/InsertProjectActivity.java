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
package br.org.yacamim.relationship.mapping.defaults.bidirectionalManyToManyRelationships;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import br.org.yacamim.YBaseListActivity;
import br.org.yacamim.persistence.DefaultDBAdapter;
import br.org.yacamim.relationship.mapping.defaults.R;
import br.org.yacamim.relationship.mapping.defaults.bidirectionalManyToManyRelationships.entity.Employee;
import br.org.yacamim.relationship.mapping.defaults.bidirectionalManyToManyRelationships.entity.Project;
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
public class InsertProjectActivity extends YBaseListActivity {
	
	private static final String TAG = InsertProjectActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bidirectional_many_to_many);
		this.init();
	}

	/**
	 * 
	 */
	protected void init() {
		try {
			final Project project1 = new Project();
			project1.setName("Project 3 ");
			
			final Employee employee1 = new Employee();
			employee1.setName("Employee 2");
			
			final Employee employee2 = new Employee();
			employee2.setName("Employee 3");
			
			final List<Employee> employees = new ArrayList<Employee>();
			employees.add(employee1);
			employees.add(employee2);
			
			final List<Project> projects = new ArrayList<Project>();
			projects.add(project1);
			
			project1.setEmployees(employees);
			
			employee1.setProjects(projects);
			employee2.setProjects(projects);
			
			
			final DefaultDBAdapter<Project> defaultDBAdapter = new DefaultDBAdapter<Project>(Project.class);
			defaultDBAdapter.open();
			defaultDBAdapter.insert(project1);
			List<Project> projectsRec = defaultDBAdapter.list();
			defaultDBAdapter.close();
			
			if(projectsRec != null) {
				for(final Project project : projects) {
					Log.i(TAG, project.getName());
				}
			}
			
			this.initList(projectsRec);
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param projects
	 */
    private void initList(final List<Project> projects) {
    	try {
			List<HashMap<String, Object>> listOfMappedData = YUtilListView.buildListOfMappedData(projects);
			
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
			.setResource(R.layout.list_bidirectional_many_to_many)
			.setResourcesHint(new int[]{})
			.addRowConfigItem(new RowConfigItem("name", R.id.txtv_employee_name))
//			.addRowConfigItem(new RowConfigItem("department.name", R.id.txtv_department_name))
			;
		
		final RowConfig[] rowConfigs = {rowConfig} ;
		
		final AdapterConfig adapterConfig = new AdapterConfig(rowConfigs , ConditionFactory.getSimpleRowCondition(), null);
		return adapterConfig;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bidirectional_many_to_many,
				menu);
		return true;
	}

}
