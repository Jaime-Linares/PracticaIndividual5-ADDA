package ejercicio4.manual;

import java.util.ArrayList;
import java.util.List;

import _datos.DatosRepartidor;
import _soluciones.SolucionRepartidor;

public class RepartidorState {
	
	// Propiedades del estado
	RepartidorProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<RepartidorProblem> anteriores;
	
	
	// Constructor
	private RepartidorState(RepartidorProblem prob, Double ac, List<Integer> ls1, 
			List<RepartidorProblem> ls2) {
		actual = prob;
		acumulado = ac;
		acciones = ls1;
		anteriores = ls2;
	}
	
	
	// Metodo de factoria
	public static RepartidorState of(RepartidorProblem prob, Double ac, List<Integer> ls1, 
			List<RepartidorProblem> ls2) {
		return new RepartidorState(prob, ac, ls1, ls2);
	}
	
	
	// Estado inicial: lo obtenemos a partir del problema inicial
	public static RepartidorState initial() {
		RepartidorProblem probInicial = RepartidorProblem.initial();
		List<Integer> acciones = new ArrayList<>();
		List<RepartidorProblem> anteriores = new ArrayList<>();
		return of(probInicial, 0., acciones, anteriores);
	}
	
	
	// forward: como cambia el estado al avanzar, es decir, al acumulado sumarle el beneficio de ir 
	// a ese vertice menos la distancia recorrida acumulada, añadir a la lista de acciones la accion
	// que tomamos, añadir a anteriores el problema actual y el actual pasa a ser el problema vecino
	public void forward(Integer a) {
		Double beneficio = DatosRepartidor.getBeneficioCliente(a);
		Double distanciaKms = actual.kms();
		if(actual.cliente() == 0) {
			distanciaKms += DatosRepartidor.getPesoArista(0, a);
		} else {
			distanciaKms += DatosRepartidor.getPesoArista(acciones.get(acciones.size()-1), a);
		}
		
		acumulado += beneficio - distanciaKms;
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
	}
	
	
	// back: como cambia el estado al volver atras, es decir, al acumulado hay que restarle el beneficio
	// de haber llegado al ultimo menos los kms que ha recorrido, quitar la ultima accion de la lista de 
	// acciones, quitar de la lista de anteriores el ultimo visto y el actual pasa a ser este ultimo
	// que hemos quitado
	public void back() {
		int last = acciones.size()-1;
		RepartidorProblem prob_ant = anteriores.get(last);
		
		Double beneficio = DatosRepartidor.getBeneficioCliente(acciones.get(last));
		Double distanciaKms = actual.kms();

		acumulado -= (beneficio - distanciaKms);
		acciones.remove(last);
		anteriores.remove(last);
		actual = prob_ant;
	}
	
	
	// Alternativas: alternativas del estado inicial, es decir, las alternativas del vertice actual
	public List<Integer> alternativas() {
		return actual.actions();
	}
	
	
	// Cota: sirve para saber que es lo mejor que puede pasar desde un vertice tomando la alternativa a
	public Double cota(Integer a) {
		Double weight = DatosRepartidor.getBeneficioCliente(a) - actual.kms();
		if(actual.cliente() == 0) {
			weight -= DatosRepartidor.getPesoArista(0, a);
		} else {
			weight -= DatosRepartidor.getPesoArista(acciones.get(acciones.size()-1), a);
		}
		
		return acumulado + weight + actual.neighbor(a).heuristic();
	}
	
	
	// esSolucion: que no queden vertices por visitar, es decir, que los pendientes sea un conjunto
	// vacio
	public Boolean esSolucion() {
		return actual.pendientes().size() == 0;
	}
	
	
	// esTerminal: recorrer todos los vertices del grafo, es decir, que el cliente sea el numero de 
	// vertices que hay en el grafo
	public Boolean esTerminal() {
		return actual.cliente() == DatosRepartidor.getNumVertices();
	}
	
	
	// getSolucion: devuelve la solucion del estado en el que estamos
	public SolucionRepartidor getSolucion() {
		return SolucionRepartidor.of(acciones);
	}
	
}
