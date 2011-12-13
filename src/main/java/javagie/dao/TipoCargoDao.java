package javagie.dao;

import java.util.List;

import javagie.entities.TipoCargo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class TipoCargoDao {

	
	@PersistenceContext
	private EntityManager em;

	public List<TipoCargo> traerTodos() {
		return em.createNamedQuery("TipoCargo.traerTodos").getResultList();
	}
}
