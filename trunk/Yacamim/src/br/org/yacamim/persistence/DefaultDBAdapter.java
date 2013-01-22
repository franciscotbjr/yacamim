/**
 * DefaultDBAdapter.java
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
import br.org.yacamim.util.YUtilReflection;

/**
 *
 * Class DefaultDBAdapter TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class DefaultDBAdapter<E> {

	private SQLiteDatabase database;
	private DefaultDBHelper dbHelper;

	/**
	 *
	 * @param context
	 */
	public DefaultDBAdapter() {
		super();
		this.setDbHelper(new DefaultDBHelper(YacamimState.getInstance().getCurrentActivity().getApplicationContext()));
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
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			final String[] columns = this.getColumnNamesAsArray(this.getClass());
			if(columns != null && columns.length > 0) {
				final Cursor cursor = this.getDatabase().query(this.getTableName(), columns, null, null, null,
						null, null);
				if (cursor != null && cursor.moveToFirst()) {
					entities.add(build(cursor, this.getClass()));
					while(cursor.moveToNext()) {
						entities.add(build(cursor, this.getClass()));
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
	 * @param entity
	 * @return
	 */
	public boolean insert(final E entity) {
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			final ContentValues initialValues = createContentValues(entity);

			long newId = this.getDatabase().insert(this.getTableName(), null, initialValues);

			this.setId(entity, newId);

			return newId > -1;
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.insert", e.getMessage());
			return false;
		}
	}

	/**
	 * @param entity
	 * @return
	 */
	public boolean update(final E entity) {
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			final ContentValues updateValues = createContentValues(entity);
			updateValues.remove(getIdColumnName(this.getClass()));
			return this.getDatabase().update(this.getTableName(), updateValues, this.getIdColumnName(this.getClass()) + "=" + this.getId(entity), null) > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	public boolean delete(final E entity) {
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			return this.getDatabase().delete(this.getTableName(), this.getIdColumnName(this.getClass()) + "=" + this.getId(entity), null) > 0;
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.delete", e.getMessage());
			return false;
		}
	}

	/**
	 *
	 * @return
	 */
	public boolean deleteAll() {
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			return this.getDatabase().delete(this.getTableName(), null, null) > 0;
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.deleteAll", e.getMessage());
			return false;
		}
	}

	/**
	 *
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public E getByID(final long id) throws SQLException {
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			return this.getByID(id, this.getClass());
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getByID", e.getMessage());
			return null;
		}

	}

	/**
	 *
	 * @param id
	 * @param type
	 * @return
	 * @throws SQLException
	 */

	E getByID(final long id, Class<?> type) throws SQLException {
		E resultado = null;
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			Table table = type.getAnnotation(Table.class);
			final String[] colunas = this.getColumnNamesAsArray(type);
			if(colunas != null && colunas.length > 0) {
				final Cursor cursor = this.getDatabase().query(table.name(), colunas, this.getIdColumnName(type) + " = ?", new String[]{String.valueOf(id)}, null,
						null, null);
				if (cursor != null && cursor.moveToFirst()) {
					resultado = build(cursor, type);
				}
				cursor.close();
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getByID", e.getMessage());
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
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			StringBuilder sql = new StringBuilder();
			sql.append("select max(" + this.getIdColumnName(this.getClass()) + ") ");
			sql.append("from " + this.getTableName());

			final Cursor cursor = getDatabase().rawQuery(sql.toString(), null);
			if (cursor != null && cursor.moveToFirst()) {
				retorno = cursor.getInt(0) + 1;
			}
			cursor.close();
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getNextId", e.getMessage());
		}
		return retorno;
	}

	/**
	 *
	 * @return
	 */
	public String getTableName() {
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			return YUtilReflection.getGenericSuperclassClass(this.getClass()).getAnnotation(Table.class).name();
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getTableName", e.getMessage());
		}
		return null;
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	protected String getIdColumnName(Class<?> type) {
		try {
			final Method getMethod = getIdGetMethod(type);
			if(getMethod != null) {
				final Column column = getMethod.getAnnotation(Column.class);
				if(column != null) {
					return column.name();
				}
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getIdColumnName", e.getMessage());
		}
		return null;
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	protected Method getIdGetMethod(Class<?> type) {
		Method idMethod = null;
		try {
			final Class<?> classe = getGenericSuperclassClass(type);

			final List<Method> getMethods = YUtilReflection.getGetMethodList(classe);

			for(final Method getMethod : getMethods) {
				Id pk = getMethod.getAnnotation(Id.class);
				if(pk != null) {
					idMethod = getMethod;
					break;
				}
				if(getMethod.getName().equals("getId")) {
					idMethod = getMethod;
				}
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getPkGetMethod", e.getMessage());
		}
		return idMethod;
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	protected Method getIdSetMethod(Class<?> type) {
		try {
			final Method getMethod = getIdGetMethod(type);
			if(getMethod != null) {
				return YUtilReflection.getSetMethod(
						YUtilReflection.getPropertyName(getMethod),
						type);
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getPkSetMethod", e.getMessage());
		}
		return null;
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	protected String getIdPropertyName(Class<?> type) {
		try {
			final Method getMethod = getIdGetMethod(type);
			if(getMethod != null) {
				return YUtilReflection.getPropertyName(getMethod);
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getPkPropertyName", e.getMessage());
		}
		return null;
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	protected List<String> getColumnNames(Class<?> type) {
		final List<String> columns = new ArrayList<String>();
		try {
			final Class<?> genericClass = this.getGenericSuperclassClass(type);

			final List<Method> getMethods = YUtilReflection.getGetMethodList(genericClass);
			for(final Method getMethod : getMethods) {
				final Column column = getMethod.getAnnotation(Column.class);
				if(column != null) {
					columns.add(column.name());
				}
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getColumnNames", e.getMessage());
		}
		return columns;
	}

	/**
	 * @param type
	 * @return
	 */
	protected Class<?> getGenericSuperclassClass(Class<?> type) {
		return YUtilReflection.getGenericSuperclassClass(type);
	}

	/**
	 *
	 * @return
	 */
	protected Class<?> getGenericSuperclassClass() {
		return YUtilReflection.getGenericSuperclassClass(this.getClass());
	}

	/**
	 *
	 * @return
	 */
	protected boolean isEntity() {
		return YUtilReflection.getGenericSuperclassClass(this.getClass()).getAnnotation(Table.class) == null;
	}

	/**
	 * @param entity
	 * @param newId
	 */
	protected void setId(final E entity, long newId) {
		YUtilReflection.setValueToProperty(getIdPropertyName(this.getGenericSuperclassClass()), Long.valueOf(newId), entity);
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	protected long getId(final E entity) {
		return (Long)YUtilReflection.getPropertyValue(getIdPropertyName(this.getGenericSuperclassClass()), entity);
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	protected String[] getColumnNamesAsArray(Class<?> type) {
		String[] columns = null;
		try {
			final List<String> columnList = getColumnNames(type);
			columns = new String[columnList.size()];
			for(int i = 0; i < columnList.size(); i++) {
				columns[i] = columnList.get(i);
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getColumnNamesAsArray", e.getMessage());
		}
		return columns;
	}

	/**
	 *
	 * @param cursor
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected E build(final Cursor cursor, final Class<?> type) {
		E object = null;
		try {
			final Class<?> genericClass = getGenericSuperclassClass(type);
			object = (E) genericClass.newInstance();

			final List<Method> getMethods = YUtilReflection.getGetMethodList(genericClass);

			for(final Method getMethod : getMethods) {
				final Column column = getMethod.getAnnotation(Column.class);
				if(column != null) {
					final String columnName = column.name();
					if(!DataAdapterHelper.treatRawData(cursor, object, getMethod, columnName)) {
						if(DataAdapterHelper.isOneToOneOwner(getMethod)) {
							DataAdapterHelper.treatOneToOne(cursor, object, getMethod, columnName);
						} else if (DataAdapterHelper.isManyToOne(getMethod)) {

						} else if (DataAdapterHelper.isManyToMany(getMethod)) {

						}
					}
				}
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.build", e.getMessage());
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
			final Class<?> genericClass = YUtilReflection.getGenericSuperclassClass(this.getClass());

			final List<Method> getMethods = YUtilReflection.getGetMethodList(genericClass);

			for(final Method getMethod : getMethods) {
				final Column column = getMethod.getAnnotation(Column.class);
				if(column != null) {
					if(getMethod.getReturnType().equals(String.class)) {
						values.put(column.name(), (String)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Byte.class) || getMethod.getReturnType().equals(byte.class)) {
						values.put(column.name(), (Byte)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Short.class) || getMethod.getReturnType().equals(short.class)) {
						values.put(column.name(), (Short)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Integer.class) || getMethod.getReturnType().equals(int.class)) {
						values.put(column.name(), (Integer)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Float.class) || getMethod.getReturnType().equals(float.class)) {
						values.put(column.name(), (Float)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Double.class) || getMethod.getReturnType().equals(double.class)) {
						values.put(column.name(), (Double)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Long.class) || getMethod.getReturnType().equals(long.class)) {
						Long valueLong = (Long)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade);
						// Quando for uma coluna de ID só adiciona se não for zero.
						if (column.name().indexOf("id") >= 0) {
							if (valueLong > 0) {
								values.put(column.name(), valueLong);
							}
						} else {
							values.put(column.name(), valueLong);
						}
					} else if (getMethod.getReturnType().equals(Date.class)) {
						final Date date = (Date)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade);
						if(date != null) {
							values.put(column.name(), date.getTime());
						}
					}
				}
			}

		} catch (Exception e) {
			Log.e("DefaultDBAdapter.createContentValues", e.getMessage());
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
			sql.append(" where ");
			whereAdded = true;
		} else {
			sql.append(" and ");
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
			sql.append(" and ");
		}
		return whereAdded;
	}
}