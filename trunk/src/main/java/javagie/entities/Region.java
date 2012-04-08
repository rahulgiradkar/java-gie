package javagie.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author eduardo
 */
@Entity
public class Region implements Serializable{

    @Id
    @Column(name="id_region")
    private Integer idRegion;
    
    @Column(name="nombre")
    private String nombre;
    
    @OneToMany(mappedBy="region")
    private Set<Provincia> provincias = new HashSet<Provincia>();

    public Integer getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Integer idRegion) {
        this.idRegion = idRegion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(Set<Provincia> provincias) {
        this.provincias = provincias;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Region other = (Region) obj;
        if (this.idRegion != other.idRegion && (this.idRegion == null || !this.idRegion.equals(other.idRegion))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.idRegion != null ? this.idRegion.hashCode() : 0);
        return hash;
    }
}
