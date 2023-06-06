package ejercicio4.manual;

import java.util.HashSet;
import java.util.Set;

import _soluciones.SolucionRepartidor;

public class RepartidorBT {
	
	// Propiedades
	private static Double mejorValor;
	private static RepartidorState estado;
	private static Set<SolucionRepartidor> soluciones;
	
	
	// Funcion que da los valores iniciales y llama al algoritmo en si
	public static void search() {
		soluciones = new HashSet<>();
		mejorValor = 0.;  // estamos maximizando
		estado = RepartidorState.initial();
		bt_search();
	}

	
	// Funcion que es basicamente el algoritmo
	private static void bt_search() {
		if (estado.esSolucion()) {
			Double valorObtenido = estado.acumulado;
			if (valorObtenido > mejorValor) {  		// estamos maximizando
				mejorValor = valorObtenido;
				soluciones.add(estado.getSolucion());
			}
		} else if(!estado.esTerminal()){
			for (Integer a: estado.alternativas()) {
				if (estado.cota(a) >= mejorValor) {  	// estamos maximizando
					estado.forward(a);
					bt_search();
					estado.back();
				}
			}
		}
	}
	
	
	// Funcion que devuelve las soluciones del problema
	public static Set<SolucionRepartidor> getSoluciones() {
		return soluciones;
	}

}
