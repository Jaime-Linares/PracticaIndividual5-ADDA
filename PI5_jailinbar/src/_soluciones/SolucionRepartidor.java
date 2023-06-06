package _soluciones;

import java.util.List;

import org.jgrapht.GraphPath;

import _datos.DatosRepartidor;
import ejercicio4.RepartidorEdge;
import ejercicio4.RepartidorVertex;
import us.lsi.common.List2;

public class SolucionRepartidor {

	// Propiedades
	private List<Integer> camino;
	private Double distancia;
	private Double beneficio;
	private List<Integer> path;
	
	
	// getter del beneficio
	public Double getBeneficio() {
		return beneficio;
	}
	
	
	// Metodo de factoria
	public static SolucionRepartidor of(List<Integer> ls) {
		List<Integer> lsCopy = List2.copy(ls);	// para que me deje añadirle el 0 en la primera posicion
		return new SolucionRepartidor(lsCopy);
	}

	public static SolucionRepartidor of(GraphPath<RepartidorVertex, RepartidorEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(x -> x.action()).toList();
		List<Integer> camino = List2.copy(ls);	// para que me deje añadirle el 0 en la primera posicion
		SolucionRepartidor res = of(camino);
		res.path = ls;		
		return res;	
	}
	
	
	// Constructor
	public SolucionRepartidor(List<Integer> ls) {
		Double km = 0.;
		Double beneficios = 0.;

		// Recorremos el cromosoma para hallar la distancia recorrida y el beneficio
		for(int i=0; i<ls.size(); i++) {
			// Beneficio += beneficio - cada minuto que se pase (ponemos los km, ya que tarda 1 minuto en hacer 1km)
			// Si no existe la arista aumentamos el error
			if(i==0) {
				km += DatosRepartidor.getPesoArista(0, ls.get(i));
				beneficios += DatosRepartidor.getBeneficioCliente(ls.get(i)) - km;
			} else {
				km += DatosRepartidor.getPesoArista(ls.get(i-1), ls.get(i));
				beneficios += DatosRepartidor.getBeneficioCliente(ls.get(i)) - km;
			}
		}
		// Añadimos a la posicion 0 el inicial, es decir, el 0
		ls.add(0, 0);
		
		camino = ls;
		distancia = km;
		beneficio = beneficios;
	}
	
	
	// Metodo toString que devuelve una cadena con el orden de clientes que ha seguido, 
	// la distancia recorrida en km y el beneficio total
	@Override
	public String toString() {
		String res = String.format("\nCamino desde 0 hasta 0: %s;\nKms: %f;\nBeneficio: %f;", 
				this.camino, this.distancia, this.beneficio);
		return path==null? res: String.format("%s\nPath de la solucion = %s", res, this.path);
	}

}
