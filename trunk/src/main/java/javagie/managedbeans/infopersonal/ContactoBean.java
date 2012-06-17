package javagie.managedbeans.infopersonal;

import java.util.List;
import javagie.controllers.StreamedContentController;
import javagie.dto.StreamedContentDto;
import javagie.entities.*;
import javagie.exceptions.AccesoDenegadoBeanException;
import javagie.exceptions.LogicaNegocioException;
import javagie.managedbeans.sesion.Identidad;
import javagie.services.DireccionService;
import javagie.services.PerfilUsuarioService;
import javagie.services.SecurityService;
import javagie.util.ConstantesUtil;
import javagie.util.FacesUtil;
import javagie.util.ImageUtil;
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
    @Autowired
    private ImageUtil imageUtil;
    @Autowired
    private DireccionService direccionService;
    
    private boolean mostrarCropDialog;
    private boolean existeImagenPerfil;
    private CroppedImage croppedImage;
    private Usuario usuario;
    private Region region;
    private Provincia provincia;
    private List<Region> regiones;
    private List<Provincia> provincias;
    private List<Comuna> comunas;
    
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
        
        //carga de listas
        regiones = direccionService.traerRegiones();
        if(usuario.getDireccionActual() != null && usuario.getDireccionActual().getComuna() != null) {
            provincia = usuario.getDireccionActual().getComuna().getProvincia();
            region = provincia.getRegion();
            provincias = direccionService.traerProvinciasPorRegion(region);
            comunas = direccionService.traerComunasPorProvincia(provincia);
        }else {
            usuario.setDireccionActual(new Direccion());
        }
    }
    
    public void cambiarRegion() {
        provincia = null;
        usuario.getDireccionActual().setComuna(null);
        provincias = direccionService.traerProvinciasPorRegion(region);
        comunas = null;
    }
    
    public void cambiarProvincia() {
        usuario.getDireccionActual().setComuna(null);
        comunas = direccionService.traerComunasPorProvincia(provincia);
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
        StreamedContentDto streamedContentPreview = new StreamedContentDto(imageUtil.escalar(croppedImage.getBytes(), 100, 100, imageUtil.obtenerFormato(contentType)),contentType , "peview");
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

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Region> getRegiones() {
        return regiones;
    }

    public void setRegiones(List<Region> regiones) {
        this.regiones = regiones;
    }

    public List<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<Provincia> provincias) {
        this.provincias = provincias;
    }

    public List<Comuna> getComunas() {
        return comunas;
    }

    public void setComunas(List<Comuna> comunas) {
        this.comunas = comunas;
    }
    
    
}
