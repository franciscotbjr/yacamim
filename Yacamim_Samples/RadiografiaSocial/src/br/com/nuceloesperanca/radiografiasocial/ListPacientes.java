/**
 * ListPacientes.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import br.com.nuceloesperanca.radiografiasocial.entidade.Paciente;
import br.com.nuceloesperanca.radiografiasocial.persitencia.PacienteDBAdapter;
import br.com.nuceloesperanca.radiografiasocial.util.ConditionFactory;
import br.org.yacamim.YBaseListActivity;
import br.org.yacamim.ui.components.AdapterConfig;
import br.org.yacamim.ui.components.ComplexListSimpleAdapter;
import br.org.yacamim.ui.components.RowConfig;
import br.org.yacamim.ui.components.RowConfigItem;
import br.org.yacamim.util.YUtilListView;

/**
 * Classe para exibir lista de pacientes de acordo com a pesquisa realizada.
 * 
 * @author manny.
 * Criado em Feb 12, 2013 10:13:44 AM
 * @version 1.0
 * @since 1.0
 */
public class ListPacientes extends YBaseListActivity {
	/**
	 * Construtor da classe.
	 */
	public ListPacientes() {
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
		} catch (Exception _e) {
			Log.e("ListPacientes.recuperaListaPacientes", _e.getMessage());
			return null;
		} finally {
			adapter.close();
		}
	}
}