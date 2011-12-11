package javagie.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vitacora database table.
 * 
 */
@Entity
@Table(name="bitacora")
@NamedQueries({
	@NamedQuery(name="Bitacora.traerPorFechaYProyectoYEmailUsuario", 
			query="SELECT DISTINCT b FROM Bitacora b " +
					"WHERE b.id.dia = :dia " +
					"AND b.id.mes = :mes " +
					"AND b.id.ano = :ano " +
					"AND b.participante.proyecto = :proyecto " +
					"AND b.participante.usuario.email = :email"),
	@NamedQuery(name="Bitacora.traerPorProyectoYEmailUsuario", 
			query="SELECT DISTINCT b FROM Bitacora b " +
					"WHERE b.participante.proyecto = :proyecto " +
					"AND b.participante.usuario.email = :email " +
					"ORDER BY b.id.ano DESC, b.id.mes DESC, b.id.dia DESC")
})
public class Bitacora implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BitacoraPK id;

	@Column(name="horas_hombre", nullable=false)
	private Integer horasHombre;

	@Column(nullable=false, length=2147483647)
	private String texto;

	//bi-directional many-to-one association to Participante
    @ManyToOne
	@JoinColumn(name="id_participante", nullable=false, insertable=false, updatable=false)
	private Participante participante;

    public Bitacora() {
    }

	public BitacoraPK getId() {
		return this.id;
	}

	public void setId(BitacoraPK id) {
		this.id = id;
	}
	
	public Integer getHorasHombre() {
		return this.horasHombre;
	}

	public void setHorasHombre(Integer horasHombre) {
		this.horasHombre = horasHombre;
	}

	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Participante getParticipante() {
		return this.participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Bitacora))
			return false;
		Bitacora other = (Bitacora) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}