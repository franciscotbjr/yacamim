/**
 * YProgressDialogFragment.java
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
package br.org.yacamim.ui.components;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import br.org.yacamim.util.YUtilString;

/**
 * Classe YProgressDialogFragment TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YProgressDialogFragment  extends DialogFragment {
	
	private static final String MESSAGE = "message";
	private static final String TITLE = "title";
	
	private int dialogId;
	
	/**
	 * 
	 */
	public YProgressDialogFragment() {
		super();
	}
	
	/**
	 * 
	 * @param dialogId
	 * @param titleTextResourceId
	 * @param messageResourceId
	 * @return
	 */
	public static YProgressDialogFragment newInstance(
			final int dialogId,
			final int titleTextResourceId, 
			final int messageResourceId 
			) {
		final YProgressDialogFragment alertDialogFragment = new YProgressDialogFragment();
		alertDialogFragment.setDialogId(dialogId);
		final Bundle bundle = new Bundle();
		bundle.putInt(TITLE, titleTextResourceId);
		bundle.putInt(MESSAGE, messageResourceId);
		alertDialogFragment.setArguments(bundle);
		return alertDialogFragment;
	}

	/**
	 * 
	 * @param dialogId
	 * @param titleTextResource
	 * @param messageResource
	 * @return
	 */
	public static YProgressDialogFragment newInstance(
			final int dialogId,
			final String titleTextResource, 
			final String messageResource 
			) {
		final YProgressDialogFragment alertDialogFragment = new YProgressDialogFragment();
		alertDialogFragment.setDialogId(dialogId);
		final Bundle bundle = new Bundle();
		bundle.putString(TITLE, titleTextResource);
		bundle.putString(MESSAGE, messageResource);
		alertDialogFragment.setArguments(bundle);
		return alertDialogFragment;
	}

	
	/**
	 * 
	 * @see android.app.DialogFragment#onCreate(android.os.Bundle)
	 */
    @Override    
    public void onCreate(final Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	this.setCancelable(true);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        setStyle(style,theme);
    }

    /**
     * 
     * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
     */
    @Override    
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
    	final Activity activity = getActivity();
    	String message;
    	if(YUtilString.isEmptyString(message = this.getArguments().getString(MESSAGE))) {
    		int messageResource;
    		if((messageResource = this.getArguments().getInt(MESSAGE)) > 0) {
    			message = activity.getText(messageResource).toString();
    		} else {
    			message = "";
    		}
    	}
    	
    	String title;
		if(YUtilString.isEmptyString(title = this.getArguments().getString(TITLE))) {
			int titleResource;
			if((titleResource = this.getArguments().getInt(TITLE)) > 0) {
				title = activity.getText(titleResource).toString();
	    	} else {
	    		title = "";
	    	}	
    	}

		final ProgressDialog dialog = new ProgressDialog(getActivity());
		dialog.setMessage(message);
		dialog.setTitle(title);
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		
		final OnKeyListener keyListener = new OnKeyListener() {
			 
			@Override
			public boolean onKey(final DialogInterface dialog, 
					final int keyCode, 
					final KeyEvent event) {
				return true;
			}
		
		};
		dialog.setOnKeyListener(keyListener);

    	return dialog;
    }
    
	/**
	 * 
	 * @param dialogId
	 */
	private void setDialogId(final int dialogId) {
		this.dialogId = dialogId;
	}   

	/**
	 * 
	 * @return
	 */
    public int getDialogId() {
		return this.dialogId;
	}
   
}