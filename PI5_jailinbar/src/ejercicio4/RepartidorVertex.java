package ejercicio4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import _datos.DatosRepartidor;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record RepartidorVertex(Integer cliente, Set<Integer> pendientes, List<Integer> visitados,
		Double kms) implements VirtualVertex<RepartidorVertex, RepartidorEdge, Integer> {
	
	// Metodo de factoria
	public static RepartidorVertex of(Integer cliente, Set<Integer> pendientes, List<Integer> visitados,
		Double kms) {
		return new RepartidorVertex(cliente, pendientes, visitados, kms);
	}
	

	// Vertice inicial: el cliente es el 0, los pendientes son todos los vertices que hay en el grafo,
	// los visitados es una lista vacia y los km recorridos son 0
	public static RepartidorVertex initial() {
		List<Integer> visitados = new ArrayList<>();
		
		Set<Integer> pendientes = new HashSet<>();
		for(int i=0; i<DatosRepartidor.getNumVertices(); i++) {
			pendientes.add(i);
		}
	
		return of(0, pendientes, visitados, 0.);
	}
	
	
	// goal: recorrer todos los vertices del grafo, es decir, que el cliente final sea el numero de 
	// vertices que hay en el grafo
	public static Predicate<RepartidorVertex> goal() {
		return x -> x.cliente() == DatosRepartidor.getNumVertices();
	}
	
	
	// goalHasSolution: que no queden vertices por visitar, es decir, que los pendientes sea un conjunto
	// vacio
	public static Predicate<RepartidorVertex> goalHasSolution() {
		return x -> x.pendientes().size() == 0;
	}
	
	
	// Alternativas a las que podemos ir, es decir, a los vertices (del grafo) a los que podemos ir desde 
	// el ultimo que hemos visitado
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<>();
		List<Integer> pendientesVisitar = pendientes().stream().toList();
		
		// si ya hemos recorrido todos los vertices no habra alternativas
		if(cliente() < DatosRepartidor.getNumVertices()) {
			// si estamos en el cliente 0, tenemos que ver aquellos que esten conectados al vertice
			// origen, es decir, el 0, quitando obviamente el 0 
			if(cliente() == 0) {
				for(Integer vertice: pendientesVisitar) {
					if(vertice != 0 && DatosRepartidor.existeArista(0, vertice)) {
						alternativas.add(vertice);
					}
				}
			} // si estamos en el ultimo cliente, tenemos que ver que el ultimo visitado pueda conectarse
			  // con el destino que es el 0
			else if(cliente() == DatosRepartidor.getNumVertices()-1) {
				if(pendientesVisitar.contains(0) && 
						DatosRepartidor.existeArista(visitados().get(visitados().size()-1), 0)) {
					alternativas.add(0);
				}
			} // si estamos en el cualquier otro vertice, tenemos que mirar aquellos que queden que puedan
			  // estar conectados con el ultimo vertice visitado quitando el 0 que sera el destino final
			else {
				for(Integer vertice: pendientesVisitar) {
					if(vertice != 0 && 
							DatosRepartidor.existeArista(visitados().get(visitados().size()-1), vertice)) {
						alternativas.add(vertice);
					}
				}
			}
		}
		
		return alternativas;
	}

	
	// Vecino al que vamos, es decir, incrementamos el indice, quitamos la accion (vertice al que vamos)
	// del conjunto de pendientes, añadimos la accion al conjunto de vertices visitados y aumentamos los 
	// kms con el peso de la arista entre vertices
	@Override
	public RepartidorVertex neighbor(Integer a) {
		// eliminamos de los pendientes
		Set<Integer> pendientesVecino = Set2.copy(pendientes());
		pendientesVecino.remove(a);
		
		// añadimos a los visitados
		List<Integer> visitadosVecino = List2.copy(visitados());
		visitadosVecino.add(a);
		
		// añadimos kms recorridos
		Double kmsVecino = kms();
		if(cliente() == 0) {
			kmsVecino += DatosRepartidor.getPesoArista(0, a);
		} else {
			kmsVecino += DatosRepartidor.getPesoArista(visitados().get(visitados().size()-1), a);
		}
			
		return of(cliente()+1, pendientesVecino, visitadosVecino, kmsVecino);
	}

	
	// Arista que construimos, en este caso, donde la accion sera el cliente al que vamos y donde el 
	// peso sera el beneficio de ir a ese cliente menos los kms que recorremos hasta llegar a ese 
	// vertice (es decir, los que llevamos vamos mas los que hay al que vamos a ir)
	@Override
	public RepartidorEdge edge(Integer a) {
		return RepartidorEdge.of(this, neighbor(a), a);
	}

	
	// Metodo toString que define la representacion en forma de cadena de un vertice
	@Override
	public String toString() {
		return String.format("%d; %s; %s; %f", this.cliente, this.pendientes, this.visitados, this.kms);
	}

}
