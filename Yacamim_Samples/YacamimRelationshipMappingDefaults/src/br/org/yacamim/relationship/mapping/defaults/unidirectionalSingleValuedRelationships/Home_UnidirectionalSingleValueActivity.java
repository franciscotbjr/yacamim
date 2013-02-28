/**
 * Home_UnidirectionalSingleValue.java
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
package br.org.yacamim.relationship.mapping.defaults.unidirectionalSingleValuedRelationships;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.relationship.mapping.defaults.R;

/**
 * Classe Home_UnidirectionalSingleValue TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class Home_UnidirectionalSingleValueActivity extends YBaseActivity {
	
	private static final String TAG = Home_UnidirectionalSingleValueActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_unidirectional_single_value);
	}

	public void startListEmployee1(final View view) {
		try {
			startActivity(new Intent(this, OneToOneActivity.class));
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	public void startListEmployee2(final View view) {
		try {
			startActivity(new Intent(this, ManyToOneActivity.class));
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	public void startInsertEmployee(final View view) {
		try {
			startActivity(new Intent(this, InsertEmployeeActivity.class));
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bidirectional_many_to_one,
				menu);
		return true;
	}

}
