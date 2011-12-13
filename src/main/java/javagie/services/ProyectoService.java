package javagie.services;

import java.util.Date;
import java.util.List;

import javagie.dao.GenericDao;
import javagie.dao.ParticipanteDao;
import javagie.dao.TipoCargoDao;
import javagie.dao.TipoProyectoDao;
import javagie.dao.UsuarioDao;
import javagie.entities.Participante;
import javagie.entities.Proyecto;
import javagie.entities.TipoCargo;
import javagie.entities.TipoProyecto;
import javagie.entities.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProyectoService {

	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private TipoProyectoDao tipoProyectoDao;
	
	@Autowired
	private TipoCargoDao tipoCargoDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ParticipanteDao participanteDao;
	
	@Transactional(readOnly=true)
	public List<TipoProyecto> traerTodosTiposDeProyectos() {
		return tipoProyectoDao.traerTodos();
	}
	
	@Transactional(readOnly=true)
	public List<TipoCargo> traerTodosTipoCargo() {
		return tipoCargoDao.traerTodos();
	}
	
	@Transactional(readOnly=true)
	public List<Usuario> traerUsuariosParaProyecto() {
		return usuarioDao.traerTodosMenosAdministradores();
	}
	
	@Transactional(readOnly=true)
	public Proyecto traerProyecto(Long idProyecto) {
		if(idProyecto == null) {
			throw new IllegalArgumentException("argumento nulo");
		}
		
		return  genericDao.traerPorId(Proyecto.class, idProyecto);
	}
	
	@Transactional(readOnly=false)
	public Proyecto guardarProyecto(Proyecto proyecto, List<Long> idParticipantesEliminados) {
		if(proyecto == null) {
			throw new IllegalArgumentException("proyecto nulo");
		}
		
		if(proyecto.getIdProyecto() == null) {
			//nuevo proyecto
			proyecto.setFechaInicio(new Date());
			proyecto.setTipoProyecto(new TipoProyecto(TipoProyecto.ID_TIPO_PROYECTO_DESARROLLO));
			genericDao.crear(proyecto);
		}
		
		//actualizar o crear participantes
		for(Participante participante : proyecto.getParticipantes()) {
			participante.setProyecto(proyecto);
			Participante partNuevo = genericDao.actualizar(participante);
			participante.setIdParticipante(partNuevo.getIdParticipante());
		}
		
		//eliminar participantes
		for(Long idParticipanteAEliminar : idParticipantesEliminados) {
			Participante partEliminar = genericDao.traerPorId(Participante.class, idParticipanteAEliminar);
			genericDao.eliminar(partEliminar);
		}
		
		return proyecto;
	}

	@Transactional(readOnly=true)
	public List<Participante> traerParticipantes(Proyecto proyecto) {
		return participanteDao.traerPorProyecto(proyecto);
	}
	
}
