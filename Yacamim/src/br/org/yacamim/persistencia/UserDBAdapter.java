/**
 * UserDBAdapter.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.persistencia;

import android.content.Context;
import br.org.yacamim.entidade.User;

/**
 * Adapter de banco de dados para a entidade User.
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class UserDBAdapter extends DefaultDBAdapter<User> {
	/**
	 * Constutor da classe.
	 * 
	 * @param _context
	 */
	public UserDBAdapter(Context _context) {
		super();
		super.setDbHelper(new DefaultDBHelper(_context));
	}

}