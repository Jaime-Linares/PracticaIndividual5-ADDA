package ejemplos.tests.manual;

import java.util.List;

import _datos.DatosSubconjuntos;
import _utils.TestsPI5;
import ejemplos.ejemplo2.manual.SubconjuntosBT;
import us.lsi.common.String2;

public class TestsEM2 {

	public static void main(String[] args) {
		List.of(1,2).forEach(num_test -> {
			DatosSubconjuntos.iniDatos("ficheros/Ejemplo2DatosEntrada"+num_test+".txt");
			SubconjuntosBT.search();
			SubconjuntosBT.getSoluciones().forEach(s -> String2.toConsole("Solucion obtenida: %s\n", s));
			TestsPI5.line("*");
		});
	}
	
}
