package dev.giselli.tarefa1.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String msg) {
		super(msg);
		//repassa o argumento para o construtor da superclasse
		
	}
	
}
