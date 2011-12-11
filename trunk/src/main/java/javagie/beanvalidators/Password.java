package javagie.beanvalidators;


import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
	 String message() default "Por favor ingrese contraseña en formato correcto";
	 
	  Class<?>[] groups() default {};
	  
	  Class<? extends Payload>[] payload() default {};
	
}