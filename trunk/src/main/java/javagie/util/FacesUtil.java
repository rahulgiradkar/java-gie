package javagie.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
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
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    public void addInfoMessage(String componentId, String messsage) {
        FacesContext.getCurrentInstance().addMessage(
                componentId,
                new FacesMessage(FacesMessage.SEVERITY_INFO, messsage, null));
    }

    public void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    public void addErrorMessage(String componentId, String messsage) {
        FacesContext.getCurrentInstance().addMessage(
                componentId,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, messsage, null));
    }

    public HttpServletRequest obtenerHttpServletRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public HttpServletResponse obtenerHttpServletResponse() {
        return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    public void setFlashAttribute(String key, Object object) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().putNow(key, object);
    }

    public Object getFlashAttribute(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getFlash().get(key);
    }

    public void setSessionAttribute(String key, Object object) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, object);
    }

    public Object getSessionAttribute(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public void removeSessionAttribute(String key) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
    }

    public String getParameter(String key) {
        return obtenerHttpServletRequest().getParameter(key);
    }

    public HttpSession obtenerHttpSession() {
        return obtenerHttpServletRequest().getSession();
    }
}
