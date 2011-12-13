package javagie.managedbeans.proyecto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import javagie.entities.Participante;
import javagie.entities.Proyecto;
import javagie.services.ProyectoService;
import javagie.util.FacesUtil;

public abstract class AbstractVerProyectoBean {

	@Autowired
	protected ProyectoService proyectoService;
	
	@Autowired
	protected FacesUtil facesUtil;
	
	protected Proyecto proyecto;
	protected List<Participante> participanteList;
	
	protected void irVerProyecto(Long idProyecto) {
		if(idProyecto != null) {
			proyecto = proyectoService.traerProyecto(idProyecto);
			participanteList = proyectoService.traerParticipantes(proyecto);
		}
		else {
			proyecto = new Proyecto();
			participanteList = new ArrayList<Participante>();
		}
	}
	
	public List<Participante> getParticipanteList() {
		return participanteList;
	}
	public void setParticipanteList(List<Participante> participanteList) {
		this.participanteList = participanteList;
	}
	public Proyecto getProyecto() {
		return proyecto;
	}
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
}
