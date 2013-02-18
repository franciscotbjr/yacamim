package br.com.nuceloesperanca.radiografiasocial.util;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import br.com.nuceloesperanca.radiografiasocial.R;
import br.com.nuceloesperanca.radiografiasocial.entidade.Cid;
import br.com.nuceloesperanca.radiografiasocial.entidade.Paciente;
import br.org.yacamim.YBaseActivity;
import br.org.yacamim.util.YUtilText;

/**
 * @author Francisco Tarcizo Bomfim Júnior
 *
 */
public final class UtilCombos {
	/**
	 * Construtor da classe. 
	 */
	private UtilCombos() {
		super();
	}
	
	/**
	 * 
	 * @param baseActivity
	 * @param resourceCombo
	 * @return
	 */
	public static boolean isComboSelecionado(final YBaseActivity baseActivity, final int resourceCombo) {
		return !YUtilText.getTextFromSpinner(baseActivity, resourceCombo).equals(baseActivity.getText(R.string.valor_selecione_item).toString());
	}

	/**
	 * 
	 * @param activity
	 * @param viewId
	 * @return
	 */
	public static Object getSelectedObjectFromSpinner(final Activity activity, final int viewId) {
		try {
			return ((Spinner) activity.findViewById(viewId)).getSelectedItem();
		} catch (Exception e) {
			Log.e("UtilText.getSelectedObjectFromSpinner", e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param activity
	 * @param viewId
	 * @param object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void setSelectedObjectToSpinner(final Activity activity, final int viewId, final Object object) {
		try {
			final Spinner spinner = ((Spinner) activity.findViewById(viewId));
			spinner.setSelection(((ArrayAdapter)spinner.getAdapter()).getPosition(object));
		} catch (Exception e) {
			Log.e("UtilText.setSelectedObjectFromSpinner", e.getMessage());
		}
	}

	/**
	 * Inicializa o combo de tipos de cids.
	 * 
	 * @param spinner
	 * @param baseActivity
	 */
	public static final void initComboCIDs(final Spinner spinner, final YBaseActivity baseActivity, List<Cid> cids) {
		try {
			Cid cid = new Cid(baseActivity.getString(R.string.valor_selecione_item));
			cids.add(0, cid);
			initCombo(spinner, baseActivity, cids);
		} catch (Exception e) {
			Log.e("UtilCombos.initComboCIDs", e.getMessage());
		}
	}

	/**
	 * Inicializa o combo de tipos de cids.
	 * 
	 * @param spinner
	 * @param baseActivity
	 */
	public static final void initComboPacientes(final Spinner spinner, final YBaseActivity baseActivity, List<Paciente> pacientes) {
		try {
			Paciente paciente = new Paciente(baseActivity.getString(R.string.valor_selecione_item));
			pacientes.add(0, paciente);
			initCombo(spinner, baseActivity, pacientes);
		} catch (Exception e) {
			Log.e("UtilCombos.initComboPacientes", e.getMessage());
		}
	}

    /**
     * @param spinner
     * @param baseActivity
     * @param array
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void initCombo(final Spinner spinner, final YBaseActivity baseActivity, final List array) {
		try {
			ArrayAdapter<?> adapter = new ArrayAdapter(baseActivity, android.R.layout.simple_spinner_item, array);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			spinner.setAdapter(adapter);
		} catch (Exception e) {
			Log.e("UtilCombos.initCombo", e.getMessage());
		}
	}
}