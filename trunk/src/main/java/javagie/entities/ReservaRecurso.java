/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javagie.entities;

import javax.persistence.*;

/**
 *
 * @author eduardo
 */
@Entity
@Table(name="reserva_recurso")
@PrimaryKeyJoinColumn(name="id_reserva_recurso")
public class ReservaRecurso extends Reserva {
    
    @ManyToOne
    @JoinColumn(name="id_recurso", nullable=false)
    private Recurso recurso;
    
    @ManyToOne
    @JoinColumn(name="id_proyecto", nullable=false)
    private Proyecto proyecto;

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
}
