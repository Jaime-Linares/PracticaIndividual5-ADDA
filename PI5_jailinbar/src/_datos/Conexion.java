package _datos;

public record Conexion(Integer conexionId, Double distanciaKm) {
	
	private static Integer id = 0;
	
	public static Conexion of(Double distanciaKm) {
		Integer conexionId = id;
		id++;
		return new Conexion(conexionId, distanciaKm);
	}
	
	public static Conexion ofFormat(String[] datos) {
		Double distanciaKm = Double.valueOf(datos[2].trim());
		return of(distanciaKm);
	}

}
