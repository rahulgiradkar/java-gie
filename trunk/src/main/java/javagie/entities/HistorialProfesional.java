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
public class HistorialProfesional implements Serializable {
    
    @EmbeddedId
    private HistorialProfesionalPK primaryKey;
    
    @ManyToOne
    @JoinColumn(name="id_usuario", insertable=false, updatable=false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name="id_empresa", insertable=false, updatable=false)
    private Empresa empresa;

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public HistorialProfesionalPK getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(HistorialProfesionalPK primaryKey) {
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
        final HistorialProfesional other = (HistorialProfesional) obj;
        if (this.primaryKey != other.primaryKey && (this.primaryKey == null || !this.primaryKey.equals(other.primaryKey))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.primaryKey != null ? this.primaryKey.hashCode() : 0);
        return hash;
    }
}
