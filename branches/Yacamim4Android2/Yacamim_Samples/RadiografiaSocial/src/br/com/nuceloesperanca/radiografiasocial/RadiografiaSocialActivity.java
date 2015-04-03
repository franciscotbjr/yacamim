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
import br.org.yacamim.YBaseActivity;

/**
 * Activity principal do sistema.
 * 
 * @author manny.
 * Criado em Feb 8, 2013 10:41:47 PM
 * @version 1.0
 * @since 1.0
 */
public class RadiografiaSocialActivity extends YBaseActivity {
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
	 * Envia para a tela de consulta de pacientes.
	 * 
	 * @param _view
	 */
	public void manterPaciente(final View _view) {
		try {
			startActivity(new Intent(this, ConsultarPacienteActivity.class));
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
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
			Log.e(TAG_CLASS, e.getMessage());
		}
	}
}