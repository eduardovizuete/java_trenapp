package org.edviz.trenapp.exception;

public class NoExisteRutaException extends RuntimeException {
	
	private static final long serialVersionUID = 8357302585189569085L;

	public NoExisteRutaException() {
        super("No existe ruta");
    }
}
