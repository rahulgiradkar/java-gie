package javagie.dao;

import java.util.List;
import javagie.entities.Provincia;
import javagie.entities.Region;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eduardo
 */
@Repository
public class ProvinciaDao {
    
    @PersistenceContext
    private EntityManager em;
    
    
    public List<Provincia> traerPor(Region region) {
        return em.createNamedQuery("Provincia.traerPorRegion")
                .setParameter("region", region)
                .getResultList();
    }
    
}
