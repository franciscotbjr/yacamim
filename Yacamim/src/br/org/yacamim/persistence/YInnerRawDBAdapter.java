/**
 * YInnerRawDBAdapter.java
 *
 * Copyright 2013 yacamim.org.br
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
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import br.org.yacamim.YRawData;

/**
 *
 * Class YInnerRawDBAdapter TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
final class YInnerRawDBAdapter<E> extends DefaultDBAdapter<E> {
	
	private final String SELECT = YUtilPersistence.SQL_WORD_SELECT;
	private final String DISTINCT = YUtilPersistence.SQL_WORD_DISTINCT;
	private final String FROM = YUtilPersistence.SQL_WORD_FROM;
	private final String WHERE = YUtilPersistence.SQL_WORD_WHERE;
	private final String AND = YUtilPersistence.SQL_WORD_AND;


	/**
	 *
	 * @param context
	 */
	public YInnerRawDBAdapter(final Class<E> genericType) {
		super(genericType);
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
						YUtilPersistence.getIdColumnName(this.getGenericClass()) + " = ?", 
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
	 * @param ownerClass
	 * @param ownedClass
	 * @param ownerId
	 * @param ownedId
	 * @return
	 * @throws SQLException
	 */
	List<YRawData> selectRawDataWithJoinTable(final Class<?> ownerClass, final Class<?> ownedClass, final long ownerId, final long ownedId) throws SQLException {
		final List<YRawData> entities = new ArrayList<YRawData>();
		try {
			if(!this.isEntity()) {
				throw new NotAnEntityException();
			}
			final List<Method> attColumnGetMethods = YUtilPersistence.getGetColumnMethodList(this.getGenericClass());
			final String[] columns = this.getColumnNamesAsArray();
			
			if(columns != null && columns.length > 0) {
				final Cursor cursor = 
						this.getDatabase().rawQuery(
								buildSelectWithJoinTableById(ownerClass, ownedClass, ownerId, ownedId, columns), 
								buildSelectionArgs(ownerId, ownedId));
						;
				
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
	 * @param ownerClass
	 * @param ownedClass
	 * @param ownerId
	 * @param ownedId
	 * @param columns
	 * @return
	 */
	private String buildSelectWithJoinTableById(final Class<?> ownerClass, final Class<?> ownedClass, 
			final long ownerId, final long ownedId, final String[] columns) {
		final String ownerTableName = YUtilPersistence.getTableName(ownerClass);
		final String ownedTableName = YUtilPersistence.getTableName(ownedClass);
		final String joinTableName = ownerTableName + "_" + ownedTableName;
		final StringBuilder builder = new StringBuilder();
		
		builder.append(SELECT + DISTINCT);
		builder.append(buildColumnsSelecString(this.getTableName() , columns));
		builder.append(FROM);
		builder.append(this.getTableName() + ", " + joinTableName);
		builder.append(WHERE);
		final String thisTableColumnName = this.getTableName() + "." + YUtilPersistence.getIdColumnName(this.getGenericClass());
		String joinTableColumnName = "";
		if(ownerId > 0) {
			joinTableColumnName = joinTableName + "." + (ownerTableName + "_" + YUtilPersistence.getIdColumnName(ownerClass));
		} else if (ownedId > 0) {
			joinTableColumnName = joinTableName + "." + (ownedTableName + "_" + YUtilPersistence.getIdColumnName(ownedClass));
		}
		builder.append(thisTableColumnName);
		builder.append(" = ");
		builder.append(joinTableColumnName);
		builder.append(AND);
		builder.append(joinTableColumnName + " = ?;");
		return builder.toString();
	}
	
	/**
	 * 
	 * @param ownerId
	 * @param ownedId
	 * @return
	 */
	private String[] buildSelectionArgs(final long ownerId, final long ownedId) {
		final String[] selectionArgs = new String[1];
		if(ownerId > 0) {
			selectionArgs[0] = "" + ownerId;
		} else if (ownedId > 0) {
			selectionArgs[0] = "" + ownedId;
		}
		return selectionArgs;
	}

}