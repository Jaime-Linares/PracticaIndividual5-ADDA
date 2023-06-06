package ejercicios.tests;

import java.util.List;

import _datos.DatosRepartidor;
import _soluciones.SolucionRepartidor;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicio4.RepartidorVertex;

public class TestsEjercicio4 {
	
	public static void main(String[] args) {
		System.out.println("* TESTS EJERCICIO 4 *");
		TestsPI5.line("_");
		TestsPI5.line("*");
		
		List.of(1,2).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio4DatosEntrada", num_test, DatosRepartidor::iniDatos); 
			TestsPI5.tests(
				RepartidorVertex.initial(),				// Vertice Inicial
				RepartidorVertex.goal(),				// Predicado para un vertice final
				GraphsPI5::repartidorBuilder,			// Referencia al Builder del grafo
				SolucionRepartidor::of);				// Referencia al metodo factoria para la solucion
		});
		
	}

}
