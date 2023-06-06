package _soluciones;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;

import _datos.DatosCursos;
import ejercicio2.CursosEdge;
import ejercicio2.CursosVertex;

public class SolucionCursos {
	
	// Propiedades
	private Double coste;
	private List<String> cursos;
	private List<Integer> path;
	
	
	// Metodos de factoria
	public static SolucionCursos of(List<Integer> ls) {
		return new SolucionCursos(ls);
	}
	
	public static SolucionCursos of(GraphPath<CursosVertex, CursosEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(x -> x.action()).toList();
		SolucionCursos res = of(ls);
		res.path = ls;		
		return res;		
	}
	
	
	// Constructor
	public SolucionCursos(List<Integer> ls) {
		Double costeTotal = 0.;
		List<String> cursosElegidos = new ArrayList<>();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i) > 0) {
				costeTotal += DatosCursos.getPrecioInscripcion(i);
				String curso = DatosCursos.getCursos().get(i).id();
				cursosElegidos.add(curso);
			}
		}
		
		coste = costeTotal;
		cursos = cursosElegidos;
	}

	
	// Metodo toString que devuelve una cadena con los cursos y el coste total de elegir esos cursos
	@Override
	public String toString() {
		String res = String.format("\nCursos seleccionados = %s; Coste = %f;", 
				this.cursos, this.coste);
		return path==null? res: String.format("%s\nPath de la solucion = %s", res, this.path);
	}

}
