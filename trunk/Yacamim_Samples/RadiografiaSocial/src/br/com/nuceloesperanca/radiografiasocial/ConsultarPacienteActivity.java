/**
 * ConsultarPacienteActivity.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import br.com.nuceloesperanca.radiografiasocial.entidade.Paciente;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.util.YUtilString;
import br.org.yacamim.util.YUtilText;

/**
 * Activity de consulta de pacientes.
 * 
 * @author manny.
 * Criado em Feb 11, 2013 5:41:02 PM
 * @version 1.0
 * @since 1.0
 */
public class ConsultarPacienteActivity extends YBaseActivity {
	private static final String TAG_CLASS = ConsultarPacienteActivity.class.getName();

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consultar_paciente);
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_paciente, menu);
		return true;
	}

	/**
     * Realiza a consuta de paciente.
     *  
     * @param view
     */
    public void consultarPaciente(final View view) {
    	try {
    		Intent intent = montaParametrosPesquisa();
    		this.startActivity(intent);
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
    }

	/**
	 * Monta o objeto usuário para ser parametro de pesquisa.
	 */
	private Intent montaParametrosPesquisa() {
		Intent intent = new Intent(this, ListPacientes.class);
		Paciente pacientePesquisa = new Paciente();
		if (!YUtilString.isEmptyString(YUtilText.getTextFromEditText(this, R.id.txte_prontuario))) {
			pacientePesquisa.setProntuario(Integer.valueOf(YUtilText.getTextFromEditText(this, R.id.txte_prontuario)));
		}
		if (!YUtilString.isEmptyString(YUtilText.getTextFromEditText(this, R.id.txte_nome))) {
			pacientePesquisa.setNome(YUtilText.getTextFromEditText(this, R.id.txte_nome));
		}
		intent.putExtra("pacientePesquisa", pacientePesquisa);
		return intent;
	}
}