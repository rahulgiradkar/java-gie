package javagie.managedbeans.infopersonal;

import javagie.controllers.StreamedContentController;
import javagie.dto.StreamedContentDto;
import javagie.entities.ImagenPerfil;
import javagie.entities.Usuario;
import javagie.exceptions.AccesoDenegadoBeanException;
import javagie.exceptions.LogicaNegocioException;
import javagie.managedbeans.sesion.Identidad;
import javagie.services.PerfilUsuarioService;
import javagie.services.SecurityService;
import javagie.util.ConstantesUtil;
import javagie.util.FacesUtil;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author eduardo
 */

@Component
@Scope("request")
public class ContactoBean {
    private Logger log = LoggerFactory.getLogger(ContactoBean.class);
    
    @Autowired
    private StreamedContentController streamedContentController;
    @Autowired
    private FacesUtil facesUtil;
    @Autowired
    private Identidad identidad;
    @Autowired
    private PerfilUsuarioService perfilUsuarioService;
    @Autowired
    private SecurityService securityService;
    
    private boolean mostrarCropDialog;
    private boolean existeImagenPerfil;
    private CroppedImage croppedImage;
    private Usuario usuario;
    
    public void iniciar() {
        if(FacesContext.getCurrentInstance().isPostback()) {
            return;
        }
        log.debug("entre al bean");
        limpiarModoCrop();
        
        //verificar si existe el usuario
        usuario = securityService.traerUsuarioValidoPorEmail(identidad.getEmail());
        if(usuario == null) {
            throw new AccesoDenegadoBeanException("Acceso denegado al perfil. No existe usuario");
        }
        
        //obtener imagen de usuario y cargar en session
        ImagenPerfil imagenPerfil = perfilUsuarioService.traerImagenPerfil(usuario);
        existeImagenPerfil = imagenPerfil != null? true:false;
        if(existeImagenPerfil) {
            StreamedContentDto streamedContent = 
                    new StreamedContentDto(imagenPerfil.getBytesImagen(), imagenPerfil.getContentType(), "preview");
            streamedContentController.setStreamedContent(facesUtil.obtenerHttpSession(), streamedContent);
        }
    }
    
    public void subirImagen(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        StreamedContentDto streamedContent = new StreamedContentDto(file.getContents(), file.getContentType(), file.getFileName());
        streamedContentController.setStreamedContent(facesUtil.obtenerHttpSession(), streamedContent);
        mostrarCropDialog = true;
        log.debug("subi imagen. size bytes: {}, contentType: {}", 
                new Object[]{streamedContent.getByteArray().length, streamedContent.getContentType()});
    }
    
    public void quitarImagen() {
        existeImagenPerfil = false;
        streamedContentController.limpiarSession(facesUtil.obtenerHttpSession());
    }
    
    public void crop() {
        log.debug("crop imagen. size crop: {}", croppedImage.getBytes().length );
        String contentType = streamedContentController.getStreamedContent(facesUtil.obtenerHttpSession(), 0).getContentType();
        StreamedContentDto streamedContentPreview = new StreamedContentDto(croppedImage.getBytes(),contentType , "peview");
        existeImagenPerfil = true;
        limpiarModoCrop();
        streamedContentController.setStreamedContent(facesUtil.obtenerHttpSession(), streamedContentPreview);
    }
    
    public void cancelarCrop() {
        limpiarModoCrop();
    }
    
    public void limpiarModoCrop() {
        croppedImage = null;
        mostrarCropDialog = false;
        streamedContentController.limpiarSession(facesUtil.obtenerHttpSession());
    }
    
    public void guardar() {
        try {
            StreamedContentDto streamedContent = streamedContentController
                    .getStreamedContent(facesUtil.obtenerHttpSession(), 0);
            perfilUsuarioService.guardarInformacionContacto(usuario, streamedContent);
            facesUtil.addInfoMessage(ConstantesUtil.MSJ_INFO_CAMBIOS_REALIZADOS);
        }catch(LogicaNegocioException ex) {
            facesUtil.addErrorMessage(ex.getMessage());
        }catch(Exception ex) {
            log.error("error", ex);
            facesUtil.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
        }
    }
    
    //getters and setters 

    public CroppedImage getCroppedImage() {
        return croppedImage;
    }

    public void setCroppedImage(CroppedImage croppedImage) {
        this.croppedImage = croppedImage;
    }

    public boolean isExisteImagenPerfil() {
        return existeImagenPerfil;
    }

    public void setExisteImagenPerfil(boolean existeImagenPerfil) {
        this.existeImagenPerfil = existeImagenPerfil;
    }

    public boolean isMostrarCropDialog() {
        return mostrarCropDialog;
    }

    public void setMostrarCropDialog(boolean mostrarCropDialog) {
        this.mostrarCropDialog = mostrarCropDialog;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
