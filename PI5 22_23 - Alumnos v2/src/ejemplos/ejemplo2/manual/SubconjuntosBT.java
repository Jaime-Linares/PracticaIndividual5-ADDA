package ejemplos.ejemplo2.manual;

import java.util.Set;

import _soluciones.SolucionSubconjuntos;
import us.lsi.common.Set2;

public class SubconjuntosBT {

	private static Double mejorValor;
	private static SubconjuntosState estado;
	private static Set<SolucionSubconjuntos> soluciones;
	
	public static void search() {
		soluciones = Set2.newTreeSet();
		mejorValor = Double.MAX_VALUE; // Estamos minimizando
		estado = SubconjuntosState.initial();
		bt_search();
	}

	private static void bt_search() {
		if (estado.esSolucion()) {
			Double valorObtenido = estado.acumulado;
			if (valorObtenido < mejorValor) {  // Estamos minimizando
				mejorValor = valorObtenido;
				soluciones.add(estado.getSolucion());
			}
		} else if(!estado.esTerminal()){
			// a partir de aqui se puede reutilizar en todos los codigos
			for (Integer a: estado.alternativas()) {
				if (estado.cota(a) <= mejorValor) {  // Estamos minimizando
					estado.forward(a);
					bt_search();
					estado.back();
				}
			}
		}
	}

	public static Set<SolucionSubconjuntos> getSoluciones() {
		return soluciones;
	}

}
