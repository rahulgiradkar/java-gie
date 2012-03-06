package javagie.services;

import java.util.List;
import javagie.dao.RecursoDao;
import javagie.dao.TipoRecursoDao;
import javagie.entities.Recurso;
import javagie.entities.TipoRecurso;
import javagie.exceptions.NombreRecursoRepetidoException;
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
public class RecursoService {

    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private TipoRecursoDao tipoRecursoDao;
    
    @Autowired
    private RecursoDao recursoDao;
    
    @Transactional(readOnly=true)
    public List<TipoRecurso> traerTipoRecursosTodos() {
        return tipoRecursoDao.traerTodos();
    }

    @Transactional(readOnly=true)
    public List<Recurso> traerPorTipoRecurso(TipoRecurso tipoRecurso) {
        return recursoDao.traerPorTipoRecurso(tipoRecurso);
    }

    @Transactional(readOnly=false)
    public void crearNuevoRecurso(Recurso nuevoRecurso) {
        
        //verificar si existe otro recurso con el mismo nombre
        nuevoRecurso.setNombre(nuevoRecurso.getNombre().trim());
        Recurso recursoRepetido = recursoDao.traerPorNombre(nuevoRecurso.getNombre());
        if(recursoRepetido != null) {
            throw new NombreRecursoRepetidoException("recurso repetido");
        }
        em.persist(nuevoRecurso);
    }
    
}
