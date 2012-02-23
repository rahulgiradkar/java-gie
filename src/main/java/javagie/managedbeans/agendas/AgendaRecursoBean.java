package javagie.managedbeans.agendas;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javagie.entities.*;
import javagie.exceptions.LogicaNegocioException;
import javagie.services.ProyectoService;
import javagie.services.RecursoService;
import javagie.services.SecurityService;
import javagie.util.ConstantesUtil;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author eduardo
 */
@Component
@Scope("request")
public class AgendaRecursoBean extends AbstractAgendaBean {

    @Autowired
    private ProyectoService proyectoService;
    @Autowired
    private RecursoService recursoService;
    @Autowired
    private SecurityService securityService;
    
    private List<Proyecto> proyectosList;
    private List<TipoRecurso> tipoRecursoList;
    private TipoRecurso tipoRecursoSelected;
    private List<Recurso> recursoList;
    private Recurso recursoSelected;
    private ReservaRecurso reservaRecurso;

    public String irAgendaRecurso() {
        proyectosList = proyectoService.traerProyectosPorEmailUsuario(identidad.getEmail());
        tipoRecursoList = recursoService.traerTipoRecursosTodos();
        return "agenda-recurso";
    }

    public void cambiarTipoRecurso() {
        recursoSelected = null;
        agendaModel = null;
        recursoList = recursoService.traerPorTipoRecurso(tipoRecursoSelected);
    }

    public void cambiarRecurso() {
        agendaModel = null;
    }

    public void enSeleccionFecha(DateSelectEvent selectEvent) {
        Calendar dateSelectedCal = Calendar.getInstance();
        dateSelectedCal.setTime(selectEvent.getDate());
        dateSelectedCal.add(Calendar.HOUR, -1);
        Date dateSelected = dateSelectedCal.getTime();
        
        reservaRecurso = new ReservaRecurso();
        reservaRecurso.setRecurso(recursoSelected);
        reservaRecurso.setFechaInicio(new Date(dateSelected.getTime()));
        reservaRecurso.setFechaFin(new Date(dateSelected.getTime()));
        reservaRecurso.setReservadoPor(securityService.traerUsuarioPorEmail(identidad.getEmail()));
    }

    public void guardarReservaRecurso() {
        try {
            reservaService.guardarReservaRecurso(reservaRecurso);
            agendaModel.addEvent(instanciarAgendaEvent(reservaRecurso));
            
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("agendaWidget.update()");
            context.execute("reservaWidget.hide()"); //llama a una funcion javascript en la vista !!! wow
            context.addPartialUpdateTarget("agendaForm:agenda"); //llama a un update desde el managedBean!
            
        } catch (LogicaNegocioException ex) {
            log.error("error al guardar reserva", ex);
            facesUtil.addErrorMessage("agendaForm:guardarReservaBoton", ex.getMessage());
        } catch (Exception ex) {
            log.error("error interno", ex);
            facesUtil.addErrorMessage("agendaForm:guardarReservaBoton", ConstantesUtil.MSJ_ERROR_INTERNO);
        }

    }

    public void eliminarReservaRecurso() {
    }

    @Override
    public void cargarModeloAgenda() {
        agendaModel = new DefaultScheduleModel();
        List<Reserva> reservas = reservaService.traerReservas(recursoSelected);
        for (Reserva reserva : reservas) {
            if (!(reserva instanceof ReservaRecurso)) {
                continue;
            }
            agendaModel.addEvent(instanciarAgendaEvent((ReservaRecurso)reserva));
            
            log.debug("fecha inicio: {}", reserva.getFechaInicio() );
            log.debug("fecha fin: {}", reserva.getFechaFin());
        }
    }

    //PRIVADOS
    private ScheduleEvent instanciarAgendaEvent(ReservaRecurso reserva) {
        String titulo = reserva.getProyecto().getNombre()
                + "-" + reserva.getReservadoPor().getNombres()
                + " " + reserva.getReservadoPor().getApellidos();
        if (reserva.getObservacion() != null && !reserva.getObservacion().trim().isEmpty()) {
            titulo += " (" + reserva.getObservacion() + ")";
        }
        
        Calendar calInicio = Calendar.getInstance();
        calInicio.setTime(reserva.getFechaInicio());
        calInicio.add(Calendar.HOUR, 1);
        calInicio.add(Calendar.SECOND, 1);
        
        Calendar calFin = Calendar.getInstance();
        calFin.setTime(reserva.getFechaFin());
        calFin.add(Calendar.HOUR, 1);
        calFin.add(Calendar.SECOND, 1);
        
        return new DefaultScheduleEvent(titulo,
                calInicio.getTime(),
                calFin.getTime(),
                reserva);

    }

    //GETTERS AND SETTERS
    public List<Proyecto> getProyectosList() {
        return proyectosList;
    }

    public void setProyectosList(List<Proyecto> proyectosList) {
        this.proyectosList = proyectosList;
    }

    public Recurso getRecursoSelected() {
        return recursoSelected;
    }

    public void setRecursoSelected(Recurso recursoSelected) {
        this.recursoSelected = recursoSelected;
    }

    public ReservaRecurso getReservaRecurso() {
        return reservaRecurso;
    }

    public void setReservaRecurso(ReservaRecurso reservaRecurso) {
        this.reservaRecurso = reservaRecurso;
    }

    public List<TipoRecurso> getTipoRecursoList() {
        return tipoRecursoList;
    }

    public void setTipoRecursoList(List<TipoRecurso> tipoRecursoList) {
        this.tipoRecursoList = tipoRecursoList;
    }

    public List<Recurso> getRecursoList() {
        return recursoList;
    }

    public void setRecursoList(List<Recurso> recursoList) {
        this.recursoList = recursoList;
    }

    public TipoRecurso getTipoRecursoSelected() {
        return tipoRecursoSelected;
    }

    public void setTipoRecursoSelected(TipoRecurso tipoRecursoSelected) {
        this.tipoRecursoSelected = tipoRecursoSelected;
    }
}
