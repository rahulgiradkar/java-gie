package javagie.managedbeans.proyecto;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javagie.entities.Participante;
import javagie.entities.Proyecto;
import javagie.services.ProyectoService;
import javagie.util.FacesUtil;

public abstract class AbstractVerProyectoBean {

	private static Logger log = LoggerFactory.getLogger(AbstractVerProyectoBean.class);
	@Autowired
	protected ProyectoService proyectoService;
	
	@Autowired
	protected FacesUtil facesUtil;
	
	protected Proyecto proyecto;
	protected List<Participante> participanteList;
	
	protected void irVerProyecto(Proyecto proyecto) {
		log.debug("irVerProyecto, el proyecto es {}", proyecto);
		if(proyecto != null) {
			this.proyecto = proyectoService.traerProyecto(proyecto.getIdProyecto());
			this.participanteList = proyectoService.traerParticipantes(proyecto);
		}
		else {
			this.proyecto = new Proyecto();
			this.participanteList = new ArrayList<Participante>();
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
