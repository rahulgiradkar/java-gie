package javagie.converters;

import javagie.entities.Recurso;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author eduardo
 */
@FacesConverter("recursoConverter")
public class RecursoConverter extends AbstractJSF2Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Recurso entity;
        if (value == null || value.trim().length() < 1) {
            entity = null;
        } else {
            Long id = new Long(value);
            entity = (Recurso) getGenericDao(context).traerPorId(Recurso.class, id);
            if (entity == null) {
                log.debug("There is no entity with id:  {}", id);
            }
        }
        return entity;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !(value instanceof Recurso)) {
            throw new IllegalArgumentException(
                    "This converter only handles instances of Recurso");
        }
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return (String) value;
        }
        Recurso entity = (Recurso) value;
        return entity.getIdRecurso() == null ? "" : entity.getIdRecurso().toString();
    }
}
