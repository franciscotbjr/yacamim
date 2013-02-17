/**
 * PacienteDBAdapter.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial.persitencia;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import br.com.nuceloesperanca.radiografiasocial.entidade.Paciente;
import br.org.yacamim.persistence.DefaultDBAdapter;
import br.org.yacamim.util.YUtilString;

/**
 * Adapter de banco de dados para a entidade Paciente.
 * 
 * @author manny.
 * Criado em Feb 12, 2013 10:38:51 AM
 * @version 1.0
 * @since 1.0
 */
public class PacienteDBAdapter extends DefaultDBAdapter<Paciente> {
	/**
	 * Construtor da classe.
	 *
	 * @param genericType
	 */
	public PacienteDBAdapter(Class<Paciente> genericType) {
		super(genericType);
	}

	/**
	 * Pesquisa os usuários pelo cpf e nome.
	 * 
	 * @param prontuario
	 * @param nome
	 * @return
	 */
	public List<Paciente> pesquisaPacientes(int prontuario, String nome) {
		List<Paciente> retorno = null;
		String where = "";
		StringBuilder sql = new StringBuilder();
		sql.append("select p.Id, p.Nome, p.Nascimento, p.NomeMae, p.NomePai, p.Irmaos, ");
		sql.append("p.Telefones, p.Endereco, p.Latitude, p.Longitude ");
		sql.append("from Paciente p ");
		sql.append("where ");
		List<String> selectionArgsList = new ArrayList<String>();
		String[] selectionArgs = null;
		Integer indice = 0;
		if (prontuario != 0) {
			where += "p.Prontuario like ? and ";
			selectionArgsList.add(String.valueOf(prontuario));
		}
		if (!YUtilString.isEmptyString(nome)) {
			where += "p.Nome like ? and ";
			selectionArgsList.add(nome);
		}
		//retira o and do final.
		where = where.substring(0, where.length() - 4);

		selectionArgs = new String[selectionArgsList.size()];
		for (String arg : selectionArgsList) {
			selectionArgs[indice++] = arg;
		}

		final Cursor cursor = super.getDatabase().rawQuery(sql.toString() + where + "order by p.Nome", selectionArgs);
		if (cursor != null && cursor.moveToFirst()) {
			retorno = new ArrayList<Paciente>();
			retorno.add(build(cursor));
			while (cursor.moveToNext()) {
				retorno.add(build(cursor));
			}
		}

		return retorno;
	}
}