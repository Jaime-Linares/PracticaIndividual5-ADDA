package ejercicios.tests.manual;

import java.util.List;

import _datos.DatosCafes;
import _utils.TestsPI5;
import ejercicio1.manual.CafePDR;
import us.lsi.common.String2;

public class TestsEjercicio1PDR {
	
	public static void main(String[] args) {
		System.out.println("* TESTS EJERCICIO 1 - PDR MANUAL *\n");
		TestsPI5.line("*");
		
		List.of(1,2,3).forEach(num_test -> {
			DatosCafes.iniDatos("ficheros/Ejercicio1DatosEntrada"+num_test+".txt");
			String2.toConsole("\n- Solucion obtenida: %s\n", CafePDR.search());
			TestsPI5.line("*");
		});
	}

}
