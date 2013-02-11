/**
 * YLoader.java
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
package br.org.yacamim.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.YacamimConfig;
import br.org.yacamim.YacamimInitializer;
import br.org.yacamim.YacamimState;
import br.org.yacamim.entity.DbScript;
import br.org.yacamim.entity.ServiceURL;
import br.org.yacamim.persistence.DbLoadList;
import br.org.yacamim.util.YacamimClassMapping;

/**
 * Class YLoader TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YLoader {

	/**
	 *
	 */
	public static final int URL_RAIZ = 0;

	/**
	 *
	 */
	public YLoader() {
		super();
	}

	/**
	 *
	 * @param yBaseActivity
	 */
	public static void loadClassMapping(final YBaseActivity yBaseActivity, final YacamimClassMapping yacamimClassMapping) {
		try {
			if(!YacamimState.getInstance().isClassMappingLoaded()) {
				final XmlResourceParser xmlClassMapping = yBaseActivity.getAssets().openXmlResourceParser("res/xml/y_service_class_mapping.xml");

				int eventType = -1;

				while (eventType != XmlResourceParser.END_DOCUMENT) {
					if (eventType == XmlResourceParser.START_TAG) {

						final String strName = xmlClassMapping.getName();
						if (strName.equals(YDefaultXmlElements.ELEMENT_Y_CLASS.toString())) {
							final String classeServico = xmlClassMapping.getAttributeValue(null, YDefaultXmlElements.ATTR_SERVICE_NAME.toString());
							final String classeAndroid = xmlClassMapping.getAttributeValue(null, YDefaultXmlElements.ATTR_LOCAL_NAME.toString());
							yacamimClassMapping.add(classeServico, classeAndroid);
						}
					}
					eventType = xmlClassMapping.next();
				}
				YacamimState.getInstance().setClassMappingLoaded(true);
			}
		} catch (Exception e) {
			Log.e(YLoader.class.getName() + ".loadClassMapping", e.getMessage());
		}
	}

	/**
	 *
	 * @param yBaseActivity
	 */
	public static void loadParams(final YBaseActivity yBaseActivity) {
		try {
			if(!YacamimState.getInstance().isParamsLoaded()) {
				final XmlResourceParser xmlParams = yBaseActivity.getAssets().openXmlResourceParser("res/xml/y_param_mapping.xml");

				int eventType = -1;

				while (eventType != XmlResourceParser.END_DOCUMENT) {
					if (eventType == XmlResourceParser.START_TAG) {

						final String strName = xmlParams.getName();
						if (strName.equals(YDefaultXmlElements.ELEMENT_Y_PARAM_ITEM.toString())) {
							final String name = xmlParams.getAttributeValue(null, YDefaultXmlElements.ATTR_NAME.toString());
							final String value = xmlParams.getAttributeValue(null, YDefaultXmlElements.ATTR_VALUE.toString());
							YacamimConfig.getInstance().getConfigItems().put(name, value);
						}
					}
					eventType = xmlParams.next();
				}
				YacamimState.getInstance().setParamsLoaded(true);
			}
		} catch (Exception e) {
			Log.e(YLoader.class.getName() + ".loadParams", e.getMessage());
		}
	}

	/**
	 *
	 * @param yBaseActivity
	 */
	public static void loadServiceURLs(final YBaseActivity yBaseActivity) {
    	try {
    		if(!YacamimState.getInstance().isServiceUrlsLoaded()) {
    			final XmlResourceParser xmlServicos = yBaseActivity.getAssets().openXmlResourceParser("res/xml/y_service_mapping.xml");

        		int eventType = -1;

            	while (eventType != XmlResourceParser.END_DOCUMENT) {
            		if (eventType == XmlResourceParser.START_TAG) {

            			final String strName = xmlServicos.getName();
            			if (strName.equals(YDefaultXmlElements.ELEMENT_Y_SERVICE.toString())) {
            				final ServiceURL serviceURL = new ServiceURL();
            				serviceURL.id = Integer.parseInt(xmlServicos.getAttributeValue(null, YDefaultXmlElements.ATTR_ID.toString()));
            				serviceURL.url = xmlServicos.getAttributeValue(null, YDefaultXmlElements.ATTR_URL.toString());
            				YacamimState.getInstance().getUrlServicos().add(serviceURL);
            			}
            		}
            		eventType = xmlServicos.next();
            	}
            	YacamimState.getInstance().setServiceUrlsLoaded(true);
    		}
		} catch (Exception e) {
			Log.e(YLoader.class.getName() + ".loadURLsServices", e.getMessage());
		}
    }

	/**
	 *
	 * @param context
	 */
	public static DbScript loadDBScript(final Context context) {
		final DbScript dbScript = new DbScript();
		try {
			final XmlResourceParser xmlDbScript = context.getAssets().openXmlResourceParser("res/xml/y_db_script.xml");

			int eventType = -1;

			while (eventType != XmlResourceParser.END_DOCUMENT) {
				if (eventType == XmlResourceParser.START_TAG) {
					final String strName = xmlDbScript.getName();

					if (strName.equals(YDefaultXmlElements.ELEMENT_Y_DB.toString())) {
						dbScript.setDbVersion(Integer.parseInt(xmlDbScript.getAttributeValue(null, YDefaultXmlElements.ATTR_VERSION.toString())));
					}
					else if (strName.equals(YDefaultXmlElements.ELEMENT_Y_CREATE_TABLE.toString())) {
						final String script = xmlDbScript.nextText();
						dbScript.getCreateTables().add(script);
					}
					else if (strName.equals(YDefaultXmlElements.ELEMENT_Y_ALTER_TABLE.toString())) {
						final String script = xmlDbScript.nextText();
						dbScript.getCreateTables().add(script);
					}
					else if (strName.equals(YDefaultXmlElements.ELEMENT_Y_INSERT.toString())) {
						final String script = xmlDbScript.nextText();
						dbScript.getInserts().add(script);
					}
					else if (strName.equals(YDefaultXmlElements.ELEMENT_Y_UPDATE.toString())) {
						final String script = xmlDbScript.nextText();
						dbScript.getUpdates().add(script);
					}
				}
				eventType = xmlDbScript.next();
			}
		} catch (Exception e) {
			Log.e(YLoader.class.getName() + ".loadDBScript", e.getMessage());
		}
		return dbScript;
	}
	
	/**
	 * 
	 * @param yBaseActivity
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws XmlPullParserException 
	 */
	public static void loadConfigs(final YBaseActivity yBaseActivity) throws ClassNotFoundException, XmlPullParserException, IOException {
    		if(!YacamimInitializer.getInstance().isInitialized()) {
    			final XmlResourceParser xmlConfigs = yBaseActivity.getAssets().openXmlResourceParser("res/xml/y_config.xml");

        		int eventType = -1;
        		
        		String pacoteAtual = "";
        		boolean entityMappingFound = false;
            	while (eventType != XmlResourceParser.END_DOCUMENT) {
            		if (eventType == XmlResourceParser.START_TAG) {
            			final String strName = xmlConfigs.getName();

            			if (strName.equals(YDefaultXmlElements.ELEMENT_Y_PACKAGE.toString())) {
            				pacoteAtual = xmlConfigs.getAttributeValue(null, YDefaultXmlElements.ATTR_NAME.toString());
            			} else if (strName.equals(YDefaultXmlElements.ELEMENT_Y_ENTITY_MAPPING.toString())) {
            				entityMappingFound = true;
	            		} else if (entityMappingFound 
	            				&& strName.equals(YDefaultXmlElements.ELEMENT_Y_ENTITY.toString())
            					&& pacoteAtual!= null 
            					&& !pacoteAtual.trim().equals("")) {
            				
            				YacamimConfig.getInstance().addEntity(Class.forName(pacoteAtual + "." + xmlConfigs.getAttributeValue(null, YDefaultXmlElements.ATTR_NAME.toString())));
            				
            			} else if (strName.equals(YDefaultXmlElements.ELEMENT_Y_CONFIG_ITEM.toString())) {
            				YacamimConfig.getInstance().add(
            						xmlConfigs.getAttributeValue(null, YDefaultXmlElements.ATTR_NAME.toString()), 
            						xmlConfigs.getAttributeValue(null, YDefaultXmlElements.ATTR_VALUE.toString())
            						);
            			}
            		} else if (eventType == XmlResourceParser.END_TAG) {
            			final String strName = xmlConfigs.getName();
            			if (strName.equals(YDefaultXmlElements.ELEMENT_Y_PACKAGE.toString())) {
            				pacoteAtual = "";
            			}
            		}
            		eventType = xmlConfigs.next();
            	}
            	YacamimConfig.getInstance().setConfigItemsLoaded(true);
    		}
    }
	
	/**
	 * 
	 * @param yBaseActivity
	 * @return
	 * @throws ClassNotFoundException
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static void loadDBLoad(final YBaseActivity yBaseActivity) throws ClassNotFoundException, XmlPullParserException, IOException {
		final DbLoadList dbLoadList = DbLoadList.getInstance();
		if(YacamimConfig.getInstance().isConfigItemsLoaded() && YacamimConfig.getInstance().usesDBLoad()) {
			final XmlResourceParser xmlDbLoad = yBaseActivity.getAssets().openXmlResourceParser("res/xml/y_db_load.xml");
			
			int eventType = -1;
			
			String pacoteAtual = "";
			String entidadeAtual = "";
			boolean insert = false;
			boolean update = false;
			boolean delete = false;
			while (eventType != XmlResourceParser.END_DOCUMENT) {
				if (eventType == XmlResourceParser.START_TAG) {
					final String strName = xmlDbLoad.getName();
					if (strName.equals(YDefaultXmlElements.ELEMENT_Y_PACKAGE.toString())) {
						pacoteAtual = xmlDbLoad.getAttributeValue(null, YDefaultXmlElements.ATTR_NAME.toString());
					} else if (strName.equals(YDefaultXmlElements.ELEMENT_Y_INSERT.toString())) {
						entidadeAtual = xmlDbLoad.getAttributeValue(null, YDefaultXmlElements.ATTR_Y_ENTITY.toString());
						insert = true;
						update = false;
						delete = false;
					} else if (strName.equals(YDefaultXmlElements.ELEMENT_Y_UPDATE.toString())) {
						entidadeAtual = xmlDbLoad.getAttributeValue(null, YDefaultXmlElements.ATTR_Y_ENTITY.toString());
						insert = false;
						update = true;
						delete = false;
					} else if (strName.equals(YDefaultXmlElements.ELEMENT_Y_DELETE.toString())) {
						entidadeAtual = xmlDbLoad.getAttributeValue(null, YDefaultXmlElements.ATTR_Y_ENTITY.toString());
						insert = false;
						update = false;
						delete = true;
					} else if (insert && strName.equals(YDefaultXmlElements.ELEMENT_Y_ROW.toString())) {
						dbLoadList.addRowToInsert(Class.forName(pacoteAtual + "." + entidadeAtual), xmlDbLoad.nextText());
					} else if (update && strName.equals(YDefaultXmlElements.ELEMENT_Y_ROW.toString())) {
						dbLoadList.addRowToInsert(Class.forName(pacoteAtual + "." + entidadeAtual), xmlDbLoad.nextText());
					} else if (delete && strName.equals(YDefaultXmlElements.ELEMENT_Y_ROW.toString())) {
						dbLoadList.addRowToInsert(Class.forName(pacoteAtual + "." + entidadeAtual), xmlDbLoad.nextText());
					}
				} else if (eventType == XmlResourceParser.END_TAG) {
					final String strName = xmlDbLoad.getName();
					if (strName.equals(YDefaultXmlElements.ELEMENT_Y_PACKAGE.toString())) {
						pacoteAtual = "";
						insert = false;
						update = false;
						delete = false;
					} else if (strName.equals(YDefaultXmlElements.ELEMENT_Y_INSERT.toString())) {
						entidadeAtual = "";
						insert = false;
						update = false;
						delete = false;
					}
				}
				eventType = xmlDbLoad.next();
			}
		}
	}

}