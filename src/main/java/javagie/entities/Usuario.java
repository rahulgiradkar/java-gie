package javagie.entities;

import java.util.HashSet;
import java.util.Set;
import javagie.arquitectura.BaseEntity;
import javax.persistence.*;

/**
 * The persistent class for the usuario database table.
 *
 */
@Entity
@Table(name = "usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.traerTodosMenosAdministradores", query = "SELECT u FROM Usuario u "
    + "WHERE u.esAdmin = false ORDER BY u.apellidos ASC, u.nombres ASC"),
    @NamedQuery(name = "Usuario.traerPorEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.traerTodas",
    query = "SELECT u FROM Usuario u ORDER BY u.apellidos ASC"),
    @NamedQuery(name = "Usuario.buscar",
    query = "SELECT u FROM Usuario u "
    + "WHERE UPPER(CONCAT(CONCAT(u.nombres, u.apellidos),u.email)) LIKE UPPER(:texto) "
    + "ORDER BY u.apellidos ASC")
})
public class Usuario implements BaseEntity {

    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "USUARIO_IDUSUARIO_GENERATOR", sequenceName = "USUARIO_ID_USUARIO_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_IDUSUARIO_GENERATOR")
    @Column(name = "id_usuario", unique = true, nullable = false)
    private Long idUsuario;
    
    @Column(length = 100)
    private String apellidos;
    
    @Column(nullable = false, length = 50)
    private String email;
    
    @Column(name = "es_admin", nullable = false)
    private Boolean esAdmin;
    
    @Column(length = 100)
    private String nombres;
    
    @Column(length = 500)
    private String password;
    
    //bi-directional many-to-one association to Participante
    @OneToMany(mappedBy = "usuario")
    private Set<Participante> participantes = new HashSet<Participante>();
    
    @OneToMany(mappedBy = "reservadoPor", cascade = CascadeType.REMOVE)
    private Set<Reserva> reservasRealizadas = new HashSet<Reserva>();
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="direccion_id_comuna", column=@Column(name="direccion_actual_id_comuna")),
        @AttributeOverride(name="direccion_calle", column=@Column(name="direccion_actual_calle"))
    })
    private Direccion direccionActual;
    
    @OneToOne(mappedBy="usuario")
    private ImagenPerfil imagenPerfil;
    
    @OneToMany(mappedBy="usuario")
    private Set<HistorialAcademico> historialesAcademicos;
    
    @OneToMany(mappedBy="usuario")
    private Set<HistorialProfesional> historialesProfesionales;
    
    
    public Usuario() {
    }

    public Usuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEsAdmin() {
        return this.esAdmin;
    }

    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Participante> getParticipantes() {
        return this.participantes;
    }

    public void setParticipantes(Set<Participante> participantes) {
        this.participantes = participantes;
    }

    public Set<Reserva> getReservasRealizadas() {
        return reservasRealizadas;
    }

    public void setReservasRealizadas(Set<Reserva> reservasRealizadas) {
        this.reservasRealizadas = reservasRealizadas;
    }

    public Direccion getDireccionActual() {
        return direccionActual;
    }

    public void setDireccionActual(Direccion direccionActual) {
        this.direccionActual = direccionActual;
    }

    public ImagenPerfil getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(ImagenPerfil imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public Set<HistorialAcademico> getHistorialesAcademicos() {
        return historialesAcademicos;
    }

    public void setHistorialesAcademicos(Set<HistorialAcademico> historialesAcademicos) {
        this.historialesAcademicos = historialesAcademicos;
    }

    public Set<HistorialProfesional> getHistorialesProfesionales() {
        return historialesProfesionales;
    }

    public void setHistorialesProfesionales(Set<HistorialProfesional> historialesProfesionales) {
        this.historialesProfesionales = historialesProfesionales;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        return true;
    }

    @Transient
    @Override
    public Object getPrimaryKey() {
        return getIdUsuario();
    }
}