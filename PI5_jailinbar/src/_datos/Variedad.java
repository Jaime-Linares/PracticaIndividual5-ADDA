package _datos;

import java.util.ArrayList;
import java.util.List;

public record Variedad(String nombre, Double beneficio, List<TuplaEj1> comp) {
	
	public static Variedad of(String nombre, Double beneficio, List<TuplaEj1> comp) {
		return new Variedad(nombre, beneficio, comp);
	}
	
	// Funcion para parsear una cadena a variedad
	public static Variedad parseaVariedad(String cadena) {
		String[] partes = cadena.split("->");
		
		
		String nombre = partes[0].trim();
		
		
		String[] partes1 = partes[1].split(";");
		
		String[] partes10 = partes1[0].trim().split("=");
		String beneficio = partes10[1].trim();
		
		String cadenaComponentes = partes1[1].trim();
		List<TuplaEj1> comp = listaComponentes(cadenaComponentes);
		
		return Variedad.of(nombre, Double.valueOf(beneficio), comp);
				
	}
	
	// Funcion auxiliar para parsear la lista de componentes
	private static List<TuplaEj1> listaComponentes(String cadena) {
		List<TuplaEj1> res = new ArrayList<>();
		String[] partes = cadena.split("=");
		
		String[] partes1 = partes[1].trim().split(",");
		for(String cadenaTupla: partes1) {
			TuplaEj1 tupla = TuplaEj1.parseaTuplaEj1(cadenaTupla.trim());
			res.add(tupla);
		}
		
		return res;
	}

}
