/**
 * ListPacientesActivity.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial;

import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import br.com.nuceloesperanca.radiografiasocial.entidade.Paciente;
import br.com.nuceloesperanca.radiografiasocial.persitencia.PacienteDBAdapter;
import br.com.nuceloesperanca.radiografiasocial.util.ConditionFactory;
import br.org.yacamim.YBaseListActivity;
import br.org.yacamim.ui.components.AdapterConfig;
import br.org.yacamim.ui.components.ComplexListSimpleAdapter;
import br.org.yacamim.ui.components.DefaultAlertDialogBuilder;
import br.org.yacamim.ui.components.InteractionConfig;
import br.org.yacamim.ui.components.RowConfig;
import br.org.yacamim.ui.components.RowConfigItem;
import br.org.yacamim.util.YConstants;
import br.org.yacamim.util.YUtilListView;

/**
 * Classe para exibir lista de pacientes de acordo com a pesquisa realizada.
 * 
 * @author manny.
 * Criado em Feb 12, 2013 10:13:44 AM
 * @version 1.0
 * @since 1.0
 */
public class ListPacientesActivity extends YBaseListActivity {
	private static final String TAG_CLASS = ListPacientesActivity.class.getName();
	private Paciente pacienteSelecionado; 

	/**
	 * Construtor da classe.
	 */
	public ListPacientesActivity() {
		super();
	}

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bg_grids);

		montaGrid();
	}

	/**
	 * Realiza a montagem da grid.
	 */
	private void montaGrid() {
		final List<Paciente> pacientes = recuperaListaPacientes();
		if (pacientes != null && pacientes.size() > 0) {
			List<HashMap<String, Object>> listOfMappedData = YUtilListView.buildListOfMappedData(pacientes);
			final AdapterConfig adapterConfig = this.buildAdapterConfig();
			final ComplexListSimpleAdapter complexListSimpleAdapter = new ComplexListSimpleAdapter(
					this, 
					listOfMappedData, 
					adapterConfig);
			setListAdapter(complexListSimpleAdapter);
		} else {
			super.finishForNoRecordFound();
		}
	}

	/**
	 * Retorna a configuração do grid.
	 * 
	 * @return
	 */
	protected AdapterConfig buildAdapterConfig() {
		final RowConfig rowConfig = new RowConfig();
		rowConfig.setResource(R.layout.grid_pacientes);
		rowConfig.setResourcesHint(new int[]{});

		rowConfig.addRowConfigItem(new RowConfigItem("prontuario", R.id.txtv_prontuario));
		rowConfig.addRowConfigItem(new RowConfigItem("nome", R.id.txtv_nome));
		rowConfig.addRowConfigItem(new RowConfigItem("nomeMae", R.id.txtv_nome_mae));
		rowConfig.addRowConfigItem(new RowConfigItem(ConditionFactory.getConditionPaciente(), new InteractionConfig(R.id.btn_editar, Button.class)));
		rowConfig.addRowConfigItem(new RowConfigItem(ConditionFactory.getConditionPaciente(), new InteractionConfig(R.id.btn_excluir, Button.class)));

		final RowConfig[] rowConfigs = {rowConfig} ;

		final AdapterConfig adapterConfig = new AdapterConfig(rowConfigs, ConditionFactory.getSimpleRowCondition(), null);
		return adapterConfig;
	}

	/**
	 * Recupera a lista de pacientes realizando a consulta no banco de dados com os parametros informados.
	 * 
	 * @return
	 */
	private List<Paciente> recuperaListaPacientes() {
		PacienteDBAdapter adapter = new PacienteDBAdapter(Paciente.class);
		try {
			List<Paciente> retorno = null;
			Paciente parametro = (Paciente) getIntent().getExtras().getParcelable("pacientePesquisa");
			adapter.open();
			retorno = adapter.pesquisaPacientes(parametro.getProntuario(), parametro.getNome());

			return retorno;
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
			return null;
		} finally {
			adapter.close();
		}
	}

	/**
	 * @see br.org.yacamim.YBaseListActivity#onListViewClick(android.view.View, java.lang.Object)
	 */
	@Override
	public void onListViewClick(View view, Object objectValue) {
		try {
			this.pacienteSelecionado = (Paciente) objectValue; 
			if(view.getId() == R.id.btn_editar) {
				showGerenciarPaciente();
			} else if(view.getId() == R.id.btn_excluir) {
				tratarExclusao();
			}
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
	}

	/**
	 * Exibe a tela de manutenção dos dados do Paciente.
	 */
	private void showGerenciarPaciente() {
		try {
			final Intent intent = new Intent(this, PacienteActivity.class);
			intent.putExtra("pacienteCadastro", pacienteSelecionado);
			this.startActivityForResult(intent, YConstants.LOGOFF);
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
	}

	/**
	 * Realiza a exclusão de um Paciente.
	 */
	private void tratarExclusao() {
		try {
			showDialog(YConstants.DIALOG_YES_NO);
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
	}

	/**
	 * Exclui os dados do paciente.
	 * 
	 * @param paciente
	 */
	private void excluirDados() {
		PacienteDBAdapter adapter = new PacienteDBAdapter(Paciente.class);
		try {
			adapter.open();
			adapter.delete(pacienteSelecionado);
			finish();
		} catch (Exception e) {
			adapter.close();
			Log.e(TAG_CLASS, e.getMessage());
		}
	}

	/**
	 * @see br.org.yacamim.YBaseListActivity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(int idDialog) {
		String msg = null;
		switch(idDialog) {
			case YConstants.DIALOG_YES_NO:
				msg = this.getText(R.string.msg2).toString().replace("{0}", pacienteSelecionado.getNome());
				AlertDialog.Builder builderSimNao = new DefaultAlertDialogBuilder(this, msg, false);
	        	builderSimNao.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
	        		public void onClick(DialogInterface dialog, int id) {
	        			ListPacientesActivity.this.excluirDados();
	        		}
	        	}).setNegativeButton("Não", new DialogInterface.OnClickListener() {
	        		public void onClick(DialogInterface dialog, int id) {
	        			dialog.cancel();
	        		}
	        	});
	        	return builderSimNao.show();
			default:
				return super.onCreateDialog(idDialog);
		}
	}
}