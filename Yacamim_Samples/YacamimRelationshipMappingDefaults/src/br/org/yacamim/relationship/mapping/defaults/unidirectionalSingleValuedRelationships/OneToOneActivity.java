package br.org.yacamim.relationship.mapping.defaults.unidirectionalSingleValuedRelationships;

import br.org.yacamim.relationship.mapping.defaults.R;
import br.org.yacamim.relationship.mapping.defaults.R.layout;
import br.org.yacamim.relationship.mapping.defaults.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class OneToOneActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unidirectional_one_to_one);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_unidirectional_one_to_one,
				menu);
		return true;
	}

}