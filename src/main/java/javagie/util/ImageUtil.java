package javagie.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.primefaces.model.DefaultStreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author eduardo
 */
@Component
public class ImageUtil {
    
    private Logger log = LoggerFactory.getLogger(ImageUtil.class);
    
    /**
     * Valida tamaño width y height. si es -1 la omite
     * @param bufImage
     * @param width
     * @param height
     * @return 
     */
    public boolean proporcionesValidas(BufferedImage bufImage, int width, int height) {
        if(
                (bufImage.getWidth() == width || width == -1) 
                && 
                (bufImage.getHeight() == height || height == -1)) {
            return true;
        }
        else{
            return false;
        }
    }
    
    public BufferedImage toBufferImage(byte[] bytesImage) {
        InputStream imageInputStrem = new ByteArrayInputStream(bytesImage);
        try {
           return   ImageIO.read(imageInputStrem);
        }catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }finally {
            try {
                imageInputStrem.close();
            } catch (IOException ex) {
            }
        }
    }

    public DefaultStreamedContent nuevaInstanciaStreamedImg(byte[] bytes, String contentType) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            DefaultStreamedContent aux = new DefaultStreamedContent(inputStream, contentType);
            inputStream.close();
            return aux;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public byte[] escalar(byte[] img, int width, int height, String extensionImagen) {
        try {
            InputStream is = new ByteArrayInputStream(img);
            BufferedImage bufImage = ImageIO.read(is);
            is.close();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(escalar(bufImage, width, height), extensionImagen, os);

            byte[] nuevaImagenBytes = os.toByteArray();
            os.close();
            return nuevaImagenBytes;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("error escalar");
        }
    }
    
    private BufferedImage escalar(BufferedImage img, int width, int height) throws IOException {
        // Create new (blank) image of required (scaled) size
        log.debug("escalando a {}x{}", new Object[]{width,height});

        BufferedImage scaledImage = new BufferedImage(
           width, height, BufferedImage.TYPE_INT_RGB);

        // Paint scaled version of image to new image
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(img, 0, 0, width, height, null);

        // clean up
        return scaledImage;
    }
    
    public String obtenerFormato(String contentType) {
        if (contentType == null) {
            throw new RuntimeException("Problem getting url contentType is null!");
        }

        String format = contentType.toLowerCase().trim().replace("image/", "");

        if (format == null || format.length() == 0) {
            throw new RuntimeException("Unknow image mime type");
        }

        if (format.contains(";")) //contains a semicolon
        {
            format = format.split(";")[0];
        }

        if (format == null || format.length() == 0) {
            throw new RuntimeException("Unknow image mime type");
        }
        return format;
    }
    
    
}
