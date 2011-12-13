package javagie.dao;

import java.util.List;

import javagie.entities.TipoProyecto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class TipoProyectoDao {

	@PersistenceContext
	private EntityManager em;

	public List<TipoProyecto> traerTodos() {
		return em.createNamedQuery("TipoProyecto.traerTodos").getResultList();
	}
}
