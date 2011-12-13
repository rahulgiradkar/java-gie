package javagie.exceptions;

public class LogicaNegocioException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LogicaNegocioException(String mensaje) {
		super(mensaje);	
	}
}
