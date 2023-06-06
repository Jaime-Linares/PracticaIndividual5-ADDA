package ejercicios.tests.manual;

import java.util.Comparator;
import java.util.List;

import _datos.DatosInvestigadores;
import _soluciones.SolucionInvestigadores;
import _utils.TestsPI5;
import ejercicio3.manual.InvestigadoresBT;

public class TestsEjercicio3BT {
	
	public static void main(String[] args) {
		System.out.println("* TESTS EJERCICIO 3 - BT MANUAL *\n");
		TestsPI5.line("*");
		
		List.of(1,2).forEach(num_test -> {
			DatosInvestigadores.iniDatos("ficheros/Ejercicio3DatosEntrada"+num_test+".txt");
			InvestigadoresBT.search();
			SolucionInvestigadores sol = InvestigadoresBT.getSoluciones().stream().max(Comparator.comparing(SolucionInvestigadores::getGoal)).orElse(null);
			String res = "\n- Solucion obtenida: "+  sol + "\n";
			System.out.println(res);
			TestsPI5.line("*");
		});
	}

}
