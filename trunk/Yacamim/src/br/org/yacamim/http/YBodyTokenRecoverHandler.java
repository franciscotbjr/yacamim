package br.org.yacamim.http;

import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;

public interface YBodyTokenRecoverHandler {
	
	public List<NameValuePair> recover(StringBuilder body, Set<String> tokenNames);

}
