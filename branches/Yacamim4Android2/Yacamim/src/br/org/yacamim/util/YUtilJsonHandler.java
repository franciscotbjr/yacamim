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
import br.org.yacamim.entity.YBaseEntity;

/**
 * Class YUtilJsonHandler TODO
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YUtilJsonHandler {
	
	private static final String TAG = YUtilJsonHandler.class.getSimpleName();

	/**
	 *
	 */
	private YUtilJsonHandler() {
		super();
	}

	/**
	 *
	 * @param baseActivity
	 * @param httpEntity
	 * @return
	 */
	public static synchronized List<YBaseEntity> getList(final YBaseActivity baseActivity, final HttpEntity httpEntity) {
		final List<YBaseEntity> objects = new ArrayList<YBaseEntity>();
		try {
			InputStream inputStream = httpEntity.getContent();

			String strJSON = YUtilIO.convertToStringBuilder(inputStream).toString();

			final JSONObject root = new JSONObject(strJSON);

			final YJSONDictionary yJSONDictionary = buildDictionary(root, "d");

			final JSONArray jsonObjects = root.getJSONArray("r");

			if(jsonObjects != null) {
				for(int i = 0; i < jsonObjects.length(); i++) {

					objects.add(getObject(jsonObjects.getJSONObject(i), yJSONDictionary));

				}
			}

		} catch (Exception e) {
			Log.e(TAG + ".getList", e.getMessage());
		}
		return objects;
	}

	/**
	 * @param _jsonObjects
	 * @throws JSONException
	 */
	protected static YBaseEntity getObject(final JSONObject object, final YJSONDictionary dicionarioJSON) throws JSONException {
		Object objectBE = null;
		try {
			final JSONArray names = object.names();

			if(names != null) {
				final String classServiceName = object.getString("c");
				final String classLocalName = YacamimClassMapping.getInstance().get(dicionarioJSON.get(classServiceName));

				final Class<?> classBE = Class.forName(classLocalName);

				objectBE = classBE.newInstance();

				for(int i = 0; i < names.length(); i++) {

					final String name = names.getString(i);
					if(!name.equalsIgnoreCase("c")) {

						if(isObject(object, name)) {

							setValueToJsonObject(object, dicionarioJSON, objectBE, name);

						} else
						if (isArray(object, name)) {
							final JSONArray jsonObjects = object.getJSONArray(name);

							if(jsonObjects != null) {
								final String nomePropriedade = dicionarioJSON.get(name);

								final List<YBaseEntity> yBaseEntities = new ArrayList<YBaseEntity>();
								for(int j = 0; j < jsonObjects.length(); j++) {


									final YBaseEntity yBaseEntity = getObject(jsonObjects.getJSONObject(j), dicionarioJSON);

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
									Log.e(TAG + ".getObject", e.getMessage());
								}
							}

						} else {
							setValueToJSONProperty(object, dicionarioJSON, objectBE, classBE, name);
						}
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG + ".getObject", e.getMessage());
		}
		return (YBaseEntity)objectBE;
	}

	/**
	 * @param object
	 * @param jsonDictionary
	 * @param objectBE
	 * @param _classBE
	 * @param name
	 * @throws JSONException
	 */
	protected static void setValueToJsonObject(final JSONObject object,
			final YJSONDictionary jsonDictionary, final Object objectBE,
			final String name) throws JSONException {
		try {
			final String propertyName = jsonDictionary.get(name);

			final YBaseEntity yBaseEntity = getObject(object.getJSONObject(name), jsonDictionary);

			YUtilReflection.setValueToProperty(propertyName, yBaseEntity, objectBE);
		} catch (Exception _e) {
			Log.e(TAG + ".setValueToJsonObject", _e.getMessage());
		}
	}

	/**
	 * @param object
	 * @param jsonDictionary
	 * @param objectBE
	 * @param classBE
	 * @param name
	 * @throws JSONException
	 * @throws ParseException
	 */
	protected static void setValueToJSONProperty(final JSONObject object,
			final YJSONDictionary jsonDictionary, final Object objectBE,
			final Class<?> classBE, final String name) throws JSONException,
			ParseException {
		try {
			final String value = object.getString(name);

			final String propertyName = jsonDictionary.get(name);

			final Class<?> valueType = YUtilReflection.getGetMethod(YUtilReflection.getGetMethodName(propertyName), classBE).getReturnType();

			if(valueType.equals(String.class)) {
				YUtilReflection.setValueToProperty(propertyName, value, objectBE);
			} else
			if(valueType.equals(Long.class)) {
				YUtilReflection.setValueToProperty(propertyName, Long.valueOf(value), objectBE);
			} else
			if(valueType.equals(Integer.class)) {
				YUtilReflection.setValueToProperty(propertyName, Integer.valueOf(value), objectBE);
			} else
			if(valueType.equals(Double.class)) {
				YUtilReflection.setValueToProperty(propertyName, Double.valueOf(value), objectBE);
			} else
			if(valueType.equals(Boolean.class)) {
				if(Boolean.parseBoolean(value)) {
					YUtilReflection.setValueToProperty(propertyName, YacamimConfig.getInstance().getYSqliteTrue(), objectBE);
				} else {
					YUtilReflection.setValueToProperty(propertyName, YacamimConfig.getInstance().getYSqliteFalse(), objectBE);
				}
			} else
			if(valueType.equals(long.class)) {
				YUtilReflection.setValueToProperty(propertyName, Long.parseLong(value), objectBE);
			} else
			if(valueType.equals(int.class)) {
				YUtilReflection.setValueToProperty(propertyName, Integer.parseInt(value), objectBE);
			} else
			if(valueType.equals(double.class)) {
				YUtilReflection.setValueToProperty(propertyName, Double.parseDouble(value), objectBE);
			} else
			if(valueType.equals(java.util.Date.class)) {
				if(!value.contains(":")) {
					YUtilReflection.setValueToProperty(propertyName, SimpleDateFormat.getDateInstance().parse(value), objectBE);
				} else {
					YUtilReflection.setValueToProperty(propertyName, SimpleDateFormat.getDateTimeInstance().parse(value), objectBE);
				}
			} else
			if(valueType.equals(boolean.class)) {
				if(Boolean.parseBoolean(value)) {
					YUtilReflection.setValueToProperty(propertyName, YacamimConfig.getInstance().getYSqliteTrue(), objectBE);
				} else {
					YUtilReflection.setValueToProperty(propertyName, YacamimConfig.getInstance().getYSqliteFalse(), objectBE);
				}
			}
		} catch (Exception e) {
			Log.e(TAG + ".setValueToJSONProperty", e.getMessage());
		}

	}

	/**
	 *
	 * @param object
	 * @param name
	 * @return
	 */
	protected static boolean isObject(final JSONObject object, final String name) {
		try {
			object.getJSONObject(name);
			return true;
		} catch (Exception e) {
			Log.e(TAG + ".isObject", e.getMessage());
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
		} catch (Exception e) {
			Log.e(TAG + ".isArray", e.getMessage());
			return false;
		}
	}

	/**
	 * @param root
	 * @throws JSONException
	 */
	protected static YJSONDictionary buildDictionary(final JSONObject root, final String dictionaryName) throws JSONException {
		final YJSONDictionary yJSONDictionary = new YJSONDictionary();
		try {
			final JSONObject dictionaryObject = root.getJSONObject(dictionaryName);

			final JSONArray names = dictionaryObject.names();

			if(names != null) {
				for(int i = 0; i < names.length(); i++) {
					yJSONDictionary.add(names.getString(i), dictionaryObject.getString(names.getString(i)));
				}
			}
		} catch (Exception e) {
			Log.e(TAG + ".buildDictionary", e.getMessage());
		}
		return yJSONDictionary;
	}

}