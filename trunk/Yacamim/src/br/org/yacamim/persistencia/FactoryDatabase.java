/**
 * FactoryDatabase.java
 *
 * Copyright 2012 yacamim.org.br
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