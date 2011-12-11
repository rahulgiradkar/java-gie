package javagie.arquitectura;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NoTrolinValidator.class)
@Documented
public @interface NoTrolin {
	 String message() default "No mas trolines";
	 
	  Class<?>[] groups() default {};
	  
	  Class<? extends Payload>[] payload() default {};
	
}
