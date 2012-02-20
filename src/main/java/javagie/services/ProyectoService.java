package javagie.services;

import java.util.Date;
import java.util.List;

import javagie.dao.ParticipanteDao;
import javagie.dao.ProyectoDao;
import javagie.dao.TipoCargoDao;
import javagie.dao.TipoEstadoProyectoDao;
import javagie.dao.TipoProyectoDao;
import javagie.dao.UsuarioDao;
import javagie.dto.FiltrosBuscarProyectoDto;
import javagie.entities.Participante;
import javagie.entities.Proyecto;
import javagie.entities.TipoCargo;
import javagie.entities.TipoEstadoProyecto;
import javagie.entities.TipoProyecto;
import javagie.entities.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProyectoService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private TipoProyectoDao tipoProyectoDao;
    @Autowired
    private TipoEstadoProyectoDao tipoEstadoProyectoDao;
    @Autowired
    private TipoCargoDao tipoCargoDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private ParticipanteDao participanteDao;
    @Autowired
    private ProyectoDao proyectoDao;

    @Transactional(readOnly = true)
    public List<TipoProyecto> traerTodosTiposDeProyectos() {
        return tipoProyectoDao.traerTodos();
    }

    @Transactional(readOnly = true)
    public List<TipoCargo> traerTodosTipoCargo() {
        return tipoCargoDao.traerTodos();
    }

    @Transactional(readOnly = true)
    public List<Usuario> traerUsuariosParaProyecto() {
        return usuarioDao.traerTodosMenosAdministradores();
    }

    @Transactional(readOnly = true)
    public Proyecto traerProyecto(Long idProyecto) {
        if (idProyecto == null) {
            throw new IllegalArgumentException("argumento nulo");
        }

        return em.find(Proyecto.class, idProyecto);
    }

    @Transactional(readOnly = false)
    public Proyecto guardarProyecto(Proyecto proyecto, List<Long> idParticipantesEliminados) {
        if (proyecto == null) {
            throw new IllegalArgumentException("proyecto nulo");
        }

        if (proyecto.getIdProyecto() == null) {
            //nuevo proyecto
            proyecto.setFechaInicio(new Date());
            proyecto.setTipoEstadoProyecto(new TipoEstadoProyecto(TipoEstadoProyecto.ID_INICIADO));
            em.persist(proyecto);
        } else {
            em.merge(proyecto);
        }

        //actualizar o crear participantes
        for (Participante participante : proyecto.getParticipantes()) {
            participante.setProyecto(proyecto);
            Participante partNuevo = em.merge(participante);
            participante.setIdParticipante(partNuevo.getIdParticipante());
        }

        //eliminar participantes
        for (Long idParticipanteAEliminar : idParticipantesEliminados) {
            Participante partEliminar = em.find(Participante.class, idParticipanteAEliminar);
            em.remove(partEliminar);
        }

        return proyecto;
    }

    @Transactional(readOnly = true)
    public List<Participante> traerParticipantes(Proyecto proyecto) {
        return participanteDao.traerPorProyecto(proyecto);
    }

    @Transactional(readOnly = true)
    public List<Proyecto> buscarProyectos(FiltrosBuscarProyectoDto filtrosDto, int pagInicio, int pagFin) {
        List<Proyecto> proyectoList = proyectoDao.buscar(filtrosDto, pagInicio, pagFin);
        for (Proyecto proyecto : proyectoList) {
            proyecto.getTipoProyecto().getNombre();
            proyecto.getTipoEstadoProyecto().getNombre();
        }
        return proyectoList;
    }

    @Transactional(readOnly = true)
    public Integer buscarProyectosRowCount(FiltrosBuscarProyectoDto filtrosDto) {
        return proyectoDao.buscarRowCount(filtrosDto);
    }

    @Transactional(readOnly = false)
    public void eliminar(Proyecto proyecto) {
        if (proyecto == null) {
            throw new IllegalArgumentException("proyecto nulo");
        }
        proyecto = em.find(Proyecto.class, proyecto.getIdProyecto());
        if (proyecto == null) {
            throw new IllegalArgumentException("no se encontro proyecto");
        }
        em.remove(proyecto);
    }

    @Transactional(readOnly = true)
    public List<TipoEstadoProyecto> traerTodosTipoEstadoProyecto() {
        return tipoEstadoProyectoDao.traerTodos();
    }

    @Transactional(readOnly=true)
    public List<Proyecto> traerProyectosPorEmailUsuario(String email) {
        return proyectoDao.traerPorEmailUsuario(email);
    }
}
