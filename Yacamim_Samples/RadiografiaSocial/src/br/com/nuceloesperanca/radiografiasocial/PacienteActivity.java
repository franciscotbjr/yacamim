/**
 * PacienteActivity.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import br.com.nuceloesperanca.radiografiasocial.entidade.Cid;
import br.com.nuceloesperanca.radiografiasocial.persitencia.CidDBAdapter;
import br.com.nuceloesperanca.radiografiasocial.util.UtilCombos;
import br.org.yacamim.YBaseActivity;

/**
 * @author manny.
 * Criado em Feb 16, 2013 11:29:22 PM
 * @version 1.0
 * @since 1.0
 */
public class PacienteActivity extends YBaseActivity {
	private static final String TAG_CLASS = ConsultarPacienteActivity.class.getName();

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
    		super.initGPS();

    		this.initCampos();

    		this.initCombos();

    		//this.initDatePickers();
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
    }

	/**
     * Inicializa os campos do formulário. 
     */
    private void initCampos() {
    	try {
    		final EditText editTextLatitude = (EditText) this.findViewById(R.id.txte_latitude);
    		final EditText editTextLongitude = (EditText) this.findViewById(R.id.txte_longitude);

    		editTextLatitude.setText(String.valueOf(getBestLocalization().getLatitude()));
    		editTextLongitude.setText(String.valueOf(getBestLocalization().getLongitude()));
		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
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
     * Salva os dados do paciente.
     *  
     * @param view
     */
    public void salvar(final View view) {
    	try {

		} catch (Exception e) {
			Log.e(TAG_CLASS, e.getMessage());
		}
    }
}