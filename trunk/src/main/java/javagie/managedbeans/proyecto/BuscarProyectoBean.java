package javagie.managedbeans.proyecto;

import java.util.List;
import java.util.Map;

import javagie.dto.FiltrosBuscarProyectoDto;
import javagie.entities.Proyecto;
import javagie.entities.TipoEstadoProyecto;
import javagie.entities.TipoProyecto;
import javagie.entities.Usuario;
import javagie.exceptions.LogicaNegocioException;
import javagie.services.ProyectoService;
import javagie.util.ConstantesUtil;
import javagie.util.FacesUtil;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class BuscarProyectoBean {

	private static Logger log = LoggerFactory.getLogger(BuscarProyectoBean.class);
	
	@Autowired
	private ProyectoService proyectoService;
	
	@Autowired
	private FacesUtil facesUtil;
	
	private FiltrosBuscarProyectoDto filtrosDto;
	private int tablaProyectosRows = 15;
	private LazyDataModel<Proyecto> proyectoLazyList;
	private List<TipoProyecto> tipoProyectoList;
	private List<Usuario> usuarioList;
	private List<TipoEstadoProyecto> estadoProyectoList;
	
	//*********************************
	//PUBLICOS
	//******************************
	
	
	public String irBuscarProyectos() {
		log.debug("iniciando buscar proyectos");
		
		tipoProyectoList = proyectoService.traerTodosTiposDeProyectos();
		estadoProyectoList = proyectoService.traerTodosTipoEstadoProyecto();
		usuarioList = proyectoService.traerUsuariosParaProyecto();
				
		cargarFiltrosPorDefecto();
		cargarProyectoLazyList();
		return "buscar-proyectos";
	}
	
	public void buscar() {
		log.debug("buscando proyectos");
		
		cargarProyectoLazyList();
	}
	
	public void eliminarProyecto(Proyecto proyecto) {
		try {
			proyectoService.eliminar(proyecto);
			facesUtil.addInfoMessage(ConstantesUtil.MSJ_INFO_CAMBIOS_REALIZADOS);
			cargarProyectoLazyList();
		}catch(LogicaNegocioException ex) {
			facesUtil.addErrorMessage(ex.getMessage());
		}catch(Exception ex) {
			ex.printStackTrace();
			facesUtil.addErrorMessage(ConstantesUtil.MSJ_ERROR_INTERNO);
		}
	}
	
	//*************************************
	// METODOS PRIVADOS
	//*************************************
	
	
	private void cargarFiltrosPorDefecto() {
		if(filtrosDto == null) {
			filtrosDto = new FiltrosBuscarProyectoDto();
		}
		
		filtrosDto.setFechaFinal(null);
		filtrosDto.setFechaInicial(null);
		filtrosDto.setNombre(null);
		filtrosDto.setTipoEstadoProyecto(null);
		filtrosDto.setTipoProyecto(null);
		filtrosDto.setUsuario(null);
	}
	
	private void cargarProyectoLazyList() {
		proyectoLazyList = new LazyDataModel<Proyecto>() {

			@Override
			public List<Proyecto> load(int first, int pageSize, String sortField,SortOrder sortOrder, Map<String, String> filters) {
				int fin = first + pageSize;
				if(fin > getRowCount()) fin = getRowCount();
				
				long t1 = System.currentTimeMillis();
				List<Proyecto> proyectoList =proyectoService.buscarProyectos(filtrosDto, first, fin);
				long t2 = System.currentTimeMillis();
				log.debug("tiempo buscar(carga real): {} ms", t2-t1);
				
				return proyectoList;
			}
		};
		long t1 = System.currentTimeMillis();
		proyectoLazyList.setRowCount(proyectoService.buscarProyectosRowCount(filtrosDto));
		long t2 = System.currentTimeMillis();
		log.debug("tiempo buscar row count: {} ms", t2-t1);
		
		proyectoLazyList.setPageSize(tablaProyectosRows);
	}
	
	//**************************
	// GETTERS AND SETTERS
	//**************************
	
	public FiltrosBuscarProyectoDto getFiltrosDto() {
		return filtrosDto;
	}
	public void setFiltrosDto(FiltrosBuscarProyectoDto filtrosDto) {
		this.filtrosDto = filtrosDto;
	}
	public LazyDataModel<Proyecto> getProyectoLazyList() {
		return proyectoLazyList;
	}
	public void setProyectoLazyList(LazyDataModel<Proyecto> proyectoLazyList) {
		this.proyectoLazyList = proyectoLazyList;
	}

	public List<TipoProyecto> getTipoProyectoList() {
		return tipoProyectoList;
	}

	public void setTipoProyectoList(List<TipoProyecto> tipoProyectoList) {
		this.tipoProyectoList = tipoProyectoList;
	}

	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}
	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}

	public List<TipoEstadoProyecto> getEstadoProyectoList() {
		return estadoProyectoList;
	}

	public void setEstadoProyectoList(List<TipoEstadoProyecto> estadoProyectoList) {
		this.estadoProyectoList = estadoProyectoList;
	}
	public int getTablaProyectosRows() {
		return tablaProyectosRows;
	}
	public void setTablaProyectosRows(int tablaProyectosRows) {
		this.tablaProyectosRows = tablaProyectosRows;
	}

}
