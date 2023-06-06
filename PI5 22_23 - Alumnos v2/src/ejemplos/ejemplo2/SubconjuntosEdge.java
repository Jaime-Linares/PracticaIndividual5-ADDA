package ejemplos.ejemplo2;

import _datos.DatosSubconjuntos;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record SubconjuntosEdge(SubconjuntosVertex source, SubconjuntosVertex target, Integer action, 
		Double weight) implements SimpleEdgeAction<SubconjuntosVertex,Integer> {

	public static SubconjuntosEdge of(SubconjuntosVertex s, SubconjuntosVertex t, Integer a) {
		// TODO La arista debe tener peso 
		return new SubconjuntosEdge(s, t, a, a*DatosSubconjuntos.getPeso(s.index()));
	}

	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
	
	
}
