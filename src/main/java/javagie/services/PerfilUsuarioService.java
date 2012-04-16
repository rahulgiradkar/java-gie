package javagie.services;

import javagie.dao.ImagenPerfilDao;
import javagie.dto.StreamedContentDto;
import javagie.entities.ImagenPerfil;
import javagie.entities.Usuario;
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
public class PerfilUsuarioService {
    
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private ImagenPerfilDao imagenPerfilDao;
    

    @Transactional(readOnly=true)
    public ImagenPerfil traerImagenPerfil(Usuario usuario) {
        return imagenPerfilDao.traerPor(usuario); 
    }

    @Transactional(readOnly=false)
    public void guardarInformacionContacto(Usuario usuario, StreamedContentDto streamedContent) {
        if(usuario == null) {
            throw new IllegalArgumentException("El usuario es nulo!");
        }
        
        usuario = em.merge(usuario);
        if(streamedContent == null) {
            return;
        }
        
        ImagenPerfil imagenPerfil = new ImagenPerfil();
        imagenPerfil.setId(usuario.getIdUsuario());
        imagenPerfil.setUsuario(usuario);
        imagenPerfil.setBytesImagen(streamedContent.getByteArray());
        imagenPerfil.setContentType(streamedContent.getContentType());
        imagenPerfil = em.merge(imagenPerfil);
        usuario.setImagenPerfil(imagenPerfil);
    }
    
}
