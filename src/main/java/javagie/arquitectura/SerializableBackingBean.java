package javagie.arquitectura;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SerializableBackingBean implements Serializable {

    private static Logger log = LoggerFactory.getLogger(SerializableBackingBean.class);

    private void readObject(ObjectInputStream is) {
        try {
            is.defaultReadObject();
            reinjectar();
            postActivation();
        } catch (Exception ex) {
            log.error("error al deserializar", ex);
        }
    }

    @Deprecated
    protected void postActivation() {
    }

    private final void reinjectar() {
        try {
            Class clase = this.getClass();
            for (Method method : clase.getDeclaredMethods()) {
                if (method.getAnnotation(Autowired.class) != null) {

//                    Method metodoSetter = null;
//                    try {
//                        String metodoSetterNombre = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
//                        metodoSetter = clase.getMethod(metodoSetterNombre);
//                    }catch(NoSuchMethodException ex) {
//                        continue;
//                    }

                    log.debug("method param: {}", method.getParameterTypes()[0]);
                    log.debug("this: {}", this);
                    method.invoke(this, ServiceLocator.getBean(method.getParameterTypes()[0]));
                }
            }
        } catch (Exception ex) {
            log.error("error al reinjectar", ex);
        }

    }
}
