package ejercicio4;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosRepartidor;

public class RepartidorHeuristic {
	
	// Heuristica: lo mejor que puede pasar a partir de un vertice es que se consigan todos los beneficios
	// de los clientes (vertices) que quedan por ir y restandole los kms suponiendo que la distancia minima
	// es 1
	public static Double heuristic(RepartidorVertex v1, Predicate<RepartidorVertex> goal, RepartidorVertex v2) {
		Double beneficio = 0.;
		
		List<Integer> pendientes = v1.pendientes().stream().toList(); // para poder recorrerlo
		for(Integer vertice: pendientes) {
			beneficio += DatosRepartidor.getBeneficioCliente(vertice);
		}		
		Double kms = IntStream.range(1, v1.pendientes().size()).mapToDouble(x -> v1.kms()+x).sum();
		
		return beneficio-kms-1;
	}

	
}
