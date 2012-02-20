package javagie.dao;

import java.util.List;
import javagie.entities.Recurso;
import javagie.entities.TipoRecurso;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eduardo
 */
@Repository
public class RecursoDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Recurso> traerPorTipoRecurso(TipoRecurso tipoRecurso) {
        return em.createNamedQuery("Recurso.traerPorTipoRecurso")
                .setParameter("tipoRecurso", tipoRecurso)
                .getResultList();
    }
    
}
