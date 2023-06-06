package ejercicio1;

import _datos.DatosCafes;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CafeEdge(CafeVertex source, CafeVertex target, Integer action, Double weight) 
		implements SimpleEdgeAction<CafeVertex, Integer> {

	// Metodo de factoria
	public static CafeEdge of(CafeVertex source, CafeVertex target, Integer action) {
		Double weight = DatosCafes.getBeneficioVentaKg(source.index()) * action;
		return new CafeEdge(source, target, action, weight);
	}
	
	
	// Metodo toString que define la representacion en forma de cadena de una arista
	@Override
	public String toString() {
		return String.format("%d; %.2f", this.action, this.weight);
	}
	
}
