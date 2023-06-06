package ejercicio1.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import _datos.DatosCafes;
import _soluciones.SolucionCafes;

public class CafePDR {
	
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
	public static Map<CafeProblem,Spm> memory;
	public static Double mejorValor;
	
	
	// Funcion que se encarga de obtener la solucion optima
	public static SolucionCafes search() {
		memory = new HashMap<>();
		mejorValor = 0.;  // estamos maximizando
		pdr_search(CafeProblem.initial(), 0., memory);	// llamada al algoritmo
		return getSolucion();
	}
	

	// Funcion que es basicamente el algortimo
	private static Spm pdr_search(CafeProblem prob, Double acumulado, Map<CafeProblem,Spm> memoria) {
		Spm res;
		Boolean esTerminal = prob.index() == DatosCafes.getNumVariedades();
		
		if(memory.containsKey(prob)) {		// comprobamos si se encuentra en memoria
			res = memory.get(prob);
			
		} else if(esTerminal) {				// si es terminal...
			res = Spm.of(null, 0.);
			memory.put(prob, res);
			if(acumulado > mejorValor) {  // porque estamos maximizando
				mejorValor = acumulado;
			}
			
		} else {							// si no es terminal ni se encuentra en memoria...
			List<Spm> soluciones = new ArrayList<>();
			for(Integer accion: prob.actions()) {
				Double cota = acotar(acumulado, prob, accion);
				if(cota < mejorValor) {	
					continue; 
				}
				CafeProblem vecino = prob.neighbor(accion); 
				Spm s = pdr_search(vecino, acumulado + accion*DatosCafes.getBeneficioVentaKg(prob.index()), memory);
				if(s != null) {
					Spm amp = Spm.of(accion, s.weight() + accion*DatosCafes.getBeneficioVentaKg(prob.index()));
					soluciones.add(amp);
				}
			}
			// estamos maximizando
			res = soluciones.stream()
					.max(Comparator.naturalOrder()).orElse(null);
			if(res != null) {
				memory.put(prob, res);
			}			
		}		
		
		return res;
	}
	
	
	// Funcion acotar: sirve para saber que es lo mejor que puede pasar desde un vertice 
	// tomando la alternativa a
	private static Double acotar(Double acum, CafeProblem p, Integer a) {
		return acum + a*DatosCafes.getBeneficioVentaKg(p.index()) + p.neighbor(a).heuristic();
	}
	
	
	// Funcion que te da la solucion tras aplicar el algoritmo
	public static SolucionCafes getSolucion() {
		List<Integer> acciones = new ArrayList<>();
		CafeProblem prob = CafeProblem.initial();
		Spm spm = memory.get(prob);
		while (spm != null && spm.action != null) {
			CafeProblem old = prob;
			acciones.add(spm.action);
			prob = old.neighbor(spm.action);
			spm = memory.get(prob);
		}		
		return SolucionCafes.of(acciones);
	}
		

}
