package ejemplos.tests;

import java.util.List;
import _datos.DatosSubconjuntos;
import _soluciones.SolucionSubconjuntos;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejemplos.ejemplo2.SubconjuntosVertex;

public class TestsEjemplo2 {

	public static void main(String[] args) {
		List.of(1,2).forEach(num_test -> {
			TestsPI5.iniTest("Ejemplo2DatosEntrada", num_test, DatosSubconjuntos::iniDatos);
			
			// TODO Defina en el tipo vertice un m. factoria para el vertice inicial
			// TODO Defina en el tipo vertice un m. static para los vertices finales 
			TestsPI5.tests(
				SubconjuntosVertex.initial(),		// Vertice Inicial
				SubconjuntosVertex.goal(),			// Predicado para un vertice final
				GraphsPI5::subsetBuilder,			// Referencia al Builder del grafo
				SubconjuntosVertex::greedyEdge,		// Referencia a la Funcion para la arista voraz
				SolucionSubconjuntos::of);			// Referencia al metodo factoria para la solucion
		});
	}

}
