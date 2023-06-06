package ejemplos.ejemplo1.manual;

import java.util.List;
import java.util.stream.IntStream;

import _datos.DatosMulticonjunto;
import us.lsi.common.List2;

public record MulticonjuntoProblem(Integer index, Integer remaining) {
	
	public static MulticonjuntoProblem of(Integer i, Integer rest) {
		return new MulticonjuntoProblem(i, rest);
	}
	
	public static MulticonjuntoProblem initial() {
		return of(0, DatosMulticonjunto.getSuma());
	}
	
	public List<Integer> actions() {
		List<Integer> alternativas= List2.empty();
		if(index<DatosMulticonjunto.getNumElementos()) {
			Integer value= DatosMulticonjunto.getElemento(index);
			Integer options= remaining/value;
			if(index==DatosMulticonjunto.getNumElementos()-1) {
				if(remaining%value==0) {
					alternativas= List2.of(remaining/value);
				} else {
					alternativas= List2.of(0);
				}
			} else {
				alternativas=List2.rangeList(0, options+1);
			}
		}
		return alternativas;
	}
	
	public MulticonjuntoProblem neighbor(Integer a) {
		return of(index+1, remaining-a*DatosMulticonjunto.getElemento(index));
	}
	
	// Lo mas optimista: si todavia falta, con el mayor de los que quedan puede ser
	public Double heuristic() {
		Double res = 0.;
		if (remaining > 0) {
			Integer max = IntStream.range(index, DatosMulticonjunto.getNumElementos())
					.map(i -> DatosMulticonjunto.getElemento(i)).filter(e -> e <= remaining).max().orElse(0);
			if (max > 0) {
				Integer r = remaining() / max;
				res += remaining() % max == 0 ? r : r + 1;
				// A PARTIR DE AQUI ES EXTRA, NO SE TENDRIA PORQUE HACER
			} else { // Penalizamos estilo generico
				res += 100;
			}
		} else if (remaining < 0) { // Penalizamos estilo generico
			res += 100;
		}

		return res;
	}

}
