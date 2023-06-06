package _utils;

import java.util.function.Predicate;

import ejercicio1.CafeEdge;
import ejercicio1.CafeHeuristic;
import ejercicio1.CafeVertex;
import ejercicio2.CursosEdge;
import ejercicio2.CursosHeuristic;
import ejercicio2.CursosVertex;
import ejercicio3.InvestigadoresEdge;
import ejercicio3.InvestigadoresHeuristic;
import ejercicio3.InvestigadoresVertex;
import ejercicio4.RepartidorEdge;
import ejercicio4.RepartidorHeuristic;
import ejercicio4.RepartidorVertex;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.graphs.virtual.EGraphBuilder;
import us.lsi.path.EGraphPath.PathType;

// Clase factoria para crear los constructores de los grafos de los ejercicios
public class GraphsPI5 {
		
	// Ejercicio1: Builder
	public static EGraphBuilder<CafeVertex, CafeEdge> cafeBuilder(CafeVertex v_inicial, 
			Predicate<CafeVertex> es_terminal) { 
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max)
				.goalHasSolution(CafeVertex.goalHasSolution())
				.heuristic(CafeHeuristic::heuristic);
	}
	
	
	// Ejercicio2: Builder
	public static EGraphBuilder<CursosVertex, CursosEdge> cursosBuilder(CursosVertex v_inicial,
			Predicate<CursosVertex> es_terminal) {
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Min)
				.goalHasSolution(CursosVertex.goalHasSolution())
				.heuristic(CursosHeuristic::heuristic);
	}
	
	
	// Ejercicio3: Builder
	public static EGraphBuilder<InvestigadoresVertex, InvestigadoresEdge> investigadoresBuilder(
			InvestigadoresVertex v_inicial, Predicate<InvestigadoresVertex> es_terminal) {
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max)
				.goalHasSolution(InvestigadoresVertex.goalHasSolution())
				.heuristic(InvestigadoresHeuristic::heuristic);
	}
	
	
	// Ejercicio4: Builder
	public static EGraphBuilder<RepartidorVertex, RepartidorEdge> repartidorBuilder(
			RepartidorVertex v_inicial, Predicate<RepartidorVertex> es_terminal) {
		return EGraph.virtual(v_inicial, es_terminal, PathType.Sum, Type.Max)
				.goalHasSolution(RepartidorVertex.goalHasSolution())
				.heuristic(RepartidorHeuristic::heuristic);
	}
		
}
