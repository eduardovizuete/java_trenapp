package org.edviz.trenapp.model;

import java.util.ArrayList;
import java.util.List;

import org.edviz.trenapp.exception.NoExisteRutaException;

public class Grafo {
	
	private List<Nodo> nodos = new ArrayList<Nodo>();

	public Grafo() {}

	public List<Nodo> getNodos() {
		return nodos;
	}

	public void setNodos(List<Nodo> nodos) {
		this.nodos = nodos;
	}
	
	public void insertarNodo(Nodo nodo) {
		nodos.add(nodo);
    }
	
	public Nodo getNodo(String nombre) {
		for (final Nodo n: getNodos()) {
			if (n.getNombre().equals(nombre)) {
				return n;
			}
		}
		return null;
	}
	
	@Override
    public String toString() {
        return "Grafo directo [nodos=" + nodos + "]";
    }
	
	public List<Ruta> todasRutas(final Nodo nodoOrigen, final Nodo nodoFinal, final Ruta filtro) {
		existeNodo(nodoOrigen);
		existeNodo(nodoFinal);

        final List<Ruta> rutas = new ArrayList<Ruta>();
        
        for (final Enlace e : getNodo(nodoOrigen.getNombre()).getEnlaces()) {
            final Ruta ruta = Ruta.rutaVacia();
            ruta.insertarEnlace(e);
            rutas.addAll(buscar(ruta, filtro, nodoFinal));
        }

        if (rutas.isEmpty()) {
            throw new NoExisteRutaException();
        }
        return rutas;
    }
	
	public List<Ruta> todasRutasParadasMax(final Nodo nodoOrigen, final Nodo nodoFinal, final int numParadas) {
		existeNodo(nodoOrigen);
		existeNodo(nodoFinal);

        final List<Ruta> rutas = new ArrayList<Ruta>();
        
        for (final Enlace e : getNodo(nodoOrigen.getNombre()).getEnlaces()) {
            final Ruta ruta = Ruta.rutaVacia();
            ruta.insertarEnlace(e);
            rutas.addAll(buscarParadasMax(ruta, numParadas, nodoFinal));
        }

        if (rutas.isEmpty()) {
            throw new NoExisteRutaException();
        }
        return rutas;
    }
		
	public List<Ruta> todasRutasMenorDistancia(final Nodo nodoOrigen, final Nodo nodoFinal) {
		existeNodo(nodoOrigen);
		existeNodo(nodoFinal);

        final List<Ruta> rutas = new ArrayList<Ruta>();
        
        for (final Enlace e : getNodo(nodoOrigen.getNombre()).getEnlaces()) {
            final Ruta ruta = Ruta.rutaVacia();
            ruta.insertarEnlace(e);
            rutas.addAll(buscarRutasMenorDistancia(ruta, nodoFinal));
        }

        if (rutas.isEmpty()) {
            throw new NoExisteRutaException();
        }
        return rutas;
    }
	
	public List<Ruta> todasRutasDistanciaMax(final Nodo nodoOrigen, final Nodo nodoFinal, final int distancia) {
		existeNodo(nodoOrigen);
		existeNodo(nodoFinal);

        final List<Ruta> rutas = new ArrayList<Ruta>();
        
        for (final Enlace e : getNodo(nodoOrigen.getNombre()).getEnlaces()) {
            final Ruta ruta = Ruta.rutaVacia();
            ruta.insertarEnlace(e);
            rutas.addAll(buscarMaxDistancia(ruta, distancia, nodoFinal));
        }

        if (rutas.isEmpty()) {
            throw new NoExisteRutaException();
        }
        return rutas;
    }
	
	private void existeNodo(final Nodo nodo) {
        if (getNodo(nodo.getNombre()) == null) {
            throw new IllegalArgumentException("Nodo " + nodo.getNombre() + " no existe");
        }
    }
	
	// DFS 
	private List<Ruta> buscar(final Ruta ruta, final Ruta filtro, final Nodo nodoFinal) {
        final List<Ruta> rutas = new ArrayList<Ruta>();
        if (filtro.iniciaConRuta(ruta)) {
            if (alcanzaRutaFinal(ruta, nodoFinal)) {
                rutas.add(Ruta.copiarRuta(ruta));
            }
            for (final Enlace e : getNodo(ruta.getUltimoNodo().getNombre()).getEnlaces()) {
            	ruta.insertarEnlace(e);
            	rutas.addAll(buscar(ruta, filtro, nodoFinal));
            }

        }
        ruta.eliminarUltimoEnlace();
        return rutas;
    }
	
	// DFS 
	private List<Ruta> buscarParadasMax(final Ruta ruta, final int numParadas, final Nodo nodoFinal) {
        final List<Ruta> rutas = new ArrayList<Ruta>();
        if (ruta.filtroMaxNumSaltos(numParadas)) {
            if (alcanzaRutaFinal(ruta, nodoFinal)) {
                rutas.add(Ruta.copiarRuta(ruta));
            }
            for (final Enlace e : getNodo(ruta.getUltimoNodo().getNombre()).getEnlaces()) {
            	ruta.insertarEnlace(e);
            	rutas.addAll(buscarParadasMax(ruta, numParadas, nodoFinal));
            }

        }
        ruta.eliminarUltimoEnlace();
        return rutas;
    }
	
	// DFS 
	private List<Ruta> buscarRutasMenorDistancia(final Ruta ruta, final Nodo nodoFinal) {
        final List<Ruta> rutas = new ArrayList<Ruta>();
        if (ruta.filtroEnlacesDuplicados()) {
            if (alcanzaRutaFinal(ruta, nodoFinal)) {
                rutas.add(Ruta.copiarRuta(ruta));
            }
            for (final Enlace e : getNodo(ruta.getUltimoNodo().getNombre()).getEnlaces()) {
            	ruta.insertarEnlace(e);
            	rutas.addAll(buscarRutasMenorDistancia(ruta, nodoFinal));
            }

        }
        ruta.eliminarUltimoEnlace();
        return rutas;
    }
	
	// DFS 
	private List<Ruta> buscarMaxDistancia(final Ruta ruta, final int distancia, final Nodo nodoFinal) {
        final List<Ruta> rutas = new ArrayList<Ruta>();
        if (ruta.filtroMaxDistancia(distancia)) {
            if (alcanzaRutaFinal(ruta, nodoFinal)) {
                rutas.add(Ruta.copiarRuta(ruta));
            }
            for (final Enlace e : getNodo(ruta.getUltimoNodo().getNombre()).getEnlaces()) {
            	ruta.insertarEnlace(e);
            	rutas.addAll(buscarMaxDistancia(ruta, distancia, nodoFinal));
            }

        }
        ruta.eliminarUltimoEnlace();
        return rutas;
    }	
	
	private boolean alcanzaRutaFinal(final Ruta ruta, final Nodo nodoFinal) {
        return ruta.getUltimoNodo().getNombre().equals(nodoFinal.getNombre());
    }
}
