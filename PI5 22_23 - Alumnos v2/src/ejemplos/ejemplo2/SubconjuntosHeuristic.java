package ejemplos.ejemplo2;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosSubconjuntos;
import us.lsi.common.List2;

public class SubconjuntosHeuristic {
	
	// Se explica en practicas.
	// Lo mas optimista es que SI todavia quedan por cubrir es coger el que tenga menor peso
	public static Double heuristic(SubconjuntosVertex v1, Predicate<SubconjuntosVertex> goal, SubconjuntosVertex v2) {
		return v1.remaining().isEmpty()? 0.: 
			IntStream.range(v1.index(), DatosSubconjuntos.getNumSubconjuntos())
			.filter(i -> !List2.intersection(v1.remaining(), 
					DatosSubconjuntos.getElementos(i)).isEmpty())
			// penalizacion en el estilo genetico con el OrElse
			.mapToDouble(i -> DatosSubconjuntos.getPeso(i)).min().orElse(100.);
	}	

}

