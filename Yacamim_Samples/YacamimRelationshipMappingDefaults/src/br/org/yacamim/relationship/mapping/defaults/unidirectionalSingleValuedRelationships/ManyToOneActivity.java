package br.org.yacamim.relationship.mapping.defaults.unidirectionalSingleValuedRelationships;

import android.os.Bundle;
import android.view.Menu;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.relationship.mapping.defaults.R;

public class ManyToOneActivity extends YBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unidirectional_many_to_one);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_unidirectional_many_to_one,
				menu);
		return true;
	}

}
