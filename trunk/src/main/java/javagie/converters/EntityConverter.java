package javagie.converters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author eduardo
 */
@Component
public class EntityConverter implements Converter {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().length() == 0) {
            return null;
        }

        Class classEntity = getClazz(context, component);

        if (classEntity.getAnnotation(javax.persistence.Entity.class) == null) {
            throw new IllegalArgumentException("No es javax.persistence.Entity!");
        }

        for (Field field : classEntity.getDeclaredFields()) {
            Annotation annotationId = null;
            annotationId = field.getAnnotation(javax.persistence.Id.class);
            if (annotationId == null) {
                annotationId = field.getAnnotation(javax.persistence.EmbeddedId.class);
            }
            if (annotationId == null) {
                continue;
            }

            //tiene el field que esta anotado como id. 
            //obtener el nombre del field
            String nombreGetterField = "get" + StringUtils.capitalize(field.getName());
            Method getterMethod = null;
            try {
                getterMethod = classEntity.getMethod(nombreGetterField, null);
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }

            //del getter obtener la Class del Id 
            Class<?> classId = getterMethod.getReturnType();

            //del getter obtener el valor del Id
            Object objectId = obtenerValorDeLaId(classId, value);

            //Obtener el Objeto real con classEntity , Class del Id con Valor del Id
            return em.find(classEntity, objectId);
        }

        throw new IllegalArgumentException("No se encontre ID en la Entidad!");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return (String) value;
        }
        if (value instanceof Integer) {
            return value+"";
        }
        if (value instanceof Long) {
            return value +"";
        }
        if (value instanceof Short) {
            return value+"";
        }
        
        Class classEntity = value.getClass();
        
        if (classEntity.getAnnotation(javax.persistence.Entity.class) == null) {
            throw new IllegalArgumentException("No es javax.persistence.Entity!");
        }
        
        for (Field field : classEntity.getDeclaredFields()) {
            Annotation annotationId = null;
            annotationId = field.getAnnotation(javax.persistence.Id.class);
            if (annotationId == null) {
                annotationId = field.getAnnotation(javax.persistence.EmbeddedId.class);
            }
            if (annotationId == null) {
                continue;
            }

            //tiene el field que esta anotado como id. 
            //obtener el nombre del field
            String nombreGetterField = "get" + StringUtils.capitalize(field.getName());
            Method getterMethod = null;
            try {
                getterMethod = classEntity.getMethod(nombreGetterField, null);
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }

            //del getter obtener la Class del Id 
            Class<?> classId = getterMethod.getReturnType();

            //del getter obtener el valor del Id
            Object objectId;
            try {
                objectId = getterMethod.invoke(value, null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            
            if(objectId == null) {
                return "";
            }

            //Obtener el Objeto real con classEntity , Class del Id con Valor del Id
            return objectId.toString();
        }

        throw new IllegalArgumentException("No se encontre ID en la Entidad!");
    }

    // Gets the class corresponding to the context in jsf page
    @SuppressWarnings("unchecked")
    private Class getClazz(FacesContext facesContext, UIComponent component) {
        return component.getValueExpression("value").getType(facesContext.getELContext());
    }

    /*
     * Este metodo se puede sobreescribir para crear un converter de una entidad compuesta o no numerica
     */
    protected <T> T obtenerValorDeLaId(Class<T> claseId, String valorCadena) {
        if (claseId.isInstance(new Integer(0))) {
            return claseId.cast(Integer.valueOf(valorCadena));
        } else if (claseId.isInstance(new Long(0))) {
            return claseId.cast(Long.valueOf(valorCadena));
        } else if (claseId.isInstance(new Short((short) 0))) {
            return claseId.cast(Short.valueOf(valorCadena));
        } else if (claseId.isInstance("")) {
            return claseId.cast(String.valueOf(valorCadena));
        }
        throw new RuntimeException("Tipo de dato no manejado por EntityConverter");
    }
}
