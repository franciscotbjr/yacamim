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
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.org.yacamim.YacamimConfig;
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

	private Context context;
	
	/**
	 *
	 * @param context
	 */
	public DefaultDBHelper(final Context context) {
		super(context, DefaultDBHelper.getDbName(), null, DefaultDBHelper.getDbVersion());
		this.context = context;
	}

	/**
	 *
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(final SQLiteDatabase database) {
		try {
			if(YacamimConfig.getInstance().usesDBScript()) {
				handleDBScript(database);
			} else if (YacamimConfig.getInstance().isConfigItemsLoaded()) {
				YSQLBuilder ysqlBuilder = new YSQLBuilder();
				List<StringBuilder> createScripts = ysqlBuilder.buildCreateScript(YacamimConfig.getInstance().getEntities());
				if(createScripts != null) {
					for(final StringBuilder createScript : createScripts) {
						database.execSQL(createScript.toString());
					}
				}
			}
			
			if(YacamimConfig.getInstance().usesDBLoad()) {
				if(DbLoadList.getInstance().getInserts() != null) {
					final List<DbLoad> inserts = DbLoadList.getInstance().getInserts();
					for(final DbLoad dbLoad : inserts) {
						final List<String> rows = dbLoad.getRows();
						for(final String row : rows) {
							database.execSQL(YSQLBuilder.convertToInsert(dbLoad, row).toString());
						}
					}
				}
				if(DbLoadList.getInstance().getUpdates() != null) {
					
				}
				if(DbLoadList.getInstance().getDeletes() != null) {
					
				}
			}
		} catch (Exception e) {
			Log.e("DefaultDBHelper.onCreate", e.getMessage());
		}
	}

	/**
	 * 
	 * @param database
	 */
	private void handleDBScript(final SQLiteDatabase database) {
		try {
			final DbScript dbScriptCreateUpdate = YLoader.loadDBScript(this.context);
			// Cria tabelas
			if(dbScriptCreateUpdate.getCreateTables().size() > 0) {
				final Collection<String> scriptsCreate = dbScriptCreateUpdate.getCreateTables();
				for(final String scriptCreateTable : scriptsCreate) {
					try {
						database.execSQL(scriptCreateTable);
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
						database.execSQL(scriptInserts);
					} catch (Exception _e) {
						Log.e("DefaultDBHelper.onCreate", _e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			Log.e("DefaultDBHelper.handleDBScript", e.getMessage());
		}
	}

	/**

	/**
	 *
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@SuppressWarnings("unused")
	@Override
	public void onUpgrade(final SQLiteDatabase database, final int _oldVersion, final int _newVersion) {
		if(false) { // FIXME 
			final DbScript dbScriptCreateUpdate = YLoader.loadDBScript(this.context);
			if(dbScriptCreateUpdate != null) {
				// Create tables
				if(dbScriptCreateUpdate.getAlterTables().size() > 0) {
					for(final String scriptCreateTable : dbScriptCreateUpdate.getCreateTables()) {
						database.execSQL(scriptCreateTable);
					}
				}
				// Inserts (carga de dados)
				if(dbScriptCreateUpdate.getUpdates().size() > 0) {
					for(final String scriptInserts : dbScriptCreateUpdate.getInserts()) {
						database.execSQL(scriptInserts);
					}
				}
			}
		}
	}

	/**
	 * @return the dbName
	 */
	protected static String getDbName() {
		return YacamimConfig.getInstance().getDbName();
	}

	/**
	 * @return the dbVersion
	 */
	protected static int getDbVersion() {
		return YacamimConfig.getInstance().getDbVersion();
	}

}