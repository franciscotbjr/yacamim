/**
 * DefaultDBHelper.java
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

import java.util.Collection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.org.yacamim.entity.DbScript;
import br.org.yacamim.xml.YLoader;

/**
 *
 * Class DefaultDBHelper TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class DefaultDBHelper extends SQLiteOpenHelper {

	private static String dbName = null;
	private static int dbVersion = -1;

	private Context context;
	/**
	 *
	 * @param _context
	 */
	public DefaultDBHelper(final Context _context) {
		super(_context, DefaultDBHelper.getDbName(), null, DefaultDBHelper.getDbVersion());
		this.context = _context;
	}

	/**
	 *
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(final SQLiteDatabase _database) {
		try {
			final DbScript dbScriptCreateUpdate = YLoader.loadDBScript(this.context);
			if(dbScriptCreateUpdate != null) {
				// Cria tabelas
				if(dbScriptCreateUpdate.getCreateTables().size() > 0) {
					final Collection<String> scriptsCreate = dbScriptCreateUpdate.getCreateTables();
					for(final String scriptCreateTable : scriptsCreate) {
						try {
							_database.execSQL(scriptCreateTable);
						} catch (Exception _e) {
							Log.e("DefaultDBHelper.onCreate", _e.getMessage());
						}
					}
				}
				// Inserts (carga de dados)
				if(dbScriptCreateUpdate.getInserts().size() > 0) {
					final Collection<String> scriptsInsert = dbScriptCreateUpdate.getInserts();
					for(final String scriptInserts : scriptsInsert) {
						try {
							_database.execSQL(scriptInserts);
						} catch (Exception _e) {
							Log.e("DefaultDBHelper.onCreate", _e.getMessage());
						}
					}
				}
			}
		} catch (Exception _e) {
			Log.e("DefaultDBHelper.onCreate", _e.getMessage());
		}
	}

	/**

	/**
	 *
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@SuppressWarnings("unused")
	@Override
	public void onUpgrade(final SQLiteDatabase _database, final int _oldVersion, final int _newVersion) {
		if(false) { // FIXME alterar quando houver update
			final DbScript dbScriptCreateUpdate = YLoader.loadDBScript(this.context);
			if(dbScriptCreateUpdate != null) {
				// Create tables
				if(dbScriptCreateUpdate.getAlterTables().size() > 0) {
					for(final String scriptCreateTable : dbScriptCreateUpdate.getCreateTables()) {
						_database.execSQL(scriptCreateTable);
					}
				}
				// Inserts (carga de dados)
				if(dbScriptCreateUpdate.getUpdates().size() > 0) {
					for(final String scriptInserts : dbScriptCreateUpdate.getInserts()) {
						_database.execSQL(scriptInserts);
					}
				}
			}
		}
	}

	/**
	 * @return the dbName
	 */
	protected static String getDbName() {
		return dbName;
	}

	/**
	 * @param dbName the dbName to set
	 */
	protected static void setDbName(String dbName) {
		DefaultDBHelper.dbName = dbName;
	}

	/**
	 * @return the dbVersion
	 */
	protected static int getDbVersion() {
		return dbVersion;
	}

	/**
	 * @param dbVersion the dbVersion to set
	 */
	protected static void setDbVersion(int dbVersion) {
		DefaultDBHelper.dbVersion = dbVersion;
	}

}