package javagie.arquitectura;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.jsf.FacesContextUtils;

import javagie.dao.GenericDao;


@FacesConverter("genericConverter")
public class GenericConverter implements Converter {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		BaseEntity entity;
        if (value == null || value.trim().length() < 1) {
            entity = null;
        } else {
            Long id = new Long(value);
            entity = (BaseEntity) getGenericDao(context).traerPorId(getClazz(context, component), id);
            if (entity == null) {
                log.debug("There is no entity with id:  {}" , id);
                //throw new IllegalArgumentException("There is no entity with id:  " + id);
            }
        }
        return entity;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && !(value instanceof BaseEntity)) {
            throw new IllegalArgumentException(
                    "This converter only handles instances of BaseEntity");
        }
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return (String) value;
        }
        BaseEntity entity = (BaseEntity) value;
        return entity.getPrimaryKey() == null ? "" : entity.getPrimaryKey().toString();
	}
	
	protected GenericDao getGenericDao(FacesContext context) {
		return FacesContextUtils.getWebApplicationContext(context).getBean(GenericDao.class); 
	}
	
	 // Gets the class corresponding to the context in jsf page
    protected Class<?> getClazz(FacesContext facesContext, UIComponent component) {
        return component.getValueExpression("value").getType(facesContext.getELContext());
    }

}
