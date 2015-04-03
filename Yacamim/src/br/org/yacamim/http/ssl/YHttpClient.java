/**
 * HttpClient.java
 *
 */
package br.org.yacamim.http.ssl;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * 
 * Class HttpClient TODO
 * 
 * @author Community
 * @version 1.0
 * @since 1.0
 */
public class YHttpClient extends DefaultHttpClient {

	/**
	 * 
	 */
	public YHttpClient() {
    	super();
    }
    
	/**
	 * 
	 * @param httpParams
	 */
    public YHttpClient(final HttpParams httpParams) {
    	super(httpParams);
    }
 
    /**
     * 
     */
    @Override
    protected ClientConnectionManager createClientConnectionManager() {
    	SchemeRegistry schemeRegistry = new SchemeRegistry();
    	schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    	schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
    	 
    	HttpParams params = new BasicHttpParams();
    	params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
    	params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
    	params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
    	HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
    	 
    	return new SingleClientConnManager(params, schemeRegistry);
    }
 
}
