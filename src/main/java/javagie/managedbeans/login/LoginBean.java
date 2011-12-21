package javagie.managedbeans.login;

import java.io.IOException;

import javagie.beanvalidators.Email;
import javagie.util.FacesUtil;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;


@Component("loginBean")
@Scope("request")
public class LoginBean {
	private static Logger log = LoggerFactory.getLogger(LoginBean.class);
	
	@Autowired
	private FacesUtil facesUtil;
	
	@Email
	private String usuario;
	
	private String password;
	
	public void verificarAuthentificationException(ComponentSystemEvent event) {
		
		Exception e = (Exception) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(
        		WebAttributes.AUTHENTICATION_EXCEPTION);
 
        if (e instanceof AuthenticationException)
        {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
            facesUtil.addErrorMessage(e.getMessage());
            
        }
	}
	
	public String doLogin(String urlSecurityCheck) throws IOException, ServletException {
		log.debug("username: {}, password: {}, url: {}", new Object[]{usuario, password, urlSecurityCheck});
		
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher(urlSecurityCheck);

		dispatcher.forward((ServletRequest) context.getRequest(),
				(ServletResponse) context.getResponse());

		FacesContext.getCurrentInstance().responseComplete();

		// It's OK to return null here because Faces is just going to exit.
		return null;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}