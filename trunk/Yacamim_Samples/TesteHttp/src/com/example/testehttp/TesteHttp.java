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
    		// Creates a new instance of YSimpleAsyncHttp
    		YSimpleAsyncHttp ySimpleAsyncHttp = new YSimpleAsyncHttp(this, (YAsyncHttpResponseHandler)this);
    		
    		// Call its "execute" inherited from AsyncTask
    		ySimpleAsyncHttp.execute(
    				// New Adapter
    				new YSimpleHttpRequestAdpaterImpl()
	    		.setUri("http://192.168.2.5:8080/TesteHttpServer/httpTesteServlet")
	    		.addParam("usuario", "nome")
	    		.addParam("senha", "123456")
	    		// Provides a token name that would exist inside HTTP response header or
	    		// inside HTTP response body
	    		.manageToken("testToken")
	    		// Provides a handler that is responsible for extract a toke
	    		// named "testToken" from the HTTP response body, as long as it is there
	    		.setBodyTokenRecoverHandler(new MyBodyTokenRecoverHandler())
	    		);
    		TextView textView = (TextView)this.findViewById(R.id.textView1);
        	textView.setText("Wait...");
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    /**
     * This method will be call at end of the HTTP call started throw the "execute" method call 
     * over the instance of YSimpleAsyncHttp that was triggered "testeHttp" method of this class instance.<br/>
     * 
     * @see br.org.yacamim.http.YAsyncHttpResponseHandler#onAsyncHttpResponse(br.org.yacamim.http.YSimpleHttpResponseAdapter)
     */
    public void onAsyncHttpResponse(YSimpleHttpResponseAdapter ySimpleHttpResponseAdapter) {
    	TextView textView = (TextView)this.findViewById(R.id.textView1);
    	textView.setText(ySimpleHttpResponseAdapter.getBody());
    	Log.i("", ySimpleHttpResponseAdapter.getBody().toString());
    }
}
