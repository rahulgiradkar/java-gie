package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the tipo_cargo database table.
 * 
 */
@Entity
@Table(name="tipo_cargo")
public class TipoCargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_cargo", unique=true, nullable=false)
	private Integer idTipoCargo;

	@Column(length=50)
	private String nombre;

	//bi-directional many-to-one association to HorasSoporte
	@OneToMany(mappedBy="tipoCargo")
	private Set<HorasSoporte> horasSoportes;

	//bi-directional many-to-one association to Participante
	@OneToMany(mappedBy="tipoCargo")
	private Set<Participante> participantes;

    public TipoCargo() {
    }

	public Integer getIdTipoCargo() {
		return this.idTipoCargo;
	}

	public void setIdTipoCargo(Integer idTipoCargo) {
		this.idTipoCargo = idTipoCargo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<HorasSoporte> getHorasSoportes() {
		return this.horasSoportes;
	}

	public void setHorasSoportes(Set<HorasSoporte> horasSoportes) {
		this.horasSoportes = horasSoportes;
	}
	
	public Set<Participante> getParticipantes() {
		return this.participantes;
	}

	public void setParticipantes(Set<Participante> participantes) {
		this.participantes = participantes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idTipoCargo == null) ? 0 : idTipoCargo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TipoCargo))
			return false;
		TipoCargo other = (TipoCargo) obj;
		if (idTipoCargo == null) {
			if (other.idTipoCargo != null)
				return false;
		} else if (!idTipoCargo.equals(other.idTipoCargo))
			return false;
		return true;
	}
	
	
}