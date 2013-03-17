/**
 * VisitaActivity.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.nuceloesperanca.radiografiasocial.entidade.Paciente;
import br.com.nuceloesperanca.radiografiasocial.entidade.Visita;
import br.com.nuceloesperanca.radiografiasocial.persitencia.PacienteDBAdapter;
import br.com.nuceloesperanca.radiografiasocial.persitencia.VisitaDBAdapter;
import br.com.nuceloesperanca.radiografiasocial.util.Constantes;
import br.com.nuceloesperanca.radiografiasocial.util.UtilCombos;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.ui.components.BaseDatePickerDialog;
import br.org.yacamim.ui.components.BaseOnDateSetListener;
import br.org.yacamim.util.YUtilDate;
import br.org.yacamim.util.YUtilString;
import br.org.yacamim.util.YUtilText;

/**
 * Activity de registro de visitas.
 * 
 * @author manny.
 * Criado em Feb 17, 2013 10:33:57 PM
 * @version 1.0
 * @since 1.0
 */
public class VisitaActivity extends YBaseActivity {
	private static final String TAG_CLASS = VisitaActivity.class.getName();
	static final int DATE_DIALOG_DATA_VISITA = 0;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visita);

		this.init();
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_visita, menu);
		return true;
	}

    /**
     * Inicializa o formulário. 
     */
    private void init() {
    	try {
    		super.initGPS();

    		this.initCombos();

    		this.initDatePickers();
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
    }

    /**
     * Inicializa os combos do formulário.
     */
    private void initCombos() {
    	PacienteDBAdapter adapter = new PacienteDBAdapter(Paciente.class);
		try {
			final Spinner cmbPaciente = (Spinner)findViewById(R.id.cmb_paciente);
			adapter.open();
			List<Paciente> pacientes = adapter.list();
			UtilCombos.initComboPacientes(cmbPaciente, this, pacientes);
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		} finally {
			adapter.close();
		}
    }

    /**
     * Inicializa os componentes de data. 
     */
    private void initDatePickers() {
    	final Button pickDataVisita = (Button) findViewById(R.id.btn_data_visita);
        pickDataVisita.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_DATA_VISITA);
            }
        });
    }

	/**
     * Salva os dados do paciente.
     *  
     * @param view
     */
    public void salvar(final View view) {
    	try {
    		if (this.realizaValidacoes()) {
    			gravarDados();
    		}
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
    }

    /**
     * Realiza as validações do formulário.
     * 
     * @return
     */
    private boolean realizaValidacoes() {
    	List<String> nomesCampos = new ArrayList<String>();
    	if (!this.validaCamposObrigatorios(nomesCampos)) {
    		this.buildMessage(nomesCampos, R.string.msg1);
    		this.showDialog(Constantes.ERROR_REQUIRED_FIELDS);
    		return false;
    	}

    	return true;
    }

    /**
	 * Realiza a gravação dos dados do paciente. 
	 */
    private void gravarDados() {
    	VisitaDBAdapter adapter = null;
    	try {
			adapter = new VisitaDBAdapter(Visita.class);
			adapter.open();
			adapter.beginTransaction();
			Visita visita = new Visita();
			if (((Spinner) this.findViewById(R.id.cmb_paciente)).getSelectedItem() != null) {
				visita.setPaciente((Paciente)((Spinner)this.findViewById(R.id.cmb_paciente)).getSelectedItem());
			}
			visita.setData((YUtilDate.convertDate(YUtilText.getTextFromTextView(this, R.id.txtv_data_visita))));
			visita.setItensLevados(YUtilText.getTextFromEditText(this, R.id.txte_itens_levados));

			if (YUtilText.getDoubleFromEditText(this, R.id.txte_latitude) != null) {
				visita.setLatitude(YUtilText.getDoubleFromEditText(this, R.id.txte_latitude));
			}

			if (YUtilText.getDoubleFromEditText(this, R.id.txte_longitude) != null) {
				visita.setLongitude(YUtilText.getLongFromEditText(this, R.id.txte_longitude));
			}

			adapter.insert(visita);
			showDialog(Constantes.INFO_DATA_SUCCESFULLY_INSERTED);
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
			if (adapter != null) {
				adapter.endTransaction(false);
				adapter.close();
			}
		} finally {
			if (adapter != null) {
				adapter.endTransaction(true);
				adapter.close();
			}
		}
    }

    /**
     * Valida se os campos obrigatórios foram preenchidos.
     * 
     * @param nomesCampos
     * @return
     */
    private boolean validaCamposObrigatorios(List<String> nomesCampos) {
    	if (!YUtilText.isSpinnerSelected(this, R.id.cmb_paciente)) {
    		nomesCampos.add(this.getText(R.string.lbl_paciente).toString());
    	}
    	if (YUtilString.isEmptyString(YUtilText.getTextFromTextView(this, R.id.txtv_data_visita))) {
        	nomesCampos.add(this.getText(R.string.lbl_data_visita).toString());
    	}
		return nomesCampos.size() == 0;
	}

    /**
     * @see br.com.poliedro.arquitetura.android.PopupMenuBaseActivity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(final int idDialog) {
    	final Calendar calendarInicio = YUtilDate.getCalendar(super.getBestLocation());
    	switch (idDialog) {
			case DATE_DIALOG_DATA_VISITA:
	            return new BaseDatePickerDialog(
	            		this, 
	            		new BaseOnDateSetListener((TextView) findViewById(R.id.txtv_data_visita), "dd/MM/yyyy"), 
	            		calendarInicio.get(Calendar.YEAR), 
	            		calendarInicio.get(Calendar.MONTH), 
	            		calendarInicio.get(Calendar.DAY_OF_MONTH), 
	            		R.string.valor_definir_data,
	            		true);
			default:
				return super.onCreateDialog(idDialog);
		}
    }
}