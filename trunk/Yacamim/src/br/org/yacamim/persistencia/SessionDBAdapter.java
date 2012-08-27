/**
 * SessionDBAdapter.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.persistencia;

import android.content.Context;
import br.org.yacamim.entidade.Session;

/**
 * 
 * Class SessionDBAdapter TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class SessionDBAdapter extends DefaultDBAdapter<Session> {
	
	/**
	 * 
	 * @param context
	 */
	public SessionDBAdapter(Context context) {
		super();
		super.setDbHelper(new DefaultDBHelper(context));
	}

}
