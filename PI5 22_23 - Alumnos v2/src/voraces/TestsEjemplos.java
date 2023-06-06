package voraces;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import _datos.DatosAlumnos;
import _datos.DatosMulticonjunto;
import _datos.DatosSubconjuntos;
import _soluciones.SolucionAlumnos;
import _soluciones.SolucionMulticonjunto;
import _soluciones.SolucionSubconjuntos;
import ejemplos.ejemplo1.MulticonjuntoEdge;
import ejemplos.ejemplo1.MulticonjuntoVertex;
import ejemplos.ejemplo2.SubconjuntosEdge;
import ejemplos.ejemplo2.SubconjuntosVertex;
import ejemplos.ejemplo3.AlumnosEdge;
import ejemplos.ejemplo3.AlumnosVertex;
import us.lsi.common.List2;
import us.lsi.common.String2;

// Voraces de forma manual para los ejemplos. Soluciones iterativas y funcionales
public class TestsEjemplos {

	public static void main(String[] args) {
		testVorazE1();
		
		testVorazE2();
		
		// TODO: test voraz ejemplo 3
	}

	// Ejemplo1: Voraz Iterativo Manual. Iterando sobre vertices
	private static void testVorazE1() {
		List.of(1,2,3).forEach(num_test -> {
			DatosMulticonjunto.iniDatos("ficheros/Ejemplo1DatosEntrada"+num_test+".txt");

			List<Integer> path = List2.empty();
			
			MulticonjuntoVertex v = MulticonjuntoVertex.initial();
			Predicate<MulticonjuntoVertex> last = MulticonjuntoVertex.goal();
			Predicate<MulticonjuntoVertex> solution = MulticonjuntoVertex.goalHasSolution();
			while(!solution.test(v) && !last.test(v)) {
				var e = v.greedyEdge();
				path.add(e.action());
				v = e.target();
			}
			String2.toConsole("Voraz Manual: %s\n%s",SolucionMulticonjunto.of(path),String2.linea());
		});		
	}
	
	
	// Ejemplo2: Voraz Funcional Manual. Iterando sobre aristas
	private static void testVorazE2() {
		List.of(1,2).forEach(num_test -> {
			DatosSubconjuntos.iniDatos("ficheros/Ejemplo2DatosEntrada"+num_test+".txt");
			
			SubconjuntosEdge seed = SubconjuntosVertex.initial().greedyEdge();
			Predicate<SubconjuntosVertex> last = SubconjuntosVertex.goal();
			Predicate<SubconjuntosVertex> solution = SubconjuntosVertex.goalHasSolution();
			
			UnaryOperator<SubconjuntosEdge> nxt = e ->
				solution.test(e.target()) || last.test(e.target())? null: e.target().greedyEdge();
			
			List<Integer> path = Stream.iterate(seed, e -> nxt.apply(e))
					.takeWhile(e -> e!=null).map(e -> e.action()).toList();
			
			String2.toConsole("Voraz Manual: %s\n%s",SolucionSubconjuntos.of(path),String2.linea());
		});		
	}

}
