package javagie.dao;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javagie.dto.FiltrosBuscarProyectoDto;
import javagie.entities.Proyecto;
import javagie.entities.TipoEstadoProyecto;
import javagie.entities.TipoProyecto;
import javagie.entities.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class ProyectoDao {

	@PersistenceContext
	private EntityManager em;

	
	public List<Proyecto> buscar(FiltrosBuscarProyectoDto filtrosDto, int pagInicio, int pagFin) {
		
		if(filtrosDto == null) {
			return Collections.EMPTY_LIST;
		}
		
		Criteria criteria = buscar(filtrosDto);
		
		return criteria
				.setFirstResult(pagInicio)
				.setMaxResults(pagInicio + (pagFin-pagInicio))
				.list();
	}
	
	public Integer buscarRowCount(FiltrosBuscarProyectoDto filtrosDto) {
		if(filtrosDto == null) {
			return 0;
		}
		
		Criteria criteria = buscar(filtrosDto);
		criteria.setProjection(Projections.rowCount());
	    return (Integer) criteria.uniqueResult();
	}
	
	//**************
	//PRIVADOS
	//**************
	
	private Criteria buscar(FiltrosBuscarProyectoDto filtrosDto) {
		String nombre = (filtrosDto.getNombre() != null && filtrosDto.getNombre().trim().length() > 0)?filtrosDto.getNombre().trim():null;
		Long idUsuario = (filtrosDto.getIdUsuario() != null && filtrosDto.getIdUsuario() != -1)?filtrosDto.getIdUsuario():null;
		Long idTipoProyecto = (filtrosDto.getIdTipoProyecto() != null && filtrosDto.getIdTipoProyecto() != -1)?filtrosDto.getIdTipoProyecto():null;
		Long idTipoEstado = (filtrosDto.getIdTipoEstado()!=null && filtrosDto.getIdTipoEstado() != -1)?filtrosDto.getIdTipoEstado():null;
		Date fechaInicio = (filtrosDto.getFechaInicial()!=null)?filtrosDto.getFechaInicial():null;
		Date fechaFin = (filtrosDto.getFechaFinal()!=null)?filtrosDto.getFechaFinal():null;
		
		Session hibernateSession = (Session) em.getDelegate();
		Criteria criteria = hibernateSession.createCriteria(Proyecto.class);
		
		if(nombre!=null) {
			criteria.add(Restrictions.ilike("nombre", nombre, MatchMode.ANYWHERE));
		}
		if(idUsuario!=null) {
			criteria.createAlias("participantes", "participante");
			criteria.add(Restrictions.eq("participante.usuario", new Usuario(idUsuario)));
		}
		if(idTipoProyecto!=null){
			criteria.add(Restrictions.eq("tipoProyecto", new TipoProyecto(idTipoProyecto)));
		}
		if(idTipoEstado!= null) {
			criteria.add(Restrictions.eq("tipoEstadoProyecto", new TipoEstadoProyecto(idTipoEstado)));
		}
		if(fechaInicio!=null && fechaFin!=null) {
			criteria.add(Restrictions.between("fechaInicio", fechaInicio, fechaFin));
		}
		
		criteria.addOrder(Order.asc("nombre"));
		return criteria;
	}
}
