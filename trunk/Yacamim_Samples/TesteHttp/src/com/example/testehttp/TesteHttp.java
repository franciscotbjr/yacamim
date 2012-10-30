package com.example.testehttp;

import br.org.yacamim.http.YAsyncHttpResponseHandler;
import br.org.yacamim.http.YSimpleAsyncHttp;
import br.org.yacamim.http.YSimpleHttpRequestAdpaterImpl;
import br.org.yacamim.http.YSimpleHttpResponseAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class TesteHttp extends Activity implements YAsyncHttpResponseHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_http);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_teste_http, menu);
        return true;
    }
    
    public void testeHttp(View view) {
    	try {
    		YSimpleAsyncHttp ySimpleAsyncHttp = new YSimpleAsyncHttp(this);
    		ySimpleAsyncHttp.execute(new YSimpleHttpRequestAdpaterImpl()
	    		.setUri("http://192.168.2.5:8080/TesteHttpServer/httpTesteServlet")
	    		.addParam("usuario", "nome")
	    		.addParam("senha", "123456")
	    		.manageToken("testToken")
	    		.setBodyTokenRecoverHandler(new MyBodyTokenRecoverHandler())
	    		);
    		TextView textView = (TextView)this.findViewById(R.id.textView1);
        	textView.setText("Aguarde...");
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    // BaseActivity
    public void onAsyncHttpResponse(YSimpleHttpResponseAdapter ySimpleHttpResponseAdapter) {
    	TextView textView = (TextView)this.findViewById(R.id.textView1);
    	textView.setText(ySimpleHttpResponseAdapter.getBody());
    	Log.i("", ySimpleHttpResponseAdapter.getBody().toString());
    }
}
