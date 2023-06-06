package ejercicios.tests;

import java.util.List;

import _datos.DatosCafes;
import _soluciones.SolucionCafes;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicio1.CafeVertex;

public class TestsEjercicio1 {
	
	public static void main(String[] args) {
		System.out.println("* TESTS EJERCICIO 1 *");
		TestsPI5.line("_");
		TestsPI5.line("*");
		
		List.of(1,2,3).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio1DatosEntrada", num_test, DatosCafes::iniDatos); 
			TestsPI5.tests(
				CafeVertex.initial(),				// Vertice Inicial
				CafeVertex.goal(),					// Predicado para un vertice final
				GraphsPI5::cafeBuilder,				// Referencia al Builder del grafo
				SolucionCafes::of);					// Referencia al metodo factoria para la solucion
		});
		
	}

}
