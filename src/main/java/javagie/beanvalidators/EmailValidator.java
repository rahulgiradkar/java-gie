package javagie.beanvalidators;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator  implements ConstraintValidator<Email, String> {

	@Override
	public void initialize(Email constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return emailValido(value);
	}
	
	private boolean emailValido(String email) {
		String regex = "(^[0-9a-zA-Z]+(?:[._][0-9a-zA-Z]+)*)@([0-9a-zA-Z]+(?:[._-][0-9a-zA-Z]+)*\\.[a-zA-Z]{2,3})$";
		if (email == null || email.length() == 0) {
			return true;
		}
                if(email.length() > 50) {
                    return false;
                }
		return email.matches(regex);
	}
}