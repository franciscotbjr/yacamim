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

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;
import br.org.yacamim.BaseActivity;
import br.org.yacamim.YacamimConfig;
import br.org.yacamim.YacamimState;
import br.org.yacamim.entity.DbScript;
import br.org.yacamim.entity.ServiceURL;

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
	 * @param baseActivity
	 */
	public static void loadClassMapping(final BaseActivity baseActivity) {
		try {
			if(!YacamimState.getInstance().isClassMappingLoaded()) {
				final XmlResourceParser xmlClassMapping = baseActivity.getResources().getXml(YacamimState.getInstance().getYacamimResources().getIdResourceClassMapping());

				int eventType = -1;

				while (eventType != XmlResourceParser.END_DOCUMENT) {
					if (eventType == XmlResourceParser.START_TAG) {

						final String strName = xmlClassMapping.getName();
						if (strName.equals(YDefaultXmlElements.ELEMENT_CLASS.toString())) {
							final String classeServico = xmlClassMapping.getAttributeValue(null, YDefaultXmlElements.ATTR_SERVICE_NAME.toString());
							final String classeAndroid = xmlClassMapping.getAttributeValue(null, YDefaultXmlElements.ATTR_LOCAL_NAME.toString());
							YacamimState.getInstance().getMapeamentoClasses().add(classeServico, classeAndroid);
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
	 * @param baseActivity
	 */
	public static void loadParams(final BaseActivity baseActivity) {
		try {
			if(!YacamimState.getInstance().isParamsLoaded()) {
				final XmlResourceParser xmlParams = baseActivity.getResources().getXml(YacamimState.getInstance().getYacamimResources().getIdResourceParams());

				int eventType = -1;

				while (eventType != XmlResourceParser.END_DOCUMENT) {
					if (eventType == XmlResourceParser.START_TAG) {

						final String strName = xmlParams.getName();
						if (strName.equals(YDefaultXmlElements.ELEMENT_Y_CONFIG_ITEM.toString())) {
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
	 * @param baseActivity
	 */
	public static void loadServiceURLs(final BaseActivity baseActivity) {
    	try {
    		if(!YacamimState.getInstance().isServiceUrlsLoaded()) {
    			final XmlResourceParser xmlServicos = baseActivity.getResources().getXml(YacamimState.getInstance().getYacamimResources().getIdResourceServices());

        		int eventType = -1;

            	while (eventType != XmlResourceParser.END_DOCUMENT) {
            		if (eventType == XmlResourceParser.START_TAG) {

            			final String strName = xmlServicos.getName();
            			if (strName.equals(YDefaultXmlElements.ELEMENT_SERVICE.toString())) {
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
			final XmlResourceParser xmlDbScript = context.getResources().getXml(YacamimState.getInstance().getYacamimResources().getIdResourceDbScript());

			int eventType = -1;

			while (eventType != XmlResourceParser.END_DOCUMENT) {
				if (eventType == XmlResourceParser.START_TAG) {
					final String strName = xmlDbScript.getName();

					if (strName.equals(YDefaultXmlElements.ELEMENT_DB.toString())) {
						dbScript.setDbVersion(Integer.parseInt(xmlDbScript.getAttributeValue(null, YDefaultXmlElements.ATTR_VERSION.toString())));
					}
					else if (strName.equals(YDefaultXmlElements.ELEMENT_CREATE_TABLE.toString())) {
						final String script = xmlDbScript.nextText();
						dbScript.getCreateTables().add(script);
					}
					else if (strName.equals(YDefaultXmlElements.ELEMENT_ALTER_TABLE.toString())) {
						final String script = xmlDbScript.nextText();
						dbScript.getCreateTables().add(script);
					}
					else if (strName.equals(YDefaultXmlElements.ELEMENT_INSERT.toString())) {
						final String script = xmlDbScript.nextText();
						dbScript.getInserts().add(script);
					}
					else if (strName.equals(YDefaultXmlElements.ELEMENT_UPDATE.toString())) {
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
	 * @param baseActivity
	 */
	public static void loadConfigs(final BaseActivity baseActivity) {
    	try {
    		if(!YacamimConfig.getInstance().isConfigItemsLoaded()) {
    			final XmlResourceParser xmlConfigs = baseActivity.getResources().getXml(YacamimState.getInstance().getYacamimResources().getIdResourceServices());

        		int eventType = -1;

        		String pacoteAtual = "";
            	while (eventType != XmlResourceParser.END_DOCUMENT) {
            		final String strName = xmlConfigs.getName();
            		
            		if (eventType == XmlResourceParser.START_TAG) {

            			if (strName.equals(YDefaultXmlElements.ELEMENT_PACKAGE.toString())) {
            				pacoteAtual = xmlConfigs.getAttributeValue(null, YDefaultXmlElements.ATTR_NAME.toString());
            			} else if (strName.equals(YDefaultXmlElements.ELEMENT_ENTITY_MAPPING.toString())) {
            				
            				
            				
            			} else if (strName.equals(YDefaultXmlElements.ELEMENT_Y_CONFIG_ITEM.toString()) 
            					&& pacoteAtual!= null 
            					&& !pacoteAtual.trim().equals("")) {
            				YacamimConfig.getInstance().add(
            						xmlConfigs.getAttributeValue(null, YDefaultXmlElements.ATTR_NAME.toString()), 
            						xmlConfigs.getAttributeValue(null, YDefaultXmlElements.ATTR_VALUE.toString())
            						);
            			}
            		} else if (eventType == XmlResourceParser.END_TAG) {
            			if (strName.equals(YDefaultXmlElements.ELEMENT_PACKAGE.toString())) {
            				pacoteAtual = "";
            			}
            		}
            		eventType = xmlConfigs.next();
            	}
            	YacamimConfig.getInstance().setConfigItemsLoaded(true);
    		}
		} catch (Exception e) {
			Log.e(YLoader.class.getName() + ".loadConfigs", e.getMessage());
		}
    }

}