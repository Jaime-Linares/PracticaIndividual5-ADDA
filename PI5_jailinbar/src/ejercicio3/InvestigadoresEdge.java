package ejercicio3;

import _datos.DatosInvestigadores;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record InvestigadoresEdge(InvestigadoresVertex source, InvestigadoresVertex target, Integer action,
		Double weight) implements SimpleEdgeAction<InvestigadoresVertex, Integer> {
	
	// Metodo de factoria
	public static InvestigadoresEdge of(InvestigadoresVertex source, InvestigadoresVertex target, Integer action) {
		Double weight = 0.;
		
		// comprobamos si el trabajo que se esta haciendo se ha acaba o no
		Integer indiceTrabajo = source.index()%DatosInvestigadores.getNumTrabajos();
		Integer sumDiasTrabajoSource = source.distribution().get(indiceTrabajo).stream().mapToInt(x -> x).sum();
		Integer sumDiasTrabajoTarget = target.distribution().get(indiceTrabajo).stream().mapToInt(x -> x).sum();
		if(sumDiasTrabajoSource > 0 && sumDiasTrabajoTarget == 0) {
			weight += DatosInvestigadores.getCalidadTrabajo(indiceTrabajo);
		}
		
		return new InvestigadoresEdge(source, target, action, weight);
	}
	
	// Metodo toString que define la representacion en forma de cadena de una arista
	@Override
	public String toString() {
		return String.format("%d; %.2f", this.action, this.weight);
	}
	
}
