package javagie.dao;

import java.util.List;

import javagie.entities.TipoEstadoProyecto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class TipoEstadoProyectoDao {

	@PersistenceContext
	private EntityManager em;

	public List<TipoEstadoProyecto> traerTodos() {
		return em.createNamedQuery("TipoEstadoProyecto.traerTodos").getResultList();
	}
	
	
}
