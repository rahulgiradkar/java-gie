package javagie.dao;

import javagie.entities.ImagenPerfil;
import javagie.entities.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author eduardo
 */
@Repository
public class ImagenPerfilDao {
    
    @PersistenceContext
    private EntityManager em;

    public ImagenPerfil traerPor(Usuario usuario) {
        try {
            return (ImagenPerfil) em.createNamedQuery("ImagenPerfil.traerPorUsuario")
                    .setParameter("idUsuario", usuario.getIdUsuario())
                    .getSingleResult();
        }catch(NoResultException ex) {
            return null;
        }
    }
    
    
    
}
