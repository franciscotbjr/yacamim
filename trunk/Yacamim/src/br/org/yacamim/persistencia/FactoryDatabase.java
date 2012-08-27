/**
 * FactoryDatabase.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.persistencia;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class FactoryDatabase {
	
	private static FactoryDatabase factoryDatabase = null;
	private static SQLiteDatabase database;
	
	/**
	 * Construtor da classe.
	 */
	private FactoryDatabase() {
	}

	/**
	 * Retorna a instância da classe.
	 * @return
	 */
	public static FactoryDatabase getInstance() {
		if (factoryDatabase == null) {
			factoryDatabase = new FactoryDatabase();
		}
		return factoryDatabase;
	}

	/**
	 * Retorna a instância de database.
	 * 
	 * @param _dbHelper
	 * @return
	 */
	public SQLiteDatabase getDatabase(DefaultDBHelper _dbHelper) {
		if (database == null) {
			database = _dbHelper.getWritableDatabase();
		}

		return database;
	}
	
	/**
	 * Set <tt>null</tt> to <tt>database</tt> only when it is not in tha same transaction.
	 */
	public void close(DefaultDBHelper _dbHelper) {
		if (database != null) {
			if (!database.inTransaction()) {
				database = null;
				_dbHelper.close();
			}
		} else {
			_dbHelper.close();
		}
	}
}