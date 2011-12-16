package javagie.dto;

import java.io.Serializable;
import java.util.Date;

public class FiltrosBuscarProyectoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private Long idTipoEstado;
	private Long idTipoProyecto;
	private Long idUsuario;
	private Date fechaInicial;
	private Date fechaFinal;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getIdTipoEstado() {
		return idTipoEstado;
	}
	public void setIdTipoEstado(Long idTipoEstado) {
		this.idTipoEstado = idTipoEstado;
	}
	public Long getIdTipoProyecto() {
		return idTipoProyecto;
	}
	public void setIdTipoProyecto(Long idTipoProyecto) {
		this.idTipoProyecto = idTipoProyecto;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Date getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	public Date getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	@Override
	public String toString() {
		return "FiltrosBuscarProyectoDto [nombre=" + nombre + ", idTipoEstado="
				+ idTipoEstado + ", idTipoProyecto=" + idTipoProyecto
				+ ", idUsuario=" + idUsuario + ", fechaInicial=" + fechaInicial
				+ ", fechaFinal=" + fechaFinal + "]";
	}

}
