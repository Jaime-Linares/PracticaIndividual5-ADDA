package ejercicio3;

import java.util.function.Predicate;

import _datos.DatosInvestigadores;

public class InvestigadoresHeuristic {
	
	// Heuristica: lo mejor que puede pasar a partir de un vertice es que se cumplan todos los trabajos
	// restantes a partir de ese vertice (incluido el del vertice en el que estamos)
	public static Double heuristic(InvestigadoresVertex v1, Predicate<InvestigadoresVertex> goal,
			InvestigadoresVertex v2) {
		Double res = 0.;
		
		Integer indiceInvestigador = v1.index()/DatosInvestigadores.getNumTrabajos();
		Integer ultimoInv = DatosInvestigadores.getNumInvestigadores()-1;
		// si el investigador es el ultimo miramos por que trabajo vamos para solo sumar a partir de ese
		if(indiceInvestigador == ultimoInv) {	
			Integer indiceTrabajo = v1.index()%DatosInvestigadores.getNumTrabajos();
			for(int j=indiceTrabajo; j<DatosInvestigadores.getNumTrabajos(); j++) {
				res += DatosInvestigadores.getCalidadTrabajo(j);
			}
		} else {	// si el investigador no es el ultimo se deben sumar de todos los trabajos
			for(int j=0; j<DatosInvestigadores.getNumTrabajos(); j++) {
				res += DatosInvestigadores.getCalidadTrabajo(j);
			}
		}
		
		return res;
	}
	

}
