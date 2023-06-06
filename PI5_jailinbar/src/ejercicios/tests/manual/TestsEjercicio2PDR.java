package ejercicios.tests.manual;

import java.util.List;

import _datos.DatosCursos;
import _utils.TestsPI5;
import ejercicio2.manual.CursosPDR;
import us.lsi.common.String2;

public class TestsEjercicio2PDR {
	
	public static void main(String[] args) {
		System.out.println("* TESTS EJERCICIO 2 - PDR MANUAL *\n");
		TestsPI5.line("*");
		
		List.of(1,2,3).forEach(num_test -> {
			DatosCursos.iniDatos("ficheros/Ejercicio2DatosEntrada"+num_test+".txt");
			String2.toConsole("\n- Solucion obtenida: %s\n", CursosPDR.search());
			TestsPI5.line("*");
		});
	}

}
