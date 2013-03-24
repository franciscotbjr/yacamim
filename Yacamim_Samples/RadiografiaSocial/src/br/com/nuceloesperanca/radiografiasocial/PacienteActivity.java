/**
 * PacienteActivity.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
import br.org.yacamim.entity.GpsLocationInfo;
import br.org.yacamim.ui.components.BaseDatePickerDialog;
import br.org.yacamim.ui.components.BaseOnDateSetListener;
import br.org.yacamim.util.YUtilDate;
import br.org.yacamim.util.YUtilString;
import br.org.yacamim.util.YUtilText;

/**
 * Activity de cadastro de pacientes.
 * 
 * @author manny.
 * Criado em Feb 16, 2013 11:29:22 PM
 * @version 1.0
 * @since 1.0
 */
public class PacienteActivity extends YBaseActivity {
	private static final String TAG_CLASS = PacienteActivity.class.getName();
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
    		if (this.paciente != null && paciente.getId() != 0) {
    			pacienteDBAdapter = new PacienteDBAdapter(Paciente.class);
    			pacienteDBAdapter.open();
    			this.paciente = pacienteDBAdapter.getByID(this.paciente.getId());

    			YUtilText.setIntegerToEditText(this, R.id.txte_prontuario, this.paciente.getProntuario());
    			YUtilText.setTextToEditText(this, R.id.txte_nome, this.paciente.getNome());

    	        final TextView textView = (TextView) findViewById(R.id.txtv_nascimento);
    	        if (this.paciente.getNascimento() != null) {
    	        	textView.setText(YUtilDate.getSimpleDateFormatData().format(this.paciente.getNascimento()));
    	        }

    			YUtilText.setTextToEditText(this, R.id.txte_nome_mae, this.paciente.getNomeMae());
    			YUtilText.setTextToEditText(this, R.id.txte_nome_pai, this.paciente.getNomePai());
    			YUtilText.setTextToEditText(this, R.id.txte_irmaos, this.paciente.getIrmaos());
    			YUtilText.setTextToEditText(this, R.id.txte_telefones, this.paciente.getTelefones());
    			YUtilText.setTextToEditText(this, R.id.txte_endereco, this.paciente.getEndereco());
    			if (this.paciente.getCid() != null && this.paciente.getCid().getDescricao() != null) {
    				UtilCombos.setSelectedObjectToSpinner(this, R.id.cmb_cid, this.paciente.getCid());
    			}
    			YUtilText.setBolStringFromRadioButton(this, R.id.radio_sim_em_tratamento, R.id.radio_nao_em_tratamento, this.paciente.getEmTratamento());
    			YUtilText.setBolStringFromRadioButton(this, R.id.radio_sim_obito, R.id.radio_nao_obito, this.paciente.getObito());
    		} else {
    			YUtilText.setBolStringFromRadioButton(this, R.id.radio_sim_em_tratamento, R.id.radio_nao_em_tratamento, Constantes.SIM);
    			YUtilText.setBolStringFromRadioButton(this, R.id.radio_sim_obito, R.id.radio_nao_obito, Constantes.NAO);
    		}

    		this.atualizaCamposGeo(this.getBestLocation());
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
			UtilCombos.initComboCIDs(cmbCID, this, cids);
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
    	final Button pickDataNascimento = (Button) findViewById(R.id.btn_nacimento);
        pickDataNascimento.setOnClickListener(new View.OnClickListener() {
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
    	PacienteDBAdapter adapter = null;
    	try {
			adapter = new PacienteDBAdapter(Paciente.class);
			adapter.open();
			adapter.beginTransaction();
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

			paciente.setEmTratamento(YUtilText.getBolStringFromRadioButton(this, R.id.radio_sim_em_tratamento));
			paciente.setObito(YUtilText.getBolStringFromRadioButton(this, R.id.radio_sim_obito));

			if (YUtilText.getDoubleFromEditText(this, R.id.txte_latitude) != null) {
				paciente.setLatitude(YUtilText.getDoubleFromEditText(this, R.id.txte_latitude));
			}

			if (YUtilText.getDoubleFromEditText(this, R.id.txte_longitude) != null) {
				paciente.setLongitude(YUtilText.getDoubleFromEditText(this, R.id.txte_longitude));
			}

			if (inclusao) {
				adapter.insert(paciente);
				showDialog(Constantes.INFO_DATA_SUCCESSFULLY_INSERTED);
			} else {
				adapter.update(paciente);
				showDialog(Constantes.INFO_DATA_SUCCESSFULLY_UPDATED);
			}
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
    	final Calendar calendarInicio = YUtilDate.getCalendar(super.getBestLocation());
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

    /**
     * @see br.org.yacamim.YBaseActivity#onUpdateGpsLocationInfo(br.org.yacamim.entity.GpsLocationInfo)
     */
    @Override
    public void onUpdateGpsLocationInfo(GpsLocationInfo _gpsLocationInfo) {
    	super.onUpdateGpsLocationInfo(_gpsLocationInfo);
		this.atualizaCamposGeo(_gpsLocationInfo);
    }

    /**
     * Atualiza os campos de localização.
     * 
     * @param _gpsLocationInfo
     */
	private void atualizaCamposGeo(GpsLocationInfo _gpsLocationInfo) {
		try {
			final EditText editTextLatitude = (EditText)this.findViewById(R.id.txte_latitude);
			final EditText editTextLongitude = (EditText)this.findViewById(R.id.txte_longitude);

			final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
			numberFormat.setMaximumFractionDigits(10);
			numberFormat.setMinimumFractionDigits(10);

			editTextLatitude.setText(numberFormat.format(_gpsLocationInfo.getLatitude()));
			editTextLongitude.setText(numberFormat.format(_gpsLocationInfo.getLongitude()));
		} catch (Exception _e) {
			Log.e("PacienteActivity.onUpdateGpsLocationInfo", _e.getMessage());
		}
	}
}