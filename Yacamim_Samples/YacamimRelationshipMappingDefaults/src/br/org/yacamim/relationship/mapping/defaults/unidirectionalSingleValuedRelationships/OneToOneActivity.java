package br.org.yacamim.relationship.mapping.defaults.unidirectionalSingleValuedRelationships;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.persistence.DefaultDBAdapter;
import br.org.yacamim.relationship.mapping.defaults.R;
import br.org.yacamim.relationship.mapping.defaults.unidirectionalSingleValuedRelationships.entity.Employee;

public class OneToOneActivity extends YBaseActivity {
	
	private static final String TAG = OneToOneActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unidirectional_one_to_one);
		this.init();
	}
	
	/**
	 * 
	 */
	protected void init() {
		try {
			final DefaultDBAdapter<Employee> defaultDBAdapter = new DefaultDBAdapter<Employee>(Employee.class);
			defaultDBAdapter.open();
			List<Employee> employees = defaultDBAdapter.list();
			defaultDBAdapter.close();
			
			if(employees != null) {
				for(final Employee employee : employees) {
					Log.i(TAG, employee.getName() + " : " + employee.getProfile().getName());
				}
			}
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_unidirectional_one_to_one,
				menu);
		return true;
	}

}
