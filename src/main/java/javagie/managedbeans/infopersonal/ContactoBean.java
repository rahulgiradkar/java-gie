/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javagie.managedbeans.infopersonal;

import javagie.managedbeans.sesion.UploadImagenCropBean;
import javax.faces.context.FacesContext;
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
    private UploadImagenCropBean uploadImagenCropBean;
    
    public void iniciar() {
        if(FacesContext.getCurrentInstance().isPostback()) {
            return;
        }
        
    }
    
    
    
}
