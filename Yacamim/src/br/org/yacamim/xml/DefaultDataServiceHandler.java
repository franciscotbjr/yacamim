/**
 * DefaultDataServiceHandler.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.xml;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import br.org.yacamim.BaseActivity;
import br.org.yacamim.YacamimState;
import br.org.yacamim.entidade.DbScript;
import br.org.yacamim.entidade.ServiceURL;
import br.org.yacamim.ssl.HttpClient;

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
	 * @param _baseActivity
	 */
	public static void loadClassMapping(final BaseActivity _baseActivity) {
		try {
			if(!YacamimState.getInstance().isClassMappingLoaded()) {
				final XmlResourceParser xmlClassMapping = _baseActivity.getResources().getXml(YacamimState.getInstance().getYacamimResources().getIdResourceClassMapping());
				
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
	 * @param _baseActivity
	 */
	public static void loadParams(final BaseActivity _baseActivity) {
		try {
			if(!YacamimState.getInstance().isParamsLoaded()) {
				final XmlResourceParser xmlParams = _baseActivity.getResources().getXml(YacamimState.getInstance().getYacamimResources().getIdResourceParams());
				
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
	 * @param _baseActivity
	 */
	public static void loadURLsServices(final BaseActivity _baseActivity) {
    	try {
    		if(!YacamimState.getInstance().isServiceUrlsLoaded()) {
    			final XmlResourceParser xmlServicos = _baseActivity.getResources().getXml(YacamimState.getInstance().getYacamimResources().getIdResourceServices());
        		
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
	

	/**
	 * 
	 * @param _baseActivity
	 * @param _idUrl
	 * @param _nameValuePairs
	 * @return
	 */
	public static synchronized HttpEntity httpEntityFactory(final BaseActivity _baseActivity, final int _idUrl, final List<NameValuePair> _nameValuePairs) {
		try {
			if(checkInternetConnection(_baseActivity)) {
				final HttpClient client = new HttpClient(_baseActivity.getApplicationContext());
				final HttpPost httppost = new HttpPost(YacamimState.getInstance().getUrlServico(URL_RAIZ).url + YacamimState.getInstance().getUrlServico(_idUrl).url);
				if(_nameValuePairs != null) {
					httppost.setEntity(new UrlEncodedFormEntity(_nameValuePairs, "UTF-8"));
				}
				final HttpResponse response = client.execute(httppost);
				return response.getEntity();
			}
		} catch (Exception _e) {
			Log.e("DefaultDataServiceHandler.httpEntityFactory", _e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @param _baseActivity
	 * @return
	 */
	public static synchronized boolean checkInternetConnection(final BaseActivity _baseActivity) {
		boolean wifi = false;
		try {
			final ConnectivityManager connectivityManager = (ConnectivityManager)_baseActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
			if(connectivityManager != null
					&& connectivityManager.getActiveNetworkInfo() != null) {
				// handle wifi if the user wants to use only wifi connection
				if(YacamimState.getInstance().getPreferencias(_baseActivity).useOnlyWifi) {
					wifi = true;
					final NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
					if (mWifi != null && mWifi.isConnected()) {
						return true;
					}
				} else {
					if(connectivityManager.getActiveNetworkInfo().isAvailable()
							&& connectivityManager.getActiveNetworkInfo().isConnected()) {
						return true;
					}
				}
			}
		} catch (Exception _e) {
			Log.e("DefaultDataServiceHandler.checkInternetConnection", _e.getMessage());
		}
		_baseActivity.clearProgressDialogStack();
		if(wifi) {
			_baseActivity.displayDialogWifiAccess();
		} else {
			_baseActivity.displayDialogNetworkAccess();
		}
		return false;
	}
	
	/**
	 * 
	 * @param _baseActivity
	 * @return
	 */
	public static synchronized boolean checkWifiConnection(final BaseActivity _baseActivity) {
		try {
			final ConnectivityManager connectivityManager = (ConnectivityManager)_baseActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
			if(connectivityManager != null
					&& connectivityManager.getActiveNetworkInfo() != null) {
				// trata wifi se o usuário quiser usar apenas wifi
				if(YacamimState.getInstance().getPreferencias(_baseActivity).useOnlyWifi) {
					final NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
					if (mWifi != null && mWifi.isConnected()) {
						return true;
					}
				} else {
					// Wifi não é a única alternativa
					return true;
				}
			}
		} catch (Exception _e) {
			Log.e("DefaultDataServiceHandler.checkWifiConnection", _e.getMessage());
		}
		return false;
	}
	
	/**
	 * 
	 * @param _baseActivity
	 * @return
	 */
	public static boolean isOnLine(final BaseActivity _baseActivity) {
		return checkInternetConnection(_baseActivity) || checkWifiConnection(_baseActivity);
	}

}