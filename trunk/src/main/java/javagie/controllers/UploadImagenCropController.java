/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javagie.controllers;

import java.io.OutputStream;
import javagie.managedbeans.sesion.UploadImagenCropBean;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author eduardo
 */
@Controller
@RequestMapping("/uploadimagencrop")
public class UploadImagenCropController {
    
    @Autowired
    private UploadImagenCropBean uploadImagenCropBean;

    @RequestMapping("/subida")
    public ResponseEntity<byte[]> imagenSubida(HttpServletResponse response) throws Exception{
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(uploadImagenCropBean.getImagenSubida(), headers, HttpStatus.OK);
    }
    
    @RequestMapping("/crop")
    public ResponseEntity<byte[]> imagenCrop(HttpServletResponse response) throws Exception {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(uploadImagenCropBean.getImagenCrop(), headers, HttpStatus.OK);
    }
    
}
