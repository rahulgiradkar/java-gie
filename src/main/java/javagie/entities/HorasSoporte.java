package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the horas_soporte database table.
 * 
 */
@Entity
@Table(name="horas_soporte")
public class HorasSoporte implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HorasSoportePK id;

	@Column(name="horas_hombres")
	private Integer horasHombres;

	//bi-directional many-to-one association to Proyecto
    @ManyToOne
	@JoinColumn(name="id_proyecto", nullable=false, insertable=false, updatable=false)
	private Proyecto proyecto;

	//bi-directional many-to-one association to TipoCargo
    @ManyToOne
	@JoinColumn(name="id_tipo_cargo", nullable=false, insertable=false, updatable=false)
	private TipoCargo tipoCargo;

    public HorasSoporte() {
    }

	public HorasSoportePK getId() {
		return this.id;
	}

	public void setId(HorasSoportePK id) {
		this.id = id;
	}
	
	public Integer getHorasHombres() {
		return this.horasHombres;
	}

	public void setHorasHombres(Integer horasHombres) {
		this.horasHombres = horasHombres;
	}

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	public TipoCargo getTipoCargo() {
		return this.tipoCargo;
	}

	public void setTipoCargo(TipoCargo tipoCargo) {
		this.tipoCargo = tipoCargo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof HorasSoporte))
			return false;
		HorasSoporte other = (HorasSoporte) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}