package _datos;

public record Cliente(Integer clienteId, Double beneficio) {
	
	public static Cliente of(Integer clienteId, Double beneficio) {
		return new Cliente(clienteId, beneficio);
	}
	
	public static Cliente ofFormat(String[] datos) {
		Integer clienteId = Integer.valueOf(datos[0].trim());
		Double beneficio = Double.valueOf(datos[1].trim());
		return of(clienteId, beneficio);
	}

}
