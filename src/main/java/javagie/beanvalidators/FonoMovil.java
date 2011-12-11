package javagie.beanvalidators;


import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = FonoMovilValidator.class)
@Documented
public @interface FonoMovil {
	 String message() default "Por favor ingrese fono movil en formato correcto.";
	 
	  Class<?>[] groups() default {};
	  
	  Class<? extends Payload>[] payload() default {};
	
}
