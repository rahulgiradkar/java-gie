package javagie.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author eduardo
 */
@Entity
public class HistorialAcademico implements Serializable {
    
    @EmbeddedId
    private HistorialAcademicoPK primaryKey;
    
    @ManyToOne
    @JoinColumn(name="id_usuario", insertable=false, updatable=false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name="id_institucion", insertable=false, updatable=false)
    private Institucion institucion;

    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    public HistorialAcademicoPK getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(HistorialAcademicoPK primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HistorialAcademico other = (HistorialAcademico) obj;
        if (this.primaryKey != other.primaryKey && (this.primaryKey == null || !this.primaryKey.equals(other.primaryKey))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.primaryKey != null ? this.primaryKey.hashCode() : 0);
        return hash;
    }
}
