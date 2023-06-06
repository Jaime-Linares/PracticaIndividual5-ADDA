package ejercicios.tests;

import java.util.List;

import _datos.DatosInvestigadores;
import _soluciones.SolucionInvestigadores;
import _utils.GraphsPI5;
import _utils.TestsPI5;
import ejercicio3.InvestigadoresVertex;

public class TestsEjercicio3 {
	
	public static void main(String[] args) {
		System.out.println("* TESTS EJERCICIO 3 *");
		TestsPI5.line("_");
		TestsPI5.line("*");
		
		List.of(1,2).forEach(num_test -> {
			TestsPI5.iniTest("Ejercicio3DatosEntrada", num_test, DatosInvestigadores::iniDatos); 
			TestsPI5.tests(
				InvestigadoresVertex.initial(),				// Vertice Inicial
				InvestigadoresVertex.goal(),				// Predicado para un vertice final
				GraphsPI5::investigadoresBuilder,			// Referencia al Builder del grafo
				SolucionInvestigadores::of);				// Referencia al metodo factoria para la solucion
		});
		
	}

}

