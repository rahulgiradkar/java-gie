package javagie.services;

import java.util.List;
import javagie.dao.ComunaDao;
import javagie.dao.ProvinciaDao;
import javagie.dao.RegionDao;
import javagie.entities.Comuna;
import javagie.entities.Provincia;
import javagie.entities.Region;
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
public class DireccionService {
    
    
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RegionDao regionDao;
    @Autowired
    private ProvinciaDao provinciaDao;
    @Autowired
    private ComunaDao comunaDao;
    
    
    @Transactional(readOnly=true)
    public List<Region> traerRegiones() {
        return regionDao.traerTodas();
    }
    
    @Transactional(readOnly=true)
    public List<Provincia> traerProvinciasPorRegion(Region region) {
        return provinciaDao.traerPor(region);
    }
    
    @Transactional(readOnly=true)
    public List<Comuna> traerComunasPorProvincia(Provincia provincia) {
        return comunaDao.traerPor(provincia);
    }
    
}
