/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author eduardo
 */
@Entity
public class Institucion implements Serializable {

    @Id
    @GeneratedValue(generator="institucionSeq", strategy= GenerationType.SEQUENCE)
    @SequenceGenerator(name="institucionSeq", sequenceName="institucion_id_institucion_seq")
    private Long idInstitucion;
    
    @Size(max=100)
    @Column(name="nombre")
    private String nombre;
    
    @Size(max=100)
    @Column(name="fono_contacto")
    private String fonoContacto;

    public String getFonoContacto() {
        return fonoContacto;
    }

    public void setFonoContacto(String fonoContacto) {
        this.fonoContacto = fonoContacto;
    }

    public Long getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Long idInstitucion) {
        this.idInstitucion = idInstitucion;
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
        final Institucion other = (Institucion) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }
    
    
}
