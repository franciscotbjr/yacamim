/**
 * RadiografiaSocialActivity.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import br.org.yacamim.YInitBaseActivity;
import br.org.yacamim.YacamimInitializer;
import br.org.yacamim.YacamimState;

/**
 * Activity principal do sistema.
 * 
 * @author manny.
 * Criado em Feb 8, 2013 10:41:47 PM
 * @version 1.0
 * @since 1.0
 */
public class RadiografiaSocialActivity extends YInitBaseActivity {
	private static final String TAG_CLASS = RadiografiaSocialActivity.class.getName();

	/**
	 * @see br.org.yacamim.YBaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radiografia_social);
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_radiografia_social, menu);
		return true;
	}

	/**
	 * 
	 * @see br.org.yacamim.YInitBaseActivity#initApp()
	 */
	protected void initApp() {
    	try {
    		YacamimState.getInstance().setPreferencesToken("__y_prefs_#_token_@_radiografia.nucleoesperanca.com.br_::");

    		YacamimInitializer.getInstance()
    		.setIdResourceMsgOK(R.string.y_MsgOK)
    		.setIdResourceMsgSuccesfullyInserted(R.string.y_MsgSuccesfullyInserted)
    		.setIdResourceMsgSuccesfullyUpdated(R.string.y_MsgSuccesfullyUpdated)
    		.setIdResourceMsgInvalidData(R.string.y_MsgInvalidData)
    		.setIdResourceMsgWait(R.string.y_MsgWait)
    		.setIdResourceMsgSelectAnItem(R.string.y_MsgSelectAnItem)
    		.setIdResourceMsgNoInformationFound(R.string.y_MsgNoInformationFound)
    		.setIdResourceMsgNoRecordsFoundForParameters(R.string.y_MsgNoRecordsFoundForParameters)
    		.setIdResourceMsgNoRecordSelected(R.string.y_MsgNoRecordSelected)
    		.setIdResourceMsgNoConnectivityAvailable(R.string.y_MsgNoConnectivityAvailable)
    		.setIdResourceMsgNoWifiConnectivityAvailable(R.string.y_MsgNoWifiConnectivityAvailable)
    		.setIdResourceMsgConstraintDependency(R.string.y_MsgConstraintDependency)
    		;
    		
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
    }

	/**
	 * Envia para a tela de consulta de pacientes.
	 * 
	 * @param _view
	 */
	public void manterPaciente(final View _view) {
		try {
			startActivity(new Intent(this, ConsultarPacienteActivity.class));
		} catch (Exception e) {
			Log.e("RadiografiaSocialActivity.manterPaciente", e.getMessage());
		}
	}

	/**
	 * Envia para a tela de registro de visita.
	 * 
	 * @param _view
	 */
	public void registrarVisita(final View _view) {
		try {
			startActivity(new Intent(this, VisitaActivity.class));
		} catch (Exception e) {
			Log.e("RadiografiaSocialActivity.registrarVisita", e.getMessage());
		}
	}
}