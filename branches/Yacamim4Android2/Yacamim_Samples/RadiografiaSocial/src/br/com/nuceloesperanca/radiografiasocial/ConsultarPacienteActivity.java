/**
 * ConsultarPacienteActivity.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import br.com.nuceloesperanca.radiografiasocial.entidade.Paciente;
import br.com.nuceloesperanca.radiografiasocial.util.Constantes;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.ui.components.DefaultAlertDialogBuilder;
import br.org.yacamim.util.YConstants;
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
    		this.startActivityForResult(intent, YConstants.LOGOFF);
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
    }

	/**
	 * Monta o objeto usuário para ser parametro de pesquisa.
	 */
	private Intent montaParametrosPesquisa() {
		final Intent intent = new Intent(this, ListPacientesActivity.class);
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

	/**
     * Realiza a inclusão de um novo paciente.
     *  
     * @param view
     */
    public void incluirPaciente(final View view) {
    	try {
    		this.startActivityForResult(new Intent(this, PacienteActivity.class), YConstants.LOGOFF);
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
    }

    /**
     * @see br.org.yacamim.YBaseActivity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
		try {
			if(resultCode == Constantes.EXCLUSAO_REALIZADA_COM_SUCESSO) {
				this.showDialog(Constantes.EXCLUSAO_REALIZADA_COM_SUCESSO);
			}
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
    }

    /**
     * @see br.org.yacamim.YBaseActivity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(int idDialog) {
		String msg = null;
		switch(idDialog) {
			case Constantes.EXCLUSAO_REALIZADA_COM_SUCESSO:
				msg = this.getText(R.string.msg3).toString();
				AlertDialog.Builder builderExclusaoSucesso = new DefaultAlertDialogBuilder(this, msg, false);
	        	builderExclusaoSucesso.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        		public void onClick(DialogInterface dialog, int id) {
	        			dialog.cancel();
	        		}
	        	});
	        	return builderExclusaoSucesso.show();
			default:
				return super.onCreateDialog(idDialog);
		}
    }
}