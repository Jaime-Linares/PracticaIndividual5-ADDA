package ejemplos.ejemplo1;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record MulticonjuntoEdge(MulticonjuntoVertex source, MulticonjuntoVertex target, Integer action, 
		Double weight) implements SimpleEdgeAction<MulticonjuntoVertex,Integer> {

	public static MulticonjuntoEdge of(MulticonjuntoVertex s, MulticonjuntoVertex t, Integer a) {
		return new MulticonjuntoEdge(s, t, a, Double.valueOf(a));
	}
	
	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}

}

