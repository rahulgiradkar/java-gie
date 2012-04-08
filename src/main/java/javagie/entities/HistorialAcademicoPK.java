/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javagie.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author eduardo
 */
@Embeddable
public class HistorialAcademicoPK implements Serializable {
    
    @Column(name="id_institucion")
    private Long idInstitucion;
    
    @Column(name="id_usuario")
    private Integer idUsuario;

    public Long getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Long idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HistorialAcademicoPK other = (HistorialAcademicoPK) obj;
        if (this.idInstitucion != other.idInstitucion && (this.idInstitucion == null || !this.idInstitucion.equals(other.idInstitucion))) {
            return false;
        }
        if (this.idUsuario != other.idUsuario && (this.idUsuario == null || !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.idInstitucion != null ? this.idInstitucion.hashCode() : 0);
        hash = 11 * hash + (this.idUsuario != null ? this.idUsuario.hashCode() : 0);
        return hash;
    }
}
