package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author eduardo
 */
@Entity
public class Comuna implements Serializable {
    
    @Id
    @Column(name="id_comuna")
    private Integer idComuna;
    
    @Column(name="nombre")
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name="id_provincia")
    private Provincia provincia;

    public Integer getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(Integer idComuna) {
        this.idComuna = idComuna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Comuna other = (Comuna) obj;
        if (this.idComuna != other.idComuna && (this.idComuna == null || !this.idComuna.equals(other.idComuna))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.idComuna != null ? this.idComuna.hashCode() : 0);
        return hash;
    }
}
