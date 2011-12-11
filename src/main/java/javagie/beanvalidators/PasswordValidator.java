package javagie.beanvalidators;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator  implements ConstraintValidator<Password, String> {

	@Override
	public void initialize(Password constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return passwordValido(value);
	}
	
	private boolean passwordValido(String password) {
		String regex = "([0-9a-zA-ZñÑ]*)";
		
		if (password == null) {
			return false;
		}
		
		if(password.length() < 6 || password.length() > 30) {
			return false;
		}
		
		return password.matches(regex);
	}
}

