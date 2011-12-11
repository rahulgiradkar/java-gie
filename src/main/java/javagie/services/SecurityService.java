package javagie.services;

import java.util.List;

import javagie.entities.Usuario;
import javagie.util.EncodeUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@SuppressWarnings("unchecked")
public class SecurityService {

	private static Logger log = LoggerFactory.getLogger(SecurityService.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional(readOnly=false)
	public void guardarUsuario(Usuario usuario) {
		if(usuario == null ) {
			throw new IllegalArgumentException("Usuario es nulo");
		}
		
		if(usuario.getIdUsuario() == null) {
			if(usuario.getPassword() == null || usuario.getPassword().length() < 1) {
				usuario.setPassword("cambiame");
			}
			usuario.setPassword(EncodeUtil.generarHash("md5", usuario.getPassword()));
			usuario.setEsAdmin(false);
			em.persist(usuario);
		}
		else {
			log.debug("editando usuario");
			Usuario usuAux = em.find(Usuario.class, usuario.getIdUsuario());
			
			log.debug("pass anterior {0} pass nuevo {1}", usuAux.getPassword(), usuario.getPassword());
			if(usuario.getPassword() != null && usuario.getPassword().length() > 0) {
				usuario.setPassword(EncodeUtil.generarHash("md5", usuario.getPassword()));
			}
			else {
				usuario.setPassword(usuAux.getPassword());
			}
			em.merge(usuario);
		}
	}
	
	@Transactional(readOnly=false)
	public void eliminarUsuario(Usuario usuario) {
		if(usuario == null ) {
			throw new IllegalArgumentException("Usuario es nulo");
		}
		usuario = em.merge(usuario);
		em.remove(usuario);
	}
	
	@Transactional(readOnly=true)
	public List<Usuario> buscarUsuarios(String texto) {
		if(texto == null || texto.length() < 1) {
			return em.createNamedQuery("Usuario.traerTodas").getResultList();
		}
		
		return em.createNamedQuery("Usuario.buscar")
					.setParameter("texto", "%"+texto+"%")
					.getResultList();
	}

	@Transactional(readOnly=true)
	public Usuario traerUsuarioPorEmail(String email) {
		try {
			return (Usuario) em.createNamedQuery("Usuario.traerPorEmail")
					.setParameter("email", email)
					.getSingleResult();
		}catch (NoResultException ex) {
			return null;
		}
	}
}
