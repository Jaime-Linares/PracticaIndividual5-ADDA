package ejercicio2.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import _datos.DatosCursos;
import _soluciones.SolucionCursos;

public class CursosPDR {
	
	// Creamos el tipo que sera la solucion parcial, que es el par formado por la alternativa
	// elegida y el valor de la propiedad a optimizar
	public static record Spm(Integer action, Double weight) implements Comparable<Spm> {
		public static Spm of(Integer action, Double weight) {
			return new Spm(action, weight);
		}

		@Override
		public int compareTo(Spm o) {
			return this.weight.compareTo(o.weight);
		}
	}
	
	
	// Propiedades 
	public static Map<CursosProblem,Spm> memory;
	public static Double mejorValor;
	
	
	// Funcion que se encarga de obtener la solucion optima
	public static SolucionCursos search() {
		memory = new HashMap<>();
		mejorValor = Double.MAX_VALUE;	// estamos minimizando
		pdr_search(CursosProblem.initial(), 0., memory);	// llamada al algoritmo
		return getSolucion();
	}


	// Funcion que es basicamente el algoritmo
	private static Spm pdr_search(CursosProblem prob, Double acumulado, Map<CursosProblem, Spm> memoria) {
		Spm res;
		Boolean esTerminal = prob.index() == DatosCursos.getNumCursos(); 
		Boolean esSolucion = prob.tematicasRestantes().isEmpty() &&
				prob.centrosUtilizados().size() <= DatosCursos.getMaxCentros();
		
		if(memory.containsKey(prob)) {		// comprobamos si se encuentra en memoria
			res = memory.get(prob);
			
		} else if(esTerminal && esSolucion) {	// si es terminal y solucion...
			res = Spm.of(null, 0.);
			memory.put(prob, res);
			if(acumulado < mejorValor) {	// porque estamos minimizando
				mejorValor = acumulado;
			}
			
		} else {	// si no es terminal ni solucion se encuentra en memoria...
			List<Spm> soluciones = new ArrayList<>();
			for(Integer accion: prob.actions()) {
				Double cota = acotar(acumulado, prob, accion);
				if(cota > mejorValor) {
					continue;	
				}
				CursosProblem vecino = prob.neighbor(accion);
				Spm s = pdr_search(vecino, acumulado + accion*DatosCursos.getPrecioInscripcion(prob.index()), memory);
				if(s != null) {
					Spm amp = Spm.of(accion, s.weight() + accion*DatosCursos.getPrecioInscripcion(prob.index()));
					soluciones.add(amp);
				}				
			}
			// estamos minimizando
			res = soluciones.stream()
					.min(Comparator.naturalOrder()).orElse(null);
			if(res != null) {
				memory.put(prob, res);
			}
		}
		
		return res;
	}
	
	
	// Funcion acotar: sirve para saber que es lo mejor que puede pasar desde un vertice 
	// tomando la alternativa a
	private static Double acotar(Double acum, CursosProblem p, Integer a) {
		return acum + a*DatosCursos.getPrecioInscripcion(p.index()) + p.neighbor(a).heuristic();
	}
	
	
	// Funcion que te da la solucion tras aplicar el algoritmo
	public static SolucionCursos getSolucion() {
		List<Integer> acciones = new ArrayList<>();
		CursosProblem prob = CursosProblem.initial(); 
		Spm spm = memory.get(prob);
		while(spm != null && spm.action != null) {
			CursosProblem old = prob;
			acciones.add(spm.action);
			prob = old.neighbor(spm.action);
			spm = memory.get(prob);
		}
		return SolucionCursos.of(acciones);
	}
	

}
