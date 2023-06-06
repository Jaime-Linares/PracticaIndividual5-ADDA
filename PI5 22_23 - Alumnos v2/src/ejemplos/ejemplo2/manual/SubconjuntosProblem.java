package ejemplos.ejemplo2.manual;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import _datos.DatosSubconjuntos;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record SubconjuntosProblem(Integer index, Set<Integer> remaining) {
	
	public static SubconjuntosProblem of(Integer i, Set<Integer> rest) {
		return new SubconjuntosProblem(i, rest);
	}
	
	public static SubconjuntosProblem initial() {
		return of(0, Set2.copy(DatosSubconjuntos.getUniverso()));
	}
	
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		if(index<DatosSubconjuntos.getNumSubconjuntos()) {	// si ya hemos recorrido los subconjuntos no hay alternativas
			if(remaining.isEmpty()) {	// si ya hemos cogido todo el universo, no cogemos ningun subconjunto (menor peso)
				alternativas = List2.of(0);
			} else {	
				Set<Integer> rest = Set2.difference(remaining, DatosSubconjuntos.getElementos(index));
				if(rest.equals(remaining)) {	// si no se modifica aunque cojamos el subconjunto pues no lo cogemos
					alternativas = List2.of(0);
				} else if(index==DatosSubconjuntos.getNumElementos()-1) {
				// si estamos al final y no es solucion cogerlo pues no hay alternativas, si es solucion pues lo cogemos
					alternativas = rest.isEmpty()? List2.of(1): List2.empty();
				} else {	// sino lo normal es cogerlo o no cogerlo
					alternativas = List2.of(0, 1);
				}
			}
		}	
		return alternativas;
	}
	
	public SubconjuntosProblem neighbor(Integer a) {
		Set<Integer> rest = a==0? Set2.copy(remaining):
			Set2.difference(remaining, DatosSubconjuntos.getElementos(index));
		return of(index+1, rest);
	}
	
	// Lo mas optimista es que SI todavia quedan por cubrir es coger el que tenga
	// menor peso
	public Double heuristic() {
		return remaining.isEmpty() ? 0.
				: IntStream.range(index, DatosSubconjuntos.getNumSubconjuntos())
				.filter(i -> !List2.intersection(remaining, DatosSubconjuntos.getElementos(i)).isEmpty())
				// penalizacion en el estilo genetico con el OrElse
				.mapToDouble(i -> DatosSubconjuntos.getPeso(i)).min().orElse(100.);
	}

}
