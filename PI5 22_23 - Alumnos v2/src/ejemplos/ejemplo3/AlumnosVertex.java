package ejemplos.ejemplo3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosAlumnos;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

// Uso el segundo modelo
public record AlumnosVertex(Integer index, List<Integer> remaining) 
     implements VirtualVertex<AlumnosVertex,AlumnosEdge,Integer> {

	public static AlumnosVertex of(Integer i, List<Integer> rest) {
		return new AlumnosVertex(i, rest);
	}
	
	public static AlumnosVertex initial() {
		return of(0, List2.ofTam(DatosAlumnos.getTamGrupo(), DatosAlumnos.getNumAlumnos()));
	}
	
	public static Predicate<AlumnosVertex> goal() {
		return x -> x.index() == DatosAlumnos.getNumAlumnos();
	}
	
	public static Predicate<AlumnosVertex> goalHasSolution() {
		return x -> x.remaining().stream().allMatch(e -> e.equals(0));
	}
	
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<>();
		if(index() < DatosAlumnos.getNumAlumnos()) {
			alternativas = IntStream.range(0, DatosAlumnos.getNumGrupos())
					.filter(j -> DatosAlumnos.getAfinidad(index(), j)>0 && remaining().get(j)>0)
					.boxed().toList();
		}
		return alternativas;
	}

	@Override
	public AlumnosVertex neighbor(Integer a) {
		return of(index()+1, List2.setElement(remaining(), a, remaining().get(a)-1));
	}

	@Override
	public AlumnosEdge edge(Integer a) {
		return AlumnosEdge.of(this,this.neighbor(a),a);
	}
	
	// Se explica en practicas.
	public AlumnosEdge greedyEdge() {
		Comparator<Integer> cmp = Comparator.comparing(j -> DatosAlumnos.getAfinidad(index, j));
		
		Integer a = IntStream.range(0, DatosAlumnos.getNumGrupos())
		.filter(j -> DatosAlumnos.getAfinidad(index, j)>0 && remaining.get(j)>0)
		.boxed().max(cmp).orElse(0);
		
		return edge(a);
	}

	@Override
	public String toString() {
		return String.format("%d", this.index);
	}
	
	
}
