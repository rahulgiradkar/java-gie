package javagie.managedbeans.bitacora;

import java.util.Date;
import java.util.List;

import javagie.dto.ResumenBitDto;
import javagie.entities.Bitacora;
import javagie.entities.Proyecto;
import javagie.managedbeans.sesion.Identidad;
import javagie.services.FachadaService;
import javagie.util.ConstantesUtil;
import javagie.util.FacesUtil;

import org.primefaces.event.DateSelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("request")
public class EditarBitacoraBean {

	private  static Logger log = LoggerFactory.getLogger(EditarBitacoraBean.class);
	
	@Autowired
	private FachadaService service;
	
	@Autowired
	private Identidad identity;
	
	@Autowired
	private FacesUtil facesUtil;
	
	private List<Proyecto> proyectoList; //lista de proyectos disponible por usuario
	private Proyecto proyecto; //proyecto seleccionado
	private String emailUser; //identificacion unica del usuario
	private ResumenBitDto resumenBitacora; //trae resumen de horas utilizadas e historial bitacora
	private List<Bitacora> bitacoraList; //bitacoras del dia especifico
	private Date fechaActual; //Fecha actual de la vista
	
	public String irEditarBitacora() {
		emailUser = identity.getEmail();
		
		//la primera ves que se carga la vista
		proyectoList = service.traerProyectos(emailUser);
		if(proyectoList != null && proyectoList.size() > 0) {
			cambiarProyectoSeleccionado(proyectoList.get(0), new Date());
		}
		
		return "editar-bitacora";
	}
	
	//************************
	// PUBLICOS
	//************************
	
	public void guardar() {
		try {
			service.guardarBitacoras(bitacoraList, fechaActual);
			facesUtil.addInfoMessage(ConstantesUtil.MSJ_INFO_CAMBIOS_REALIZADOS);
		}catch(Exception ex) {
			facesUtil.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
		}
	}
	
	public void cambiarProyecto() {
		cambiarProyectoSeleccionado(proyecto, fechaActual);
	}
	
	public void cambiarFechaProyecto(Date fecha) {
		cambiarProyectoSeleccionado(proyecto, fecha);
	}
	
	public void calendarioListener(DateSelectEvent event) {  
        cambiarProyectoSeleccionado(proyecto, event.getDate());
    } 
	
	//************************
	// PRIVADOS
	//************************
	
	private void cambiarProyectoSeleccionado(Proyecto proyecto, Date fecha) {
		this.fechaActual = fecha;
		this.proyecto = proyecto;
		resumenBitacora = service.traerResumenBitacoraDto(this.proyecto, emailUser);
		bitacoraList = service.traerBitacorasConPartipante(this.proyecto, fechaActual, emailUser);
	}
	
	//************************
	// GETTERS AND SETTERS
	//************************
	
	public List<Proyecto> getProyectoList() {
		return proyectoList;
	}
	public void setProyectoList(List<Proyecto> proyectoList) {
		this.proyectoList = proyectoList;
	}
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	public Proyecto getProyecto() {
		return proyecto;
	}
	public ResumenBitDto getResumenBitacora() {
		return resumenBitacora;
	}
	public void setResumenBitacora(ResumenBitDto resumenBitacora) {
		this.resumenBitacora = resumenBitacora;
	}
	public List<Bitacora> getBitacoraList() {
		return bitacoraList;
	}
	public void setBitacoraList(List<Bitacora> bitacoraList) {
		this.bitacoraList = bitacoraList;
	}
	public Date getFechaActual() {
		return fechaActual;
	}
	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}
	public String getEmailUser() {
		return emailUser;
	}
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
	
}
