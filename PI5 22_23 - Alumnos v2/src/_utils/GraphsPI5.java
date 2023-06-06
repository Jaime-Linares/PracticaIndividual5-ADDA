package _utils;

import java.util.function.Predicate;

import ejemplos.ejemplo1.MulticonjuntoEdge;
import ejemplos.ejemplo1.MulticonjuntoHeuristic;
import ejemplos.ejemplo1.MulticonjuntoVertex;
import ejemplos.ejemplo2.SubconjuntosEdge;
import ejemplos.ejemplo2.SubconjuntosHeuristic;
import ejemplos.ejemplo2.SubconjuntosVertex;
import ejemplos.ejemplo3.AlumnosEdge;
import ejemplos.ejemplo3.AlumnosHeuristic;
import ejemplos.ejemplo3.AlumnosVertex;
import us.lsi.common.TriFunction;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.graphs.virtual.EGraphBuilder;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.VirtualVertex;
import us.lsi.path.EGraphPath.PathType;

// Clase Factoria para crear los constructores de los grafos de los ejemplos y ejercicios
public class GraphsPI5 {
	
	// Builder Generico para todo grafo sum & min. No lo usaremos
	public static <V extends VirtualVertex<V,E,?>, E extends SimpleEdgeAction<V,?>>
	EGraphBuilder<V, E> genericBuilder(V v_inicial, Predicate<V> es_final, Predicate<V> hasSolution,
			TriFunction<V,Predicate<V>, V, Double> heuristic) {
		return EGraph.virtual(v_inicial, es_final, PathType.Sum, Type.Min)
				.goalHasSolution(hasSolution)
				.heuristic(heuristic);
	}
	
	// Ejemplo1: Builder
	public static EGraphBuilder<MulticonjuntoVertex, MulticonjuntoEdge>
	multisetBuilder(MulticonjuntoVertex v_inicial, Predicate<MulticonjuntoVertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
				// TODO Defina en el tipo vertice un m. static / Predicate para los vertices solucion
				.goalHasSolution(MulticonjuntoVertex.goalHasSolution())
				.heuristic(MulticonjuntoHeuristic::heuristic);
	}
	
	// Ejemplo2: Builder
	public static EGraphBuilder<SubconjuntosVertex, SubconjuntosEdge>
	subsetBuilder(SubconjuntosVertex v_inicial, Predicate<SubconjuntosVertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
				// TODO Defina en el tipo vertice un m. static / Predicate para los vertices solucion
				.goalHasSolution(SubconjuntosVertex.goalHasSolution())
				.heuristic(SubconjuntosHeuristic::heuristic);
	}
	
	// Ejemplo3: Builder
	public static EGraphBuilder<AlumnosVertex, AlumnosEdge>
	alumnosBuilder(AlumnosVertex v_inicial, Predicate<AlumnosVertex> es_terminal) { 
		// TODO Implementarlo de forma similar a los de los ejemplos 1 y 2
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max)
				.goalHasSolution(AlumnosVertex.goalHasSolution())
				.heuristic(AlumnosHeuristic::heuristic);
	}
	
}
