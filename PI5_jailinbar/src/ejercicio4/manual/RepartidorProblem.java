package ejercicio4.manual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import _datos.DatosRepartidor;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record RepartidorProblem(Integer cliente, Set<Integer> pendientes, List<Integer> visitados,
		Double kms) {
	
	// Metodo de factoria
	public static RepartidorProblem of(Integer cliente, Set<Integer> pendientes, List<Integer> visitados,
		Double kms) {
		return new RepartidorProblem(cliente, pendientes, visitados, kms);
	}
		

	// Vertice inicial: el cliente es el 0, los pendientes son todos los vertices que hay en el grafo,
	// los visitados es una lista vacia y los km recorridos son 0
	public static RepartidorProblem initial() {
		List<Integer> visitados = new ArrayList<>();
		
		Set<Integer> pendientes = new HashSet<>();
		for(int i=0; i<DatosRepartidor.getNumVertices(); i++) {
			pendientes.add(i);
		}
	
		return of(0, pendientes, visitados, 0.);
	}
	
	
	// Alternativas a las que podemos ir, es decir, a los vertices (del grafo) a los que podemos ir desde 
	// el ultimo que hemos visitado
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<>();
		List<Integer> pendientesVisitar = pendientes().stream().toList();

		// si ya hemos recorrido todos los vertices no habra alternativas
		if (cliente() < DatosRepartidor.getNumVertices()) {
			// si estamos en el cliente 0, tenemos que ver aquellos que esten conectados al vertice
			// origen, es decir, el 0, quitando obviamente el 0
			if (cliente() == 0) {
				for (Integer vertice : pendientesVisitar) {
					if (vertice != 0 && DatosRepartidor.existeArista(0, vertice)) {
						alternativas.add(vertice);
					}
				}
			} // si estamos en el ultimo cliente, tenemos que ver que el ultimo visitado pueda conectarse
			  // con el destino que es el 0
			else if (cliente() == DatosRepartidor.getNumVertices() - 1) {
				if (pendientesVisitar.contains(0)
						&& DatosRepartidor.existeArista(visitados().get(visitados().size() - 1), 0)) {
					alternativas.add(0);
				}
			} // si estamos en el cualquier otro vertice, tenemos que mirar aquellos que queden que puedan
			  // estar conectados con el ultimo vertice visitado quitando el 0 que sera el destino final
			else {
				for (Integer vertice : pendientesVisitar) {
					if (vertice != 0
							&& DatosRepartidor.existeArista(visitados().get(visitados().size() - 1), vertice)) {
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
	public RepartidorProblem neighbor(Integer a) {
		// eliminamos de los pendientes
		Set<Integer> pendientesVecino = Set2.copy(pendientes());
		pendientesVecino.remove(a);

		// añadimos a los visitados
		List<Integer> visitadosVecino = List2.copy(visitados());
		visitadosVecino.add(a);

		// añadimos kms recorridos
		Double kmsVecino = kms();
		if (cliente() == 0) {
			kmsVecino += DatosRepartidor.getPesoArista(0, a);
		} else {
			kmsVecino += DatosRepartidor.getPesoArista(visitados().get(visitados().size() - 1), a);
		}

		return of(cliente() + 1, pendientesVecino, visitadosVecino, kmsVecino);
	}
	
	
	// Heuristica: lo mejor que puede pasar a partir de un vertice es que se consigan todos los beneficios
	// de los clientes (vertices) que quedan por ir y restandole los kms suponiendo que la distancia minima
	// es 1
	public Double heuristic() {
		Double beneficio = 0.;
		
		List<Integer> pendientes = pendientes().stream().toList(); // para poder recorrerlo
		for(Integer vertice: pendientes) {
			beneficio += DatosRepartidor.getBeneficioCliente(vertice);
		}		
		Double kms = IntStream.range(1, pendientes().size()).mapToDouble(x -> kms()+x).sum();
		
		return beneficio-kms-1;
	}

}
