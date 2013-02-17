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

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.org.yacamim.YRawData;
import br.org.yacamim.YacamimConfig;
import br.org.yacamim.YacamimState;
import br.org.yacamim.dex.YInvocationHandlerProxy;
import br.org.yacamim.util.YUtilReflection;

import com.google.dexmaker.stock.ProxyBuilder;

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
	private Class<E> genericType;

	/**
	 *
	 * @param context
	 */
	public DefaultDBAdapter(final Class<E> genericType) {
		super();
		this.setDbHelper(new DefaultDBHelper(YacamimState.getInstance().getCurrentActivity().getApplicationContext()));
		this.genericType = genericType;
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
	 * 
	 * @param entity
	 * @return
	 */
	public boolean update(final E entity) {
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			final ContentValues updateValues = createContentValues(entity);
			updateValues.remove(getIdColumnName());
			return this.getDatabase().update(this.getTableName(), updateValues, this.getIdColumnName() + "=" + this.getId(entity), null) > 0;
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
			return this.getDatabase().delete(this.getTableName(), this.getIdColumnName() + "=" + this.getId(entity), null) > 0;
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
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public E getByID(final long id) throws SQLException {
		E result = null;
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			final String[] columns = this.getColumnNamesAsArray();
			if(columns != null && columns.length > 0) {
				final Cursor cursor = this.getDatabase().query(
						this.getTableName(), 
						columns, 
						this.getIdColumnName() + " = ?", 
						new String[]{String.valueOf(id)}, 
						null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					result = build(cursor);
				}
				cursor.close();
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getByID", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<E> select(final E entity, final String... attributes) throws SQLException {
		final List<E> entities = new ArrayList<E>();
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			final List<Method> attColumnGetMethods = YUtilPersistence.getGetColumnMethodList(this.getGenericClass(), attributes);
			final String[] columns = this.getColumnNamesAsArray();
			if(columns != null && columns.length > 0) {
				final Cursor cursor = this.getDatabase().query(
						this.getTableName(), 
						columns, 
						this.buildSelecion(attColumnGetMethods), 
						this.buildSelecionArgs(attColumnGetMethods, entity), 
						null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					entities.add(build(cursor));
					while(cursor.moveToNext()) {
						entities.add(build(cursor));
					}
				}
				cursor.close();
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.select", e.getMessage());
		}
		return entities;
	}
	
	/**
	 * 
	 * @param id
	 * @param targetMethods
	 * @return
	 */
	YRawData getRawDataById(final Long id, final Method[] targetMethods) {
		YRawData yRawData = null;
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			final List<Method> filteredMethods = this.filterMethodsForPersistenceAccess(targetMethods);
			String[] columns = new String[filteredMethods.size()];
			for(int i = 0; i < filteredMethods.size(); i++) {
				final String columnName = YUtilPersistence.builColumnName(filteredMethods.get(i));
				if(columnName != null) {
					columns[i] = columnName;
				}
			}
			if(columns != null && columns.length > 0) {
				final Cursor cursor = this.getDatabase().query(
						this.getTableName(), 
						columns, 
						this.getIdColumnName() + " = ?", 
						new String[]{String.valueOf(id)}, 
						null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					for(int i = 0; i < targetMethods.length; i++) {
						yRawData = DataAdapterHelper.getYRawData(cursor, targetMethods[i], columns[i], yRawData);
					}
				}
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getRawDataById", e.getMessage());
		}
		return yRawData;
	}
	
	/**
	 * 
	 * @param parentClass
	 * @param parenId
	 * @return
	 * @throws SQLException
	 */
	List<YRawData> selectRawData(final Class<?> parentClass, final long parenId) throws SQLException {
		final List<YRawData> entities = new ArrayList<YRawData>();
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			final List<Method> attColumnGetMethods = YUtilPersistence.getGetColumnMethodList(this.getGenericClass());
			final String[] columns = this.getColumnNamesAsArray();
			final Method parentGetIdMethod = YUtilPersistence.getGetIdMethod(parentClass);
			if(columns != null && columns.length > 0) {
				final Cursor cursor = this.getDatabase().query(
						this.getTableName(), 
						columns, 
						YUtilPersistence.buildFkColumnName(parentClass, parentGetIdMethod.getAnnotation(Column.class), parentGetIdMethod) + " = ? ", 
						new String[]{parenId+""}, 
						null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					YRawData yRawData = new YRawDataPersistenceImpl();
					for(int i = 0; i < attColumnGetMethods.size(); i++) {
						yRawData = DataAdapterHelper.getYRawData(cursor, attColumnGetMethods.get(i), columns[i], yRawData);
					}
					entities.add(yRawData);
					while(cursor.moveToNext()) {
						yRawData = new YRawDataPersistenceImpl();
						for(int i = 0; i < attColumnGetMethods.size(); i++) {
							yRawData = DataAdapterHelper.getYRawData(cursor, attColumnGetMethods.get(i), columns[i], yRawData);
						}
						entities.add(yRawData);
					}
				}
				cursor.close();
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.selectRawData", e.getMessage());
		}
		return entities;
	}
	
	/**
	 * 
	 * @param attColumnGetMethods
	 * @return
	 */
	private String buildSelecion(final List<Method> attColumnGetMethods) {
		final StringBuilder builder = new StringBuilder();
		
		int count = 0;
		for(final Method getMethod : attColumnGetMethods) {
			builder.append(YUtilPersistence.builColumnName(getMethod) + " = ? ");
			if((count + 1) < attColumnGetMethods.size()) {
				builder.append(", ");
			}
			count++;
		}
		
		return builder.toString();
	}

	/**
	 * 
	 * @param attColumnGetMethods
	 * @param entity
	 * @return
	 */
	private String[] buildSelecionArgs(final List<Method> attColumnGetMethods, final E entity) {
		final String[] selecionArgs = new String[attColumnGetMethods.size()];
		for(int i = 0; i < attColumnGetMethods.size(); i++) {
			selecionArgs[i] = YUtilReflection.invokeMethodToString(attColumnGetMethods.get(i), entity, YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
		}
		return selecionArgs;
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
			sql.append("select max(" + this.getIdColumnName() + ") ");
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
			return YUtilPersistence.getTableName(this.getGenericClass());
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
	protected String getIdColumnName() {
		String idColumnName = null;
		try {
			final Method getMethod = YUtilPersistence.getGetIdMethod(this.getGenericClass());
			
			idColumnName = YUtilPersistence.getColumnName(getMethod.getAnnotation(Column.class), getMethod);
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getIdColumnName", e.getMessage());
		}
		return idColumnName;
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	protected Method getIdSetMethod(Class<?> type) {
		try {
			final Method getMethod = YUtilPersistence.getGetIdMethod(type);
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
			final Method getMethod = YUtilPersistence.getGetIdMethod(type);
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
	protected List<String> getColumnNames() {
		final List<String> columns = new ArrayList<String>();
		try {
			final List<Method> getMethods = YUtilReflection.getGetMethodList(this.getGenericClass());
			final Method getIDMethod = YUtilPersistence.getGetIdMethod(getMethods);
			columns.add(YUtilPersistence.getColumnName(getIDMethod.getAnnotation(Column.class), getIDMethod));
			for(final Method getMethod : getMethods) {
				final Column column = getMethod.getAnnotation(Column.class);
				if(getMethod.equals(getIDMethod)
						|| column == null) {
					continue;
				}
				
				columns.add(YUtilPersistence.builColumnName(getMethod));
				
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getColumnNames", e.getMessage());
		}
		return columns;
	}

	/**
	 *
	 * @return
	 */
	protected Class<E> getGenericClass() {
		return this.genericType;
	}

	/**
	 *
	 * @return
	 */
	protected boolean isEntity() {
		return this.getGenericClass().getAnnotation(Entity.class) != null;
	}

	/**
	 * @param entity
	 * @param newId
	 */
	protected void setId(final E entity, long newId) {
		YUtilReflection.setValueToProperty(getIdPropertyName(this.getGenericClass()), Long.valueOf(newId), entity);
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	protected long getId(final E entity) {
		return (Long)YUtilReflection.getPropertyValue(getIdPropertyName(this.getGenericClass()), entity);
	}

	/**
	 *
	 * @param type
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
	protected E build(final Cursor cursor) {
		E object = null;
		try {
			
			object = this.buildNewInstance();

			final List<Method> getMethods = YUtilReflection.getGetMethodList(this.getGenericClass());

			for(final Method getMethod : getMethods) {
				final Column column = getMethod.getAnnotation(Column.class);
				if(column != null
						|| (YUtilPersistence.isId(getMethod))) {
					final String columnName = YUtilPersistence.getColumnName(column, getMethod);
					if(!DataAdapterHelper.treatRawData(cursor, object, getMethod, columnName)
							&& !YacamimConfig.getInstance().usesLazyProxy()) {
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
	 * @return
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private E buildNewInstance() throws IOException, InstantiationException, IllegalAccessException {
		E object;
		if(YacamimConfig.getInstance().usesLazyProxy()) {
			if(!YInvocationHandlerProxy.getInstance().contains(this.getGenericClass())) {
				YInvocationHandlerProxy.getInstance().add(this.getGenericClass(), new YPersistenceInvocationHandler(new YGetMethodFilter()));
			}
			
			object = ProxyBuilder.forClass(this.getGenericClass())
					.handler(YInvocationHandlerProxy.getInstance().get(this.getGenericClass()))
					.build();
		} else {
			object = (E) this.getGenericClass().newInstance();
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
			final List<Method> getMethods = YUtilReflection.getGetMethodList(this.getGenericClass());

			for(final Method getMethod : getMethods) {
				final Column column = getMethod.getAnnotation(Column.class);
				if(column != null
						|| YUtilPersistence.isId(getMethod)) {
					final String columnName = YUtilPersistence.getColumnName(column, getMethod);
					if(getMethod.getReturnType().equals(String.class)) {
						values.put(columnName, (String)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Byte.class) || getMethod.getReturnType().equals(byte.class)) {
						values.put(columnName, (Byte)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Short.class) || getMethod.getReturnType().equals(short.class)) {
						values.put(columnName, (Short)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Integer.class) || getMethod.getReturnType().equals(int.class)) {
						values.put(columnName, (Integer)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Float.class) || getMethod.getReturnType().equals(float.class)) {
						values.put(columnName, (Float)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Double.class) || getMethod.getReturnType().equals(double.class)) {
						values.put(columnName, (Double)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
					} else if (getMethod.getReturnType().equals(Long.class) || getMethod.getReturnType().equals(long.class)) {
						Long valueLong = (Long)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade);
						// When a column is @Id, then it will only be added if its value is greater than zero
						if (YUtilPersistence.isId(getMethod)) {
							if(valueLong > 0) {
								values.put(columnName, valueLong);
							}
						} else {
							values.put(columnName, valueLong);
						}
					} else if (getMethod.getReturnType().equals(Date.class)) {
						final Date date = (Date)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade);
						if(date != null) {
							values.put(columnName, date.getTime());
						}
					} else {
						final Object object = YUtilReflection.invokeMethodWithoutParams(getMethod, entidade);
						final Class<?> returnType = object.getClass();
						if(object != null && YUtilPersistence.isEntity(returnType)) {
							final Method getFkMethod = YUtilPersistence.getGetIdMethod(returnType);
							if(getFkMethod != null) {
								final Long idFk = (Long)YUtilReflection.invokeMethodWithoutParams(getFkMethod, object);
								final String fkColumnName = YUtilPersistence.buildFkColumnName(returnType, column, getFkMethod);
								values.put(fkColumnName, idFk);
							}
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

	/**
	 * 
	 * @param targetMethods
	 * @return
	 */
	private List<Method> filterMethodsForPersistenceAccess(final Method[] targetMethods) {
		final List<Method> filteredMethods = new ArrayList<Method>();
		for(final Method method : targetMethods) {
			if(method.getAnnotation(Column.class) != null) {
				filteredMethods.add(method);
			}
		}
		return filteredMethods;
	}
}