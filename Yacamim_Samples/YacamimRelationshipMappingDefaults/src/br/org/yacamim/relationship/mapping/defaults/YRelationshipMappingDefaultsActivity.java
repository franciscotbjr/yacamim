package br.org.yacamim.relationship.mapping.defaults;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.relationship.mapping.defaults.bidirectionalManyToOneOneToManyRelationships.Home_ManyToOne_OneToManyActivity;
import br.org.yacamim.relationship.mapping.defaults.unidirectionalSingleValuedRelationships.Home_UnidirectionalSingleValueActivity;
import br.org.yacamim.relationship.mapping.defaults.unidirectionalSingleValuedRelationships.ManyToOneActivity;

public class YRelationshipMappingDefaultsActivity extends YBaseActivity {
	
	private static final String TAG = YRelationshipMappingDefaultsActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yrelationship_mapping_defaults);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_yrelationship_mapping_defaults, menu);
		return true;
	}
	
	/**
	 * 
	 * @param view
	 */
	public void startUnidirectionalSingleValueTest(final View view) {
		try {
			startActivity(new Intent(this, Home_UnidirectionalSingleValueActivity.class));
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param view
	 */
	public void startBidirectionalOneToOneTest(final View view) {
		try {
			startActivity(new Intent(this, br.org.yacamim.relationship.mapping.defaults.bidirectionalOneToOneRelationships.Home_OneToOneActivity.class));
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param view
	 */
	public void startBidirectionalManyToOneOneToManyTest(final View view) {
		try {
			startActivity(new Intent(this, Home_ManyToOne_OneToManyActivity.class));
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param view
	 */
	public void startBidirectionalManyToManyTest(final View view) {
		try {
			startActivity(new Intent(this, br.org.yacamim.relationship.mapping.defaults.bidirectionalManyToManyRelationships.Home_ManyToManyActivity.class));
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

}
