package javagie.dao;

import java.util.List;

import javagie.entities.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class UsuarioDao {

	@PersistenceContext
	private EntityManager em;

	public List<Usuario> traerTodosMenosAdministradores() {
		return em.createNamedQuery("Usuario.traerTodosMenosAdministradores").getResultList();
	}
	
    public Usuario traerPorEmail(String email) {
    	try {
    		return (Usuario) em.createNamedQuery("Usuario.traerPorEmail")
    					.setParameter("email", email)
    					.getSingleResult();
    	}catch(NoResultException ex) {
    		return null;
    	}
    }

    public List<Usuario> traerTodos() {
    	return em.createNamedQuery("Usuario.traerTodas").getResultList();
    }
    
    public List<Usuario> buscar(String keyword) {
    	return em.createNamedQuery("Usuario.buscar")
    				.setParameter("texto", keyword)
    				.getResultList();
    }
}
