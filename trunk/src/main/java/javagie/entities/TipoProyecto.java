package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the tipo_proyecto database table.
 * 
 */
@Entity
@Table(name="tipo_proyecto")
public class TipoProyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_proyecto", unique=true, nullable=false)
	private Integer idTipoProyecto;

	@Column(length=50)
	private String nombre;

	//bi-directional many-to-one association to Proyecto
	@OneToMany(mappedBy="tipoProyecto")
	private Set<Proyecto> proyectos;

    public TipoProyecto() {
    }

	public Integer getIdTipoProyecto() {
		return this.idTipoProyecto;
	}

	public void setIdTipoProyecto(Integer idTipoProyecto) {
		this.idTipoProyecto = idTipoProyecto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Proyecto> getProyectos() {
		return this.proyectos;
	}

	public void setProyectos(Set<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idTipoProyecto == null) ? 0 : idTipoProyecto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TipoProyecto))
			return false;
		TipoProyecto other = (TipoProyecto) obj;
		if (idTipoProyecto == null) {
			if (other.idTipoProyecto != null)
				return false;
		} else if (!idTipoProyecto.equals(other.idTipoProyecto))
			return false;
		return true;
	}
	
}