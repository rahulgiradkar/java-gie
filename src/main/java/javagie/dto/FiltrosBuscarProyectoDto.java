package javagie.dto;

import java.io.Serializable;
import java.util.Date;

import javagie.entities.TipoEstadoProyecto;
import javagie.entities.TipoProyecto;
import javagie.entities.Usuario;

public class FiltrosBuscarProyectoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private TipoEstadoProyecto tipoEstadoProyecto;
	private TipoProyecto tipoProyecto;
	private Usuario usuario;
	private Date fechaInicial;
	private Date fechaFinal;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public TipoEstadoProyecto getTipoEstadoProyecto() {
		return tipoEstadoProyecto;
	}
	public void setTipoEstadoProyecto(TipoEstadoProyecto tipoEstadoProyecto) {
		this.tipoEstadoProyecto = tipoEstadoProyecto;
	}
	public TipoProyecto getTipoProyecto() {
		return tipoProyecto;
	}
	public void setTipoProyecto(TipoProyecto tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
}
