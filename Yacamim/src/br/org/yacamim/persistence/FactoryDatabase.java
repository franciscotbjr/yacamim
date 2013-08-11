/**
 * FactoryDatabase.java
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
package br.org.yacamim.persistence;

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
	 * @param dbHelper
	 * @return
	 */
	public SQLiteDatabase getDatabase(DefaultDBHelper dbHelper) {
		if (database == null) {
			database = dbHelper.getWritableDatabase();
		}
		return database;
	}

	/**
	 * Set <tt>null</tt> to <tt>database</tt> only when it is not in tha same transaction.
	 */
	public void close(DefaultDBHelper dbHelper) {
		if (database != null) {
			if (!database.inTransaction()) {
				if(database.isOpen()) {
					database.close();
				}
				database = null;
				dbHelper.close();
			}
		} else {
			dbHelper.close();
		}
	}
}