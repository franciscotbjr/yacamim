/**
 * DefaultDBAdapter.java
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
package br.org.yacamim.persistence;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.org.yacamim.YacamimState;
import br.org.yacamim.entity.BaseEntity;
import br.org.yacamim.util.UtilDate;
import br.org.yacamim.util.UtilReflection;

/**
 * 
 * Class DefaultDBAdapter TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class DefaultDBAdapter<E extends BaseEntity> {
	
	private SQLiteDatabase database;
	private DefaultDBHelper dbHelper;
	
	/**
	 * 
	 * @param context
	 */
	public DefaultDBAdapter() {
		super();
		this.setDbHelper(new DefaultDBHelper(YacamimState.getInstance().getCurrentActivity()));
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DefaultDBAdapter<E> open() throws SQLException {
		this.database = FactoryDatabase.getInstance().getDatabase(getDbHelper());
		return this;
	}
	
	/**
	 * 
	 */
	public void beginTransaction() {
		if(this.database != null && this.database.isOpen() && !this.database.inTransaction()) {
			this.database.beginTransaction();
		}
	}
	
	/**
	 * 
	 */
	public void endTransaction(boolean _statusTransaction) {
		if(this.database != null && this.database.isOpen() && this.database.inTransaction()) {
			if (_statusTransaction) {
				this.database.setTransactionSuccessful();
			}
			this.database.endTransaction();
		}
	}
	
	/**
	 * 
	 */
	public boolean inTransaction() {
		if(this.database != null && this.database.isOpen()) {
			return this.database.inTransaction();
		}
		return false;
	}
	
	
	/**
	 * @return the dbHelper
	 */
	protected DefaultDBHelper getDbHelper() {
		return dbHelper;
	}

	/**
	 * @param dbHelper the dbHelper to set
	 */
	protected void setDbHelper(DefaultDBHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	/**
	 * 
	 */
	public void close() {
		FactoryDatabase.getInstance().close(getDbHelper());
	}

	/**
	 * 
	 * @param _aeroporto
	 * @return
	 */
	public void clearCache() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public SQLiteDatabase getDatabase() {
		return database;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<E> list() {
		final List<E> entities = new ArrayList<E>();
		try {
			final String[] columns = this.getColumnNamesAsArray();
			if(columns != null && columns.length > 0) {
				final Cursor cursor = this.getDatabase().query(this.getTableName(), columns, null, null, null,
						null, null);
				if (cursor != null && cursor.moveToFirst()) {
					entities.add(build(cursor));
					while(cursor.moveToNext()) {
						entities.add(build(cursor));
					}
				}
				cursor.close();
			}
		} catch (Exception _e) {
			Log.e("DefaultDBAdapter.list", _e.getMessage());
		}
		return entities;
	}

	/**
	 * 
	 * @param _entity
	 * @return
	 */
	public boolean insert(final E _entity) {
		try {
			final ContentValues initialValues = createContentValues(_entity);
			
			_entity.setId(this.getDatabase().insert(this.getTableName(), null, initialValues));
			
			return _entity.getId() > -1;
		} catch (Exception _e) {
			Log.e("DefaultDBAdapter.insert", _e.getMessage());
			return false;
		}
	}

	/**
	 * @param _entity
	 * @return
	 */
	public boolean update(final E _entity) {
		final ContentValues updateValues = createContentValues(_entity);
		updateValues.remove(getIdColumnName());
		try {
			return this.getDatabase().update(this.getTableName(), updateValues, this.getIdColumnName() + "=" + _entity.getId(), null) > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param _entity
	 * @return
	 */
	public boolean delete(final E _entity) {
		try {
			return this.getDatabase().delete(this.getTableName(), this.getIdColumnName() + "=" + _entity.getId(), null) > 0;
		} catch (Exception _e) {
			Log.e("DefaultDBAdapter.delete", _e.getMessage());
			return false;
		}
	}	
	
	/**
	 * 
	 * @return
	 */
	public boolean deleteAll() {
		try {
			return this.getDatabase().delete(this.getTableName(), null, null) > 0;
		} catch (Exception _e) {
			Log.e("DefaultDBAdapter.deleteAll", _e.getMessage());
			return false;
		}
	}
	
	/**
	 * 
	 * @param _id
	 * @return
	 * @throws SQLException
	 */
	public E getByID(final long _id) throws SQLException {
		E resultado = null;
		try {
			final String[] colunas = this.getColumnNamesAsArray();
			if(colunas != null && colunas.length > 0) {
				final Cursor cursor = this.getDatabase().query(this.getTableName(), colunas, this.getIdColumnName() + " = ?", new String[]{String.valueOf(_id)}, null,
						null, null);
				if (cursor != null && cursor.moveToFirst()) {
					resultado = build(cursor);
				}
				cursor.close();
			}
		} catch (Exception _e) {
			Log.e("DefaultDBAdapter.getByID", _e.getMessage());
		}
		return resultado;
	}

	/**
	 * Retorna o próximo id da tabela.
	 * 
	 * @return
	 */
	public int getNextId() {
		int retorno = 0;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select max(" + this.getIdColumnName() + ") ");
			sql.append("from " + this.getTableName());
			
			final Cursor cursor = getDatabase().rawQuery(sql.toString(), null);
			if (cursor != null && cursor.moveToFirst()) {
				retorno = cursor.getInt(0) + 1;
			}
			cursor.close();
		} catch (Exception _e) {
			Log.e("DefaultDBAdapter.getNextId", _e.getMessage());
		}
		return retorno;
	}

	/**
	 * 
	 * @return
	 */
	public String getTableName() {
		try {
			return UtilReflection.getGenericSuperclassClass(this.getClass()).getAnnotation(Table.class).name();
		} catch (Exception _e) {
			Log.e("DefaultDBAdapter.getTableName", _e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	protected String getIdColumnName() {
		try {
			final Class<?> classe = UtilReflection.getGenericSuperclassClass(this.getClass());
			
			final List<Method> getMethods = UtilReflection.getGetMethodList(classe);
			
			for(final Method getMethod : getMethods) {
				PK pk = getMethod.getAnnotation(PK.class);
				if(pk != null) {
					final Column column = getMethod.getAnnotation(Column.class);
					if(column != null) {
						return column.name();
					}
				}
			}
		} catch (Exception _e) {
			Log.e("DefaultDBAdapter.getIdColumnName", _e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	protected List<String> getColumnNames() {
		final List<String> columns = new ArrayList<String>();
		try {
			final Class<?> genericClass = UtilReflection.getGenericSuperclassClass(this.getClass());
			
			final List<Method> getMethods = UtilReflection.getGetMethodList(genericClass);
			for(final Method getMethod : getMethods) {
				final Column column = getMethod.getAnnotation(Column.class);
				if(column != null) {
					columns.add(column.name());
				}
			}
		} catch (Exception _e) {
			Log.e("DefaultDBAdapter.getColumnNames", _e.getMessage());
		}
		return columns;
	}
	
	/**
	 * 
	 * @return
	 */
	protected String[] getColumnNamesAsArray() {
		String[] columns = null;
		try {
			final List<String> columnList = getColumnNames();
			columns = new String[columnList.size()];
			for(int i = 0; i < columnList.size(); i++) {
				columns[i] = columnList.get(i);
			}
		} catch (Exception _e) {
			Log.e("DefaultDBAdapter.getColumnNamesAsArray", _e.getMessage());
		}
		return columns;
	}

	/**
	 * 
	 * @param _cursor
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected E build(final Cursor _cursor) {
		E object = null;
		try {
			final Class<?> genericClass = UtilReflection.getGenericSuperclassClass(this.getClass());
			object = (E) genericClass.newInstance();
			
			final List<Method> getMethods = UtilReflection.getGetMethodList(genericClass);
			
			for(final Method getMethod : getMethods) {
				final Column column = getMethod.getAnnotation(Column.class);
				if(!DataAdapterHelper.isTransiente(getMethod) && column != null) {
					final String columnName = column.name();
					if(!DataAdapterHelper.treatRawData(_cursor, object, getMethod, columnName)) {
						if(DataAdapterHelper.isOneToOne(getMethod)) {
							
						} else if (DataAdapterHelper.isManyToOne(getMethod)) {
							
						} else if (DataAdapterHelper.isManyToMany(getMethod)) {
							
						}
					}
				}
			}
		} catch (Exception _e) {
			Log.e("DefaultDBAdapter.build", _e.getMessage());
		}
		return object;
	}
	
	/**
	 * 
	 * @param entidade
	 * @return
	 */
	protected ContentValues createContentValues(final E entidade) {
		final ContentValues values = new ContentValues();
		try {
			final Class<?> genericClass = UtilReflection.getGenericSuperclassClass(this.getClass());
			
			final List<Method> getMethods = UtilReflection.getGetMethodList(genericClass);
			
			for(final Method getMethod : getMethods) {
				final Column column = getMethod.getAnnotation(Column.class);
				if(column != null) {
					if(getMethod.getReturnType().equals(String.class)) {
						values.put(column.name(), (String)UtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Integer.class) || getMethod.getReturnType().equals(int.class)) {
						values.put(column.name(), (Integer)UtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Double.class) || getMethod.getReturnType().equals(double.class)) {
						values.put(column.name(), (Double)UtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Long.class) || getMethod.getReturnType().equals(long.class)) {
						Long valueLong = (Long)UtilReflection.invokeMethodWithoutParams(getMethod, entidade);
						//Quando for uma coluna de id* só adiciona se não for zero. 
						if (column.name().indexOf("id") >= 0) {
							if (valueLong > 0) {
								values.put(column.name(), valueLong);
							}
						} else {
							values.put(column.name(), valueLong);
						}
					} else if (getMethod.getReturnType().equals(Date.class)) {
						final Date date = (Date)UtilReflection.invokeMethodWithoutParams(getMethod, entidade);
						if(date != null) {
							values.put(column.name(), UtilDate.getSimpleDateFormatDateTime().format(date));
						}
					}
				}
			}
			
		} catch (Exception _e) {
			Log.e("DefaultDBAdapter.createContentValues", _e.getMessage());
		}
		return values;
	}
	
	/**
	 * 
	 * @param whereAdded
	 * @param sql
	 * @return
	 */
	protected boolean configWhereOrAnd(boolean whereAdded, final StringBuilder sql) {
		if(!whereAdded) {
			sql.append("where ");
			whereAdded = true;
		} else {
			sql.append("and ");
		}
		return whereAdded;
	}
	
	/**
	 * 
	 * @param whereAdded
	 * @param sql
	 * @return
	 */
	protected boolean configAnd(boolean whereAdded, final StringBuilder sql) {
		if(!whereAdded) {
			whereAdded = true;
		} else {
			sql.append("and ");
		}
		return whereAdded;
	}
}