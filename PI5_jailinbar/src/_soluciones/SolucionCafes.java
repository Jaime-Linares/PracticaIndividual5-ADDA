package _soluciones;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;

import _datos.DatosCafes;
import ejercicio1.CafeEdge;
import ejercicio1.CafeVertex;

public class SolucionCafes {
	
	// Propiedades
	private Double beneficio;
	private Map<String,Integer> solucion;
	private List<Integer> path;
	
	
	// Metodos de factoria
	public static SolucionCafes of(List<Integer> ls) {
		return new SolucionCafes(ls);
	}
	
	public static SolucionCafes of(GraphPath<CafeVertex,CafeEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(x -> x.action()).toList();
		SolucionCafes res = of(ls);
		res.path = ls;
		return res;
	}
	
	
	// Constructor
	private SolucionCafes(List<Integer> ls) {
		Double beneficioTotal = 0.;
		Map<String,Integer> mp = new HashMap<>();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i) > 0) {
				beneficioTotal += ls.get(i) * DatosCafes.getBeneficioVentaKg(i);
				mp.put(DatosCafes.getVariedades().get(i).nombre(), ls.get(i));
			}
		}
		
		beneficio = beneficioTotal; 
		solucion = mp;
	}
		
	// Metodo toString que devuelve una cadena con los kg de cada variedad y el beneficio final obtenido
	@Override
	public String toString() {
		String res = String.format("\nVariedades = %s; Beneficio = %f;", this.solucion, this.beneficio);
		return path==null? res: String.format("%s\nPath de la solucion = %s", res, this.path);
	}

}
