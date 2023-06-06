package ejercicio1.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import _datos.DatosCafes;
import us.lsi.common.List2;

public record CafeProblem(Integer index, List<Double> cantidadesCafes) {
	
	// Metodo de factoria
	public static CafeProblem of(Integer index, List<Double> cantidadesCafes) {
		return new CafeProblem(index, cantidadesCafes);
	}

	
	// Vertice inicial: indice sera 0 y las cantidades de cafe sera una lista donde
	// cada posicion correspondera a la cantidad de cafe disponible incialmente
	public static CafeProblem initial() {
		List<Double> cantidadesInicialCafe = new ArrayList<>();
		for (int j = 0; j < DatosCafes.getNumTiposCafe(); j++) {
			Double cantidadCafe = DatosCafes.getCantidadCafe(j) + 0.;
			cantidadesInicialCafe.add(j, cantidadCafe);
		}
		return of(0, cantidadesInicialCafe);
	}
	
	
	// Alternativas a las que podemos ir desde ese vertice, es decir, el rango de cantidad de
	// kg de la variedad podemos coger
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<>();

		// en caso de que se hayan recorrido todas las variedades no habra alternativas
		if (index() < DatosCafes.getNumVariedades()) {

			// obtengo una lista con las maxima cantidad que se puede coger de cada cafe
			// en tal variedad siempre y cuando se necesite tal cafe
			List<Double> cantidadesMaximasUsables = new ArrayList<>();
			for (int j = 0; j < DatosCafes.getNumTiposCafe(); j++) {
				Double cantidadDisponibleCafe = cantidadesCafes().get(j);
				Double porcentajeCafeEnVariedad = DatosCafes.getPorcentajeCafeKg(index(), j);
				if (porcentajeCafeEnVariedad > 0.) {    // si se necesita el cafe en la variedad
					Double cantidadMaxPuedeUsar = cantidadDisponibleCafe / porcentajeCafeEnVariedad;
					cantidadesMaximasUsables.add(cantidadMaxPuedeUsar);
				}
			}

			// si ocurre que estamos en la ultima variedad cogemos el maximo que podemos coger,
			// es decir, el minimo de la lista
			Integer alternativa = cantidadesMaximasUsables.stream().min(Comparator.naturalOrder()).orElse(0.)
					.intValue();
			if (index().equals(DatosCafes.getNumVariedades() - 1)) {
				alternativas = List2.of(alternativa);
			} else { 				// sino alternativas = [minimo de la lista .. 0]
				alternativas = List2.rangeList(0, alternativa + 1); 	// +1 porque es rango abierto
			}
		}

		return alternativas;
	}
	
	
	// Vertice vecino al que vamos, es decir, aumentar el indice y restar a la cantidad de cafe
	// disponible el numero de variedades por cantidad de kg necesarios si ese tipo de cafe es
	// necesario en tal variedad
	public CafeProblem neighbor(Integer a) {
		List<Double> listaCantidades = List2.copy(cantidadesCafes());

		for (int j = 0; j < DatosCafes.getNumTiposCafe(); j++) {
			Double cantidadInicial = listaCantidades.get(j);
			Double porcentajeCafeEnVariedad = DatosCafes.getPorcentajeCafeKg(index(), j);
			Double cantidadAEliminar = a * porcentajeCafeEnVariedad;
			if (cantidadAEliminar > 0.) {
				Double cantidadFinal = cantidadInicial - cantidadAEliminar;
				listaCantidades.set(j, cantidadFinal);
			}
		}

		return of(index() + 1, listaCantidades);
	}
	
	
	// Heuristica: lo mejor que puede pasar en un vertice es que se cojan las cantidades maximas
	// posibles de cada variedad que queda por recorrer
	public Double heuristic() {
		Double res = 0.;
		for (int i = index(); i < DatosCafes.getNumVariedades(); i++) {
			List<Double> cantidadesMaximasUsables = new ArrayList<>();
			// obtengo una lista con las maxima cantidad que se puede coger de cada cafe
			// en tal variedad siempre y cuando se necesite tal cafe
			for (int j = 0; j < DatosCafes.getNumTiposCafe(); j++) {
				Double cantidadCafeDisponible = cantidadesCafes().get(j);
				Double porcentajeCafeEnVariedad = DatosCafes.getPorcentajeCafeKg(i, j);
				if (porcentajeCafeEnVariedad > 0.) { 	// si se necesita el cafe en la variedad
					Double cantidadMaxPuedeUsar = cantidadCafeDisponible / porcentajeCafeEnVariedad;
					cantidadesMaximasUsables.add(cantidadMaxPuedeUsar);
				}
			}

			// cogemos el minimo de la lista que sera el maximo de kg que podemos coger de la variedad
			// que estamos recorriendo
			Integer maxPosible = cantidadesMaximasUsables.stream()
					.min(Comparator.naturalOrder()).get().intValue();

			// multiplicamos el maximo de kg que podemos coger por el beneficio por kg de tal variedad
			Double beneficio = DatosCafes.getBeneficioVentaKg(i);
			res += maxPosible * beneficio;
		}

		return res;
	}

	
}
