package javagie.services;

import java.util.Collections;
import java.util.List;
import javagie.dao.ReservaRecursoDao;
import javagie.entities.Recurso;
import javagie.entities.Reserva;
import javagie.entities.ReservaRecurso;
import javagie.exceptions.LogicaNegocioException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author eduardo
 */
@Service
public class ReservaService {
    
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private ReservaRecursoDao reservaRecursoDao;

    @Transactional(readOnly=true)
    public List<Reserva> traerReservas(Recurso recurso) {
        return reservaRecursoDao.traerPorRecurso(recurso);
    }

    @Transactional(readOnly=false)
    public void guardarReservaRecurso(ReservaRecurso reservaRecurso) {
        
        //verificar que la reserva no topa con otra
        List<ReservaRecurso> reservasQueIntersectan = reservaRecursoDao.intersectan(reservaRecurso);
        if(reservasQueIntersectan != null && !reservasQueIntersectan.isEmpty()) {
            throw new LogicaNegocioException("La reserva intersecta con otra");
        }
        
        //si no intercepta se guarda
        em.merge(reservaRecurso);
    }
    
    
    
}
