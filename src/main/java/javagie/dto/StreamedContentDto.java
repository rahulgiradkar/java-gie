package javagie.dto;

import java.io.Serializable;

/**
 *
 * @author eduardo
 */
public class StreamedContentDto implements Serializable {
    
    private final byte[] byteArray;
    private final String contentType;
    private final String fileName;

    public StreamedContentDto(byte[] byteArray, String contentType, String fileName) {
        this.byteArray = byteArray;
        this.contentType = contentType;
        this.fileName = fileName;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileName() {
        return fileName;
    }
}
