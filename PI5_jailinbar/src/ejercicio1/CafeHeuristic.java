package ejercicio1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import _datos.DatosCafes;

public class CafeHeuristic {
	
	// Heuristica: lo mejor que puede pasar en un vertice es que se cojan las cantidades maximas
	// posibles de cada variedad que queda por recorrer 
	public static Double heuristic(CafeVertex v1, Predicate<CafeVertex> goal, CafeVertex v2) {
		Double res = 0.;
		
		for(int i=v1.index(); i<DatosCafes.getNumVariedades(); i++) {
			List<Double> cantidadesMaximasUsables = new ArrayList<>();
			// obtengo una lista con las maxima cantidad que se puede coger de cada cafe
			// en tal variedad siempre y cuando se necesite tal cafe
			for(int j=0; j<DatosCafes.getNumTiposCafe(); j++) {
				Double cantidadCafeDisponible = v1.cantidadesCafes().get(j);
				Double porcentajeCafeEnVariedad = DatosCafes.getPorcentajeCafeKg(i, j);
				if(porcentajeCafeEnVariedad > 0.) {		// si se necesita el cafe en la variedad
					Double cantidadMaxPuedeUsar = cantidadCafeDisponible/porcentajeCafeEnVariedad;
					cantidadesMaximasUsables.add(cantidadMaxPuedeUsar);
				}
			}
			
			// cogemos el minimo de la lista que sera el maximo de kg que podemos coger de la variedad 
			// que estamos recorriendo
			Integer maxPosible = cantidadesMaximasUsables.stream()
					.min(Comparator.naturalOrder()).get().intValue();
			
			// multiplicamos el maximo de kg que podemos coger por el beneficio por kg de tal variedad
			Double beneficio = DatosCafes.getBeneficioVentaKg(i);
			res += maxPosible*beneficio;
		}
					
		return res;
	}

	
}
