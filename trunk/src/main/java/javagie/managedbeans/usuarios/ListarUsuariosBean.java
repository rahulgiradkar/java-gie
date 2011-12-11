package javagie.managedbeans.usuarios;

import java.util.List;

import javagie.entities.Usuario;
import javagie.services.FachadaService;
import javagie.util.ConstantesUtil;
import javagie.util.FacesUtil;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("request")
public class ListarUsuariosBean {

	@Autowired
	private FachadaService service;
	
	@Autowired
	private FacesUtil facesUtil;
	
	private List<Usuario> usuarioList;
	
	private String textoBuscar;
	
	@PostConstruct
	protected void iniciar() {
		usuarioList = service.buscarUsuarios(null);
	}
	
	public String buscar() {
		usuarioList = service.buscarUsuarios(textoBuscar);
		return null;
	}
	
	public String eliminar(Usuario usuario) {
		try {
			service.eliminarUsuario(usuario);
			usuarioList = service.buscarUsuarios(textoBuscar);
			facesUtil.addInfoMessage(ConstantesUtil.MSJ_INFO_CAMBIOS_REALIZADOS);
		}catch(Exception ex) {
			ex.printStackTrace();
			facesUtil.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
		}
		return null;
	}
	
	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}
	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}
	public String getTextoBuscar() {
		return textoBuscar;
	}
	public void setTextoBuscar(String textoBuscar) {
		this.textoBuscar = textoBuscar;
	}
	
}
