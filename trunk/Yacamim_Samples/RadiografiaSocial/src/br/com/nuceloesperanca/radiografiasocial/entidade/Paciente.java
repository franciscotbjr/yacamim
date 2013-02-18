/**
 * Paciente.java
 *
 * Copyright 2013 Blessing Informática.
 */
package br.com.nuceloesperanca.radiografiasocial.entidade;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;
import br.com.nuceloesperanca.radiografiasocial.util.Constantes;
import br.org.yacamim.entity.YBaseEntity;
import br.org.yacamim.persistence.Column;
import br.org.yacamim.persistence.Entity;
import br.org.yacamim.persistence.Id;
import br.org.yacamim.persistence.OneToMany;

/**
 * Entidade que representa a tabela de paciente.
 * 
 * @author manny.
 * Criado em Feb 8, 2013 10:14:09 PM
 * @version 1.0
 * @since 1.0
 */
@Entity
public class Paciente extends YBaseEntity {
	private long id;
	private int prontuario;
	private String nome;
	private Date nascimento;
	private String nomeMae;
	private String nomePai;
	private String irmaos;
	private String telefones;
	private String endereco;
	private Cid cid;
	private double latitude;
	private double longitude;
	private String emTratamento;
	private String obito;

	/**
	 * Construtor da classe.
	 */
	public Paciente() {
		super();
		setEmTratamento(Constantes.SIM);
		setObito(Constantes.NAO);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param parcel
	 */
	public Paciente(Parcel parcel) {
		super(parcel);
	}

	/**
	 * Construtor da classe.
	 *
	 * @param nome
	 */
	public Paciente(String nome) {
		super();
		this.nome = nome;
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
	 * @return the prontuario
	 */
	@Column
	public int getProntuario() {
		return this.prontuario;
	}

	/**
	 * @param prontuario the prontuario to set
	 */
	public void setProntuario(int prontuario) {
		this.prontuario = prontuario;
	}

	/**
	 * @return the nome
	 */
	@Column
	public String getNome() {
		return this.nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the nascimento
	 */
	@Column
	public Date getNascimento() {
		return this.nascimento;
	}

	/**
	 * @param nascimento the nascimento to set
	 */
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	/**
	 * @return the nomeMae
	 */
	@Column
	public String getNomeMae() {
		return this.nomeMae;
	}

	/**
	 * @param nomeMae the nomeMae to set
	 */
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	/**
	 * @return the nomePai
	 */
	@Column
	public String getNomePai() {
		return this.nomePai;
	}

	/**
	 * @param nomePai the nomePai to set
	 */
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	/**
	 * @return the irmaos
	 */
	@Column
	public String getIrmaos() {
		return this.irmaos;
	}

	/**
	 * @param irmaos the irmaos to set
	 */
	public void setIrmaos(String irmaos) {
		this.irmaos = irmaos;
	}

	/**
	 * @return the telefones
	 */
	@Column
	public String getTelefones() {
		return this.telefones;
	}

	/**
	 * @param telefones the telefones to set
	 */
	public void setTelefones(String telefones) {
		this.telefones = telefones;
	}

	/**
	 * @return the endereco
	 */
	@Column
	public String getEndereco() {
		return this.endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the cid
	 */
	@OneToMany
	public Cid getCid() {
		return this.cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(Cid cid) {
		this.cid = cid;
	}

	/**
	 * @return the latitude
	 */
	@Column
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
	@Column
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
	 * @return the emTratamento
	 */
	@Column
	public String getEmTratamento() {
		return this.emTratamento;
	}

	/**
	 * @param emTratamento the emTratamento to set
	 */
	public void setEmTratamento(String emTratamento) {
		this.emTratamento = emTratamento;
	}

	/**
	 * @return the obito
	 */
	@Column
	public String getObito() {
		return this.obito;
	}

	/**
	 * @param obito the obito to set
	 */
	public void setObito(String obito) {
		this.obito = obito;
	}

	/**
	 * Método que possibilita a criação de uma instância para trafegar entre os intents.
	 */
	public static final Parcelable.Creator<Paciente> CREATOR = new Parcelable.Creator<Paciente>() {
        public Paciente createFromParcel(Parcel pc) {
            return new Paciente(pc);
        }
        public Paciente[] newArray(int size) {
            return new Paciente[size];
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
		Paciente other = (Paciente) obj;
		if (this.id != other.id)
			return false;
		return true;
	}
}