package javagie.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ResumenBitDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer horasTotal;
	private Map<String, Integer> cargoTotalHorasMap;
	private List<DiaBitDto> diaBitList;
	
	public ResumenBitDto(Integer horasTotal, Map<String, Integer> categoriaTotalHorasMap, List<DiaBitDto> diaBitList) {
		this.horasTotal = horasTotal;
		this.cargoTotalHorasMap = categoriaTotalHorasMap;
		this.diaBitList = diaBitList;
	}
	
	public ResumenBitDto() {
		horasTotal = 0;
	}
	
	public Integer getHorasTotal() {
		return horasTotal;
	}
	public void setHorasTotal(Integer horasTotal) {
		this.horasTotal = horasTotal;
	}
	public List<DiaBitDto> getDiaBitList() {
		return diaBitList;
	}
	public void setDiaBitList(List<DiaBitDto> diaBitList) {
		this.diaBitList = diaBitList;
	}
	public Map<String, Integer> getCargoTotalHorasMap() {
		return cargoTotalHorasMap;
	}
	public void setCargoTotalHorasMap(Map<String, Integer> cargoTotalHorasMap) {
		this.cargoTotalHorasMap = cargoTotalHorasMap;
	}
	
}
