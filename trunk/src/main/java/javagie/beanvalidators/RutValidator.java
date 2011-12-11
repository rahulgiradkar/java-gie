package javagie.beanvalidators;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RutValidator  implements ConstraintValidator<Rut, String> {

	@Override
	public void initialize(Rut constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return validarRut(value);
	}
	
private boolean validarRut(String rut) {
		try {
			if(rut == null) {
				return false;
			}
			
			Integer usrRut = Integer.valueOf(rut.split("-")[0]);
			String usrDv = rut.charAt(rut.length()-1)+"";
			
			if (usrRut == null || usrRut < 1) {
				return false;
			}
			
			usrDv = usrDv.toUpperCase();

			char dv = calculaDigitoVerificador(usrRut);
			
			return usrDv.equals(new String("" + dv));
		}catch(Exception ex) {
			return false;
		}
		
	}
	
	private  char calculaDigitoVerificador(Integer T) {
		int M = 0, S = 1;
		for (; T != 0; T /= 10) {
			S = (S + T % 10 * (9 - M++ % 6)) % 11;
		}
		return (char) (S != 0 ? S + 47 : 75);
	}

}