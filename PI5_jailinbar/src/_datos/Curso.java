package _datos;

import java.util.HashSet;
import java.util.Set;

public record Curso(String id, Set<Integer> tematicas, Double coste, Integer centro) {

	private static Integer numId = 0;
	
	public static Curso of(Set<Integer> tematicas, Double coste, Integer centro) {
		String id = "C"+numId;
		numId++;
		return new Curso(id, tematicas, coste, centro);
	}
	
	// Funcion para parsear una cadena a Curso - {1,2,3,4}:10.0:0
	public static Curso parseaCurso(String cadena) {
		String[] partes = cadena.split(":");
		
		Set<Integer> tematicas = new HashSet<>();
		String conj = partes[0].trim().substring(1, partes[0].trim().length()-1); 
		String[] partesConj = conj.split(",");
		for(int i=0; i<partesConj.length; i++) {
			Integer tematica = Integer.valueOf(partesConj[i].trim());
			tematicas.add(tematica);
		}
		
		Double coste = Double.valueOf(partes[1].trim());
		
		Integer centro = Integer.valueOf(partes[2].trim());		
		
		return Curso.of(tematicas, coste, centro);
	}

}
