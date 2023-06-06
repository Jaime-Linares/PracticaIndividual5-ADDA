package _soluciones;

import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import _datos.DatosSubconjuntos;
import _datos.DatosSubconjuntos.Subconjunto;
import ejemplos.ejemplo2.SubconjuntosEdge;
import ejemplos.ejemplo2.SubconjuntosVertex;
import us.lsi.common.List2;

// Le damos orden natural para mostrar, segun vo, las soluciones del BT manual
public class SolucionSubconjuntos implements Comparable<SolucionSubconjuntos>{
	
	public static SolucionSubconjuntos of(List<Integer> ls) {
		return new SolucionSubconjuntos(ls);
	}
	
	// Ahora en la PI5
	public static SolucionSubconjuntos of(GraphPath<SubconjuntosVertex, SubconjuntosEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionSubconjuntos res = of(ls);
		res.path = ls;
		return res;
	}
	
	private Double total;
	private List<Subconjunto> subconjuntos;
	
	// Ahora en la PI5
	private List<Integer> path;

	private SolucionSubconjuntos(List<Integer> ls) {
		total = 0.;
		subconjuntos = List2.empty();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)>0) {
				total += DatosSubconjuntos.getPeso(i);
				subconjuntos.add(DatosSubconjuntos.getSubConjunto(i));
			}
		}
	}
	
	// Ahora en la PI5
	@Override
	public String toString() {
		String s = subconjuntos.stream().map(e -> "S"+e.id())
		.collect(Collectors.joining(", ", "Subconjuntos elegidos: {", "}\n"));
		String res = String.format("%sPeso Total: %.1f", s, total);	
		return path==null? res: String.format("%s\nPath de la solucion: %s", res, path);
	}

	@Override
	public int compareTo(SolucionSubconjuntos s) {
		return total.compareTo(s.total);
	}
}
