package org.edviz.trenapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.edviz.trenapp.exception.NoExisteRutaException;
import org.edviz.trenapp.model.Enlace;
import org.edviz.trenapp.model.Grafo;
import org.edviz.trenapp.model.Nodo;
import org.edviz.trenapp.model.Ruta;
import org.edviz.trenapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GrafoService {
	
	private static final Logger log = LoggerFactory.getLogger(GrafoService.class);
	
	private final Grafo gd;
	
	public GrafoService() {
		 gd = new Grafo();
	}
	
	public Grafo crearGrafoDirecto() {	
		log.info("Creando Grafo Directo");
				
		Nodo nodoA = new Nodo(Constants.NODE_A);
		Nodo nodoB = new Nodo(Constants.NODE_B);
		Nodo nodoC = new Nodo(Constants.NODE_C);
		Nodo nodoD = new Nodo(Constants.NODE_D);
		Nodo nodoE = new Nodo(Constants.NODE_E);
		
		nodoA.insertarEnlace(new Enlace(nodoA, nodoB, 5));
		nodoB.insertarEnlace(new Enlace(nodoB, nodoC, 4));
		nodoC.insertarEnlace(new Enlace(nodoC, nodoD, 8));
		nodoD.insertarEnlace(new Enlace(nodoD, nodoC, 8));
		nodoD.insertarEnlace(new Enlace(nodoD, nodoE, 6));
		nodoA.insertarEnlace(new Enlace(nodoA, nodoD, 5));
		nodoC.insertarEnlace(new Enlace(nodoC, nodoE, 2));
		nodoE.insertarEnlace(new Enlace(nodoE, nodoB, 3));
		nodoE.insertarEnlace(new Enlace(nodoE, nodoB, 3));
		nodoA.insertarEnlace(new Enlace(nodoA, nodoE, 7));
		
		gd.insertarNodo(nodoA);
		gd.insertarNodo(nodoB);
		gd.insertarNodo(nodoC);
		gd.insertarNodo(nodoD);
		gd.insertarNodo(nodoE);
		
		return gd;		
	}
	
	public int distanciaRuta(final Nodo nodoOrigen, final Nodo nodoFinal, final List<Nodo> nodosIntermedios) {
        final Ruta ruta = crearRuta(nodoOrigen, nodoFinal, nodosIntermedios);
        final List<Ruta> todasRutas = gd.todasRutas(nodoOrigen, nodoFinal, ruta);
        return todasRutas.get(0).getPesoTotal();
    }
	
	public int rutasParadasMax(final Nodo nodoOrigen, final Nodo nodoFinal, final int numParadas) {
        return gd.todasRutasParadasMax(nodoOrigen, nodoFinal, numParadas).size();
    }
	
	public int rutasParadasExactas(final Nodo nodoOrigen, final Nodo nodoFinal, final int numParadas) {
        final List<Ruta> rutas = gd.todasRutasParadasMax(nodoOrigen, nodoFinal, numParadas);
        final List<Ruta> rutasExactas = new ArrayList<Ruta>();
        for (final Ruta r : rutas) {
            if (r.filtroIgualNumSaltos(numParadas)) {
            	rutasExactas.add(r);
            }
        }
        return rutasExactas.size();
    }
	
	@SuppressWarnings("unchecked")
	public int rutaMenorDistancia(final Nodo nodoOrigen, final Nodo nodoFinal) {
        final List<Ruta> todasRutas = gd.todasRutasMenorDistancia(nodoOrigen, nodoFinal);
        return Collections.min(todasRutas).getPesoTotal();
    }
	
	public int rutasMaximaDistancia(final Nodo nodoOrigen, final Nodo nodoFinal, final int distancia) {
        return gd.todasRutasDistanciaMax(nodoOrigen, nodoFinal, distancia).size();
    }
	
	private Ruta crearRuta(final Nodo nodoOrigen, final Nodo nodoFinal, final List<Nodo> nodosIntermedios) {
        final Ruta rutaFinal = Ruta.rutaVacia();
        Nodo nodoActual = nodoOrigen;
        if (nodosIntermedios != null) {
            for (final Nodo nodo : nodosIntermedios) {
            	rutaFinal.insertarEnlace(new Enlace(nodoActual, nodo, 0));
            	nodoActual = nodo;
            }
        }
        rutaFinal.insertarEnlace(new Enlace(nodoActual, nodoFinal, 0));
        return rutaFinal;
    }
	
	public void ejecutarOperaciones() {
		// Distancia entre nodos
		// Distancia: A-B-C
		String dn = "A-B-C";
		ejecutarOpDistancia(dn);
		// Distancia: A-D
		dn = "A-D";
		ejecutarOpDistancia(dn);
		// Distancia: A-D-C
		dn = "A-D-C";
		ejecutarOpDistancia(dn);
		// Distancia: A-E-B-C-D
		dn = "A-E-B-C-D";
		ejecutarOpDistancia(dn);
		// Distancia: A-E-D
		dn = "A-E-D";
		ejecutarOpDistancia(dn);
		// Rutas maximas paradas
		// Rutas maximas paradas: C-C-3
		String rmp = "C-C-3";
		ejecutarOpParadasMax(rmp);
		// Rutas paradas exactas
		// Rutas paradas exactas: A-C-4
		String rpe = "A-C-4";
		ejecutarOpParadasExactas(rpe);
		// Ruta menor distancia
		// Ruta menor distancia: A-C
		String rmd = "A-C";
		ejecutarOpRutaMenorDistancia(rmd);
		// Ruta menor distancia: A-C
		rmd = "B-B";
		ejecutarOpRutaMenorDistancia(rmd);
		// Rutas maxima distancia
		// Ruta maxima distancia: C-C-30
		String rmaxd = "C-C-30";
		ejecutarOpRutaMaxDistancia(rmaxd);
	}
	
	public void ejecutarOpDistancia(String dn) {
		
		final String[] nodos = dn.split("-");
		List<Nodo> nodosI = getNodosIntermedios(dn);
		try {
        	int distancia = distanciaRuta(new Nodo(nodos[0]), new Nodo(nodos[nodos.length -1]), nodosI);
        	log.info("Distancia " + dn + ": " + distancia);
        } catch (final NoExisteRutaException e) {
        	log.info("Distancia " + dn + ": " + "No existe ruta");
        }
	}
	
	public List<Nodo> getNodosIntermedios(String dn) {
		final String[] nodos = dn.split("-");
		final List<Nodo> nodosI = new ArrayList<Nodo>();
        for (int i = 1; i < nodos.length - 1; i++) {
        	nodosI.add(new Nodo(nodos[i]));
        }
        
        return nodosI;
	}
	
	public void ejecutarOpParadasMax(String rmp) {
		final String[] params = rmp.split("-");
		try {
        	int num = rutasParadasMax(new Nodo(params[0]), new Nodo(params[1]), Integer.parseInt(params[2]));
        	log.info("Numero de rutas con paradas max " + rmp + ": " + num);
        } catch (final NoExisteRutaException e) {
        	log.info("Numero de rutas con paradas max " + rmp + ": " + "No existe ruta");
        }
	}
	
	public void ejecutarOpParadasExactas(String rpe) {
		final String[] params = rpe.split("-");
		try {
        	int num = rutasParadasExactas(new Nodo(params[0]), new Nodo(params[1]), Integer.parseInt(params[2]));
        	log.info("Numero de rutas con paradas exactas " + rpe + ": " + num);
        } catch (final NoExisteRutaException e) {
        	log.info("Numero de rutas con paradas exactas " + rpe + ": " + "No existe ruta");
        }
	}
	
	public void ejecutarOpRutaMenorDistancia(String rmd) {
		final String[] params = rmd.split("-");
		try {
        	int num = rutaMenorDistancia(new Nodo(params[0]), new Nodo(params[1]));
        	log.info("Distancia menor ruta " + rmd + ": " + num);
        } catch (final NoExisteRutaException e) {
        	log.info("Distancia menor ruta " + rmd + ": " + "No existe ruta");
        }
	}
	
	public void ejecutarOpRutaMaxDistancia(String rmaxd) {
		final String[] params = rmaxd.split("-");
		try {
        	int num = rutasMaximaDistancia(new Nodo(params[0]), new Nodo(params[1]), Integer.parseInt(params[2]));
        	log.info("Numero de rutas con distancia max " + rmaxd + ": " + num);
        } catch (final NoExisteRutaException e) {
        	log.info("Numero de rutas con distancia max " + rmaxd + ": " + "No existe ruta");
        }
	}
	
	public void imprimirGrafo() {
		log.info(gd.toString());
	}

}
