package _datos;

public record TuplaEj3(Integer especialidad, Integer dias) {
	
	public static TuplaEj3 of(Integer especialidad, Integer dias) {
		return new TuplaEj3(especialidad, dias);
	}
	
	// Funcion para parsear una cadena a una tupla formada por especialidad 
	// y numero de dias necesarios de esa especialidad
	public static TuplaEj3 parseaTuplaEj3(String cadena) {
		String cadenaTupla = cadena.substring(1, cadena.length()-1);
		String[] partes = cadenaTupla.split(":");
		Integer especialidad = Integer.valueOf(partes[0].trim());
		Integer dias = Integer.valueOf(partes[1].trim());	
		return TuplaEj3.of(especialidad, dias);
	}

}
