/**
 * YAlertDialogFragment.java
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

import br.org.yacamim.util.YUtilString;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Classe YAlertDialogFragment TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YAlertDialogFragment  extends DialogFragment implements DialogInterface.OnClickListener {
	
	private static final String MESSAGE = "message";
	private static final String NEGATIVE_BUTTON = "negativeButton";
	private static final String POSITIVE_BUTTON = "positiveButton";
	private static final String NEUTRAL_BUTTON = "neutralButton";
	private static final String TITLE = "title";
	
	private boolean twoButtons;
	private boolean threeButtons;
	
	private int dialogId;
	
	/**
	 * 
	 */
	public YAlertDialogFragment() {
		super();
	}
	
	/**
	 * 
	 * @param titleTextResource
	 * @param messageResource
	 * @param positivoButtonLabelResource
	 * @return
	 */
	public static YAlertDialogFragment newInstance(
			final int dialogId,
			final int titleTextResource, 
			final int messageResource, 
			final int positivoButtonLabelResource 
			) {
		final YAlertDialogFragment alertDialogFragment = new YAlertDialogFragment();
		alertDialogFragment.setDialogId(dialogId);
		alertDialogFragment.configState(false, false);
		final Bundle bundle = new Bundle();
		bundle.putInt(TITLE, titleTextResource);
		bundle.putInt(POSITIVE_BUTTON, positivoButtonLabelResource);
		bundle.putInt(MESSAGE, messageResource);
		alertDialogFragment.setArguments(bundle);
		return alertDialogFragment;
	}

	/**
	 * 
	 * @param dialogId
	 * @param titleTextResource
	 * @param messageResource
	 * @param positivoButtonLabelResource
	 * @return
	 */
	public static YAlertDialogFragment newInstance(
			final int dialogId,
			final String titleTextResource, 
			final String messageResource, 
			final int positivoButtonLabelResource 
			) {
		final YAlertDialogFragment alertDialogFragment = new YAlertDialogFragment();
		alertDialogFragment.setDialogId(dialogId);
		alertDialogFragment.configState(false, false);
		final Bundle bundle = new Bundle();
		bundle.putString(TITLE, titleTextResource);
		bundle.putInt(POSITIVE_BUTTON, positivoButtonLabelResource);
		bundle.putString(MESSAGE, messageResource);
		alertDialogFragment.setArguments(bundle);
		return alertDialogFragment;
	}

	/**
	 * 
	 * @param titleTextResource
	 * @param messageResource
	 * @param positivoButtonLabelResource
	 * @param negativeButtonLabelResource
	 * @return
	 */
	public static YAlertDialogFragment newInstance(
			final int dialogId,
			final int titleTextResource, 
			final int messageResource, 
			final int positivoButtonLabelResource, 
			final int negativeButtonLabelResource 
			) {
		final YAlertDialogFragment alertDialogFragment = new YAlertDialogFragment();
		alertDialogFragment.setDialogId(dialogId);
		alertDialogFragment.configState(true, false);
		final Bundle bundle = new Bundle();
		bundle.putInt(TITLE, titleTextResource);
		bundle.putInt(POSITIVE_BUTTON, positivoButtonLabelResource);
		bundle.putInt(NEGATIVE_BUTTON, negativeButtonLabelResource);
		bundle.putInt(MESSAGE, messageResource);
		alertDialogFragment.setArguments(bundle);
		return alertDialogFragment;
	}

	/**
	 * 
	 * @param dialogId
	 * @param titleTextResource
	 * @param messageResource
	 * @param positivoButtonLabelResource
	 * @param negativeButtonLabelResource
	 * @return
	 */
	public static YAlertDialogFragment newInstance(
			final int dialogId,
			final String titleTextResource, 
			final String messageResource, 
			final int positivoButtonLabelResource, 
			final int negativeButtonLabelResource 
			) {
		final YAlertDialogFragment alertDialogFragment = new YAlertDialogFragment();
		alertDialogFragment.setDialogId(dialogId);
		alertDialogFragment.configState(true, false);
		final Bundle bundle = new Bundle();
		bundle.putString(TITLE, titleTextResource);
		bundle.putInt(POSITIVE_BUTTON, positivoButtonLabelResource);
		bundle.putInt(NEGATIVE_BUTTON, negativeButtonLabelResource);
		bundle.putString(MESSAGE, messageResource);
		alertDialogFragment.setArguments(bundle);
		return alertDialogFragment;
	}

	/**
	 * 
	 * @param titleTextResource
	 * @param messageResource
	 * @param positivoButtonLabelResource
	 * @param negativeButtonLabelResource
	 * @param neutralButtonLabelResource
	 * @return
	 */
	public static YAlertDialogFragment newInstance(
			final int dialogId,
			final int titleTextResource, 
			final int messageResource, 
			final int positivoButtonLabelResource, 
			final int negativeButtonLabelResource, 
			final int neutralButtonLabelResource  
			) {
		final YAlertDialogFragment alertDialogFragment = new YAlertDialogFragment();
		alertDialogFragment.setDialogId(dialogId);
		alertDialogFragment.configState(false, true);
		final Bundle bundle = new Bundle();
		bundle.putInt(TITLE, titleTextResource);
		bundle.putInt(POSITIVE_BUTTON, positivoButtonLabelResource);
		bundle.putInt(NEGATIVE_BUTTON, negativeButtonLabelResource);
		bundle.putInt(NEUTRAL_BUTTON, neutralButtonLabelResource);
		bundle.putInt(MESSAGE, messageResource);
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
    	
    	final AlertDialog.Builder builder = 
    	    new AlertDialog.Builder(activity)
    	    .setMessage(message);

    	String title;
		if(YUtilString.isEmptyString(title = this.getArguments().getString(TITLE))) {
			int titleResource;
			if((titleResource = this.getArguments().getInt(TITLE)) > 0) {
				title = activity.getText(titleResource).toString();
	    	} else {
	    		title = "";
	    	}	
    	}
		builder.setTitle(title);

    	builder.setPositiveButton(activity.getText(this.getArguments().getInt(POSITIVE_BUTTON)), this);

    	if (this.isTwoButtons()
    			|| this.isThreeButtons()) {
    		builder.setNegativeButton(activity.getText(this.getArguments().getInt(NEGATIVE_BUTTON)), this);
    	}
    	
    	if(this.isThreeButtons()) {
    		builder.setNeutralButton(activity.getText(this.getArguments().getInt(NEUTRAL_BUTTON)), this);
    	}
    	
    	return builder.create();
    }

    /**
     * 
     * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
     */
    public void onClick(final DialogInterface dialogInterface, final int which) {
    	((OnDialogDoneListener) getActivity())
    		.onDialogClick(
    				new YDialogEventImpl.Builder()
    					.withConfirmed(which == AlertDialog.BUTTON_POSITIVE)
    					.withCanceled(which == AlertDialog.BUTTON_NEGATIVE)
    					.withNeutral(which == AlertDialog.BUTTON_NEUTRAL)
    					.withDialogInterface(dialogInterface)
    					.withDialogId(this.getDialogId())
    					.build()
    			);
    }

	/**
     * 
     * @param buttonOne
     * @param buttonTwo
     * @param buttonThree
     */
    private void configState(final boolean buttonTwo, final boolean buttonThree) {
    	this.twoButtons = buttonTwo;
    	this.threeButtons = buttonThree;
    }

	/**
	 * 
	 * @return
	 */
    private boolean isTwoButtons() {
		return this.twoButtons;
	}

	/**
	 * 
	 * @return
	 */
    private boolean isThreeButtons() {
		return this.threeButtons;
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
    private int getDialogId() {
		return this.dialogId;
	}
   
}