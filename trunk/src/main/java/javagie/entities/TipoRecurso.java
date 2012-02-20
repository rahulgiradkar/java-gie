package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author eduardo
 */
@Entity
@Table(name="tipo_recurso")
@NamedQueries({
    @NamedQuery(name="TipoRecurso.traerTodos", 
        query="select t from TipoRecurso t "
        + "order by t.nombre asc")
})
public class TipoRecurso implements Serializable {
    
    public static final int ID_SALA = 1;
    public static final int ID_EQUIPO = 2; 
    
    @Id
    @Column(name="id_tipo_recurso")
    private Integer idTipoRecurso;
    
    @Column(name="nombre", nullable=false)
    @Size(max=100)
    private String nombre;

    public Integer getIdTipoRecurso() {
        return idTipoRecurso;
    }

    public void setIdTipoRecurso(Integer idTipoRecurso) {
        this.idTipoRecurso = idTipoRecurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoRecurso other = (TipoRecurso) obj;
        if (this.idTipoRecurso != other.idTipoRecurso && (this.idTipoRecurso == null || !this.idTipoRecurso.equals(other.idTipoRecurso))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.idTipoRecurso != null ? this.idTipoRecurso.hashCode() : 0);
        return hash;
    }

    
}
