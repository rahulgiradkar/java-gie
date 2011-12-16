package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the participante database table.
 * 
 */
@Entity
@Table(name="participante")
@NamedQueries({
	@NamedQuery(name="Participante.traerPorProyecto", 
			query="SELECT p FROM Participante p " +
					"WHERE p.proyecto = :proyecto " +
					"ORDER BY p.tipoCargo.idTipoCargo ASC, p.usuario.apellidos ASC"),
	@NamedQuery(name="Participante.traerPorProyectoYEmailUsuario", 
			query="SELECT p FROM Participante p " +
					"WHERE p.proyecto = :proyecto " +
					"AND p.usuario.email = :email")
})
public class Participante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PARTICIPANTE_IDPARTICIPANTE_GENERATOR", sequenceName="PARTICIPANTE_ID_PARTICIPANTE_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PARTICIPANTE_IDPARTICIPANTE_GENERATOR")
	@Column(name="id_participante", unique=true, nullable=false)
	private Long idParticipante;

	//bi-directional many-to-one association to Proyecto
    @ManyToOne
	@JoinColumn(name="id_proyecto", nullable=false)
	private Proyecto proyecto;

	//bi-directional many-to-one association to TipoCargo
    @ManyToOne
	@JoinColumn(name="id_tipo_cargo")
	private TipoCargo tipoCargo;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="id_usuario", nullable=false)
	private Usuario usuario;

	//bi-directional many-to-one association to Vitacora
	@OneToMany(mappedBy="participante", cascade=CascadeType.REMOVE)
	private Set<Bitacora> vitacoras;

    public Participante() {
    }

	public Long getIdParticipante() {
		return this.idParticipante;
	}

	public void setIdParticipante(Long idParticipante) {
		this.idParticipante = idParticipante;
	}

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	public TipoCargo getTipoCargo() {
		return this.tipoCargo;
	}

	public void setTipoCargo(TipoCargo tipoCargo) {
		this.tipoCargo = tipoCargo;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Set<Bitacora> getVitacoras() {
		return this.vitacoras;
	}

	public void setVitacoras(Set<Bitacora> vitacoras) {
		this.vitacoras = vitacoras;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((proyecto == null) ? 0 : proyecto.hashCode());
		result = prime * result
				+ ((tipoCargo == null) ? 0 : tipoCargo.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Participante))
			return false;
		Participante other = (Participante) obj;
		if (proyecto == null) {
			if (other.proyecto != null)
				return false;
		} else if (!proyecto.equals(other.proyecto))
			return false;
		if (tipoCargo == null) {
			if (other.tipoCargo != null)
				return false;
		} else if (!tipoCargo.equals(other.tipoCargo))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
}