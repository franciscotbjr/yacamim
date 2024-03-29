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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.org.yacamim.YacamimConfig;
import br.org.yacamim.YacamimState;
import br.org.yacamim.dex.YInvocationHandlerProxy;
import br.org.yacamim.entity.YBaseEntity;
import br.org.yacamim.util.YUtilReflection;
import br.org.yacamim.util.YUtilString;

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
		this.setDbHelper(new DefaultDBHelper(YacamimState.getInstance().getCurrentContext()));
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
	 * @param sqLiteDatabase
	 */
	private final void setDatabase(SQLiteDatabase sqLiteDatabase) {
		this.database = sqLiteDatabase;
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
			final String[] columns = this.getColumnNamesAsArray(this.getGenericClass());
			if(columns != null && columns.length > 0) {
				final Cursor cursor = this.getDatabase().query(this.getTableName(), columns, 
						null, null, null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					entities.add(build(cursor));
					while(cursor.moveToNext()) {
						entities.add(build(cursor));
					}
				}
				cursor.close();
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.list", e.getMessage());
		}
		return entities;
	}
	
	/**
	 * 
	 * @param orderByProperties
	 * @return
	 */
	public List<E> list(final String... orderByProperties) {
		return list(true, orderByProperties);
	}
	
	/**
	 * 
	 * @param asc
	 * @param orderByProperties
	 * @return
	 */
	public List<E> list(final boolean asc, final String... orderByProperties) {
		final List<E> entities = new ArrayList<E>();
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			final String[] columns = this.getColumnNamesAsArray(this.getGenericClass());
			if(columns != null && columns.length > 0) {
				final Cursor cursor = this.getDatabase().query(this.getTableName(), columns, 
						null, null, null, null, 
						getOrderByColumns(this.getGenericClass(), orderByProperties, asc));
				if (cursor != null && cursor.moveToFirst()) {
					entities.add(build(cursor));
					while(cursor.moveToNext()) {
						entities.add(build(cursor));
					}
				}
				cursor.close();
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.list", e.getMessage());
		}
		return entities;
	}

	/**
	 * 
	 * @param clazz
	 * @param orderByProperties
	 * @param asc
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String getOrderByColumns(final Class clazz, final String[] orderByProperties, final boolean asc) {
		final StringBuilder stringBuilder = new StringBuilder();
		try {
			final List<String> properties = new ArrayList<String>();
			for(final String string : orderByProperties) {
				properties.add(string);
			}
			final List<String> columnsOrderBy = getColumnNames(clazz, properties);
			int count = 0;
			for(final String string : columnsOrderBy) {
				count++;
				stringBuilder.append(string);
				if(columnsOrderBy.size() >= (count + 1)) {
					stringBuilder.append(", ");
				}
			}
			if (asc) {
				stringBuilder.append(YUtilPersistence.SQL_WORD_ASC);
			} else {
				stringBuilder.append(YUtilPersistence.SQL_WORD_DESC);
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.getOrderByColumns", e.getMessage());
		}
		return stringBuilder.toString();
	}

	/**
	 * 
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	public List<E> list(final String groupBy, final String having, final String orderBy) {
		final List<E> entities = new ArrayList<E>();
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			final String[] columns = this.getColumnNamesAsArray(this.getGenericClass());
			if(columns != null && columns.length > 0) {
				final Cursor cursor = this.getDatabase().query(this.getTableName(), columns, 
						null, null, groupBy, having, orderBy);
				if (cursor != null && cursor.moveToFirst()) {
					entities.add(build(cursor));
					while(cursor.moveToNext()) {
						entities.add(build(cursor));
					}
				}
				cursor.close();
			}
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.list", e.getMessage());
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
			this.beginTransaction();
			
			boolean success = this.localInsert(entity);
			
			return success;
		} catch (Exception e) {
			this.endTransaction(false);
			Log.e("DefaultDBAdapter.insert", e.getMessage());
			return false;
		} finally {
			this.endTransaction(true);
		}
	}

	/**
	 * 
	 * @param entities
	 * @return
	 */
	public boolean insert(final List<E> entities) {
		boolean success = false;
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			this.beginTransaction();
			
			for(E e : entities) {
				success = this.localInsert(e);
			}
			
			return success;
		} catch (Exception e) {
			this.endTransaction(false);
			Log.e("DefaultDBAdapter.insert", e.getMessage());
			return false;
		} finally {
			this.endTransaction(success);
		}
	}
	
	/**
	 * 
	 * @param entities
	 * @return
	 */
	public boolean insert(final List<E> entities, final boolean transaction) {
		boolean success = false;
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			if(transaction) {
				this.beginTransaction();
			}
			
			for(E e : entities) {
				success = this.localInsert(e);
			}
			
			return success;
		} catch (Exception e) {
			if(transaction) {
				this.endTransaction(false);
			}
			Log.e("DefaultDBAdapter.insert", e.getMessage());
			return false;
		} finally {
			if(transaction) {
				this.endTransaction(success);
			}
		}
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean update(final E entity) {
		try {
			boolean success = false;

			this.beginTransaction();
			if (YUtilPersistence.isProxy(entity.getClass())) {
				E loadEntity = (E) YProxyLoad.load(entity, true, null);
				success = this.localUpdate(loadEntity);
			} else {
				if(!this.isEntity()) {
					throw new NotAnEntityException();
				}
				success = this.localUpdate(entity);
			}

			return success;
		} catch (Exception e) {
			this.endTransaction(false);
			Log.e("DefaultDBAdapter.update", e.getMessage());
			return false;
		} finally {
			this.endTransaction(true);
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
			return this.getDatabase().delete(this.getTableName(), YUtilPersistence.getIdColumnName(this.getGenericClass()) + "=" + this.getId(entity), null) > 0;
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
			final String[] columns = this.getColumnNamesAsArray(this.getGenericClass());
			if(columns != null && columns.length > 0) {
				final Cursor cursor = this.getDatabase().query(
						this.getTableName(), 
						columns, 
						YUtilPersistence.getIdColumnName(this.getGenericClass()) + " = ?", 
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
			final String[] columns = this.getColumnNamesAsArray(this.getGenericClass());
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
	 * @param attColumnGetMethods
	 * @return
	 */
	String buildSelecion(final List<Method> attColumnGetMethods) {
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
	String[] buildSelecionArgs(final List<Method> attColumnGetMethods, final E entity) {
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
			sql.append(YUtilPersistence.SQL_WORD_SELECT);
			sql.append(YUtilPersistence.SQL_WORD_MAX);
			sql.append("(" + YUtilPersistence.getIdColumnName(this.getGenericClass()) + ") ");
			sql.append(YUtilPersistence.SQL_WORD_FROM);
			sql.append(this.getTableName());

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
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected List<String> getColumnNames(Class clazz) {
		final List<String> columns = new ArrayList<String>();
		try {
			final List<Method> getMethods = YUtilReflection.getGetMethodList(clazz);
			final Method getIDMethod = YUtilPersistence.getGetIdMethod(getMethods);
			columns.add(YUtilPersistence.getColumnName(getIDMethod.getAnnotation(Column.class), getIDMethod));
			for(final Method getMethod : getMethods) {
				final Column column = getMethod.getAnnotation(Column.class);
				if(getMethod.equals(getIDMethod)
						|| column == null
						|| YUtilReflection.isList(getIDMethod.getReturnType())
						|| getMethod.getAnnotation(ManyToMany.class) != null
						|| getMethod.getAnnotation(OneToMany.class) != null) {
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
	 * @param clazz
	 * @param propertiesFilter
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected List<String> getColumnNames(final Class clazz, final List<String> propertiesFilter) {
		final List<String> columns = new ArrayList<String>();
		try {
			final List<Method> getMethods = YUtilReflection.getGetMethodList(clazz);
			final Method getIDMethod = YUtilPersistence.getGetIdMethod(getMethods);
			if(propertiesFilter.contains(YUtilReflection.getPropertyName(getIDMethod))) {
				columns.add(YUtilPersistence.getColumnName(getIDMethod.getAnnotation(Column.class), getIDMethod));
			}
			for(final Method getMethod : getMethods) {
				final Column column = getMethod.getAnnotation(Column.class);
				if(getMethod.equals(getIDMethod)
						|| column == null
						|| YUtilReflection.isList(getIDMethod.getReturnType())
						|| getMethod.getAnnotation(ManyToMany.class) != null
						|| getMethod.getAnnotation(OneToMany.class) != null) {
					continue;
				}
				
				if(propertiesFilter.contains(YUtilReflection.getPropertyName(getMethod))) {
					columns.add(YUtilPersistence.builColumnName(getMethod));
				}
				
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
		return YUtilPersistence.isEntity(this.getGenericClass());
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
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected String[] getColumnNamesAsArray(Class clazz) {
		String[] columns = null;
		try {
			final List<String> columnList = getColumnNames(clazz);
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

			treatBuild(cursor, object, getMethods);
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.build", e.getMessage());
		}
		return object;
	}

	/**
	 *
	 * @param cursor
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Object build(final Cursor cursor, Class clazz) {
		Object object = null;

		try {
			object = this.buildNewInstance(clazz);

			final List<Method> getMethods = YUtilReflection.getGetMethodList(clazz);

			treatBuild(cursor, object, getMethods);
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.build", e.getMessage());
		}
		return object;
	}

	/**
	 * @param cursor
	 * @param object
	 * @param getMethods
	 * @throws ParseException
	 */
	private void treatBuild(final Cursor cursor, Object object, final List<Method> getMethods) throws ParseException {
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
	 * @return
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object buildNewInstance(Class clazz) throws IOException, InstantiationException, IllegalAccessException {
		Object object;
		if(YacamimConfig.getInstance().usesLazyProxy()) {
			if(!YInvocationHandlerProxy.getInstance().contains(clazz)) {
				YInvocationHandlerProxy.getInstance().add(clazz, new YPersistenceInvocationHandler(new YGetMethodFilter()));
			}
			
			object = ProxyBuilder.forClass(clazz)
					.handler(YInvocationHandlerProxy.getInstance().get(clazz))
					.build();
		} else {
			object = clazz.newInstance();
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

			this.createContentValues(entidade, values, getMethods);
			
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.createContentValues", e.getMessage());
		}
		return values;
	}

	/**
	 *
	 * @param entidade
	 * @return
	 */
	protected ContentValues createContentValues(final E entidade, final List<Method> getMethods) {
		final ContentValues values = new ContentValues();
		try {
			this.createContentValues(entidade, values, getMethods);
		} catch (Exception e) {
			Log.e("DefaultDBAdapter.createContentValues", e.getMessage());
		}
		return values;
	}

	/**
	 * 
	 * @param entidade
	 * @param values
	 * @param getMethods
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void createContentValues(final E entidade, final ContentValues values, final List<Method> getMethods) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException 
			 {
		for(final Method getMethod : getMethods) {
			final Column column = getMethod.getAnnotation(Column.class);
			if(column != null
					|| YUtilPersistence.isId(getMethod)) {
				final String columnName = YUtilPersistence.getColumnName(column, getMethod);
				if(getMethod.getReturnType().equals(String.class)) {
					values.put(columnName, (String)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade));
				} else if (getMethod.getReturnType().equals(Boolean.class) || getMethod.getReturnType().equals(boolean.class)) {
					final Boolean value = (Boolean)YUtilReflection.invokeMethodWithoutParams(getMethod, entidade);
					if(value != null) {
						values.put(columnName, 
								value ? YacamimConfig.getInstance().getYSqliteTrue() : YacamimConfig.getInstance().getYSqliteFalse());
					}
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
					this.createContentValues(entidade, values, getMethod, column);
				}
			}
		}
	}

	/**
	 * 
	 * @param entidade
	 * @param values
	 * @param getMethod
	 * @param column
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void createContentValues(final E entidade, final ContentValues values, 
			final Method getMethod, final Column column) throws IllegalAccessException,
			InvocationTargetException {
		final Object object = YUtilReflection.invokeMethodWithoutParams(getMethod, entidade);
		if(object != null) {
			final Class<?> returnType = getMethod.getReturnType();
			if(YUtilPersistence.isEntity(returnType)) {
				final Method getFkMethod = YUtilPersistence.getGetIdMethod(returnType);
				if(getFkMethod != null) {
					final Long idFk = (Long)YUtilReflection.invokeMethodWithoutParams(getFkMethod, object);
					final String fkColumnName = YUtilPersistence.buildFkColumnName(returnType, column, getFkMethod);
					values.put(fkColumnName, idFk);
				}
			}
		}
	}

	/**
	 *
	 * @param whereAdded
	 * @param sql
	 * @return
	 */
	protected boolean configWhereOrAnd(boolean whereAdded, final StringBuilder sql) {
		if(!whereAdded) {
			sql.append(YUtilPersistence.SQL_WORD_WHERE);
			whereAdded = true;
		} else {
			sql.append(YUtilPersistence.SQL_WORD_AND);
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
			sql.append(YUtilPersistence.SQL_WORD_AND);
		}
		return whereAdded;
	}

	/**
	 * 
	 * @param targetMethods
	 * @return
	 */
	List<Method> filterMethodsForPersistenceAccess(final Method[] targetMethods) {
		final List<Method> filteredMethods = new ArrayList<Method>();
		for(final Method method : targetMethods) {
			if(method.getAnnotation(Column.class) != null
					|| YUtilPersistence.isId(method)) {
				filteredMethods.add(method);
			}
		}
		return filteredMethods;
	}
	
	/**
	 * 
	 * @param tableName
	 * @param columns
	 * @return
	 */
	protected String buildColumnsSelecString(final String tableName, final String[] columns) {
		if(columns == null || columns.length == 0) {
			return "";
		}
		final StringBuilder stringBuilder = new StringBuilder();
		for(int i = 0; i < columns.length; i++) {
			stringBuilder.append(tableName+ "." +columns[i]);
			if((i + 1) < columns.length) {
				stringBuilder.append(", ");
			}
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @param entity
	 * @param database
	 * @return
	 * @throws Exception
	 */
	private final boolean localInsert(final E entity) throws Exception {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			
			final List<Method> getMethods = YUtilReflection.getGetMethodList(this.getGenericClass());
			
			this.handlesManyToOneRelationships(entity, getMethods);
			this.handlesOneToOneOwnerRelationships(entity, getMethods);
			
			long newId = 0;
			if((newId = YUtilPersistence.getCurrentId(entity)) < 1) {
				final ContentValues initialValues = createContentValues(entity, getMethods);
				newId = this.getDatabase().insert(this.getTableName(), null, initialValues);
				this.setId(entity, newId);
				
				this.handlesBidirectionalOneToManyMappedByRelationships(entity, getMethods);

				final List<Method> manyToManyMethods = YUtilPersistence.filterManyToManyMethods(getMethods);
				if(manyToManyMethods != null && !manyToManyMethods.isEmpty()) {
					this.insertManyToMany(entity, manyToManyMethods);
				}

				final List<Method> oneToManyMethods = YUtilPersistence.filterOneToManyMethods(entity, getMethods);
				if(oneToManyMethods != null && !oneToManyMethods.isEmpty()) {
					this.insertOneToMany(entity, oneToManyMethods);
				}
				
			}
			
			return newId > 0;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws NotAnEntityException 
	 */
	private final boolean simpleInsert(final E entity) throws NotAnEntityException {
		boolean success = false;
		if(!this.isEntity()) {
			throw new NotAnEntityException();
		}
		if(database != null && database.isOpen() && database.inTransaction()) {
			final List<Method> getMethods = YUtilReflection.getGetMethodList(this.getGenericClass());
			
			final ContentValues initialValues = createContentValues(entity, getMethods);
			
			long newId = this.getDatabase().insert(this.getTableName(), null, initialValues);
			
			this.setId(entity, newId);
			
			success = newId > 0;
		}
		return success;
	}

	/**
	 * 
	 * @param entity
	 * @param getMethods
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void handlesManyToOneRelationships(final E entity, final List<Method> getMethods) 
			throws Exception {
		final List<Method> manyToOneMethods = YUtilPersistence.filterManyToOneMethods(getMethods);
		if(manyToOneMethods != null && !manyToOneMethods.isEmpty()) { // There are ManyToOne (with mappedBy) relationships
			for(final Method manyToOneMethod : manyToOneMethods) {
				// Gets the returned objeto
				final Object object = YUtilReflection.invokeMethod(manyToOneMethod, entity, 
						YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
				if(object != null && YUtilPersistence.isEntity(object.getClass())) {
					if(YUtilPersistence.getCurrentId(object) < 1) {
						DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(object.getClass());
						defaultDBAdapter.setDatabase(this.getDatabase());
						defaultDBAdapter.localInsert(object);
					}
				}
			}
		}
	}
	
	
	/**
	 * 
	 * @param entity
	 * @param getMethods
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void handlesOneToOneOwnerRelationships(final E entity, final List<Method> getMethods) 
			throws Exception {
		final List<Method> oneToOneMethods = YUtilPersistence.filterOneToOneOwnerMethods(getMethods);
		if(oneToOneMethods != null && !oneToOneMethods.isEmpty()) { // There are OneToOne relationships
			for(final Method oneToOneMethod : oneToOneMethods) {
				// Gets the returned objeto
				final Object object = YUtilReflection.invokeMethod(oneToOneMethod, entity, 
						YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
				if(object != null && YUtilPersistence.isEntity(object.getClass())) {
					if(YUtilPersistence.getCurrentId(object) < 1) {
						DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(object.getClass());
						defaultDBAdapter.setDatabase(this.getDatabase());
						defaultDBAdapter.simpleInsert(object);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param entity
	 * @param getMethods
	 * @throws Exception
	 * @throws CloneNotSupportedException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void handlesBidirectionalOneToManyMappedByRelationships(final E entity, final List<Method> getMethods) 
			throws Exception {
		final List<Method> oneToManyMethods = YUtilPersistence.filterOneToManyMappedByMethods(entity, getMethods);
		if(oneToManyMethods != null && !oneToManyMethods.isEmpty()) { // There are OneToMany (with mappedBy) relationships
			for(final Method oneToManyMethod : oneToManyMethods) {
				// Gets the returned objeto
				final Object object = YUtilReflection.invokeMethod(oneToManyMethod, entity, 
						YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
				// Check if it is a List
				if(object != null && YUtilReflection.isList(object.getClass())) {
					final List targetList = (List)object;
					// Check the list item type
					final Class<?> targetClass = YUtilReflection.getGenericType(entity.getClass(), oneToManyMethod);
					if(targetClass != null) {
						
						// Checks its ID
						final Method idMethod = YUtilPersistence.getGetIdMethod(targetClass);
						if(idMethod != null) {
							for(Object targetObject : targetList) {
								final Long longId = (Long)YUtilReflection.invokeMethod(idMethod, targetObject, 
										YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
								// Checks the ID
								if(longId != null && longId < 1) {
									DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(targetClass);
									defaultDBAdapter.setDatabase(this.getDatabase());
									defaultDBAdapter.localInsert(targetObject);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param entity
	 * @param getMethods
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<JoinItemResult> handlesUnidirectionalOneToManyRelationships(final E entity, final List<Method> oneToManyMethods) 
			throws Exception {
		final List<JoinItemResult> handledOneToManyResults = new ArrayList<JoinItemResult>();
		if(oneToManyMethods != null && !oneToManyMethods.isEmpty()) { // There are OneToMany (with mappedBy) relationships
			for(final Method oneToManyMethod : oneToManyMethods) {
				// Gets the returned objeto
				final Object object = YUtilReflection.invokeMethod(oneToManyMethod, entity, 
						YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
				// Check if it is a List
				if(object != null && YUtilReflection.isList(object.getClass())) {
					final List targetList = (List)object;
					// Check the list item type
					final Class<?> targetClass = YUtilReflection.getGenericType(entity.getClass(), oneToManyMethod);
					if(targetClass != null) {
						
						// Checks its ID
						final Method idMethod = YUtilPersistence.getGetIdMethod(targetClass);
						if(idMethod != null) {
							for(Object targetObject : targetList) {
								final Long longId = (Long)YUtilReflection.invokeMethod(idMethod, targetObject, 
										YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
								// Checks the ID
								if(longId != null && longId < 1) {
									DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(targetClass);
									defaultDBAdapter.setDatabase(this.getDatabase());
									defaultDBAdapter.localInsert(targetObject);
								}
								JoinItemResult oneToManyResult = new JoinItemResult();
								oneToManyResult.setTargetClass(targetClass);
								oneToManyResult.setTargetMethod(oneToManyMethod);
								oneToManyResult.setTargetObject(targetObject);
								oneToManyResult.setIdMethod(idMethod);
								handledOneToManyResults.add(oneToManyResult);
							}
						}
					}
				}
			}
		}
		return handledOneToManyResults;
	}
	
	/**
	 * 
	 * @param entity
	 * @param manyToManyMethods
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<JoinItemResult> handlesManyToManyRelationships(final E entity, final List<Method> manyToManyMethods) throws Exception {
		final List<JoinItemResult> handledManyToManyResults = new ArrayList<JoinItemResult>();
		if(manyToManyMethods != null && !manyToManyMethods.isEmpty()) { // There are ManyToMany (with mappedBy) relationships
			for(final Method manyToManyMethod : manyToManyMethods) {
				final Object object = YUtilReflection.invokeMethod(manyToManyMethod, entity, 
						YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
				// Check if it is a List
				if(object != null && YUtilReflection.isList(object.getClass())) {
					final List targetList = (List)object;
					// Check the list item type
					final Class<?> targetClass = YUtilReflection.getGenericType(entity.getClass(), manyToManyMethod);
					if(targetClass != null) {
						// Checks its ID
						final Method idMethod = YUtilPersistence.getGetIdMethod(targetClass);
						if(idMethod != null) {
							for(Object targetObject : targetList) {
								final Long longId = (Long)YUtilReflection.invokeMethod(idMethod, targetObject, 
										YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
								// Checks the ID
								if(longId != null && longId < 1) {
									DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(targetClass);
									defaultDBAdapter.setDatabase(this.getDatabase());
									defaultDBAdapter.simpleInsert(targetObject);
								}
								JoinItemResult manyToManyResult = new JoinItemResult();
								manyToManyResult.setTargetClass(targetClass);
								manyToManyResult.setTargetMethod(manyToManyMethod);
								manyToManyResult.setTargetObject(targetObject);
								manyToManyResult.setIdMethod(idMethod);
								handledManyToManyResults.add(manyToManyResult);
							}
						}
					}
				}
			}
		}
		return handledManyToManyResults;
	}
	
	/**
	 * 
	 * @param entity
	 * @param manyToManyMethods
	 * @throws Exception 
	 */
	private boolean handlesManyToManyJoin(final E entity, final List<JoinItemResult> manyToManyResults) throws Exception {
		boolean result = false;
		if(manyToManyResults != null && !manyToManyResults.isEmpty()) { // There are JoinItemResult
			for(final JoinItemResult manyToManyResult : manyToManyResults) {
				final ManyToMany manyToMany = manyToManyResult.getTargetMethod().getAnnotation(ManyToMany.class); 
				final Class<?> ownerClass;
				final Class<?> ownedClass;
				if(YUtilString.isEmptyString(manyToMany.mappedBy())) {
					ownerClass = entity.getClass();
					ownedClass = manyToManyResult.getTargetClass();
				} else {
					ownerClass = manyToManyResult.getTargetClass();
					ownedClass = entity.getClass();
				}
				
				final String ownerTableName = YUtilPersistence.getTableName(ownerClass);
				final String ownedTableName = YUtilPersistence.getTableName(ownedClass);

				final String ownerIdColumnName = ownerTableName + "_" + YUtilPersistence.getIdColumnName(ownerClass);
				final String ownedIdColumnName = ownedTableName + "_" + YUtilPersistence.getIdColumnName(ownedClass);
				final String joinTableName = ownerTableName + "_" + ownedTableName;
				
				final Method ownerGetIdMethod = YUtilPersistence.getGetIdMethod(ownerClass);
				final Method ownedGetIdMethod = YUtilPersistence.getGetIdMethod(ownedClass);
				if(ownerGetIdMethod != null && ownedGetIdMethod != null) {
					final ContentValues values = new ContentValues();
					if(YUtilString.isEmptyString(manyToMany.mappedBy())) {
						values.put(ownerIdColumnName, (Long)YUtilReflection.invokeMethodWithoutParams(ownerGetIdMethod, entity));
						values.put(ownedIdColumnName, (Long)YUtilReflection.invokeMethodWithoutParams(ownedGetIdMethod, manyToManyResult.getTargetObject()));
					} else {
						values.put(ownerIdColumnName, (Long)YUtilReflection.invokeMethodWithoutParams(ownerGetIdMethod, manyToManyResult.getTargetObject()));
						values.put(ownedIdColumnName, (Long)YUtilReflection.invokeMethodWithoutParams(ownedGetIdMethod, entity));
					}
					
					if(this.getDatabase() != null && this.getDatabase().isOpen() && this.getDatabase().inTransaction()) {
						result = this.getDatabase().insert(joinTableName, null, values) > 0;
					}
				}
				
			}
		}
		return result;
	}

	/**
	 * 
	 * @param entity
	 * @param oneToManyResults
	 * @return
	 * @throws Exception
	 */
	private boolean handlesOneToManyJoin(final E entity, final List<JoinItemResult> oneToManyResults) throws Exception {
		boolean result = false;
		if(oneToManyResults != null && !oneToManyResults.isEmpty()) { // There are JoinItemResult
			for(final JoinItemResult manyToManyResult : oneToManyResults) {
				final OneToMany oneToMany = manyToManyResult.getTargetMethod().getAnnotation(OneToMany.class); 
				if(oneToMany != null) {
					final Class<?> ownerClass = entity.getClass();
					final Class<?> ownedClass = manyToManyResult.getTargetClass();
					
					final String ownerTableName = YUtilPersistence.getTableName(ownerClass);
					final String ownedTableName = YUtilPersistence.getTableName(ownedClass);
					
					final String ownerIdColumnName = ownerTableName + "_" + YUtilPersistence.getIdColumnName(ownerClass);
					final String ownedIdColumnName = ownedTableName + "_" + YUtilPersistence.getIdColumnName(ownedClass);
					final String joinTableName = ownerTableName + "_" + ownedTableName;
					
					final Method ownerGetIdMethod = YUtilPersistence.getGetIdMethod(ownerClass);
					final Method ownedGetIdMethod = YUtilPersistence.getGetIdMethod(ownedClass);
					if(ownerGetIdMethod != null && ownedGetIdMethod != null) {
						final ContentValues values = new ContentValues();
						values.put(ownerIdColumnName, (Long)YUtilReflection.invokeMethodWithoutParams(ownerGetIdMethod, entity));
						values.put(ownedIdColumnName, (Long)YUtilReflection.invokeMethodWithoutParams(ownedGetIdMethod, manyToManyResult.getTargetObject()));
						
						if(this.getDatabase() != null && this.getDatabase().isOpen() && this.getDatabase().inTransaction()) {
							result = this.getDatabase().insert(joinTableName, null, values) > 0;
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param entity
	 * @param manyToManyMethods
	 * @return
	 * @throws Exception
	 */
	private boolean insertManyToMany(final E entity, final List<Method> manyToManyMethods) throws Exception {
		final List<JoinItemResult>  manyToManyResults = this.handlesManyToManyRelationships(entity, manyToManyMethods);
		return this.handlesManyToManyJoin(entity, manyToManyResults);
	}
	
	/**
	 * 
	 * @param entity
	 * @param oneToManyMethods
	 * @return
	 * @throws Exception
	 */
	private boolean insertOneToMany(final E entity, final List<Method> oneToManyMethods) throws Exception {
		final List<JoinItemResult>  manyToManyResults = this.handlesUnidirectionalOneToManyRelationships(entity, oneToManyMethods);
		return this.handlesOneToManyJoin(entity, manyToManyResults);
	}


	/**
	 * 
	 * @param entity
	 * @param database
	 * @return
	 * @throws Exception
	 */
	private final boolean localUpdate(final E entity) throws Exception {
		if (!this.isEntity()) {
			throw new NotAnEntityException();
		}

		final List<Method> getMethods = YUtilReflection.getGetMethodList(this.getGenericClass());

		this.updateManyToOneRelationships(entity, getMethods);
		this.updateOneToOneOwnerRelationships(entity, getMethods);
		if (YUtilPersistence.getCurrentId(entity) >= 1) {
			simpleUpdate(entity);

			this.updateBidirectionalOneToManyMappedByRelationships(entity, getMethods);

			final List<Method> manyToManyMethods = YUtilPersistence.filterManyToManyMethods(getMethods);
			if(manyToManyMethods != null && !manyToManyMethods.isEmpty()) {
				this.updateManyToMany(entity, manyToManyMethods);
			}

			final List<Method> oneToManyMethods = YUtilPersistence.filterOneToManyMethods(entity, getMethods);
			if(oneToManyMethods != null && !oneToManyMethods.isEmpty()) {
				this.updateOneToMany(entity, oneToManyMethods);
			}
		}

		return true;
	}

	/**
	 * 
	 * @param entity
	 * @return
	 * @throws NotAnEntityException 
	 */
	private final boolean simpleUpdate(final E entity) throws NotAnEntityException {
		boolean success = false;
		if (!this.isEntity()) {
			throw new NotAnEntityException();
		}

		if (database != null && database.isOpen() && database.inTransaction()) {
			final ContentValues updateValues = createContentValues(entity);
			final String idColumnName = YUtilPersistence.getIdColumnName(this.getGenericClass());
			updateValues.remove(idColumnName);
			success = this.getDatabase().update(this.getTableName(), updateValues, idColumnName + " = " + this.getId(entity), null) > 0;
		}

		return success;
	}

	/**
	 * 
	 * @param entity
	 * @param getMethods
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateManyToOneRelationships(final E entity, final List<Method> getMethods) 
			throws Exception {
		final List<Method> manyToOneMethods = YUtilPersistence.filterManyToOneMethods(getMethods);
		if (manyToOneMethods != null && !manyToOneMethods.isEmpty()) { // There are ManyToOne (with mappedBy) relationships
			for (final Method manyToOneMethod : manyToOneMethods) {
				// Gets the returned objeto
				Object object = YUtilReflection.invokeMethod(manyToOneMethod, entity, 
						YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
				if (object != null && YUtilPersistence.isProxy(object.getClass())) {
					object = YProxyLoad.load(object, false, null);
				}

				if (object != null && YUtilPersistence.isEntity(object.getClass())) {
					DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(object.getClass());
					defaultDBAdapter.setDatabase(this.getDatabase());
					if (YUtilPersistence.getCurrentId(object) < 1) {
						defaultDBAdapter.localInsert(object);
					} else {
						defaultDBAdapter.localUpdate(object);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param entity
	 * @param getMethods
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateOneToOneOwnerRelationships(final E entity, final List<Method> getMethods) 
			throws Exception {
		final List<Method> oneToOneMethods = YUtilPersistence.filterOneToOneOwnerMethods(getMethods);
		if (oneToOneMethods != null && !oneToOneMethods.isEmpty()) { // There are OneToOne relationships
			for (final Method oneToOneMethod : oneToOneMethods) {
				// Gets the returned objeto
				Object object = YUtilReflection.invokeMethod(oneToOneMethod, entity, 
						YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
				if (object != null && YUtilPersistence.isProxy(object.getClass())) {
					object = YProxyLoad.load(object, false, null);
				}

				if (object != null && YUtilPersistence.isEntity(object.getClass())) {
					DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(object.getClass());
					defaultDBAdapter.setDatabase(this.getDatabase());

					if (YUtilPersistence.getCurrentId(object) < 1) {
						defaultDBAdapter.simpleInsert(object);
					} else {
						defaultDBAdapter.simpleUpdate(object);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param entity
	 * @param getMethods
	 * @throws Exception
	 * @throws CloneNotSupportedException
	 */
	private void updateBidirectionalOneToManyMappedByRelationships(final E entity, final List<Method> getMethods) 
			throws Exception {
		final List<Method> oneToManyMethods = YUtilPersistence.filterOneToManyMappedByMethods(entity, getMethods);
		if (oneToManyMethods != null && !oneToManyMethods.isEmpty()) { // There are OneToMany (with mappedBy) relationships
			for (final Method oneToManyMethod : oneToManyMethods) {
				// Gets the returned object
				final Object object = YUtilReflection.invokeMethod(oneToManyMethod, entity, 
						YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
				// Check if it is a List
				if (object != null && YUtilReflection.isList(object.getClass())) {
					final List<?> targetList = (List<?>) object;
					String tableName = YUtilPersistence.getTableName(entity.getClass());
					String fkColumn = YUtilPersistence.getIdColumnName(entity.getClass()) + tableName;;
					final List<?> originalList = getOriginalList(entity, targetList, fkColumn);

					treatNewAndUpdateObjects(targetList, originalList);
					treatDeleteObjects(targetList, originalList);
				}
			}
		}
	}

	/**
	 * @param targetList
	 * @param originalList
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void treatDeleteObjects(final List targetList, final List originalList) throws Exception {
		for (Object obj : originalList) {
			boolean found = false;
			for (Object targetObj : targetList) {
				if (YUtilPersistence.getCurrentId(targetObj) >= 1) {
					if (((YBaseEntity) obj).getId() == ((YBaseEntity) targetObj).getId()) {
						found = true;
						break;
					}
				} else {
					found = true;
				}
			}

			//object deleted
			if (!found) {
				if (YUtilPersistence.isProxy(obj.getClass())) {
					obj = (E) YProxyLoad.load(obj, true, null);
				}
				DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(obj.getClass());
				defaultDBAdapter.setDatabase(this.getDatabase());
				defaultDBAdapter.delete(obj);
			}
		}
	}

	/**
	 * @param targetList
	 * @param originalList
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void treatNewAndUpdateObjects(final List targetList, final List originalList) throws Exception {
		for (Object obj : targetList) {
			if (YUtilPersistence.isProxy(obj.getClass())) {
				obj = (Object) YProxyLoad.load(obj, true, null);
			}

			DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(obj.getClass());
			defaultDBAdapter.setDatabase(this.getDatabase());

			if (YUtilPersistence.getCurrentId(obj) < 1) {
				defaultDBAdapter.insert(obj);
			} else {
				defaultDBAdapter.simpleUpdate(obj);
			}
		}
	}

	/**
	 * Return an original list of a object.
	 * 
	 * @param entity
	 * @param targetList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<YBaseEntity> getOriginalList(final E entity, final List<?> targetList, final String fkColumn) {
		List<YBaseEntity> entities = null;
		if (targetList != null && targetList.size() > 0) {
			Object firstObject = targetList.get(0);
			if (YUtilPersistence.isProxy(firstObject.getClass())) {
				firstObject = (E) YProxyLoad.load(firstObject, true, null);
			}

			String tableName = YUtilPersistence.getTableName(firstObject.getClass());
			final long fkValue = ((YBaseEntity) entity).getId();
			final String[] columns = this.getColumnNamesAsArray(firstObject.getClass());
			entities = getOriginalList(firstObject.getClass(), tableName, fkColumn, fkValue, columns);
		}

		return entities;
	}

	/**
	 * 
	 * @param clazz
	 * @param tableName
	 * @param fkColumn
	 * @param fkValue
	 * @param columns
	 * @return
	 */
	private List<YBaseEntity> getOriginalList(final Class<?> clazz, final String tableName, final String fkColumn, final long fkValue, String[] columns) {
		final String selection = fkColumn + " = ?";
		
		final String[] fkParam = {String.valueOf(fkValue)};

		final List<YBaseEntity> entities = new ArrayList<YBaseEntity>();
		final Cursor cursor = this.getDatabase().query(tableName, columns, selection, fkParam, null,
				null, null);
		if (cursor != null && cursor.moveToFirst()) {
			Object obj = build(cursor, clazz);
			entities.add((YBaseEntity) obj);
			while(cursor.moveToNext()) {
				obj = build(cursor, clazz);
				entities.add((YBaseEntity) obj);
			}
		}
		cursor.close();
		return entities;
	}

	/**
	 * 
	 * @param entity
	 * @param manyToManyMethods
	 * @return
	 * @throws Exception
	 */
	private boolean updateManyToMany(final E entity, final List<Method> manyToManyMethods) throws Exception {
		final List<JoinItemResult>  manyToManyResults = this.handlesManyToManyRelationships(entity, manyToManyMethods);
		return this.updateManyToManyJoin(entity, manyToManyResults);
	}

	/**
	 * 
	 * @param entity
	 * @param manyToManyMethods
	 * @throws Exception 
	 */
	private boolean updateManyToManyJoin(final E entity, final List<JoinItemResult> manyToManyResults) throws Exception {
		boolean result = false;
		if(manyToManyResults != null && !manyToManyResults.isEmpty()) { // There are JoinItemResult
			for(final JoinItemResult manyToManyResult : manyToManyResults) {
				final ManyToMany manyToMany = manyToManyResult.getTargetMethod().getAnnotation(ManyToMany.class); 
				final Class<?> ownerClass;
				final Class<?> ownedClass;
				if(YUtilString.isEmptyString(manyToMany.mappedBy())) {
					ownerClass = entity.getClass();
					ownedClass = manyToManyResult.getTargetClass();
				} else {
					ownerClass = manyToManyResult.getTargetClass();
					ownedClass = entity.getClass();
				}

				final String ownerTableName = YUtilPersistence.getTableName(ownerClass);
				final String ownedTableName = YUtilPersistence.getTableName(ownedClass);

				final String ownerIdColumnName = ownerTableName + "_" + YUtilPersistence.getIdColumnName(ownerClass);
				final String ownedIdColumnName = ownedTableName + "_" + YUtilPersistence.getIdColumnName(ownedClass);
				final String joinTableName = ownerTableName + "_" + ownedTableName;

				final Object object = YUtilReflection.invokeMethod(manyToManyResult.getTargetMethod(), entity, 
						YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);
				// Check if it is a List
				if (object != null && YUtilReflection.isList(object.getClass())) {
					final Method ownerGetIdMethod = YUtilPersistence.getGetIdMethod(ownerClass);

					if(ownerGetIdMethod != null) {
						Long ownerId = (Long) YUtilReflection.invokeMethodWithoutParams(ownerGetIdMethod, entity);
						final List<?> targetList = (List<?>) object;
						final String[] columns = {ownerIdColumnName, ownedIdColumnName};
						final List<?> originalList = getOriginalList(ownedClass, joinTableName, ownerIdColumnName, ownerId, columns);

						treatInsertAndDeleteObjects(targetList, originalList, entity, joinTableName, manyToManyResult, manyToMany.mappedBy(), ownerClass, ownerIdColumnName, ownedClass, ownedIdColumnName);
					}
				}
			}
		}
		return result;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void treatInsertAndDeleteObjects(final List targetList, final List originalList, final E entity, String joinTableName,
				JoinItemResult joinItemResult, String mappedBy, Class<?> ownerClass, String ownerIdColumnName, 
				Class<?> ownedClass, String ownedIdColumnName) throws Exception {
		for (Object obj : originalList) {
			boolean found = false;
			for (Object targetObj : targetList) {
				if (YUtilPersistence.getCurrentId(targetObj) >= 1) {
					if (((YBaseEntity) obj).getId() == ((YBaseEntity) targetObj).getId()) {
						found = true;
						break;
					}
				} else {
					found = true;
				}
			}

			//object deleted
			if (!found) {
				String[] whereValue = {String.valueOf(((YBaseEntity) obj).getId())};
				this.getDatabase().delete(joinTableName, ownerIdColumnName + " = ?", whereValue);

				if (YUtilPersistence.isProxy(obj.getClass())) {
					obj = (E) YProxyLoad.load(obj, true, null);
				}
				DefaultDBAdapter defaultDBAdapter = new DefaultDBAdapter(obj.getClass());
				defaultDBAdapter.setDatabase(this.getDatabase());
				defaultDBAdapter.delete(obj);
			} else {
				//new object
				final Method ownerGetIdMethod = YUtilPersistence.getGetIdMethod(ownerClass);
				final Method ownedGetIdMethod = YUtilPersistence.getGetIdMethod(ownedClass);
				if(ownerGetIdMethod != null && ownedGetIdMethod != null) {
					final ContentValues values = new ContentValues();
					if(YUtilString.isEmptyString(mappedBy)) {
						values.put(ownerIdColumnName, (Long)YUtilReflection.invokeMethodWithoutParams(ownerGetIdMethod, entity));
						values.put(ownedIdColumnName, (Long)YUtilReflection.invokeMethodWithoutParams(ownedGetIdMethod, joinItemResult.getTargetObject()));
					} else {
						values.put(ownerIdColumnName, (Long)YUtilReflection.invokeMethodWithoutParams(ownerGetIdMethod, joinItemResult.getTargetObject()));
						values.put(ownedIdColumnName, (Long)YUtilReflection.invokeMethodWithoutParams(ownedGetIdMethod, entity));
					}

					if(this.getDatabase() != null && this.getDatabase().isOpen() && this.getDatabase().inTransaction()) {
						this.getDatabase().insert(joinTableName, null, values);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param entity
	 * @param oneToManyMethods
	 * @return
	 * @throws Exception
	 */
	private boolean updateOneToMany(final E entity, final List<Method> oneToManyMethods) throws Exception {
		final List<JoinItemResult>  manyToManyResults = this.handlesUnidirectionalOneToManyRelationships(entity, oneToManyMethods);
		return this.updateOneToManyJoin(entity, manyToManyResults);
	}

	/**
	 * 
	 * @param entity
	 * @param oneToManyResults
	 * @return
	 * @throws Exception
	 */
	private boolean updateOneToManyJoin(final E entity, final List<JoinItemResult> oneToManyResults) throws Exception {
		boolean result = false;
		if(oneToManyResults != null && !oneToManyResults.isEmpty()) { // There are JoinItemResult
			for(final JoinItemResult oneToManyResult : oneToManyResults) {
				final OneToMany oneToMany = oneToManyResult.getTargetMethod().getAnnotation(OneToMany.class); 
				if(oneToMany != null) {
					final Class<?> ownerClass = entity.getClass();
					final Class<?> ownedClass = oneToManyResult.getTargetClass();

					final String ownerTableName = YUtilPersistence.getTableName(ownerClass);
					final String ownedTableName = YUtilPersistence.getTableName(ownedClass);
					
					final String ownerIdColumnName = ownerTableName + "_" + YUtilPersistence.getIdColumnName(ownerClass);
					final String ownedIdColumnName = ownedTableName + "_" + YUtilPersistence.getIdColumnName(ownedClass);
					final String joinTableName = ownerTableName + "_" + ownedTableName;
					
					final Method ownerGetIdMethod = YUtilPersistence.getGetIdMethod(ownerClass);
					final Method ownedGetIdMethod = YUtilPersistence.getGetIdMethod(ownedClass);
					if(ownerGetIdMethod != null && ownedGetIdMethod != null) {
						final ContentValues values = new ContentValues();
						values.put(ownerIdColumnName, (Long)YUtilReflection.invokeMethodWithoutParams(ownerGetIdMethod, entity));
						values.put(ownedIdColumnName, (Long)YUtilReflection.invokeMethodWithoutParams(ownedGetIdMethod, oneToManyResult.getTargetObject()));

						final Object object = YUtilReflection.invokeMethod(oneToManyResult.getTargetMethod(), entity, 
								YUtilReflection.DEAFULT_PARAM_ARRAY_OBJECT_REFLECTION);

						// Check if it is a List
						if (object != null && YUtilReflection.isList(object.getClass())) {
							Long ownerId = (Long) YUtilReflection.invokeMethodWithoutParams(ownerGetIdMethod, entity);
							final List<?> targetList = (List<?>) object;
							final String[] columns = {ownerIdColumnName, ownedIdColumnName};
							final List<?> originalList = getOriginalList(ownedClass, joinTableName, ownerIdColumnName, ownerId, columns);

							treatInsertAndDeleteObjects(targetList, originalList, entity, joinTableName, oneToManyResult, oneToMany.mappedBy(), ownerClass, ownerIdColumnName, ownedClass, ownedIdColumnName);
						}
					}
				}
			}
		}

		return result;
	}
}