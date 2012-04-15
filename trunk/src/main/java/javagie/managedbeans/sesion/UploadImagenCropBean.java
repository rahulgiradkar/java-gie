/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javagie.managedbeans.sesion;

import java.io.Serializable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 *
 * @author eduardo
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UploadImagenCropBean implements Serializable{
    private static Logger log = LoggerFactory.getLogger(UploadImagenCropBean.class);
    
    
    private byte[] imagenSubida;
    private byte[] imagenCrop;
    private boolean subida;
    private boolean crop;
    private CroppedImage croppedImage;  

    
    public void subirImagen(FileUploadEvent event) {
        imagenSubida = event.getFile().getContents();
        subida = true;
        log.debug("subi imagen. size bytes: {}", imagenSubida.length);
    }
    
    public void crop() {
        imagenCrop = croppedImage.getBytes();
        crop = true;
        log.debug("crop imagen. size crop: {}", imagenCrop.length );
    }
    
    //getters and setters
    
    public CroppedImage getCroppedImage() {  
        return croppedImage;  
    }  
    public void setCroppedImage(CroppedImage croppedImage) {  
        this.croppedImage = croppedImage;  
    }

    public byte[] getImagenSubida() {
        return imagenSubida;
    }

    public byte[] getImagenCrop() {
        return imagenCrop;
    }
    
    public boolean isSubida() {
        return subida;
    }

    public boolean isCrop() {
        return crop;
    }
}
