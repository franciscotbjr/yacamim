package br.org.yacamim.relationship.mapping.defaults;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import br.org.yacamim.YInitBaseActivity;
import br.org.yacamim.YacamimInitializer;
import br.org.yacamim.YacamimState;
import br.org.yacamim.relationship.mapping.defaults.unidirectionalSingleValuedRelationships.OneToOneActivity;

public class YRelationshipMappingDefaultsActivity extends YInitBaseActivity {
	
	private static final String TAG = YRelationshipMappingDefaultsActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yrelationship_mapping_defaults);
	}
	
	@Override
	protected void initApp() {
		try {
    		YacamimState.getInstance().setPreferencesToken("__y_prefs_#_token_@_mapping.defaults.yacamim.org.br_::");

    		YacamimInitializer.getInstance()
    		.setIdResourceMsgOK(R.string.y_MsgOK)
    		.setIdResourceMsgSuccesfullyInserted(R.string.y_MsgSuccesfullyInserted)
    		.setIdResourceMsgSuccesfullyUpdated(R.string.y_MsgSuccesfullyUpdated)
    		.setIdResourceMsgInvalidData(R.string.y_MsgInvalidData)
    		.setIdResourceMsgWait(R.string.y_MsgWait)
    		.setIdResourceMsgSelectAnItem(R.string.y_MsgSelectAnItem)
    		.setIdResourceMsgNoInformationFound(R.string.y_MsgNoInformationFound)
    		.setIdResourceMsgNoRecordsFoundForParameters(R.string.y_MsgNoRecordsFoundForParameters)
    		.setIdResourceMsgNoRecordSelected(R.string.y_MsgNoRecordSelected)
    		.setIdResourceMsgNoConnectivityAvailable(R.string.y_MsgNoConnectivityAvailable)
    		.setIdResourceMsgNoWifiConnectivityAvailable(R.string.y_MsgNoWifiConnectivityAvailable)
    		.setIdResourceMsgConstraintDependency(R.string.y_MsgConstraintDependency)
    		;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(
				R.menu.activity_yrelationship_mapping_defaults, menu);
		return true;
	}
	
	/**
	 * 
	 * @param view
	 */
	public void startUnidirectionalOneToOneTest(final View view) {
		try {
			startActivity(new Intent(this, OneToOneActivity.class));
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

}
