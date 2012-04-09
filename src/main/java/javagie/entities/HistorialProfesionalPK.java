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
public class HistorialProfesionalPK implements Serializable{
    
    @Column(name="id_empresa")
    private Long idEmpresa;
    
    @Column(name="id_usuario")
    private Long idUsuario;

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
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
        final HistorialProfesionalPK other = (HistorialProfesionalPK) obj;
        if (this.idEmpresa != other.idEmpresa && (this.idEmpresa == null || !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        if (this.idUsuario != other.idUsuario && (this.idUsuario == null || !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.idEmpresa != null ? this.idEmpresa.hashCode() : 0);
        hash = 89 * hash + (this.idUsuario != null ? this.idUsuario.hashCode() : 0);
        return hash;
    }
}
