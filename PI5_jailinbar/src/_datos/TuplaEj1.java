package _datos;

public record TuplaEj1(String nombreCafe, Double cantidadKg) {
	
	public static TuplaEj1 of(String nombreCafe, Double cantidadKg) {
		return new TuplaEj1(nombreCafe, cantidadKg);
	}
	
	// Funcion para parsear una cadena a una tupla formada por nombre 
	// y cantidad por kg del tipo de cafe
	public static TuplaEj1 parseaTuplaEj1(String cadena) {
		String cadenaTupla = cadena.substring(1, cadena.length()-1);
		String[] partes = cadenaTupla.split(":");
		String nombreCafe = partes[0].trim();
		String cantidadKg = partes[1].trim();
		return TuplaEj1.of(nombreCafe, Double.valueOf(cantidadKg));
	}
	
}
