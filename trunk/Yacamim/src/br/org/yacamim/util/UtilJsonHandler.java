/**
 * UtilJsonHandler.java
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
package br.org.yacamim.util;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import br.org.yacamim.BaseActivity;
import br.org.yacamim.YacamimState;
import br.org.yacamim.entidade.BaseEntity;

/**
 * Class UtilJsonHandler TODO
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class UtilJsonHandler {

	/**
	 * 
	 */
	private UtilJsonHandler() {
		super();
	}
	
	/**
	 * 
	 * @param _baseActivity
	 * @param _httpEntity
	 * @return
	 */
	public static synchronized List<BaseEntity> getList(final BaseActivity _baseActivity, final HttpEntity _httpEntity) {
		final List<BaseEntity> objects = new ArrayList<BaseEntity>();
		try {
			InputStream inputStream = _httpEntity.getContent();
			
			String strJSON = UtilIO.convertToStringBuilder(inputStream).toString();
			
			final JSONObject root = new JSONObject(strJSON);
			
			final JSONDictionary jSONDictionary = buildDictionary(root, "d");
			
			final JSONArray jsonObjects = root.getJSONArray("r");
			
			if(jsonObjects != null) {
				for(int i = 0; i < jsonObjects.length(); i++) {
					
					objects.add(getObject(jsonObjects.getJSONObject(i), jSONDictionary));
					
				}
			}
			
		} catch (Exception _e) {
			Log.e("UtilJsonHandler.getList", _e.getMessage());
		}
		return objects;
	}

	/**
	 * @param _jsonObjects
	 * @throws JSONException
	 */
	protected static BaseEntity getObject(final JSONObject _object, final JSONDictionary _dicionarioJSON) throws JSONException {
		Object objectBE = null;
		try {
			final JSONArray names = _object.names();
			
			if(names != null) {
				final String classServiceName = _object.getString("c");
				final String classLocalName = YacamimState.getInstance().getMapeamentoClasses().get(_dicionarioJSON.get(classServiceName));
				
				final Class<?> classBE = Class.forName(classLocalName);
				
				objectBE = classBE.newInstance();
				
				for(int i = 0; i < names.length(); i++) {
					
					final String name = names.getString(i);
					if(!name.equalsIgnoreCase("c")) {
						
						if(isObject(_object, name)) {
							
							setValueToJsonObject(_object, _dicionarioJSON, objectBE, name);
							
						} else 
						if (isArray(_object, name)) {
							final JSONArray jsonObjects = _object.getJSONArray(name);
							
							if(jsonObjects != null) {
								final String nomePropriedade = _dicionarioJSON.get(name);

								final List<BaseEntity> baseEntities = new ArrayList<BaseEntity>();
								for(int j = 0; j < jsonObjects.length(); j++) {
									
									
									final BaseEntity baseEntity = getObject(jsonObjects.getJSONObject(j), _dicionarioJSON);
									
									baseEntities.add(baseEntity);
								}
								
								try {
									UtilReflection.invokeMethod(
											UtilReflection.getSetMethod(
													UtilReflection.getSetMethodName(nomePropriedade), 
													classBE, 
													new Class[]{List.class}), 
													objectBE, 
													baseEntities);
								} catch (Exception e) {
									Log.e("UtilJsonHandler.getObject", e.getMessage());
								}
								
								
							}
							
						} else {
							setValueToJSONProperty(_object, _dicionarioJSON, objectBE, classBE, name); 
						}

					}
					
				}
				
			}
		} catch (Exception _e) {
			Log.e("UtilJsonHandler.getObject", _e.getMessage());
		}
		return (BaseEntity)objectBE;
	}

	/**
	 * @param _object
	 * @param _jsonDictionary
	 * @param _objectBE
	 * @param _classBE
	 * @param _name
	 * @throws JSONException
	 */
	protected static void setValueToJsonObject(final JSONObject _object,
			final JSONDictionary _jsonDictionary, Object _objectBE,
			final String _name) throws JSONException {
		try {
			final String propertyName = _jsonDictionary.get(_name);
			
			final BaseEntity baseEntity = getObject(_object.getJSONObject(_name), _jsonDictionary);
			
			UtilReflection.setValueToProperty(propertyName, baseEntity, _objectBE);
		} catch (Exception _e) {
			Log.e("UtilJsonHandler.setValueToJsonObject", _e.getMessage());
		}
	}

	/**
	 * @param _object
	 * @param _jsonDictionary
	 * @param _objectBE
	 * @param _classBE
	 * @param _name
	 * @throws JSONException
	 * @throws ParseException
	 */
	protected static void setValueToJSONProperty(final JSONObject _object,
			final JSONDictionary _jsonDictionary, Object _objectBE,
			final Class<?> _classBE, final String _name) throws JSONException,
			ParseException {
		try {
			final String value = _object.getString(_name);
			
			final String propertyName = _jsonDictionary.get(_name);
			
			final Class<?> valueType = UtilReflection.getGetMethod(UtilReflection.getGetMethodName(propertyName), _classBE).getReturnType();
			
			if(valueType.equals(String.class)) {
				UtilReflection.setValueToProperty(propertyName, value, _objectBE);
			} else
			if(valueType.equals(Long.class)) {
				UtilReflection.setValueToProperty(propertyName, Long.valueOf(value), _objectBE);
			} else 
			if(valueType.equals(Integer.class)) {
				UtilReflection.setValueToProperty(propertyName, Integer.valueOf(value), _objectBE);
			} else
			if(valueType.equals(Double.class)) {
				UtilReflection.setValueToProperty(propertyName, Double.valueOf(value), _objectBE);
			} else
			if(valueType.equals(Boolean.class)) {
				if(Boolean.parseBoolean(value)) {
					UtilReflection.setValueToProperty(propertyName, Constants.YES, _objectBE);
				} else {
					UtilReflection.setValueToProperty(propertyName, Constants.NO, _objectBE);
				}
			} else 
			if(valueType.equals(long.class)) {
				UtilReflection.setValueToProperty(propertyName, Long.parseLong(value), _objectBE);
			} else 
			if(valueType.equals(int.class)) {
				UtilReflection.setValueToProperty(propertyName, Integer.parseInt(value), _objectBE);
			} else 
			if(valueType.equals(double.class)) {
				UtilReflection.setValueToProperty(propertyName, Double.parseDouble(value), _objectBE);
			} else 
			if(valueType.equals(java.util.Date.class)) {
				String strDateTime = value;
				if(!value.contains(":")) {
					strDateTime += " 00:00:00";
				}
				UtilReflection.setValueToProperty(propertyName, UtilDate.getSimpleDateFormatDateTime().parse(strDateTime), _objectBE);
			} else 
			if(valueType.equals(boolean.class)) {
				if(Boolean.parseBoolean(value)) {
					UtilReflection.setValueToProperty(propertyName, Constants.YES, _objectBE);
				} else {
					UtilReflection.setValueToProperty(propertyName, Constants.NO, _objectBE);
				}
			}
		} catch (Exception _e) {
			Log.e("UtilJsonHandler.setValueToJSONProperty", _e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * @param _object
	 * @param _name
	 * @return
	 */
	protected static boolean isObject(final JSONObject _object, final String _name) {
		try {
			_object.getJSONObject(_name);
			return true;
		} catch (Exception _e) {
			return false;
		}
	}
	
	/**
	 * 
	 * @param _object
	 * @param _name
	 * @return
	 */
	protected static boolean isArray(final JSONObject _object, final String _name) {
		try {
			_object.getJSONArray(_name);
			return true;
		} catch (Exception _e) {
			return false;
		}
	}

	/**
	 * @param _root
	 * @throws JSONException
	 */
	protected static JSONDictionary buildDictionary(final JSONObject _root, final String _dictionaryName) throws JSONException {
		final JSONDictionary jSONDictionary = new JSONDictionary();
		try {
			final JSONObject dictionaryObject = _root.getJSONObject(_dictionaryName);
			
			final JSONArray names = dictionaryObject.names();
			
			if(names != null) {
				for(int i = 0; i < names.length(); i++) {
					jSONDictionary.add(names.getString(i), dictionaryObject.getString(names.getString(i)));
				}
			}
		} catch (Exception _e) {
			Log.e("UtilJsonHandler.buildDictionary", _e.getMessage());
		}
		return jSONDictionary;
	}
	
}
