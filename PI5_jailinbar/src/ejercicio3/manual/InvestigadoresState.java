package ejercicio3.manual;

import java.util.ArrayList;
import java.util.List;

import _datos.DatosInvestigadores;
import _soluciones.SolucionInvestigadores;

public class InvestigadoresState {
	
	// Propiedades del estado
	InvestigadoresProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<InvestigadoresProblem> anteriores;

	
	// Constructor
	private InvestigadoresState(InvestigadoresProblem prob, Double ac, List<Integer> ls1, 
				List<InvestigadoresProblem> ls2) {
			actual = prob;
			acumulado = ac;
			acciones = ls1;
			anteriores = ls2;
	}
	

	// Metodo de factoria
	public static InvestigadoresState of(InvestigadoresProblem prob, Double ac, List<Integer> ls1,
			List<InvestigadoresProblem> ls2) {
		return new InvestigadoresState(prob, ac, ls1, ls2);
	}
	
	
	// Estado inicial: lo obtenemos a partir del problema inicial
	public static InvestigadoresState initial() {
		InvestigadoresProblem probInicial = InvestigadoresProblem.initial();
		List<Integer> acciones = new ArrayList<>();
		List<InvestigadoresProblem> anteriores = new ArrayList<>();
		return of(probInicial, 0., acciones, anteriores);
	}
	
	
	// forward: como cambia el estado al avanzar, es decir, al acumulado sumarle la calidad del trabajo 
	// si se ha acabado el trabajo, añadir a la lista de acciones la accion que tomamos, añadir a 
	//  anteriores el problema actual y el problema actual ahora sera el vecino
	public void forward(Integer a) {
		Double calidad = 0.;
		// comprobamos si el trabajo que se esta haciendo se acaba o no
		Integer indiceTrabajo = actual.index() % DatosInvestigadores.getNumTrabajos();
		Integer sumDiasTrabajoSource = actual.distribution().get(indiceTrabajo).stream()
				.mapToInt(x -> x).sum();
		Integer sumDiasTrabajoTarget = actual.neighbor(a).distribution().get(indiceTrabajo).stream()
				.mapToInt(x -> x).sum();
		if (sumDiasTrabajoSource > 0 && sumDiasTrabajoTarget == 0) {
			calidad += DatosInvestigadores.getCalidadTrabajo(indiceTrabajo);
		}
		
		acumulado += calidad;
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
	}
	
	
	// back: como cambia el estado al retroceder, es decir, al acumulado hay que restarle la calidad 
	// si es que el trabajo se habia terminado en ese paso, quitar la ultima accion de la lista de 
	// acciones, quitar de la lista de anteriores el ultimo visto y el actual pasa a ser este ultimo 
	// que hemos quitado
	public void back() {
		int last = acciones.size()-1;
		InvestigadoresProblem prob_ant = anteriores.get(last);
		
		Double calidad = 0.;
		// comprobamos si el trabajo que se habia acabado en el anterior paso
		Integer indiceTrabajo = prob_ant.index() % DatosInvestigadores.getNumTrabajos();
		Integer sumDiasTrabajoActual = actual.distribution().get(indiceTrabajo).stream()
				.mapToInt(x -> x).sum();
		Integer sumDiasTrabajoAnterior = prob_ant.distribution().get(indiceTrabajo).stream()
				.mapToInt(x -> x).sum();
		if (sumDiasTrabajoAnterior > 0 && sumDiasTrabajoActual == 0) {
			calidad += DatosInvestigadores.getCalidadTrabajo(indiceTrabajo);
		}
		
		acumulado -= calidad;
		acciones.remove(last);
		anteriores.remove(last);
		actual = prob_ant;
	}
	
	
	// Alternativas: alternativas del estado actual
	public List<Integer> alternativas() {
		return actual.actions();
	}
	
	
	// Cota: sirve para saber que es lo mejor que puede pasar desde un investigador en un trabajo 
	// tomando la alternativa a
	public Double cota(Integer a) {
		Double weight = 0.;		
		// comprobamos si el trabajo que se esta haciendo se acaba o no
		Integer indiceTrabajo = actual.index()%DatosInvestigadores.getNumTrabajos();
		Integer sumDiasTrabajoSource = actual.distribution().get(indiceTrabajo).stream()
				.mapToInt(x -> x).sum();
		Integer sumDiasTrabajoTarget = actual.neighbor(a).distribution().get(indiceTrabajo).stream()
				.mapToInt(x -> x).sum();
		if(sumDiasTrabajoSource > 0 && sumDiasTrabajoTarget == 0) {
			weight += DatosInvestigadores.getCalidadTrabajo(indiceTrabajo);
		}
		
		return acumulado + weight + actual.neighbor(a).heuristic();
	}
	
	
	// esSolucion: recorrer todos los variables posibles (todos los trabajos por todos los investigadores
	public Boolean esSolucion() {
		return actual.index() == DatosInvestigadores.getNumInvestigadores()*DatosInvestigadores.getNumTrabajos();
	}
	
	
	// esTerminal: recorrer todos los variables posibles (todos los trabajos por todos los investigadores
	public Boolean esTerminal() {
		return actual.index() == DatosInvestigadores.getNumInvestigadores()*DatosInvestigadores.getNumTrabajos();
	}

	
	// getSolucion: devuelve la solucion del estado en el que estamos
	public SolucionInvestigadores getSolucion() {
		return SolucionInvestigadores.of(acciones);
	}

}
