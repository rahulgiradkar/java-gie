package javagie.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author eduardo
 */
@Entity
@Table(name="recurso")
@NamedQueries({
    @NamedQuery(name="Recurso.traerPornombre",
        query="select r from Recurso r "
        + "where r.nombre = :nombreRecurso"),
    @NamedQuery(name="Recurso.traerPorTipoRecurso", 
        query="select r from Recurso r "
        + "where r.tipoRecurso = :tipoRecurso "
        + "order by r.nombre asc")
})
public class Recurso implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="recursoSeq")
    @SequenceGenerator(name="recursoSeq", sequenceName="RECURSO_ID_RECURSO_SEQ")
    @Column(name="id_recurso", nullable=false)
    private Long idRecurso;
    
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_tipo_recurso", nullable=false)
    private TipoRecurso tipoRecurso;
    
    @Size(max=100)
    @Column(name="nombre", nullable=false)
    private String nombre;
    
    @Size(max=500)
    @Column(name="detalle")
    private String detalle;
    
    @OneToMany(mappedBy="recurso", cascade=CascadeType.REMOVE)
    private Set<ReservaRecurso> reservas;

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Long getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoRecurso getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(TipoRecurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public Set<ReservaRecurso> getReservas() {
        return reservas;
    }

    public void setReservas(Set<ReservaRecurso> reservas) {
        this.reservas = reservas;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Recurso other = (Recurso) obj;
        if (this.idRecurso != other.idRecurso && (this.idRecurso == null || !this.idRecurso.equals(other.idRecurso))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.idRecurso != null ? this.idRecurso.hashCode() : 0);
        return hash;
    }
}
