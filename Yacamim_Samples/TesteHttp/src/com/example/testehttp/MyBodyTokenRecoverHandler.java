package com.example.testehttp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import br.org.yacamim.http.YBodyTokenRecoverHandler;

public class MyBodyTokenRecoverHandler implements YBodyTokenRecoverHandler {

	/**
	 * 
	 */
	public MyBodyTokenRecoverHandler() {
		super();
	}

	/**
	 * 
	 * @see br.org.yacamim.http.YBodyTokenRecoverHandler#recover(java.lang.StringBuilder, java.util.Set)
	 */
	@Override
	public List<NameValuePair> recover(StringBuilder body, Set<String> tokenNames) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		try {
			JSONObject jsonObject = new JSONObject(body.toString());
			if(!jsonObject.isNull("resposta") 
					&& !jsonObject.getJSONObject("resposta").isNull("testToken")) {
				String bodyToken = jsonObject.getJSONObject("resposta").getString("testToken");
				nameValuePairs.add(new BasicNameValuePair("testToken", bodyToken));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return nameValuePairs;
	}

}
