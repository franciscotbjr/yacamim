package br.org.yacamim.relationship.mapping.defaults.bidirectionalManyToOneOneToManyRelationships;

import android.os.Bundle;
import android.view.Menu;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.relationship.mapping.defaults.R;

public class OneToManyActivity extends YBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bidirectional_one_to_many);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bidirectional_one_to_many,
				menu);
		return true;
	}

}
