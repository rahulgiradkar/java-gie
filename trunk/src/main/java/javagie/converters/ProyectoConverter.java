package javagie.converters;

import javagie.entities.Proyecto;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author eduardo
 */
@FacesConverter("proyectoConverter")
public class ProyectoConverter extends AbstractJSF2Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Proyecto entity;
        if (value == null || value.trim().length() < 1) {
            entity = null;
        } else {
            Long id = new Long(value);
            entity = (Proyecto) getGenericDao(context).traerPorId(Proyecto.class, id);
            if (entity == null) {
                log.debug("There is no entity with id:  {}", id);
            }
        }
        return entity;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !(value instanceof Proyecto)) {
            throw new IllegalArgumentException(
                    "This converter only handles instances of Proyecto");
        }
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return (String) value;
        }
        Proyecto entity = (Proyecto) value;
        return entity.getIdProyecto() == null ? "" : entity.getIdProyecto().toString();
    }
}
