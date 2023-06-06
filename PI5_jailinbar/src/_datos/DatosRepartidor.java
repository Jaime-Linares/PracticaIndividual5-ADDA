package _datos;

import org.jgrapht.Graph;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class DatosRepartidor {
	
	public static Graph<Cliente, Conexion> grafo;
	
	// Funcion que realiza la lectura de fichero y crea un tipo DatosCafes
	public static void iniDatos(String fichero) {
		grafo = GraphsReader.newGraph(fichero, 
				Cliente::ofFormat, Conexion::ofFormat, 
				Graphs2::simpleWeightedGraph);
		
		toConsole();
	}
	
	// Para mostar el resultado por pantalla
	private static void toConsole() {
		System.out.println("- Numero de clientes leidos: " + grafo.vertexSet().size());
		System.out.println("- Clientes leidos: ");
		for(Cliente c: grafo.vertexSet()) {
			System.out.println("  " + c);
		}
		System.out.println("\n- Numero de conexiones leidas: " + grafo.edgeSet().size());
		System.out.println("- Conexiones leidas: ");
		for(Conexion co: grafo.edgeSet()) {
			System.out.println("  " + co);
		}
	}
	
	
	// Funcion auxiliar que devuelve el cliente i
	private static Cliente getCliente(Integer i) {
		return grafo.vertexSet().stream()
				.filter(x -> x.clienteId().equals(i))
				.findFirst().orElse(null);
	}
	
	// Funcion que devuelve el numero de vertices
	public static Integer getNumVertices() {
		return grafo.vertexSet().size();
	}
	
	// Funcion que devuelve el peso de la arista que une el Cliente i y el Cliente j
	public static Double getPesoArista(Integer i, Integer j) {
		return grafo.getEdge(getCliente(i), getCliente(j)).distanciaKm();
	}
	
	// Funcion que devuelve el beneficio que da el Cliente i	
	public static Double getBeneficioCliente(Integer i) {
		return grafo.vertexSet().stream()
				.filter(x -> x.clienteId().equals(i))
				.findFirst().get()
				.beneficio();
	}
	
	// Funcion que dice si existe una arista en el grafo
	public static Boolean existeArista(Integer i, Integer j) {
		return grafo.containsEdge(getCliente(i), getCliente(j));
	}
	
	
	// TEST
	public static void main(String[] args) {
		System.out.println("* TEST DatosRepartidor *\n");
		iniDatos("ficheros/Ejercicio4DatosEntrada1.txt");
	}

}
