package org.edviz.trenapp.model;

public class Enlace {
	
	private Nodo origen;
    private Nodo destino;
    private int distancia;
    
	public Enlace(Nodo origen, Nodo destino, int distancia) {
		this.origen = origen;
		this.destino = destino;
		this.distancia = distancia;
	}
	
	public Nodo getOrigen() {
		return origen;
	}
	
	public void setOrigen(Nodo origen) {
		this.origen = origen;
	}
	
	public Nodo getDestino() {
		return destino;
	}
	
	public void setDestino(Nodo destino) {
		this.destino = destino;
	}
	
	public int getDistancia() {
		return distancia;
	}
	
	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
	
	@Override
    public String toString() {
        return "Enlace [origen=" + origen.getNombre() + ", destino=" + destino.getNombre() + 
        		", distancia=" + distancia + "]";
    }
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (origen == null ? 0 : origen.hashCode());
        result = prime * result + (destino == null ? 0 : destino.hashCode());
        return result;
    }
	
	@Override
    /**
     * No permitir dos enlaces con el mismo origen y destino
     */
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Enlace obj1 = (Enlace) obj;
        
        if (origen == null) {
            if (obj1.origen != null) {
                return false;
            }
        } else if (!origen.getNombre().equals(obj1.origen.getNombre())) {
            return false;
        }
        
        if (destino == null) {
            if (obj1.destino != null) {
                return false;
            }
        } else if (!destino.getNombre().equals(obj1.destino.getNombre())) {
            return false;
        }
        
        return true;
    }
}
