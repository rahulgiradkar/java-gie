package javagie.entities;

import java.util.Set;

import javagie.arquitectura.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the tipo_proyecto database table.
 * 
 */
@Entity
@Table(name="tipo_proyecto")
@NamedQueries({
	@NamedQuery(name="TipoProyecto.traerTodos", query="SELECT t FROM TipoProyecto t ORDER BY t.nombre ASC")
})
public class TipoProyecto implements BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final long ID_TIPO_PROYECTO_DESARROLLO = 1;
	
	@Id
	@Column(name="id_tipo_proyecto", unique=true, nullable=false)
	private Long idTipoProyecto;

	@Column(length=50)
	private String nombre;

	//bi-directional many-to-one association to Proyecto
	@OneToMany(mappedBy="tipoProyecto")
	private Set<Proyecto> proyectos;

    public TipoProyecto() {
    }
    
    public TipoProyecto(Long idTipoProyecto) {
    	this.idTipoProyecto = idTipoProyecto;
    }

	public Long getIdTipoProyecto() {
		return this.idTipoProyecto;
	}

	public void setIdTipoProyecto(Long idTipoProyecto) {
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

	@Transient
	@Override
	public Object getPrimaryKey() {
		return idTipoProyecto;
	}
	
}