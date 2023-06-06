package ejemplos.ejemplo2;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import _datos.DatosSubconjuntos;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record SubconjuntosVertex(Integer index, Set<Integer> remaining) 
    implements VirtualVertex<SubconjuntosVertex, SubconjuntosEdge, Integer>{
	
	public static SubconjuntosVertex of(Integer i, Set<Integer> rest) {
		return new SubconjuntosVertex(i, rest);
	}
	
	// TODO Consulte las clases GraphsPI5 y TestPI5 
	public static SubconjuntosVertex initial() {
		return of(0, Set2.copy(DatosSubconjuntos.getUniverso()));
	}
	
	public static Predicate<SubconjuntosVertex> goal() {
		return v -> v.index() == DatosSubconjuntos.getNumSubconjuntos();
	}
	
	public static Predicate<SubconjuntosVertex> goalHasSolution() {
		return v -> v.remaining().isEmpty();
	}
	
	@Override
	public List<Integer> actions() {
		// TODO Alternativas de un vertice 
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
	
	@Override
	public SubconjuntosVertex neighbor(Integer a) {
		// TODO Vertice siguiente al actual segun la alternativa a
		Set<Integer> rest = a==0? Set2.copy(remaining):
			Set2.difference(remaining, DatosSubconjuntos.getElementos(index));
		return of(index+1, rest);
	}
	
	@Override
	public SubconjuntosEdge edge(Integer a) {
		return SubconjuntosEdge.of(this, neighbor(a), a);
	}
	
	// Se explica en practicas.
	public SubconjuntosEdge greedyEdge() {
		Set<Integer> rest = Set2.difference(remaining, DatosSubconjuntos.getElementos(index));
		return rest.equals(remaining)? edge(0): edge(1);
	}
	
	@Override
	public String toString() {
		return String.format("%d, %d", index, remaining.size());
	}

}
