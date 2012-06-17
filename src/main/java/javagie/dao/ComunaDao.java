package javagie.dao;

import java.util.List;
import javagie.entities.Comuna;
import javagie.entities.Provincia;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eduardo
 */
@Repository
public class ComunaDao {
    
    @PersistenceContext
    private EntityManager em;
    
    
    public List<Comuna> traerPor(Provincia provincia) {
        return em.createNamedQuery("Comuna.traerPorProvincia")
                .setParameter("provincia", provincia)
                .getResultList();
    }
    
}
