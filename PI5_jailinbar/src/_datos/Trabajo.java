package _datos;

import java.util.ArrayList;
import java.util.List;

public record Trabajo(String nombre, Integer calidad, List<TuplaEj3> repartos) {

	public static Trabajo of(String nombre, Integer calidad, List<TuplaEj3> repartos) {
		return new Trabajo(nombre, calidad, repartos);
	}
		
	// Funcion para parsear una cadena a Trabajo
	public static Trabajo parseaTrabajo(String cadena) {
		String[] partes = cadena.split("->");
		
		
		String nombre = partes[0].trim();
		
		
		String[] partes1 = partes[1].trim().split(";");
		
		String[] partes10 = partes1[0].trim().split("=");
		Integer calidad = Integer.valueOf(partes10[1].trim());
		
		String cadenaRepartos = partes1[1].trim();
		List<TuplaEj3> repartos = listaRepartos(cadenaRepartos);
		
		return Trabajo.of(nombre, calidad, repartos);
	}
	
	// Funcion auxiliar para parsear la lista de repartos
	private static List<TuplaEj3> listaRepartos(String cadena) {
		List<TuplaEj3> res = new ArrayList<>();
		String[] partes = cadena.split("=");
		
		String[] partes1 = partes[1].trim().split(",");
		for(String cadenaTupla: partes1) {
			TuplaEj3 tupla = TuplaEj3.parseaTuplaEj3(cadenaTupla.trim());
			res.add(tupla);
		}
	
		return res;
	}

}
