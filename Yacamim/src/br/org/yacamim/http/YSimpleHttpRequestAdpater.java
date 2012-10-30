package br.org.yacamim.http;

import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;

public interface YSimpleHttpRequestAdpater {
	
	/**
	 * 
	 * @return
	 */
	public String getEnconding();
	
	/**
	 * 
	 * @return
	 */
	public String getUri();
	
	/**
	 * 
	 * @return
	 */
	public List<NameValuePair> getParameters();
	
	/**
	 * 
	 * @param uri
	 * @return
	 */
	public YSimpleHttpRequestAdpater setUri(String uri);
	
	/**
	 * 
	 * @param enconding
	 * @return
	 */
	public YSimpleHttpRequestAdpater setEncondig(String enconding);
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public YSimpleHttpRequestAdpater addParams(final List<NameValuePair> params);
	
	/**
	 * 
	 * @param param
	 * @return
	 */
	public YSimpleHttpRequestAdpater addParam(NameValuePair param);

	/**
	 * 
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public YSimpleHttpRequestAdpater addParam(String paramName, String paramValue);
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	public YSimpleHttpRequestAdpater manageToken(String token);
	
	/**
	 * 
	 * @param yBodyTokenRecoverHandler
	 * @return
	 */
	public YSimpleHttpRequestAdpater setBodyTokenRecoverHandler(YBodyTokenRecoverHandler yBodyTokenRecoverHandler);
	
	/**
	 * 
	 * @return
	 */
	public YBodyTokenRecoverHandler getBodyTokenRecoverHandler();
	
	/**
	 * 
	 * @return
	 */
	public Set<String> getTokens();

}
