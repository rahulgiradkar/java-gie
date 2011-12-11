package javagie.beanvalidators;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FonoMovilValidator  implements ConstraintValidator<FonoMovil, String> {

	@Override
	public void initialize(FonoMovil constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return fonoMovilValido(value);
	}
	
	private boolean fonoMovilValido(String email) {
		String regex = "(^09[0-9]{7})|(^09[0-9]{8})";
		if (email == null || email.length() == 0) {
			return true;
		}
		return email.matches(regex);
	}
}