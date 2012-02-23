package javagie.dao;

import java.util.List;
import javagie.entities.Recurso;
import javagie.entities.Reserva;
import javagie.entities.ReservaRecurso;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eduardo
 */
@Repository
public class ReservaRecursoDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<ReservaRecurso> intersectan(ReservaRecurso reservaRecurso) {
        
        if(reservaRecurso == null || reservaRecurso.getFechaInicio() == null || 
                reservaRecurso.getFechaFin() == null || reservaRecurso.getRecurso() == null||
                reservaRecurso.getRecurso().getIdRecurso() == null) {
            throw new IllegalArgumentException("faltan datos para comparar interseccion!");
        }
        
        return em.createNamedQuery("ReservaRecurso.intersectan")
                .setParameter("idReserva", reservaRecurso.getIdReserva()!=null?reservaRecurso.getIdReserva():-9999999L)
                .setParameter("fechaInicio", reservaRecurso.getFechaInicio())
                .setParameter("fechaFin", reservaRecurso.getFechaFin())
                .setParameter("recurso", reservaRecurso.getRecurso())
                .getResultList();
    }

    public List<Reserva> traerPorRecurso(Recurso recurso) {
        return em.createNamedQuery("ReservaRecurso.traerPorRecurso")
                .setParameter("recurso", recurso)
                .getResultList();
    }
    
    
}
