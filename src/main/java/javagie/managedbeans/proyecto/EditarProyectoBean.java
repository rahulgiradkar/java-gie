package javagie.managedbeans.proyecto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javagie.entities.*;
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
	private boolean verFormParticipante;
	
	public String irCrearProyecto() {
		return irEditarProyecto(null);
	}
	
	public String irEditarProyecto(Proyecto proyecto) {
		irVerProyecto(proyecto);
		this.usuarioList = proyectoService.traerUsuariosParaProyecto();
		this.tipoCargoList = proyectoService.traerTodosTipoCargo();
		this.tipoProyectoList = proyectoService.traerTodosTiposDeProyectos();
		this.idParticipantesEliminar = new ArrayList<Long>();
		this.verFormParticipante = false;
		
		if(this.proyecto.getIdProyecto() == null) {
			crearProyecto = true;
			return "crear-proyecto";
		}else {
			crearProyecto = false;
			return "editar-proyecto";
		}
	}
	
	public void abrirFormParticipante() {
		this.verFormParticipante = true;
		this.usuario = null;
		this.tipoCargo = null;
	}
	
	public void cancelarFormParticipante() {
		this.verFormParticipante = false;
	}
	
	public void agregarParticipante() {
		Participante participante  = new Participante();
		participante.setUsuario(usuario);
		participante.setTipoCargo(tipoCargo);
		participante.setProyecto(proyecto);
		
		if(participanteList.contains(participante)) {
			facesUtil.addErrorMessage("editarForm:usuario", ConstantesUtil.MSJ_ERROR_YA_EXISTE_PARTICIPANTE);
		}
		else {
			participanteList.add(participante);
			this.verFormParticipante = false;
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
	
	public List<Long> getIdParticipantesEliminar() {
		return idParticipantesEliminar;
	}
	public void setIdParticipantesEliminar(List<Long> idParticipantesEliminar) {
		this.idParticipantesEliminar = idParticipantesEliminar;
	}
	public boolean isVerFormParticipante() {
		return verFormParticipante;
	}
	public void setVerFormParticipante(boolean verFormParticipante) {
		this.verFormParticipante = verFormParticipante;
	}
}
