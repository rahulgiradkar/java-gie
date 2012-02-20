package javagie.dao;

import java.util.List;
import javagie.entities.TipoRecurso;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eduardo
 */
@Repository
public class TipoRecursoDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<TipoRecurso> traerTodos() {
        return em.createNamedQuery("TipoRecurso.traerTodos").getResultList();
    }
}
