/**
 * YUtilJsonHandler.java
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
package br.org.yacamim.util;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.YacamimConfig;
import br.org.yacamim.YacamimState;
import br.org.yacamim.entity.YBaseEntity;

/**
 * Class YUtilJsonHandler TODO
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YUtilJsonHandler {

	/**
	 *
	 */
	private YUtilJsonHandler() {
		super();
	}

	/**
	 *
	 * @param _baseActivity
	 * @param _httpEntity
	 * @return
	 */
	public static synchronized List<YBaseEntity> getList(final YBaseActivity _baseActivity, final HttpEntity _httpEntity) {
		final List<YBaseEntity> objects = new ArrayList<YBaseEntity>();
		try {
			InputStream inputStream = _httpEntity.getContent();

			String strJSON = YUtilIO.convertToStringBuilder(inputStream).toString();

			final JSONObject root = new JSONObject(strJSON);

			final YJSONDictionary yJSONDictionary = buildDictionary(root, "d");

			final JSONArray jsonObjects = root.getJSONArray("r");

			if(jsonObjects != null) {
				for(int i = 0; i < jsonObjects.length(); i++) {

					objects.add(getObject(jsonObjects.getJSONObject(i), yJSONDictionary));

				}
			}

		} catch (Exception _e) {
			Log.e("YUtilJsonHandler.getList", _e.getMessage());
		}
		return objects;
	}

	/**
	 * @param _jsonObjects
	 * @throws JSONException
	 */
	protected static YBaseEntity getObject(final JSONObject _object, final YJSONDictionary _dicionarioJSON) throws JSONException {
		Object objectBE = null;
		try {
			final JSONArray names = _object.names();

			if(names != null) {
				final String classServiceName = _object.getString("c");
				final String classLocalName = YacamimClassMapping.getInstance().get(_dicionarioJSON.get(classServiceName));

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

								final List<YBaseEntity> yBaseEntities = new ArrayList<YBaseEntity>();
								for(int j = 0; j < jsonObjects.length(); j++) {


									final YBaseEntity yBaseEntity = getObject(jsonObjects.getJSONObject(j), _dicionarioJSON);

									yBaseEntities.add(yBaseEntity);
								}

								try {
									YUtilReflection.invokeMethod(
											YUtilReflection.getSetMethod(
													YUtilReflection.getSetMethodName(nomePropriedade),
													classBE,
													new Class[]{List.class}),
													objectBE,
													yBaseEntities);
								} catch (Exception e) {
									Log.e("YUtilJsonHandler.getObject", e.getMessage());
								}


							}

						} else {
							setValueToJSONProperty(_object, _dicionarioJSON, objectBE, classBE, name);
						}

					}

				}

			}
		} catch (Exception _e) {
			Log.e("YUtilJsonHandler.getObject", _e.getMessage());
		}
		return (YBaseEntity)objectBE;
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
			final YJSONDictionary _jsonDictionary, Object _objectBE,
			final String _name) throws JSONException {
		try {
			final String propertyName = _jsonDictionary.get(_name);

			final YBaseEntity yBaseEntity = getObject(_object.getJSONObject(_name), _jsonDictionary);

			YUtilReflection.setValueToProperty(propertyName, yBaseEntity, _objectBE);
		} catch (Exception _e) {
			Log.e("YUtilJsonHandler.setValueToJsonObject", _e.getMessage());
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
			final YJSONDictionary _jsonDictionary, Object _objectBE,
			final Class<?> _classBE, final String _name) throws JSONException,
			ParseException {
		try {
			final String value = _object.getString(_name);

			final String propertyName = _jsonDictionary.get(_name);

			final Class<?> valueType = YUtilReflection.getGetMethod(YUtilReflection.getGetMethodName(propertyName), _classBE).getReturnType();

			if(valueType.equals(String.class)) {
				YUtilReflection.setValueToProperty(propertyName, value, _objectBE);
			} else
			if(valueType.equals(Long.class)) {
				YUtilReflection.setValueToProperty(propertyName, Long.valueOf(value), _objectBE);
			} else
			if(valueType.equals(Integer.class)) {
				YUtilReflection.setValueToProperty(propertyName, Integer.valueOf(value), _objectBE);
			} else
			if(valueType.equals(Double.class)) {
				YUtilReflection.setValueToProperty(propertyName, Double.valueOf(value), _objectBE);
			} else
			if(valueType.equals(Boolean.class)) {
				if(Boolean.parseBoolean(value)) {
					YUtilReflection.setValueToProperty(propertyName, YacamimConfig.getInstance().getYSqliteTrue(), _objectBE);
				} else {
					YUtilReflection.setValueToProperty(propertyName, YacamimConfig.getInstance().getYSqliteFalse(), _objectBE);
				}
			} else
			if(valueType.equals(long.class)) {
				YUtilReflection.setValueToProperty(propertyName, Long.parseLong(value), _objectBE);
			} else
			if(valueType.equals(int.class)) {
				YUtilReflection.setValueToProperty(propertyName, Integer.parseInt(value), _objectBE);
			} else
			if(valueType.equals(double.class)) {
				YUtilReflection.setValueToProperty(propertyName, Double.parseDouble(value), _objectBE);
			} else
			if(valueType.equals(java.util.Date.class)) {
				if(!value.contains(":")) {
					YUtilReflection.setValueToProperty(propertyName, SimpleDateFormat.getDateInstance().parse(value), _objectBE);
				} else {
					YUtilReflection.setValueToProperty(propertyName, SimpleDateFormat.getDateTimeInstance().parse(value), _objectBE);
				}
			} else
			if(valueType.equals(boolean.class)) {
				if(Boolean.parseBoolean(value)) {
					YUtilReflection.setValueToProperty(propertyName, YacamimConfig.getInstance().getYSqliteTrue(), _objectBE);
				} else {
					YUtilReflection.setValueToProperty(propertyName, YacamimConfig.getInstance().getYSqliteFalse(), _objectBE);
				}
			}
		} catch (Exception _e) {
			Log.e("YUtilJsonHandler.setValueToJSONProperty", _e.getMessage());
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
	protected static YJSONDictionary buildDictionary(final JSONObject _root, final String _dictionaryName) throws JSONException {
		final YJSONDictionary yJSONDictionary = new YJSONDictionary();
		try {
			final JSONObject dictionaryObject = _root.getJSONObject(_dictionaryName);

			final JSONArray names = dictionaryObject.names();

			if(names != null) {
				for(int i = 0; i < names.length(); i++) {
					yJSONDictionary.add(names.getString(i), dictionaryObject.getString(names.getString(i)));
				}
			}
		} catch (Exception _e) {
			Log.e("YUtilJsonHandler.buildDictionary", _e.getMessage());
		}
		return yJSONDictionary;
	}

}
