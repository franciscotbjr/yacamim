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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
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
import br.org.yacamim.util.YUtilString;

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
public class YGetImageSimpleAsyncHttpRest extends YBaseAsyncTask<YSimpleHttpRestRequestAdpater, YSimpleHttpResponseAdapter> {
	
	private static final String UTF_8 = "UTF-8";

	private static final String TAG = YGetImageSimpleAsyncHttpRest.class.getSimpleName();

	private YSimpleHttpRestRequestAdpater ySimpleHttpRestRequestAdpater;
	private YSimpleHttpResponseAdapter ySimpleHttpResponseAdapter;
	private YAsyncHttpResponseHandler asyncHttpResponseHandler;

	/**
	 * 
	 * @param activity
	 * @param asyncHttpResponseHandler
	 */
	public YGetImageSimpleAsyncHttpRest(final Activity activity, 
			final YAsyncHttpResponseHandler asyncHttpResponseHandler) {
		super(activity);
		this.asyncHttpResponseHandler = asyncHttpResponseHandler;
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
			this.ySimpleHttpRestRequestAdpater = ySimpleHttpRestRequestAdpater[0];

			final DefaultHttpClient client = new DefaultHttpClient();
			
			final HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 30 * 1000);
			HttpConnectionParams.setSoTimeout(httpParams, 30 * 1000);

			final CookieStore cookieStore;
			if((cookieStore = buildCookieStore()) != null) {
				client.setCookieStore(cookieStore);
			}

            final HttpGet get = new HttpGet(this.ySimpleHttpRestRequestAdpater.getUri());
            get.setHeader("Accept", buildAcceptHeader());
            get.setHeader("Content-Type", buildAcceptHeader() + "; charset=" + getEncoding());

            // Http call
            final HttpResponse response = client.execute(get);
            
            

            this.ySimpleHttpResponseAdapter = new YSimpleHttpResponseAdapterImpl()
	            .setStatus(response.getStatusLine().getStatusCode())
	            .setInputStream(buildinputStream(response))
	            .addCookies(client.getCookieStore().getCookies())
	            .setRequestPath(this.ySimpleHttpRestRequestAdpater.getUri())
	            ;

            this.heandleTokens(response, this.ySimpleHttpResponseAdapter);

            YCookieProxy.getInstance().addCookies(this.ySimpleHttpResponseAdapter.getCookies());

		} catch (Exception e) {
			Log.e(TAG + ".doRest", YUtilString.nullToEmptyString(e.getMessage()));
		}
		return this.ySimpleHttpResponseAdapter;
	}
	
	private InputStream buildinputStream(HttpResponse response) {
		ByteArrayInputStream byteArrayInputStream = null;
		InputStream inputStream = null;
		try {
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			final HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
			if(entity != null) {
				final byte[] buffer = new byte[1024];
				while(inputStream.read(buffer) != -1) {
					byteArrayOutputStream.write(buffer);
				}
			}
			byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return byteArrayInputStream;
	}

	private String buildAcceptHeader() {
		final StringBuilder builder = new StringBuilder();
		builder.append("application/json");
		builder.append(",image/gif");
		builder.append(",image/jpeg");
		builder.append(",image/png");
		return builder.toString();
	}

	/**
	 * 
	 * @return
	 */
	private String getEncoding() {
		if(YUtilString.isEmptyString(this.ySimpleHttpRestRequestAdpater.getEnconding())) {
			return UTF_8;
		}
		return this.ySimpleHttpRestRequestAdpater.getEnconding();
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
		if(this.asyncHttpResponseHandler != null) {
			this.asyncHttpResponseHandler.onAsyncHttpResponse(result);
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