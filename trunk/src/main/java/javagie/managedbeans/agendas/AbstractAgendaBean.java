package javagie.managedbeans.agendas;

import javagie.managedbeans.sesion.Identidad;
import javagie.services.ReservaService;
import javagie.util.FacesUtil;
import org.primefaces.model.ScheduleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author eduardo
 */
public abstract class AbstractAgendaBean {
    
    protected Logger log = LoggerFactory.getLogger(AbstractAgendaBean.class);
    
    @Autowired
    protected Identidad identidad;
    
    @Autowired
    protected FacesUtil facesUtil;
    
    @Autowired
    protected ReservaService reservaService;
    
    protected ScheduleModel agendaModel;
    
    public abstract void cargarModeloAgenda();

    public ScheduleModel getAgendaModel() {
        return agendaModel;
    }

    public void setAgendaModel(ScheduleModel agendaModel) {
        this.agendaModel = agendaModel;
    }

    public void setReservaService(ReservaService reservaService) {
        this.reservaService = reservaService;
    }
}
