package ejercicios.tests;

import java.util.List;

import _datos.DatosCursos;
import _soluciones.SolucionCursos;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicio2.CursosVertex;

public class TestsEjercicio2 {
	
	public static void main(String[] args) {
		System.out.println("* TESTS EJERCICIO 2 *");
		TestsPI5.line("_");
		TestsPI5.line("*");
		
		List.of(1,2,3).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio2DatosEntrada", num_test, DatosCursos::iniDatos); 
			TestsPI5.tests(
				CursosVertex.initial(),				// Vertice Inicial
				CursosVertex.goal(),				// Predicado para un vertice final
				GraphsPI5::cursosBuilder,			// Referencia al Builder del grafo
				SolucionCursos::of);				// Referencia al metodo factoria para la solucion
		});
		
	}

}
