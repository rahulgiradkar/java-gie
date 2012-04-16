package javagie.dao;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository("genericDao")
public class GenericDao {

	@PersistenceContext
    protected EntityManager em;

    public <T, K extends Serializable> T traerPorId(Class<T> type, K llave) {
        return em.find(type, llave);
    }

    public <T> void crear(T t) {
        em.persist(t);
    }

    public <T> void eliminar(T t) {
    	t = em.merge(t);
        em.remove(t);
    }

    public <T> T actualizar(T t) {
        return em.merge(t);
    }
}
