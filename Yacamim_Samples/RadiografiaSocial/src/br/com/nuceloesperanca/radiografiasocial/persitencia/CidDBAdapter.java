/**
 * CidDBAdapter.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial.persitencia;

import br.com.nuceloesperanca.radiografiasocial.entidade.Cid;
import br.org.yacamim.persistence.DefaultDBAdapter;

/**
 * Adapter de banco de dados para a entidade CID.
 * 
 * @author manny.
 * Criado em Feb 16, 2013 11:50:13 PM
 * @version 1.0
 * @since 1.0
 */
public class CidDBAdapter extends DefaultDBAdapter<Cid> {
	/**
	 * Construtor da classe.
	 *
	 * @param genericType
	 */
	public CidDBAdapter(Class<Cid> genericType) {
		super(genericType);
	}
}