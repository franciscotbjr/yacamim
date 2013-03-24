/**
 * YMenuBaseActivity.java
 *
 * Copyright 2012 yacamim.org.br
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
package br.org.yacamim;

import android.util.Log;
import br.org.yacamim.persistence.SessionDBAdapter;

/**
 * 
 * Class YMenuBaseActivity TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public abstract class YMenuBaseActivity extends YBaseActivity {
	
	
	/**
	 * 
	 */
	public YMenuBaseActivity() {
		super();
	}

	/**
	 * 
	 */
	protected void logoff() {
		try {
			final SessionDBAdapter sessionDBAdapter = new SessionDBAdapter(this);
			sessionDBAdapter.open();
			
			sessionDBAdapter.delete(YacamimState.getInstance().getSessao());
			
			sessionDBAdapter.close();
			
			YacamimState.getInstance().setLogoff(true);
			
			this.finish();
		} catch (Exception e) {
			Log.e("YMenuBaseActivity.logoff", e.getMessage());
		}
	}

}