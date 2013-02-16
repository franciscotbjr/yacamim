package br.org.yacamim.relationship.mapping.defaults;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class YRelationshipMappingDefaultsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yrelationship_mapping_defaults);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(
				R.menu.activity_yrelationship_mapping_defaults, menu);
		return true;
	}

}
