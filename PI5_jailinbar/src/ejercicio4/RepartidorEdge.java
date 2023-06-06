package ejercicio4;

import _datos.DatosRepartidor;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record RepartidorEdge(RepartidorVertex source, RepartidorVertex target, Integer action, 
		Double weight) implements SimpleEdgeAction<RepartidorVertex, Integer> {
	
	// Metodo de factoria
	public static RepartidorEdge of(RepartidorVertex source, RepartidorVertex target, Integer action) {
		Double weight = DatosRepartidor.getBeneficioCliente(action);
		
		Double kmsRecorridos = source.kms();
		if(source.cliente() == 0) {
			kmsRecorridos += DatosRepartidor.getPesoArista(0, action);
		} else {
			kmsRecorridos += DatosRepartidor.getPesoArista(source.visitados().get(source.visitados().size()-1), action);
		}
		
		weight -= kmsRecorridos;
		
		return new RepartidorEdge(source, target, action, weight);
	}
	
	
	// Metodo toString que define la representacion en forma de cadena de una arista
	@Override
	public String toString() {
		return String.format("%d; %.2f", this.action, this.weight);
	}

}
