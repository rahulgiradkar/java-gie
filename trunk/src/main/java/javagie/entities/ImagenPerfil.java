package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author eduardo
 */
@Entity
@Table(name="imagen_perfil")
@NamedQueries({
    @NamedQuery(name="ImagenPerfil.traerPorUsuario", 
        query="SELECT img FROM ImagenPerfil img WHERE img.id = :idUsuario")
})
public class ImagenPerfil implements Serializable {
    
    @Id
    @Column(name="id_usuario")
    private Long id;
    
    @OneToOne
    @JoinColumn(name="id_usuario", insertable=false, updatable=false)
    private Usuario usuario;
    
    @Column(name="bytes_imagen", nullable=false)
    private byte[] bytesImagen;
    
    @Size(max=50)
    @Column(name="content_type", nullable=false)
    private String contentType;

    public byte[] getBytesImagen() {
        return bytesImagen;
    }

    public void setBytesImagen(byte[] bytesImagen) {
        this.bytesImagen = bytesImagen;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImagenPerfil other = (ImagenPerfil) obj;
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        return hash;
    }
}
