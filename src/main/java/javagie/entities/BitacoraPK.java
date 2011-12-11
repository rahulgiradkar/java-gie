package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the vitacora database table.
 * 
 */
@Embeddable
public class BitacoraPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_participante", unique=true, nullable=false)
	private Integer idParticipante;

	@Column(unique=true, nullable=false)
	private Integer dia;

	@Column(unique=true, nullable=false)
	private Integer mes;

	@Column(unique=true, nullable=false)
	private Integer ano;

    public BitacoraPK() {
    }
	public Integer getIdParticipante() {
		return this.idParticipante;
	}
	public void setIdParticipante(Integer idParticipante) {
		this.idParticipante = idParticipante;
	}
	public Integer getDia() {
		return this.dia;
	}
	public void setDia(Integer dia) {
		this.dia = dia;
	}
	public Integer getMes() {
		return this.mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Integer getAno() {
		return this.ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BitacoraPK)) {
			return false;
		}
		BitacoraPK castOther = (BitacoraPK)other;
		return 
			this.idParticipante.equals(castOther.idParticipante)
			&& this.dia.equals(castOther.dia)
			&& this.mes.equals(castOther.mes)
			&& this.ano.equals(castOther.ano);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idParticipante.hashCode();
		hash = hash * prime + this.dia.hashCode();
		hash = hash * prime + this.mes.hashCode();
		hash = hash * prime + this.ano.hashCode();
		
		return hash;
    }
}