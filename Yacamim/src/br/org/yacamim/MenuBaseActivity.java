/**
 * MenuBaseActivity.java
 *
 * Copyright 2011 yacamim.org.br
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