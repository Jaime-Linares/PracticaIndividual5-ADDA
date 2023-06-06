package ejercicio1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import _datos.DatosCafes;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CafeVertex(Integer index, List<Double> cantidadesCafes) 
		implements VirtualVertex<CafeVertex, CafeEdge, Integer> {
	
	// Metodo de factoria
	public static CafeVertex of(Integer index, List<Double> cantidadesCafes) {
		return new CafeVertex(index, cantidadesCafes);
	}
	
	
	// Vertice inicial: indice sera 0 y las cantidades de cafe sera una lista donde
	// cada posicion correspondera a la cantidad de cafe disponible incialmente
	public static CafeVertex initial() {
		List<Double> cantidadesInicialCafe = new ArrayList<>();
		for(int j=0; j<DatosCafes.getNumTiposCafe(); j++) {
			Double cantidadCafe = DatosCafes.getCantidadCafe(j) + 0.;
			cantidadesInicialCafe.add(j, cantidadCafe);
		}
		return of(0, cantidadesInicialCafe);
	}
	
	
	// goal: predicado el cual es verdadero si ya hemos recorrido todas las variedades
	public static Predicate<CafeVertex> goal() {
		return x -> x.index() == DatosCafes.getNumVariedades();
	}
	
	
	// goalHasSolution: predicado el cual es verdadero si ya hemos recorrido todas
	// las variedades
	public static Predicate<CafeVertex> goalHasSolution() {
		return x -> x.index() == DatosCafes.getNumVariedades();
	}
	
	
	// Alternativas a las que podemos ir desde ese vertice, es decir, el rango de cantidad de
	// kg de la variedad podemos coger
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<>();
		
		// en caso de que se hayan recorrido todas las variedades no habra alternativas
		if(index() < DatosCafes.getNumVariedades()) {
			
			// obtengo una lista con las maxima cantidad que se puede coger de cada cafe
			// en tal variedad siempre y cuando se necesite tal cafe
			List<Double> cantidadesMaximasUsables = new ArrayList<>();
			for(int j=0; j<DatosCafes.getNumTiposCafe(); j++) {
				Double cantidadDisponibleCafe = cantidadesCafes().get(j);
				Double porcentajeCafeEnVariedad = DatosCafes.getPorcentajeCafeKg(index(), j); 
				if(porcentajeCafeEnVariedad > 0.) {		// si se necesita el cafe en la variedad
					Double cantidadMaxPuedeUsar = cantidadDisponibleCafe/porcentajeCafeEnVariedad;
					cantidadesMaximasUsables.add(cantidadMaxPuedeUsar);
				}
			}
			
			// si ocurre que estamos en la ultima variedad cogemos el maximo que podemos coger,
			// es decir, el minimo de la lista
			Integer alternativa = cantidadesMaximasUsables.stream()
					.min(Comparator.naturalOrder()).orElse(0.).intValue();
			if(index().equals(DatosCafes.getNumVariedades()-1)) {
				alternativas = List2.of(alternativa);
			} else {	// sino alternativas = [minimo de la lista .. 0] 
				alternativas = List2.rangeList(0, alternativa+1);	// +1 porque es rango abierto
			}
		}
			
		return alternativas;
	}

	
	// Vertice vecino al que vamos, es decir, aumentar el indice y restar a la cantidad de cafe
	// disponible el numero de variedades por cantidad de kg necesarios si ese tipo de cafe es
	// necesario en tal variedad
	@Override
	public CafeVertex neighbor(Integer a) {
		List<Double> listaCantidades = List2.copy(cantidadesCafes());
		
		for(int j=0; j<DatosCafes.getNumTiposCafe(); j++) {
			Double cantidadInicial = listaCantidades.get(j);
			Double porcentajeCafeEnVariedad = DatosCafes.getPorcentajeCafeKg(index(), j);
			Double cantidadAEliminar = a*porcentajeCafeEnVariedad;
			if (cantidadAEliminar > 0.) {
				Double cantidadFinal = cantidadInicial - cantidadAEliminar;
				listaCantidades.set(j, cantidadFinal);
			}
		}
		
		return of(index()+1, listaCantidades);
	}

	
	// Arista que construimos, en este caso, la accion sera el numero de kg de la variedad con la
	// que estamos trabajando y el peso sera el beneficio por kg de tal variedad por el numero de
	// kgs seleccionados de tal variedad
	@Override
	public CafeEdge edge(Integer a) {
		return CafeEdge.of(this, neighbor(a), a);
	}

	
	// Metodo toString que define la representacion en forma de cadena de un vertice
	@Override
	public String toString() {
		return String.format("%d; %s", this.index, this.cantidadesCafes);
	}

}
