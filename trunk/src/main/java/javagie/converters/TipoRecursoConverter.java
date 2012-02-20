package javagie.converters;

import javagie.entities.TipoRecurso;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author eduardo
 */
@FacesConverter("tipoRecursoConverter")
public class TipoRecursoConverter extends AbstractJSF2Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        TipoRecurso entity;
        if (value == null || value.trim().length() < 1) {
            entity = null;
        } else {
            Integer id = new Integer(value);
            entity = (TipoRecurso) getGenericDao(context).traerPorId(TipoRecurso.class, id);
            if (entity == null) {
                log.debug("There is no entity with id:  {}", id);
            }
        }
        return entity;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !(value instanceof TipoRecurso)) {
            throw new IllegalArgumentException(
                    "This converter only handles instances of TipoRecurso");
        }
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return (String) value;
        }
        TipoRecurso entity = (TipoRecurso) value;
        return entity.getIdTipoRecurso() == null ? "" : entity.getIdTipoRecurso().toString();
    }
}
