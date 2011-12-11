package javagie.arquitectura;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServiceLocator implements ServletContextListener {

	private static WebApplicationContext springContext;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		this.springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public static Object getBean(String name) {
		return springContext.getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return springContext.getBean(clazz);
	}
}
