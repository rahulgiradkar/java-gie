package javagie.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class DiaBitDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date fecha;
	private Integer totalHoraDia;
	private Map<String,Integer> cargoHoraMap;
	
	public DiaBitDto(Date fecha, Integer totalHoraDia, Map<String,Integer> cargoHoraMap) {
		this.fecha = fecha;
		this.totalHoraDia = totalHoraDia;
		this.cargoHoraMap = cargoHoraMap;
	}
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getTotalHoraDia() {
		return totalHoraDia;
	}
	public void setTotalHoraDia(Integer totalHoraDia) {
		this.totalHoraDia = totalHoraDia;
	}
	public Map<String, Integer> getCargoHoraMap() {
		return cargoHoraMap;
	}
	public void setCargoHoraMap(Map<String, Integer> cargoHoraMap) {
		this.cargoHoraMap = cargoHoraMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DiaBitDto))
			return false;
		DiaBitDto other = (DiaBitDto) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		return true;
	}

}
