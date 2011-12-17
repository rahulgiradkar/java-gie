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
		Usuario usuario = (filtrosDto.getUsuario() != null)?filtrosDto.getUsuario():null;
		TipoProyecto tipoProyecto = (filtrosDto.getTipoProyecto() != null)?filtrosDto.getTipoProyecto():null;
		TipoEstadoProyecto tipoEstadoProyecto = (filtrosDto.getTipoEstadoProyecto()!=null)?filtrosDto.getTipoEstadoProyecto():null;
		Date fechaInicio = (filtrosDto.getFechaInicial()!=null)?filtrosDto.getFechaInicial():null;
		Date fechaFin = (filtrosDto.getFechaFinal()!=null)?filtrosDto.getFechaFinal():null;
		
		Session hibernateSession = (Session) em.getDelegate();
		Criteria criteria = hibernateSession.createCriteria(Proyecto.class);
		
		if(nombre!=null) {
			criteria.add(Restrictions.ilike("nombre", nombre, MatchMode.ANYWHERE));
		}
		if(usuario!=null) {
			criteria.add(Restrictions.eq("participante.usuario", usuario));
		}
		if(tipoProyecto!=null){
			criteria.add(Restrictions.eq("tipoProyecto", tipoProyecto));
		}
		if(tipoEstadoProyecto!= null) {
			criteria.add(Restrictions.eq("tipoEstadoProyecto", tipoEstadoProyecto));
		}
		if(fechaInicio!=null && fechaFin!=null) {
			criteria.add(Restrictions.between("fechaInicio", fechaInicio, fechaFin));
		}
		
		criteria.addOrder(Order.asc("nombre"));
		return criteria;
	}
}
