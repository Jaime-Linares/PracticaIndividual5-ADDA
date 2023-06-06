package _datos;

import java.util.ArrayList;
import java.util.List;

import us.lsi.common.Files2;

public class DatosCafes {
	
	public static List<Cafe> cafes;
	public static List<Variedad> variedades;
	
	// Funcion que realiza la lectura de fichero y crea un tipo DatosCafes
	public static void iniDatos(String fichero) {
		List<Cafe> listaCafes = new ArrayList<>();
		List<Variedad> listaVariedades = new ArrayList<>();
		
		List<String> filas = Files2.linesFromFile(fichero);
		List<String> cafesString = filas.subList(1, filas.indexOf("// VARIEDADES"));
		List<String> variedadesString = filas.subList(filas.indexOf("// VARIEDADES") + 1, filas.size());
		
		// Transformamos una cadena en el tipo Cafe y lo añadimos a la lista de cafes
		for(String linea: cafesString) {
			Cafe c = Cafe.parseaCafe(linea);
			listaCafes.add(c);
		}
		cafes = listaCafes;
		
		// Transformamos una cadena en el tipo Variedad y lo añadimos a la lista de variedades
		for(String linea: variedadesString) {
			Variedad v = Variedad.parseaVariedad(linea);
			listaVariedades.add(v);
		}
		variedades = listaVariedades;
		
		toConsole();
	}
	
	// Para mostar el resultado por pantalla
	private static void toConsole() {
		System.out.println("- Tipos de cafe leídos: ");
		for(Cafe c: cafes) {
			System.out.println("  " + c);
		}
		System.out.println("- Tipo de variedades leídas: ");
		for(Variedad v: variedades) {
			System.out.println("  " + v);
		}
	}

	
	// Funcion que me devuelve los tipos de cafe
	public static List<Cafe> getCafes() {
		return cafes;
	}
	
	// Funcion que calcula el numero de tipo de cafe
	public static Integer getNumTiposCafe() {
		return cafes.size();
	}
	
	// Funcion que me devuelve las variedades
	public static List<Variedad> getVariedades() {
		return variedades;
	}

	// Funcion que calcula el numero de variedades
	public static Integer getNumVariedades() {
		return variedades.size();
	}

	// Funcion que calcula los kg disponibles de cafe del tipo j
	public static Integer getCantidadCafe(Integer j) {
		return cafes.get(j).kgDisponibles();
	}

	// Funcion que calcula el beneficio de venta por cada kg de la variedad i
	public static Double getBeneficioVentaKg(Integer i) {
		return variedades.get(i).beneficio();
	}

	// Funcion que calcula el porcentaje del cafe j necesarios para un kg de la variedad i
	public static Double getPorcentajeCafeKg(Integer i, Integer j) {
		String nombreCafe = cafes.get(j).nombre();
		return variedades.get(i).comp().stream()
				.filter(x -> x.nombreCafe().equals(nombreCafe))
				.map(x -> x.cantidadKg())
				.findFirst()
				.orElse(0.);
	}
	
	
	// TEST
	public static void main(String[] args) {
		System.out.println("* TEST DatosCafes *\n");
		iniDatos("ficheros/Ejercicio1DatosEntrada1.txt");
	}

}
