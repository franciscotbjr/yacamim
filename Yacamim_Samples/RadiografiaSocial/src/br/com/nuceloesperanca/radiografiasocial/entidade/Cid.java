/**
 * Cid.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial.entidade;

import android.os.Parcel;
import android.os.Parcelable;
import br.org.yacamim.entity.YBaseEntity;
import br.org.yacamim.persistence.Column;
import br.org.yacamim.persistence.Entity;
import br.org.yacamim.persistence.Id;

/**
 * Entidade que representa a tabela de CID.
 * 
 * @author manny.
 * Criado em Feb 8, 2013 10:30:26 PM
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Cid extends YBaseEntity {
	private long id;
	private String descricao;

	/**
	 * Construtor da classe.
	 *
	 */
	public Cid() {
		super();
	}

	/**
	 * Construtor da classe.
	 *
	 * @param parcel
	 */
	public Cid(Parcel parcel) {
		super(parcel);
	}

	/**
	 * @return the id
	 */
	@Id
	public long getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the descricao
	 */
	@Column
	public String getDescricao() {
		return this.descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Método que possibilita a criação de uma instância para trafegar entre os intents.
	 */
	public static final Parcelable.Creator<Cid> CREATOR = new Parcelable.Creator<Cid>() {
        public Cid createFromParcel(Parcel pc) {
            return new Cid(pc);
        }
        public Cid[] newArray(int size) {
            return new Cid[size];
        }
	};

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (this.id ^ (this.id >>> 32));
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cid other = (Cid) obj;
		if (this.id != other.id)
			return false;
		return true;
	}
}