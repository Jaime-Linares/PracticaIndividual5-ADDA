package _datos;

public record Cafe(String nombre, Integer kgDisponibles) {
	
	public static Cafe of(String nombre, Integer kgDisponibles) {
		return new Cafe(nombre, kgDisponibles);
	}

	// Funcion para parsear una cadena a Cafe
	public static Cafe parseaCafe(String cadena) {
		String[] partes = cadena.split(":");
		
		String nombre = partes[0].trim();
		
		String[] partes1 = partes[1].trim().split("=");
		String kgDisponibles = partes1[1].replace(";", "").trim();
		
		return Cafe.of(nombre, Integer.valueOf(kgDisponibles));
	}
	
}
