package ejemplos.ejemplo1;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosMulticonjunto;

public class MulticonjuntoHeuristic {
	
	// Se explica en practicas.
	// Lo mas optimista: si todavia falta, con el mayor de los que quedan puede ser
	public static Double heuristic(MulticonjuntoVertex v1, Predicate<MulticonjuntoVertex> goal,
			MulticonjuntoVertex v2) {
		
		Double res = 0.;
		if(v1.remaining()>0) {
			Integer max = IntStream.range(v1.index(), DatosMulticonjunto.getNumElementos())
					.map(i -> DatosMulticonjunto.getElemento(i))
					.filter(e -> e<=v1.remaining()).max().orElse(0);
			if(max>0) {
				Integer r = v1.remaining()/max;
				res += v1.remaining()%max==0? r: r+1;	
			// A PARTIR DE AQUI ES EXTRA, NO SE TENDRIA PORQUE HACER
			} else {	// Penalizamos estilo generico
				res += 100;
			}
		} else if(v1.remaining()<0) {	// Penalizamos estilo generico
			res += 100;
		}
		
		return res; 
	}
	
	
}
