package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQueries({
    @NamedQuery(name="Usuario.traerPorEmail", query="SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name="Usuario.traerTodas", 
                    query="SELECT u FROM Usuario u ORDER BY u.apellidos ASC"),
    @NamedQuery(name="Usuario.buscar", 
                    query="SELECT u FROM Usuario u " +
                                    "WHERE UPPER(CONCAT(CONCAT(u.nombres, u.apellidos),u.email)) LIKE UPPER(:texto) " +
                                    "ORDER BY u.apellidos ASC")
})
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_IDUSUARIO_GENERATOR", sequenceName="USUARIO_ID_USUARIO_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_IDUSUARIO_GENERATOR")
	@Column(name="id_usuario", unique=true, nullable=false)
	private Integer idUsuario;

	@Column(length=100)
	private String apellidos;

	@Column(nullable=false, length=50)
	private String email;

	@Column(name="es_admin", nullable=false)
	private Boolean esAdmin;

	@Column(length=100)
	private String nombres;

	@Column(length=500)
	private String password;

	//bi-directional many-to-one association to Participante
	@OneToMany(mappedBy="usuario")
	private Set<Participante> participantes;

    public Usuario() {
    }

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Usuario))
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
	
	
}