package javagie.converters;

import javagie.dao.GenericDao;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author eduardo
 */
public abstract class AbstractJSF2Converter implements Converter {

    protected Logger log  = LoggerFactory.getLogger(AbstractJSF2Converter.class);
    
    protected GenericDao getGenericDao(FacesContext context) {
        return FacesContextUtils.getWebApplicationContext(context).getBean(GenericDao.class);
    }
}
