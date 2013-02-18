/**
 * VisitaDBAdapter.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial.persitencia;

import br.com.nuceloesperanca.radiografiasocial.entidade.Visita;
import br.org.yacamim.persistence.DefaultDBAdapter;

/**
 * Adapter de banco de dados para a entidade Visita.
 * 
 * @author manny.
 * Criado em Feb 17, 2013 10:52:21 PM
 * @version 1.0
 * @since 1.0
 */
public class VisitaDBAdapter extends DefaultDBAdapter<Visita> {
	/**
	 * Construtor da classe.
	 *
	 * @param genericType
	 */
	public VisitaDBAdapter(Class<Visita> genericType) {
		super(genericType);
	}
}
