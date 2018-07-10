package org.edviz.trenapp.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Ruta implements Comparable {
	
	private List<Enlace> enlaces = new ArrayList<Enlace>();
    private int pesoTotal = 0;
	
    private Ruta() {}
    
    private Ruta(final Ruta nuevaRuta) {
    	enlaces.addAll(nuevaRuta.getEnlaces());
        this.pesoTotal = nuevaRuta.getPesoTotal();
    }
    
    public static Ruta rutaVacia() {
        return new Ruta();
    }
    
    public static Ruta copiarRuta(final Ruta nuevaRuta) {
        return new Ruta(nuevaRuta);
    }

	public List<Enlace> getEnlaces() {
		return enlaces;
	}

	public void setEnlaces(List<Enlace> enlaces) {
		this.enlaces = enlaces;
	}

	public int getPesoTotal() {
		return pesoTotal;
	}

	public void setPesoTotal(int totalWeight) {
		this.pesoTotal = totalWeight;
	}
	
	public int getNumeroSaltos() {
        return enlaces.size();
    }
	
	public void insertarEnlace(Enlace enlace) {
		if (!enlaceSiguiente(enlace)) {
            throw new IllegalArgumentException("El enlace " + enlace + " no es consecutivo a la ruta");
        }
		enlaces.add(enlace);
        pesoTotal += enlace.getDistancia();	
    }
	
	private boolean enlaceSiguiente(final Enlace enlace) {
        final Nodo ultimoNodo = getUltimoNodo();
        if (ultimoNodo != null && !ultimoNodo.equals(enlace.getOrigen())) {
            return false;
        }
        return true;
    }
	
	public Nodo getUltimoNodo() {
		Nodo node = null;
        if (!enlaces.isEmpty()) {
            node = enlaces.get(enlaces.size() - 1).getDestino();
        }
        return node;
    }
	
	public void eliminarUltimoEnlace() {
        if (!enlaces.isEmpty()) {
            final Enlace ultimoEnlace = enlaces.get(enlaces.size() - 1);
            this.pesoTotal -= ultimoEnlace.getDistancia();
            enlaces.remove(enlaces.size() - 1);
        }
    }
	
	public boolean enlacesDuplicados() {
        for (int i = 0; i < enlaces.size(); i++) {
            for (int j = i + 1; j < enlaces.size(); j++) {
                if (enlaces.get(i).equals(enlaces.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }
	
	public boolean iniciaConRuta(final Ruta rutaI) {
        final List<Enlace> rutaParcial = rutaI.getEnlaces();
        final List<Enlace> rutaCompleta = getEnlaces();
        for (int i = 0; i < rutaParcial.size(); i++) {
            if (i >= rutaCompleta.size() || !rutaParcial.get(i).equals(rutaCompleta.get(i))) {
                return false;
            }
        }
        return true;
    }
	
	public boolean filtroMaxNumSaltos(final int maxSaltos) {
		return getNumeroSaltos() <= maxSaltos;
    }
	
	public boolean filtroIgualNumSaltos(final int numSaltos) {
		return getNumeroSaltos() == numSaltos;
    }
	
	public boolean filtroEnlacesDuplicados() {
		return !enlacesDuplicados();
    }
	
	public boolean filtroMaxDistancia(final int maxDistancia) {
		return getPesoTotal() < maxDistancia;
    }
	
	@Override
    public String toString() {
        return "GrafoRuta (" + pesoTotal + ") [enlaces=" + enlaces + "]";
    }

	@Override
	public int compareTo(Object obj1) {
		return this.getPesoTotal() - ((Ruta) obj1).getPesoTotal();
	}

}
