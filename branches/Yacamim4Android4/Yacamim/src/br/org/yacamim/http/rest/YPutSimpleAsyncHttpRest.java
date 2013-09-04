/**
 * DefaultDataServiceHandler.java
 *
 * Copyright 2013 yacamim.org.br
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
package br.org.yacamim.http.rest;

import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.util.Log;
import br.org.yacamim.YBaseAsyncTask;
import br.org.yacamim.http.YAsyncHttpResponseHandler;
import br.org.yacamim.http.YCookieProxy;
import br.org.yacamim.http.YSimpleHttpResponseAdapter;
import br.org.yacamim.http.YSimpleHttpResponseAdapterImpl;
import br.org.yacamim.http.YTokenProxy;

/**
 * Hides some complexity on handling an REST HTTP calls throw PUT method.<br/>
 *
 * Since it is an <tt>AsyncTask</tt>, all HTTP call process runs in background
 * as the recommended way to do such job on Android.<br/>
 *
 * As it also is a subclass of <tt>YBaseAsyncTask</tt>, the HTTP call process only
 * is executed as long as there is available connectivity.<br/>
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 *
 * @see android.os.AsyncTask
 */
public class YPutSimpleAsyncHttpRest extends YBaseAsyncTask<YSimpleHttpRestRequestAdpater, YSimpleHttpResponseAdapter> {
	
	private static final String UTF_8 = "UTF-8";

	private static final String APPLICATION_JSON = "application/json";

	private static final String TAG = YPutSimpleAsyncHttpRest.class.getSimpleName();

	private YSimpleHttpRestRequestAdpater ySimpleHttpRestRequestAdpater;
	private YSimpleHttpResponseAdapter ySimpleHttpResponseAdapter;
	private YAsyncHttpResponseHandler asyncHttpResponseHandler;
	private YBasicHttpRestEntity restHttpEntity;

	/**
	 * 
	 * @param activity
	 * @param asyncHttpResponseHandler
	 */
	public YPutSimpleAsyncHttpRest(final Activity activity, 
			final YAsyncHttpResponseHandler asyncHttpResponseHandler) {
		super(activity);
		this.asyncHttpResponseHandler = asyncHttpResponseHandler;
	}

	/**
	 * 
	 * @param activity
	 * @param asyncHttpResponseHandler
	 * @param restHttpEntity
	 */
	public YPutSimpleAsyncHttpRest(final Activity activity, 
			final YAsyncHttpResponseHandler asyncHttpResponseHandler,
			final YBasicHttpRestEntity restHttpEntity) {
		super(activity);
		this.asyncHttpResponseHandler = asyncHttpResponseHandler;
		this.restHttpEntity = restHttpEntity;
	}

	/**
	 *
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected YSimpleHttpResponseAdapter doInBackground(YSimpleHttpRestRequestAdpater... ySimpleHttpRestRequestAdpater) {
		if(!super.isErrorWithoutConnectivity()) {
			return doRest(ySimpleHttpRestRequestAdpater);
		}
		return null;
	}

	/**
	 * Execute the HTTP call handling Cookies e Tokens.<br/>
	 * @param ySimpleHttpRestRequestAdpater
	 * @return
	 */
	protected YSimpleHttpResponseAdapter doRest(YSimpleHttpRestRequestAdpater... ySimpleHttpRestRequestAdpater) {
		try {
			this.configHttpEntity();

			this.ySimpleHttpRestRequestAdpater = ySimpleHttpRestRequestAdpater[0];

			final DefaultHttpClient client = new DefaultHttpClient();
			
			final HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);
			HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);

			final CookieStore cookieStore;
			if((cookieStore = buildCookieStore()) != null) {
				client.setCookieStore(cookieStore);
			}

            final HttpPut put = new HttpPut(this.ySimpleHttpRestRequestAdpater.getUri());
            put.setHeader("Accept", APPLICATION_JSON);
            put.setHeader("Content-Type", APPLICATION_JSON + "; charset=" + restHttpEntity.getContentEncoding().getValue());

            final BasicResponseHandler handler = new BasicResponseHandler();
			put.setEntity(restHttpEntity);
            

            // Http call
            final HttpResponse response = client.execute(put);

            this.ySimpleHttpResponseAdapter = new YSimpleHttpResponseAdapterImpl()
	            .setStatus(response.getStatusLine().getStatusCode())
	            .setBody(buildResponseBody(handler, response))
	            .addCookies(client.getCookieStore().getCookies())
	            .setRequestPath(this.ySimpleHttpRestRequestAdpater.getUri())
	            ;

            this.heandleTokens(response, this.ySimpleHttpResponseAdapter);

            YCookieProxy.getInstance().addCookies(this.ySimpleHttpResponseAdapter.getCookies());

		} catch (Exception e) {
			Log.e(TAG + ".doRest", e.getMessage());
		}
		return this.ySimpleHttpResponseAdapter;
	}

	/**
	 * 
	 */
	private void configHttpEntity() {
		if(restHttpEntity == null) {
			this.restHttpEntity = new YBasicHttpRestEntity();
			restHttpEntity.setContentType(APPLICATION_JSON);
			restHttpEntity.setContentEncoding(UTF_8);
		} else {
			if(restHttpEntity.getContentType() == null) {
				restHttpEntity.setContentType(APPLICATION_JSON);
			}
			if(restHttpEntity.getContentEncoding() == null) {
				restHttpEntity.setContentEncoding(UTF_8);
			}
		}
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
				if(this.ySimpleHttpRestRequestAdpater.getUri()
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
			Log.e(TAG + ".buildResponseBody", e.getMessage());
			return new StringBuilder();
		}
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
		final Set<String> tokens = this.ySimpleHttpRestRequestAdpater.getTokens();
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
		if(this.ySimpleHttpRestRequestAdpater.getBodyTokenRecoverHandler() != null) {
			final List<NameValuePair> tokens =
					this.ySimpleHttpRestRequestAdpater.getBodyTokenRecoverHandler().recover(
							ySimpleHttpResponseAdapter.getBody(), this.ySimpleHttpRestRequestAdpater.getTokens()
							);
			foundTokens = !tokens.isEmpty();
			YTokenProxy.getInstance().addTokens(tokens);
		}
		return foundTokens;
	}

}