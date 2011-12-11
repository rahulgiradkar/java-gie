package javagie.arquitectura;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoTrolinValidator  implements ConstraintValidator<NoTrolin, String> {

	private static final String STRING_TROLIN = "trolin";
	
	@Override
	public void initialize(NoTrolin constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(value== null) {
			return true;
		}
		
		if(value.toLowerCase().endsWith(STRING_TROLIN)){
			return false;
		}
		else if(value.toLowerCase().split(STRING_TROLIN).length > 1) {
			return false;
		}
		
		return true;
	}
}
