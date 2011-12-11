package javagie.services;

import java.util.Date;
import java.util.List;

import javagie.dto.ResumenBitDto;
import javagie.entities.Bitacora;
import javagie.entities.Proyecto;
import javagie.entities.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FachadaService {

	@Autowired
	private BitacoraService bitacoraService;
	
	@Autowired 
	private SecurityService securityService;
	
	public List<Usuario> buscarUsuarios(String texto) {
		return securityService.buscarUsuarios(texto);
	}
	
	public void eliminarUsuario(Usuario usuario) {
		securityService.eliminarUsuario(usuario);
	}
	
	public void guardarBitacoras(List<Bitacora> bitacoras) {
		bitacoraService.guardarBitacoras(bitacoras);
	}
	
	public void guardarBitacoras(List<Bitacora> bitacoras, Date fechaActual) {
		bitacoraService.guardarBitacoras(bitacoras, fechaActual);
	}

	public void guardarUsuario(Usuario usuario) {
		 securityService.guardarUsuario(usuario);
	}
	
	public List<Bitacora> traerBitacorasConPartipante(Proyecto proyecto, Date fecha, String emailUsuario) {
		return bitacoraService.traerBitacorasConPartipante(proyecto, fecha, emailUsuario);
	}
	
	public List<Proyecto> traerProyectos(String emailUsuario) {
		return bitacoraService.traerProyectos(emailUsuario);
	}
	
	public ResumenBitDto traerResumenBitacoraDto(Proyecto proyecto, String emailUsuario) {
		return bitacoraService.traerResumenBitacoraDto(proyecto, emailUsuario);
	}
	
	public Usuario traerUsuarioPorEmail(String email) {
		return securityService.traerUsuarioPorEmail(email);
	}
	
	
}
