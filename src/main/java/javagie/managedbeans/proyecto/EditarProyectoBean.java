package javagie.managedbeans.proyecto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javagie.entities.Participante;
import javagie.entities.TipoCargo;
import javagie.entities.TipoProyecto;
import javagie.entities.Usuario;
import javagie.exceptions.LogicaNegocioException;
import javagie.util.ConstantesUtil;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class EditarProyectoBean extends AbstractVerProyectoBean {

	private List<Usuario> usuarioList;
	private List<TipoCargo> tipoCargoList;
	private List<TipoProyecto> tipoProyectoList;
	private boolean crearProyecto;
	private Usuario usuario;
	private TipoCargo tipoCargo;
	private List<Long> idParticipantesEliminar;
	
	public String irEditarProyecto(Long idProyecto) {
		irVerProyecto(idProyecto);
		usuarioList = proyectoService.traerUsuariosParaProyecto();
		tipoCargoList = proyectoService.traerTodosTipoCargo();
		tipoProyectoList = proyectoService.traerTodosTiposDeProyectos();
		idParticipantesEliminar = new ArrayList<Long>();
		
		if(proyecto.getIdProyecto() == null)
			crearProyecto = true;
		else
			crearProyecto = false;
		
		return "editar-proyecto";
	}
	
	public void agregarParticipante() {
		Participante participante  = new Participante();
		participante.setUsuario(usuario);
		participante.setTipoCargo(tipoCargo);
		participante.setProyecto(proyecto);
		
		if(participanteList.contains(participante)) {
			facesUtil.addErrorMessage("agregarBoton", ConstantesUtil.MSJ_ERROR_YA_EXISTE_PARTICIPANTE);
		}
		else {
			participanteList.add(participante);
		}
		
	}
	
	public void eliminarParticipante(Participante participanteEliminar) {
		if(participanteEliminar.getIdParticipante() != null) {
			idParticipantesEliminar.add(participanteEliminar.getIdParticipante());
		}
		int index = participanteList.indexOf(participanteEliminar);
		if(index > -1) {
			participanteList.remove(index);
		}
	}
	
	public void guardarProyecto() {
		try {
			proyecto.setParticipantes(new HashSet<Participante>(participanteList));
			proyectoService.guardarProyecto(proyecto, idParticipantesEliminar);
			facesUtil.addInfoMessage(ConstantesUtil.MSJ_INFO_CAMBIOS_REALIZADOS);
		}catch(LogicaNegocioException ex) {
			facesUtil.addErrorMessage(ex.getMessage());
		}catch(Exception ex) {
			ex.printStackTrace();
			facesUtil.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public TipoCargo getTipoCargo() {
		return tipoCargo;
	}
	public void setTipoCargo(TipoCargo tipoCargo) {
		this.tipoCargo = tipoCargo;
	}
	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}

	public List<TipoCargo> getTipoCargoList() {
		return tipoCargoList;
	}

	public void setTipoCargoList(List<TipoCargo> tipoCargoList) {
		this.tipoCargoList = tipoCargoList;
	}

	public List<TipoProyecto> getTipoProyectoList() {
		return tipoProyectoList;
	}

	public void setTipoProyectoList(List<TipoProyecto> tipoProyectoList) {
		this.tipoProyectoList = tipoProyectoList;
	}

	public boolean isCrearProyecto() {
		return crearProyecto;
	}

	public void setCrearProyecto(boolean crearProyecto) {
		this.crearProyecto = crearProyecto;
	}
}
