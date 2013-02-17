/**
 * PacienteActivity.java
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.nuceloesperanca.radiografiasocial.entidade.Cid;
import br.com.nuceloesperanca.radiografiasocial.entidade.Paciente;
import br.com.nuceloesperanca.radiografiasocial.persitencia.CidDBAdapter;
import br.com.nuceloesperanca.radiografiasocial.persitencia.PacienteDBAdapter;
import br.com.nuceloesperanca.radiografiasocial.util.Constantes;
import br.com.nuceloesperanca.radiografiasocial.util.UtilCombos;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.ui.components.BaseDatePickerDialog;
import br.org.yacamim.ui.components.BaseOnDateSetListener;
import br.org.yacamim.util.YUtilDate;
import br.org.yacamim.util.YUtilString;
import br.org.yacamim.util.YUtilText;

/**
 * @author manny.
 * Criado em Feb 16, 2013 11:29:22 PM
 * @version 1.0
 * @since 1.0
 */
public class PacienteActivity extends YBaseActivity {
	private static final String TAG_CLASS = ConsultarPacienteActivity.class.getName();
	static final int DATE_DIALOG_DATA_NASCIMENTO = 0;
	private boolean inclusao = true;
	private Paciente paciente = null;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paciente);

		this.init();
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
     * Inicializa o formulário. 
     */
    private void init() {
    	try {
    		if (getIntent().getExtras() == null || getIntent().getExtras().get("pacienteCadastro") == null) {
    			this.paciente = new Paciente();
    		} else {
    			this.paciente = (Paciente) getIntent().getExtras().getParcelable("pacienteCadastro");
    			inclusao = false;
    		}

    		super.initGPS();

    		this.initCampos();

    		this.initCombos();

    		this.initDatePickers();
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
    }

	/**
     * Inicializa os campos do formulário. 
     */
    private void initCampos() {
    	PacienteDBAdapter pacienteDBAdapter = null;
    	
    	try {
    		if (this.paciente != null) {
    			pacienteDBAdapter = new PacienteDBAdapter(Paciente.class);
    			pacienteDBAdapter.open();
    			this.paciente = pacienteDBAdapter.getByID(this.paciente.getId());

    			YUtilText.setIntegerToEditText(this, R.id.txte_prontuario, this.paciente.getProntuario());
    			YUtilText.setTextToEditText(this, R.id.txte_nome, this.paciente.getNome());

    	        final TextView textView = (TextView) findViewById(R.id.txtv_nascimento);
    	        textView.setText(YUtilDate.getSimpleDateFormatData().format(this.paciente.getNascimento()));
    	        
    			YUtilText.setTextToEditText(this, R.id.txte_nome_mae, this.paciente.getNomeMae());
    			YUtilText.setTextToEditText(this, R.id.txte_nome_pai, this.paciente.getNomePai());
    			YUtilText.setTextToEditText(this, R.id.txte_irmaos, this.paciente.getIrmaos());
    			YUtilText.setTextToEditText(this, R.id.txte_telefones, this.paciente.getTelefones());
    			YUtilText.setTextToEditText(this, R.id.txte_endereco, this.paciente.getEndereco());
    			UtilCombos.setSelectedObjectToSpinner(this, R.id.cmb_cid, this.paciente.getCid());
    		}
    		
    		final EditText editTextLatitude = (EditText) this.findViewById(R.id.txte_latitude);
    		final EditText editTextLongitude = (EditText) this.findViewById(R.id.txte_longitude);

    		editTextLatitude.setText(String.valueOf(getBestLocalization().getLatitude()));
    		editTextLongitude.setText(String.valueOf(getBestLocalization().getLongitude()));
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		} finally {
			if (pacienteDBAdapter != null) {
				pacienteDBAdapter.close();
			}
		}
    }

    /**
     * Inicializa os combos do formulário.
     */
    private void initCombos() {
    	CidDBAdapter adapter = new CidDBAdapter(Cid.class);
		try {
			final Spinner cmbCID = (Spinner)findViewById(R.id.cmb_cid);
			adapter.open();
			List<Cid> cids = adapter.list();
			UtilCombos.initComboCID(cmbCID, this, cids);
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
    	final Button pickDataInstalacao = (Button) findViewById(R.id.btn_nacimento);
        pickDataInstalacao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_DATA_NASCIMENTO);
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
    	PacienteDBAdapter pacienteDBAdapter = null;
    	try {
			pacienteDBAdapter = new PacienteDBAdapter(Paciente.class);
			pacienteDBAdapter.open();
			pacienteDBAdapter.beginTransaction();
			paciente.setProntuario(YUtilText.getIntegerFromTextView(this, R.id.txte_prontuario));
			paciente.setNome(YUtilText.getTextFromEditText(this, R.id.txte_nome));
			paciente.setNascimento((YUtilDate.convertDate(YUtilText.getTextFromTextView(this, R.id.txtv_nascimento))));
			paciente.setNomeMae(YUtilText.getTextFromEditText(this, R.id.txte_nome_mae));
			paciente.setNomePai(YUtilText.getTextFromEditText(this, R.id.txte_nome_pai));
			paciente.setIrmaos(YUtilText.getTextFromEditText(this, R.id.txte_irmaos));
			paciente.setTelefones(YUtilText.getTextFromEditText(this, R.id.txte_telefones));
			paciente.setEndereco(YUtilText.getTextFromEditText(this, R.id.txte_endereco));

			if (((Spinner) this.findViewById(R.id.cmb_cid)).getSelectedItem() != null) {
				paciente.setCid((Cid)((Spinner)this.findViewById(R.id.cmb_cid)).getSelectedItem());
			}

			if (YUtilText.getLongFromEditText(this, R.id.txte_latitude) != null) {
				paciente.setLatitude(YUtilText.getDoubleFromEditText(this, R.id.txte_latitude));
			}

			if (YUtilText.getLongFromEditText(this, R.id.txte_longitude) != null) {
				paciente.setLongitude(YUtilText.getLongFromEditText(this, R.id.txte_longitude));
			}

			if (inclusao) {
				pacienteDBAdapter.insert(paciente);
				showDialog(Constantes.INFO_DATA_SUCCESFULLY_INSERTED);
			} else {
				pacienteDBAdapter.update(paciente);
				showDialog(Constantes.INFO_DATA_SUCCESFULLY_UPDATED);
			}
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
			if (pacienteDBAdapter != null) {
				pacienteDBAdapter.endTransaction(false);
				pacienteDBAdapter.close();
			}
		} finally {
			if (pacienteDBAdapter != null) {
				pacienteDBAdapter.endTransaction(true);
				pacienteDBAdapter.close();
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
    	if (YUtilString.isEmptyString(YUtilText.getTextFromEditText(this, R.id.txte_nome))) {
        	nomesCampos.add(this.getText(R.string.lbl_nome).toString());
    	}
		return nomesCampos.size() == 0;
	}

    /**
     * @see br.com.poliedro.arquitetura.android.PopupMenuBaseActivity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(final int idDialog) {
    	final Calendar calendarInicio = YUtilDate.getCalendar(super.getBestLocalization());
    	switch (idDialog) {
			case DATE_DIALOG_DATA_NASCIMENTO:
	            return new BaseDatePickerDialog(
	            		this, 
	            		new BaseOnDateSetListener((TextView) findViewById(R.id.txtv_nascimento), "dd/MM/yyyy"), 
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