package javagie.dao;

import java.util.List;
import javagie.entities.Region;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eduardo
 */
@Repository
public class RegionDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Region> traerTodas() {
        return em.createNamedQuery("Region.traerTodas").getResultList();
    }
    
}
