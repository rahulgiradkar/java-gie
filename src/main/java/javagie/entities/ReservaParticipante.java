package javagie.entities;

import javax.persistence.*;

/**
 *
 * @author eduardo
 */
@Entity
@Table(name="reserva_participante")
@PrimaryKeyJoinColumn(name="id_reserva_participante")
public class ReservaParticipante extends Reserva{
    
    @ManyToOne
    @JoinColumn(name="id_participante", nullable=false)
    private Participante participante;

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }
    
}
