package ejemplos.tests;

import java.util.List;

import _datos.DatosAlumnos;
import _soluciones.SolucionAlumnos;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejemplos.ejemplo3.AlumnosVertex;

//Clase para todos los tests del ejemplo 3 mediante Greedy, A*, PDR y BT 
public class TestsEjemplo3 {


	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			TestsPI5.iniTest("Ejemplo3DatosEntrada", num_test, DatosAlumnos::iniDatos);
			
			TestsPI5.tests(
					AlumnosVertex.initial(),		// Vertice inicial
					AlumnosVertex.goal(), 			// Predicado para un vertice final
					GraphsPI5::alumnosBuilder,		// Referencia al Buider del grafo
					AlumnosVertex::greedyEdge,		// Referencia a la funcion para la arista voraz
					SolucionAlumnos::of);			// Referencia al metodo de facotria para la solucion
		});
	}


}
