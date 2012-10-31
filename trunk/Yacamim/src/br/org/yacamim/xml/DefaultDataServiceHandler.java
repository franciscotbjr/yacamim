/**
 * DefaultDataServiceHandler.java
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
package br.org.yacamim.xml;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;
import br.org.yacamim.BaseActivity;
import br.org.yacamim.YacamimState;
import br.org.yacamim.entity.DbScript;
import br.org.yacamim.entity.ServiceURL;

/**
 * Class DefaultDataServiceHandler TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class DefaultDataServiceHandler {
	
	/**
	 * 
	 */
	public static final int URL_RAIZ = 0;

	/**
	 * 
	 */
	public DefaultDataServiceHandler() {
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
						if (strName.equals(DefaultXmlElements.ELEMENT_CLASS.toString())) {
							final String classeServico = xmlClassMapping.getAttributeValue(null, DefaultXmlElements.ATTR_SERVICE_NAME.toString());
							final String classeAndroid = xmlClassMapping.getAttributeValue(null, DefaultXmlElements.ATTR_LOCAL_NAME.toString());
							YacamimState.getInstance().getMapeamentoClasses().add(classeServico, classeAndroid);
						}
					}
					eventType = xmlClassMapping.next();
				}
				YacamimState.getInstance().setClassMappingLoaded(true);
			}
		} catch (Exception _e) {
			Log.e("DefaultDataServiceHandler.loadClassMapping", _e.getMessage());
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
						if (strName.equals(DefaultXmlElements.ELEMENT_PARAM.toString())) {
							final String name = xmlParams.getAttributeValue(null, DefaultXmlElements.ELEMENT_NAME.toString());
							final String value = xmlParams.getAttributeValue(null, DefaultXmlElements.ELEMENT_VALUE.toString());
							YacamimState.getInstance().getParams().put(name, value);
						}
					}
					eventType = xmlParams.next();
				}
				YacamimState.getInstance().setParamsLoaded(true);
			}
		} catch (Exception _e) {
			Log.e("DefaultDataServiceHandler.loadParams", _e.getMessage());
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
            			if (strName.equals(DefaultXmlElements.ELEMENT_SERVICE.toString())) {
            				final ServiceURL serviceURL = new ServiceURL();
            				serviceURL.id = Integer.parseInt(xmlServicos.getAttributeValue(null, DefaultXmlElements.ATTR_ID.toString()));
            				serviceURL.url = xmlServicos.getAttributeValue(null, DefaultXmlElements.ATTR_URL.toString());
            				YacamimState.getInstance().getUrlServicos().add(serviceURL);
            			}
            		}
            		eventType = xmlServicos.next();
            	}
            	YacamimState.getInstance().setServiceUrlsLoaded(true);
    		}
		} catch (Exception _e) {
			Log.e("DefaultDataServiceHandler.loadURLsServices", _e.getMessage());
		}
    }
	
	/**
	 * 
	 * @param _context
	 */
	public static DbScript loadDBScript(final Context _context) {
		final DbScript dbScript = new DbScript();
		try {
			final XmlResourceParser xmlDbScript = _context.getResources().getXml(YacamimState.getInstance().getYacamimResources().getIdResourceDbScript());
			
			int eventType = -1;
			
			while (eventType != XmlResourceParser.END_DOCUMENT) {
				if (eventType == XmlResourceParser.START_TAG) {
					final String strName = xmlDbScript.getName();
					
					if (strName.equals(DefaultXmlElements.ELEMENT_DB.toString())) {
						dbScript.setDbVersion(Integer.parseInt(xmlDbScript.getAttributeValue(null, DefaultXmlElements.ATTR_VERSION.toString())));
					} 
					else if (strName.equals(DefaultXmlElements.ELEMENT_CREATE_TABLE.toString())) {
						final String script = xmlDbScript.nextText();
						dbScript.getCreateTables().add(script);
					} 
					else if (strName.equals(DefaultXmlElements.ELEMENT_ALTER_TABLE.toString())) {
						final String script = xmlDbScript.nextText();
						dbScript.getCreateTables().add(script);
					} 
					else if (strName.equals(DefaultXmlElements.ELEMENT_INSERT.toString())) {
						final String script = xmlDbScript.nextText();
						dbScript.getInserts().add(script);
					}
					else if (strName.equals(DefaultXmlElements.ELEMENT_UPDATE.toString())) {
						final String script = xmlDbScript.nextText();
						dbScript.getUpdates().add(script);
					}
				}
				eventType = xmlDbScript.next();
			}
		} catch (Exception _e) {
			Log.e("DefaultDataServiceHandler.loadDBScript", _e.getMessage());
		}
		return dbScript;
	}

}