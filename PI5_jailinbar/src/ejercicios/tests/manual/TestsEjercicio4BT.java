package ejercicios.tests.manual;

import java.util.Comparator;
import java.util.List;

import _datos.DatosRepartidor;
import _soluciones.SolucionRepartidor;
import _utils.TestsPI5;
import ejercicio4.manual.RepartidorBT;

public class TestsEjercicio4BT {
	
	public static void main(String[] args) {
		System.out.println("* TESTS EJERCICIO 4 - BT MANUAL *\n");
		TestsPI5.line("*");
		
		List.of(1,2).forEach(num_test -> {
			DatosRepartidor.iniDatos("ficheros/Ejercicio4DatosEntrada"+num_test+".txt");
			RepartidorBT.search();
			SolucionRepartidor sol = RepartidorBT.getSoluciones().stream().max(Comparator.comparing(SolucionRepartidor::getBeneficio)).orElse(null);
			String res = "\n- Solucion obtenida: "+  sol + "\n";
			System.out.println(res);
			TestsPI5.line("*");
		});
	}

}
