/**
 * MenuBaseActivity.java
 *
 * Copyright 2011 yacamim.org.br
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.org.yacamim;

import android.util.Log;
import br.org.yacamim.persistencia.SessionDBAdapter;

/**
 * 
 * Class MenuBaseActivity TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public abstract class MenuBaseActivity extends BaseActivity {
	
	
	/**
	 * 
	 */
	public MenuBaseActivity() {
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
		} catch (Exception _e) {
			Log.e("MenuBaseActivity.logoff", _e.getMessage());
		}
	}

}