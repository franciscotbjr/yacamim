package br.org.yacamim;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;

public class YBaseGCMIntentService extends GCMBaseIntentService {
	
	public YBaseGCMIntentService(final String idGcm) {
		super(idGcm);
	}

	@Override
	protected void onError(Context context, String errorId) {
		YacamimState.getInstance().setCurrentContext(context);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		YacamimState.getInstance().setCurrentContext(context);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		YacamimState.getInstance().setCurrentContext(context);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		YacamimState.getInstance().setCurrentContext(context);
	}
	
    @Override
    protected void onDeletedMessages(Context context, int total) {
    	YacamimState.getInstance().setCurrentContext(context);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
    	YacamimState.getInstance().setCurrentContext(context);
    	return false;
    }

}
