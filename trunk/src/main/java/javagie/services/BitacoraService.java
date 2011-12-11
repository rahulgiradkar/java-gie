package javagie.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javagie.dto.DiaBitDto;
import javagie.dto.ResumenBitDto;
import javagie.entities.Bitacora;
import javagie.entities.BitacoraPK;
import javagie.entities.Participante;
import javagie.entities.Proyecto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@SuppressWarnings("unchecked")
public class BitacoraService {

	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Guarda las bitacoras de un usuario para un dia determinado.
	 * Objetos bitacoras deben tener asociado participante y fechas.
	 * @param bitacoras
	 */
	@Transactional(readOnly=false)
	public void guardarBitacoras(List<Bitacora> bitacoras) {
		if(bitacoras == null || bitacoras.size() < 1) {
			throw new IllegalArgumentException();
		}
		
		for(Bitacora bitacora : bitacoras) {
			if(bitacora.getId().getIdParticipante() == null 
					|| bitacora.getId().getDia() == null 
					|| bitacora.getId().getMes() == null
					|| bitacora.getId().getAno() == null) {
				throw new IllegalArgumentException();
			}
			
			//guarda o crea
			em.merge(bitacora);
		}
	}
	
	@Transactional(readOnly=false)
	public void guardarBitacoras(List<Bitacora>bitacoras, Date fechaBitacora) {
		if(fechaBitacora == null) {
			throw new IllegalArgumentException("fecha nula");
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaBitacora);
		
		for (Bitacora bitacora : bitacoras) {
			BitacoraPK id = new BitacoraPK();
			id.setAno(cal.get(Calendar.YEAR));
			id.setMes(cal.get(Calendar.MONTH)+1);
			id.setDia(cal.get(Calendar.DAY_OF_MONTH));
			id.setIdParticipante(bitacora.getParticipante().getIdParticipante());
			bitacora.setId(id);
		}
		
		guardarBitacoras(bitacoras);
	}

	@Transactional(readOnly=true)
	public List<Proyecto> traerProyectos(String emailUsuario) {
		return em.createNamedQuery("Proyecto.traerProyectosPorEmailUsuario")
				.setParameter("email", emailUsuario)
				.getResultList();
	}
	
	@Transactional(readOnly=true)
	public ResumenBitDto traerResumenBitacoraDto(Proyecto proyecto, String emailUsuario) {
		
		DateTime fechaAux = null;
		ResumenBitDto resumenBitDto = new ResumenBitDto();
		
		List<DiaBitDto> diaBitDtoList = new ArrayList<DiaBitDto>();
		Map<String,Integer> cargoTotalHoraMap = new HashMap<String,Integer>();
		
		resumenBitDto.setCargoTotalHorasMap(cargoTotalHoraMap);
		resumenBitDto.setDiaBitList(diaBitDtoList);
		
		// taer todas las bitacoras para la tupla proyecto - usuario
		List<Bitacora> bitacoraList = em.createNamedQuery("Bitacora.traerPorProyectoYEmailUsuario")
				.setParameter("proyecto", proyecto)
				.setParameter("email", emailUsuario)
				.getResultList();
		
		for (Bitacora bitacora : bitacoraList) {
			int year = bitacora.getId().getAno();
			int mes = bitacora.getId().getMes();
			int dia = bitacora.getId().getDia();
			fechaAux = new DateTime(year,mes,dia,1,1,1,1);
			
			String cargoAux = bitacora.getParticipante().getTipoCargo().getNombre();
			Integer horasAux = bitacora.getHorasHombre();

			//incrementar totales de horas
			resumenBitDto.setHorasTotal(resumenBitDto.getHorasTotal() + horasAux);
			if(cargoTotalHoraMap.containsKey(cargoAux)) {
				cargoTotalHoraMap.put(cargoAux, cargoTotalHoraMap.get(cargoAux) + horasAux);
			}
			else {
				cargoTotalHoraMap.put(cargoAux, horasAux);
			}
			
			//llenar datos de horas del dia
			Map<String,Integer> cargoHoraMapAux = new HashMap<String,Integer>();
			cargoHoraMapAux.put(cargoAux, horasAux);
			DiaBitDto diaBitDto = new DiaBitDto(fechaAux.toDate(), bitacora.getHorasHombre(), cargoHoraMapAux);
			
			if(diaBitDtoList.contains(diaBitDto)) {
				int index = diaBitDtoList.indexOf(diaBitDto);
				DiaBitDto diaExistente = diaBitDtoList.get(index);
				diaExistente.getCargoHoraMap().put(cargoAux, horasAux); //no deberian haber cargos repetidos para el mismo dia
				diaExistente.setTotalHoraDia(diaExistente.getTotalHoraDia() + horasAux);
			}
			else {
				diaBitDtoList.add(diaBitDto);
			}
			
		}
		
		return resumenBitDto;
	}

	/**
	 * trae las bitacoras para una dia y un usuario determinado. Si para tal usuario y dia no existe bitacora
	 * se devuelve una nueva instancia de bitacoras, segun la cantidad de participante que posea el usuario.
	 * 
	 * 
	 * @param fecha dia de la bitacora
	 * @param emailUsuario identificador unico del usuario
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Bitacora> traerBitacorasConPartipante(Proyecto proyecto, Date fecha, String emailUsuario) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		
		List<Bitacora> bitacoras = em.createNamedQuery("Bitacora.traerPorFechaYProyectoYEmailUsuario")
				.setParameter("dia", cal.get(Calendar.DAY_OF_MONTH))
				.setParameter("mes", cal.get(Calendar.MONTH)+1)
				.setParameter("ano", cal.get(Calendar.YEAR))
				.setParameter("proyecto", proyecto)
				.setParameter("email", emailUsuario)
				.getResultList();

		//carga datos en objeto participante
		for (Bitacora b : bitacoras) {
			b.getParticipante().getTipoCargo().getIdTipoCargo();
		}
		
		//si no vienen bitacoras. se deben crear nuevas instancias por cada cargo del participante
		if(bitacoras.size() < 1) {
			List<Participante> participantes = em.createNamedQuery("Participante.traerPorProyectoYEmailUsuario")
					.setParameter("proyecto", proyecto)
					.setParameter("email", emailUsuario)
					.getResultList();
			
			for (Participante p : participantes) {
				DateTime jodaDate = new DateTime(fecha);
				Bitacora b = new Bitacora();
				BitacoraPK bPK = new BitacoraPK();
				
				bPK.setAno(jodaDate.getYear());
				bPK.setMes(jodaDate.getMonthOfYear());
				bPK.setDia(jodaDate.getDayOfMonth());
				
				b.setId(bPK);
				b.setParticipante(p);
				
				//carga datos en objeto participante
				p.getTipoCargo().getNombre();
				
				bitacoras.add(b);
			}
			
		}
		
		return bitacoras;
	}
	
	
}
