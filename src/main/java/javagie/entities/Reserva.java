package javagie.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author eduardo
 */
@Entity
@Table(name="reserva")
@Inheritance(strategy= InheritanceType.JOINED)
public abstract class Reserva implements Serializable{
    
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="reservaSeq")
    @SequenceGenerator(name="reservaSeq", sequenceName="RESERVA_ID_RESERVA_SEQ")
    @Column(name="id_reserva", nullable=false)
    protected Long idReserva;
    
    @ManyToOne
    @JoinColumn(name="id_usuario", nullable=false)
    protected Usuario reservadoPor;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_inicio", nullable=false)
    protected Date fechaInicio;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_fin", nullable=false)
    protected Date fechaFin;
    
    @Size(max=255)
    @Column(name="observacion")
    protected String observacion;

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Usuario getReservadoPor() {
        return reservadoPor;
    }

    public void setReservadoPor(Usuario reservadoPor) {
        this.reservadoPor = reservadoPor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reserva other = (Reserva) obj;
        if (this.idReserva != other.idReserva && (this.idReserva == null || !this.idReserva.equals(other.idReserva))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.idReserva != null ? this.idReserva.hashCode() : 0);
        return hash;
    }
}
