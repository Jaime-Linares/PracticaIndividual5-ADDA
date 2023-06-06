package _utils;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Style;
import us.lsi.common.String2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraphBuilder;

// Reune y simplifica todos los tests.
public class TestsPI5 {
	
	public static<V,E> GraphPath<V, E> testGreedy(EGraph<V, E> graph) {
		var alg_voraz = GreedyOnGraph.of(graph);		
		GraphPath<V, E> path = alg_voraz.path();
		return alg_voraz.isSolution(path)? path: null;
	}

	public static<V,E> GraphPath<V, E> testAStar(EGraph<V, E> graph, GraphPath<V, E> gp) {	
		var alg_star = gp==null? AStar.of(graph):
			AStar.of(graph, null, gp.getWeight(), gp);
		
		var res = alg_star.search().orElse(null);
		var outGraph = alg_star.outGraph();
		if(outGraph!=null)
			toDot("AStar", outGraph, res);
		return res;
	}

	public static<V,E> GraphPath<V, E> testPDR(EGraph<V, E> graph, GraphPath<V, E> gp) {
		var alg_pdr = gp==null? DPR.of(graph):
			DPR.of(graph, null, gp.getWeight(), gp, true);

		var res = alg_pdr.search().orElse(null);
		var outGraph = alg_pdr.outGraph();
		if(outGraph!=null)
			toDot("PDR", outGraph, res);
		return res;
	}
	
	public static<V,E,S> GraphPath<V, E> testBT(EGraph<V, E> graph, GraphPath<V, E> gp) {
		var alg_bt = gp==null? BT.of(graph):
			BT.of(graph, null, gp.getWeight(), gp, true);
		
		var res = alg_bt.search().orElse(null);
		var outGraph = alg_bt.outGraph();
		if(outGraph!=null)
			toDot("BT", outGraph, res);
		return res;
	}
	
	public static<V,E> void toConsole(String type, GraphPath<V, E> path, Function<GraphPath<V, E>, ?> fSolution) {
		if(path!=null)
			String2.toConsole("Solucion %s: %s", type, fSolution.apply(path));
		else
			String2.toConsole("%s no obtuvo solucion", type);
		
		line("_");		
	}
	
	
	private static String file;
	private static Integer num_test;
	
	public static void iniTest(String f, Integer t, Consumer<String> iniDatos) {
		file = f;
		num_test = t;
		iniDatos.accept("ficheros/"+f+t+".txt");
		line("=");
	}
	
	public static void line(String s) {
		String2.toConsole(IntStream.range(0, 100).mapToObj(i ->s).collect(Collectors.joining()));		
	}
	
	private static <V,E> void toDot(String type, Graph<V,E> g, GraphPath<V,E> sol){
		Predicate<V> vs = v -> sol.getVertexList().contains(v);
		Predicate<E> es = e -> sol.getEdgeList().contains(e);
		GraphColors.toDot(g, "grafos/"+type+"-"+file+num_test+".gv", 
				V::toString, 
				E::toString,
				v -> GraphColors.styleIf(Style.bold, vs.test(v)),
				e -> GraphColors.styleIf(Style.bold, es.test(e)));
	}

	
	public static<V,E> void tests(V vInicial, Predicate<V> goal, 
		BiFunction<V, Predicate<V>, EGraphBuilder<V,E>> builder,
		Function<V, E> greedyEdge, Function<GraphPath<V, E>, ?> solutionOf) {
		
		EGraph<V,E> gg = builder.apply(vInicial, goal).greedyEdge(greedyEdge).build();
	
		var gp = testGreedy(gg);
		toConsole("Voraz", gp, solutionOf);
		
		var path = testAStar(builder.apply(vInicial, goal).build(), gp);
		toConsole("A*", path, solutionOf);
		
		path = testPDR(builder.apply(vInicial, goal).build(), gp);
		toConsole("PDR", path, solutionOf);
		
		path = testBT(builder.apply(vInicial, goal).build(), gp);
		toConsole("BT", path, solutionOf);
		
		line("*");
	}

}
