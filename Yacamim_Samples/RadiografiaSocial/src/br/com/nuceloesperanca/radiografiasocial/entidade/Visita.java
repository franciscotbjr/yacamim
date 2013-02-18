/**
 * Visita.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial.entidade;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;
import br.org.yacamim.entity.YBaseEntity;
import br.org.yacamim.persistence.Column;
import br.org.yacamim.persistence.Entity;
import br.org.yacamim.persistence.Id;
import br.org.yacamim.persistence.OneToMany;

/**
 * Entidade que representa a tabela de Visita.
 * 
 * @author manny.
 * Criado em Feb 8, 2013 10:45:31 PM
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Visita extends YBaseEntity {
	private long id;
	private Date data;
	private String itensLevados;
	private double latitude;
	private double longitude;
	private Paciente paciente;

	/**
	 * Construtor da classe.
	 *
	 */
	public Visita() {
		super();
	}

	/**
	 * Construtor da classe.
	 *
	 * @param parcel
	 */
	public Visita(Parcel parcel) {
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
	 * @return the data
	 */
	@Column
	public Date getData() {
		return this.data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * @return the itensLevados
	 */
	@Column(nullable = true)
	public String getItensLevados() {
		return this.itensLevados;
	}

	/**
	 * @param itensLevados the itensLevados to set
	 */
	public void setItensLevados(String itensLevados) {
		this.itensLevados = itensLevados;
	}

	/**
	 * @return the latitude
	 */
	@Column(nullable = true)
	public double getLatitude() {
		return this.latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	@Column(nullable = true)
	public double getLongitude() {
		return this.longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the paciente
	 */
	@OneToMany
	public Paciente getPaciente() {
		return this.paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * Método que possibilita a criação de uma instância para trafegar entre os intents.
	 */
	public static final Parcelable.Creator<Visita> CREATOR = new Parcelable.Creator<Visita>() {
        public Visita createFromParcel(Parcel pc) {
            return new Visita(pc);
        }
        public Visita[] newArray(int size) {
            return new Visita[size];
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
		Visita other = (Visita) obj;
		if (this.id != other.id)
			return false;
		return true;
	}
}