package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the tipo_estado_proyecto database table.
 * 
 */
@Entity
@Table(name="tipo_estado_proyecto")
public class TipoEstadoProyecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_estado_proyecto")
	private Long idTipoEstadoProyecto;

	@Column(nullable=false, length=50)
	private String nombre;

	//bi-directional many-to-one association to Proyecto
	@OneToMany(mappedBy="tipoEstadoProyecto")
	private Set<Proyecto> proyectos;

    public TipoEstadoProyecto() {
    }

	public TipoEstadoProyecto(Long idTipoEstado) {
		this.idTipoEstadoProyecto = idTipoEstado;
	}

	public Long getIdTipoEstadoProyecto() {
		return this.idTipoEstadoProyecto;
	}

	public void setIdTipoEstadoProyecto(Long idTipoEstadoProyecto) {
		this.idTipoEstadoProyecto = idTipoEstadoProyecto;
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
		result = prime
				* result
				+ ((idTipoEstadoProyecto == null) ? 0 : idTipoEstadoProyecto
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TipoEstadoProyecto))
			return false;
		TipoEstadoProyecto other = (TipoEstadoProyecto) obj;
		if (idTipoEstadoProyecto == null) {
			if (other.idTipoEstadoProyecto != null)
				return false;
		} else if (!idTipoEstadoProyecto.equals(other.idTipoEstadoProyecto))
			return false;
		return true;
	}
	
	
	
}