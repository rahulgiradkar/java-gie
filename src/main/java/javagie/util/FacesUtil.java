package javagie.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component("facesUtil")
public class FacesUtil {

	
	public void setRequestAttribute(String key, Object object) {
		this.obtenerHttpServletRequest().setAttribute(key, object);
	}
	
	public Object getRequestAttribute(String key) {
		return this.obtenerHttpServletRequest().getAttribute(key);
	}
	
	public String traerParameter(String paramName) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(paramName);
	}
	
	public void addInfoMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,message, null));
	}
	
	
	public void addErrorMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,message, null));
	}
	
	public void addErrorMessage(String componentId, String messsage) {
		FacesContext.getCurrentInstance().addMessage(
				componentId,
				new FacesMessage(FacesMessage.SEVERITY_ERROR,messsage, null));
	}
	
	public HttpServletRequest obtenerHttpServletRequest() {
		return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
}
