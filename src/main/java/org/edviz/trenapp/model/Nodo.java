package org.edviz.trenapp.model;

import java.util.ArrayList;
import java.util.List;

public class Nodo {
	
	private String nombre;
    private List<Enlace> enlaces = new ArrayList<Enlace>();
	
    public Nodo(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Enlace> getEnlaces() {
		return enlaces;
	}

	public void setEnlaces(List<Enlace> enlaces) {
		this.enlaces = enlaces;
	}
	
	public void insertarEnlace(Enlace enlace) {
		if (!enlaces.contains(enlace)) {
			enlaces.add(enlace);
        }		
    }
	
	@Override
    public String toString() {
        return "\nNodo [nombre=" + nombre + ", \n\tenlaces=" + enlaces + "]";
    }
}
