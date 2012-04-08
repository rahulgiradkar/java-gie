package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author eduardo
 */
@Entity
public class Empresa implements Serializable {
   
   @Id
   @GeneratedValue(generator="empresaSeq", strategy= GenerationType.SEQUENCE)
   @SequenceGenerator(name="empresaSeq", sequenceName="empresa_id_empresa_seq")
   @Column(name="id_empresa")
   private Long idEmpresa;
   
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

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
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
        final Empresa other = (Empresa) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }
}
