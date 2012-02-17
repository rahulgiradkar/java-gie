package javagie.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author eduardo
 */
@Entity
@Table(name="tipo_recurso")
public class TipoRecurso implements Serializable {
    
    public static final int ID_SALA = 1;
    public static final int ID_EQUIPO = 2; 
    
    @Id
    @Column(name="id_recurso")
    private Integer idRecurso;
    
    @Column(name="nombre", nullable=false)
    @Size(max=100)
    private String nombre;

    public Integer getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Integer idRecurso) {
        this.idRecurso = idRecurso;
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
        if (this.idRecurso != other.idRecurso && (this.idRecurso == null || !this.idRecurso.equals(other.idRecurso))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.idRecurso != null ? this.idRecurso.hashCode() : 0);
        return hash;
    }
}
