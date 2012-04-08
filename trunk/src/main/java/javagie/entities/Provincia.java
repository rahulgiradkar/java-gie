package javagie.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author eduardo
 */
@Entity
public class Provincia implements Serializable {
    
    @Id
    @Column(name="id_provincia")
    private Integer idProvincia;
    
    @Column(name="nombre")
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name="id_region")
    private Region region;
    
    @OneToMany(mappedBy="provincia")
    private Set<Comuna> comunas;

    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Set<Comuna> getComunas() {
        return comunas;
    }

    public void setComunas(Set<Comuna> comunas) {
        this.comunas = comunas;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Provincia other = (Provincia) obj;
        if (this.idProvincia != other.idProvincia && (this.idProvincia == null || !this.idProvincia.equals(other.idProvincia))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.idProvincia != null ? this.idProvincia.hashCode() : 0);
        return hash;
    }
}
