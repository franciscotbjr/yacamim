package br.org.yacamim.http;

import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import android.util.Log;

public class YSimpleAsyncHttp extends AsyncTask<YSimpleHttpRequestAdpater, Integer, YSimpleHttpResponseAdapter> {
	
	private YSimpleHttpRequestAdpater ySimpleHttpAdpater;
	private YSimpleHttpResponseAdapter ySimpleHttpResponseAdapter;
	private YAsyncHttpResponseHandler asyncHttpResponseHandler;
	
	/**
	 * 
	 * @param asyncHttpResponseHandler
	 */
	public YSimpleAsyncHttp(YAsyncHttpResponseHandler asyncHttpResponseHandler) {
		super();
		this.asyncHttpResponseHandler = asyncHttpResponseHandler;
	}
	
	/**
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected YSimpleHttpResponseAdapter doInBackground(YSimpleHttpRequestAdpater... arg0) {
		try {
			this.ySimpleHttpAdpater = arg0[0];
			
			final HttpParams params = new BasicHttpParams();
			final DefaultHttpClient client = new DefaultHttpClient(params);

			final CookieStore cookieStore;
			if((cookieStore = buildCookieStore()) != null) {
				client.setCookieStore(cookieStore);
			}
            
            final HttpPost post = new HttpPost(this.ySimpleHttpAdpater.getUri());
            
            final BasicResponseHandler handler = new BasicResponseHandler();
            
            // Encoding parameters
            post.setEntity(new UrlEncodedFormEntity(this.updateParamsWithTokens(), ySimpleHttpAdpater.getEnconding()));
                        
            // Http call
            final HttpResponse response = client.execute(post);
            
            this.ySimpleHttpResponseAdapter = new YSimpleHttpResponseAdapterImpl()
            .setStatus(response.getStatusLine().getStatusCode())
            .setBody(buildResponseBody(handler, response))
            .addCookies(client.getCookieStore().getCookies());
            
            this.heandleTokens(response, this.ySimpleHttpResponseAdapter);
            
            YCookieProxy.getInstance().addCookies(this.ySimpleHttpResponseAdapter.getCookies());
			
		} catch (Exception e) {
			Log.e("YSimpleHttpResponseAdapter.doInBackground", e.getMessage());
		}
		return this.ySimpleHttpResponseAdapter;
	}

	/**
	 * 
	 * @return
	 */
	private CookieStore buildCookieStore() {
		CookieStore cookieStore = null;
		if(!YCookieProxy.getInstance().getCookies().isEmpty()) {
			for(final Cookie cookieRec : YCookieProxy.getInstance().getCookies()) {
				String baseUri = cookieRec.getDomain() + cookieRec.getPath();
				if(this.ySimpleHttpAdpater.getUri()
						.replace("http://", "")
						.replace("https://", "")
						.startsWith(baseUri));
				if(cookieStore == null) {
					cookieStore = new BasicCookieStore();
				}
				cookieStore.addCookie(cookieRec);
			}
		}
		return cookieStore;
	}
	
	/**
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(YSimpleHttpResponseAdapter result) {
		super.onPostExecute(result);
		this.asyncHttpResponseHandler.onAsyncHttpResponse(result);
	}

	/**
	 * 
	 * @param handler
	 * @param response
	 * @return
	 */
	private StringBuilder buildResponseBody(final BasicResponseHandler handler, final HttpResponse response) {
		try {
			return new StringBuilder(handler.handleResponse(response));
		} catch (Exception e) {
			Log.e("YSimpleHttpResponseAdapter.buildResponseBody", e.getMessage());
			return new StringBuilder();
		}
	}

	/**
	 * 
	 * @return
	 */
	private List<NameValuePair> updateParamsWithTokens() {
		List<NameValuePair> parameters = this.ySimpleHttpAdpater.getParameters();
		List<NameValuePair> tokens = YTokenProxy.getInstance().getTokens();
		if(!tokens.isEmpty() && !this.ySimpleHttpAdpater.getTokens().isEmpty()) {
			for(NameValuePair token : tokens) {
				for(String strToken : this.ySimpleHttpAdpater.getTokens()) {
					if(token.getName().equals(strToken)) {
						parameters.add(token);
					}
				}
			}
		}
		return parameters;
	}

	/**
	 * 
	 * @param response
	 * @param ySimpleHttpResponseAdapter 
	 */
	private void heandleTokens(final HttpResponse response, final YSimpleHttpResponseAdapter ySimpleHttpResponseAdapter) {
		if(!handleTokensFromBody(response, ySimpleHttpResponseAdapter)) {
			// 
			this.handleTokensFromHeader(response);
		}
	}

	/**
	 * 
	 * @param response
	 */
	private void handleTokensFromHeader(final HttpResponse response) {
		final Set<String> tokens = this.ySimpleHttpAdpater.getTokens();
		for(String tokenName : tokens) {
			if(response.containsHeader(tokenName)) {
				YTokenProxy.getInstance().addToken(tokenName, response.getFirstHeader(tokenName).getValue());
			}
			Log.i(tokenName, response.containsHeader(tokenName)+"");
		}
	}

	/**
	 * 
	 * @param response
	 * @param ySimpleHttpResponseAdapter 
	 */
	private boolean handleTokensFromBody(final HttpResponse response, final YSimpleHttpResponseAdapter ySimpleHttpResponseAdapter) {
		boolean foundTokens = false;
		if(this.ySimpleHttpAdpater.getBodyTokenRecoverHandler() != null) {
			final List<NameValuePair> tokens = 
					this.ySimpleHttpAdpater.getBodyTokenRecoverHandler().recover(
							ySimpleHttpResponseAdapter.getBody(), this.ySimpleHttpAdpater.getTokens()
							);
			foundTokens = !tokens.isEmpty();
			YTokenProxy.getInstance().addTokens(tokens);
		}
		return foundTokens;
	}

}