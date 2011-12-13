package javagie.dao;

import java.util.List;

import javagie.entities.Participante;
import javagie.entities.Proyecto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ParticipanteDao {

	@PersistenceContext
	private EntityManager em;
	
	public List<Participante> traerPorProyecto(Proyecto proyecto) {
		return em.createNamedQuery("Participante.traerPorProyecto")
				.setParameter("proyecto", proyecto)
				.getResultList();
	}
}
