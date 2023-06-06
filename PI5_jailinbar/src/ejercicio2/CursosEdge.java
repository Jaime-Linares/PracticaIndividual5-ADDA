package ejercicio2;

import _datos.DatosCursos;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CursosEdge(CursosVertex source, CursosVertex target, Integer action, Double weight) 
		implements SimpleEdgeAction<CursosVertex, Integer> {
	
	// Metodo factoria
	public static CursosEdge of(CursosVertex source, CursosVertex target, Integer action) {
		Double weight = DatosCursos.getPrecioInscripcion(source.index()) * action;
		return new CursosEdge(source, target, action, weight);
	}
	
	
	// Metodo toString que define la representacion en forma de cadena de una arista
	@Override
	public String toString() {
		return String.format("%d; %.2f", this.action, this.weight);
	}

}
