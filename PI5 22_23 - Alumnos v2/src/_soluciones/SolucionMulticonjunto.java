package _soluciones;

import java.util.List;

import org.jgrapht.GraphPath;

import _datos.DatosMulticonjunto;
import ejemplos.ejemplo1.MulticonjuntoEdge;
import ejemplos.ejemplo1.MulticonjuntoVertex;
import us.lsi.common.Multiset;

public class SolucionMulticonjunto {
	// De la PI4
	public static SolucionMulticonjunto of(List<Integer> ls) {
		return new SolucionMulticonjunto(ls);
	}
	
	// Ahora en la PI5
	public static SolucionMulticonjunto of(GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionMulticonjunto res = of(ls);
		res.path = ls;
		return res;
	}
	
	// De la PI4
	private Integer suma, tam;
	private Multiset<Integer> solucion;

	// Ahora en la PI5
	private List<Integer> path;
	
	private SolucionMulticonjunto(List<Integer> ls) {
		suma = tam  = 0;
		solucion = Multiset.of();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)>0) {				
				Integer e = ls.get(i);
				Integer v = DatosMulticonjunto.getElemento(i);
				tam += e;
				solucion.add(v, e);
				suma += v*e;
			}
		}
	}
	
	// Ahora en la PI5
	@Override
	public String toString() {
		int error = Math.abs(DatosMulticonjunto.getSuma() - suma);
		String e = error<1? "": String.format("Error = %d", error);
		String res = String.format("MS = %s; Distintos = %d; Total = %d; %s", solucion, solucion.size(), tam, e);
		return path==null? res: String.format("%s\nPath de la solucion: %s", res, path);
	}

}
