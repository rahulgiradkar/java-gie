package javagie.managedbeans.agendas;

import java.util.List;
import javagie.entities.*;
import javagie.services.ProyectoService;
import javagie.services.RecursoService;
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
    
    @Override
    public void cargarModeloAgenda() {
        agendaModel = new DefaultScheduleModel();
        reservas = reservaService.traerReservas(recursoSelected);
        for(Reserva reserva: reservas) {
            if(!(reserva instanceof ReservaRecurso)) {
                continue;
            }
            ReservaRecurso reservaRecursoAux = (ReservaRecurso) reserva;
            
            String titulo = reservaRecursoAux.getProyecto().getNombre() 
                    + "-" + reservaRecursoAux.getReservadoPor().getNombres() 
                    + " " + reservaRecursoAux.getReservadoPor().getApellidos(); 
            if(reservaRecursoAux.getObservacion() != null || !reservaRecursoAux.getObservacion().isEmpty()) {
                titulo += " (" + reservaRecursoAux.getObservacion() + ")";
            }
            
            ScheduleEvent event = new DefaultScheduleEvent(titulo, 
                    reservaRecursoAux.getFechaInicio(),
                    reservaRecursoAux.getFechaFin(), 
                    reservaRecursoAux);
            
            agendaModel.addEvent(event);
        }
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
