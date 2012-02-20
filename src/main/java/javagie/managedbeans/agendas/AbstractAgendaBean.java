package javagie.managedbeans.agendas;

import java.util.ArrayList;
import java.util.List;
import javagie.entities.Reserva;
import javagie.security.Identidad;
import javagie.services.ReservaService;
import javagie.util.FacesUtil;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author eduardo
 */
public abstract class AbstractAgendaBean {
    
    @Autowired
    protected Identidad identidad;
    
    @Autowired
    protected FacesUtil facesUtil;
    
    @Autowired
    protected ReservaService reservaService;
    
    protected ScheduleModel agendaModel;
    protected List<Reserva> reservas = new ArrayList<Reserva>();
    
    
    public abstract void cargarModeloAgenda();

    public ScheduleModel getAgendaModel() {
        return agendaModel;
    }

    public void setAgendaModel(ScheduleModel agendaModel) {
        this.agendaModel = agendaModel;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservaService(ReservaService reservaService) {
        this.reservaService = reservaService;
    }
}
