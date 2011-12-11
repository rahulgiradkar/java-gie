package javagie.converters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("rutConverter")
public class RutConverter implements Converter {
	
	private static final String RUT_REGEX = "([0-9]{8}-[0-9kK]{1})|" +
	"([0-9]{7}-[0-9kK]{1})";

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		
		if (value == null || value.isEmpty()) {
			return null;
		}
		
		String rutField;
		Integer rut;
		char digito;
		
		/* Create the correct mask */
		Pattern mask = null;
		mask = Pattern.compile(RUT_REGEX);

		/* Get the string value of the current field */
		rutField = (String) value;
		rutField = rutField.trim();
		rutField = rutField.toUpperCase();

		/* validar la forma del rut */
		Matcher matcher = mask.matcher(rutField);
		if (!matcher.matches()) {
			FacesMessage message = new FacesMessage();
			message.setDetail("Por favor ingrese RUT en formato correcto");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(message);
		}
		
		/*validar digito verificador*/
		//divide el rut que tiene la forma "XXXXXXXX-X"
        String rutAux[] = rutField.split("-");
        rut = Integer.parseInt(rutAux[0]);
        digito = rutAux[1].charAt(0);
        
        //validar que sean numeros positivos
        if(rut < 1 || digito < 0) {
            FacesMessage message = new FacesMessage();
            message.setDetail("RUT invalido");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(message);
        }
        
        //algoritmo raro sacado de algun lado
        int M = 0, S = 1, T = rut;
        for (; T != 0; T /= 10) {
            S = (S + T % 10 * (9 - M++ % 6)) % 11;
        }
        char digitoAux = (char) (S != 0 ? S + 47 : 75);

        /* Si el digito verificador no es valido manda exception*/
        if (digito != digitoAux) {
        	FacesMessage message = new FacesMessage();
			message.setDetail("Digito verificador incorrecto");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(message);
        }
        
        return rut;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object oRut) throws ConverterException {

		/*convertir numero de rut a String con su digito verificador*/
		String rutAux = null;
		int rut;
		if (oRut == null) {
			rut = -1;
		}else {
			rut = (Integer) oRut;
		}
        if (rut > 0){
            int M = 0; 
            int S = 1; 
            int T = (Integer)rut;
            for (; T != 0; T /= 10) {
                S = (S + T % 10 * (9 - M++ % 6)) % 11;
            }
            char digitoAux = (char) (S != 0 ? S + 47 : 75);
            rutAux = String.valueOf((Integer)rut)+"-"+digitoAux;
        }
        return rutAux ;
	}

}

