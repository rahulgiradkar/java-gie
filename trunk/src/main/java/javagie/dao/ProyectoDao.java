package javagie.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class ProyectoDao {

	@PersistenceContext
	private EntityManager em;
}
