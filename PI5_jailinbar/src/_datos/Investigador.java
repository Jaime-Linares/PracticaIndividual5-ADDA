package _datos;

public record Investigador(String nombre, Integer capacidad, Integer especialidad) {

	public static Investigador of(String nombre, Integer capacidad, Integer especialidad) {
		return new Investigador(nombre, capacidad, especialidad);
	}
	
	// Funcion para parsear una cadena a Investigador
	public static Investigador parseaInvestigador(String cadena) {
		String[] partes = cadena.split(":");
		
		
		String nombre = partes[0].trim();
		
		
		String[] partes1 = partes[1].trim().split(";");
		
		String[] partes10 = partes1[0].trim().split("=");
		Integer capacidad = Integer.valueOf(partes10[1].trim());
		
		String[] partes11 = partes1[1].trim().split("=");
		Integer especialidad = Integer.valueOf(partes11[1].trim());
		
		
		return Investigador.of(nombre, capacidad, especialidad);
	}

}
