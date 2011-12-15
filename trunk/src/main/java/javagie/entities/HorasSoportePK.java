package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the horas_soporte database table.
 * 
 */
@Embeddable
public class HorasSoportePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_tipo_cargo", unique=true, nullable=false)
	private Long idTipoCargo;

	@Column(name="id_proyecto", unique=true, nullable=false)
	private Long idProyecto;

    public HorasSoportePK() {
    }
	public Long getIdTipoCargo() {
		return this.idTipoCargo;
	}
	public void setIdTipoCargo(Long idTipoCargo) {
		this.idTipoCargo = idTipoCargo;
	}
	public Long getIdProyecto() {
		return this.idProyecto;
	}
	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HorasSoportePK)) {
			return false;
		}
		HorasSoportePK castOther = (HorasSoportePK)other;
		return 
			this.idTipoCargo.equals(castOther.idTipoCargo)
			&& this.idProyecto.equals(castOther.idProyecto);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idTipoCargo.hashCode();
		hash = hash * prime + this.idProyecto.hashCode();
		
		return hash;
    }
}