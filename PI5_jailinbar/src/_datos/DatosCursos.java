package _datos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.common.Files2;

public class DatosCursos {
	
	public static Integer maxCentros;
	public static List<Curso> cursos;
	
	// Funcion que realiza la lectura de fichero y crea un tipo DatosCursos
	public static void iniDatos(String fichero) {
		List<Curso> listaCursos = new ArrayList<>();
		
		List<String> filas = Files2.linesFromFile(fichero);
		List<String> cursosString = filas.subList(1, filas.size());
		
		// La primera linea del fichero es el numero maximo de centros
		String primeraLinea = filas.get(0);
		String[] partes = primeraLinea.split("=");
		Integer numMaxCentros = Integer.valueOf(partes[1].trim());
		maxCentros = numMaxCentros;
		
		// Tranformamos una cadena en el tipo Curso y lo añadimos a la lista de cursos
		for(String linea: cursosString) {
			Curso c = Curso.parseaCurso(linea);
			listaCursos.add(c);
		}
		cursos = listaCursos;
		
		toConsole();
	}
	
	// Para mostar el resultado por pantalla
	private static void toConsole() {
		System.out.println("- Numero maximo de centros leido: " + maxCentros);
		System.out.println("- Cursos leidos: ");
		for(Curso c: cursos) {
			System.out.println("  " + c);
		}
	}
	
	
	// Funcion que devuelve la lista de centros
	public static List<Curso> getCursos() {
		return cursos;
	}
	
	// Funcion que devuelve el numero de cursos
	public static Integer getNumCursos() {
		return cursos.size();
	}
	
	// Funcion que devuelve las tematicas del problema
	public static Set<Integer> getAllTematicas() {
		Set<Integer> tematicas = new HashSet<>();
		for(Curso c: cursos) {
			Set<Integer> t = c.tematicas();
			tematicas.addAll(t);
		}
		return tematicas;
	}

	// Funcion que devuelve el numero de tematicas
	public static Integer getNumTematicas() {
		return getAllTematicas().size();
	}
		
	// Funcion que devuelve el numero de centros
	public static Integer getNumCentros() {
		Set<Integer> centros = new HashSet<>();
		for (Curso c : cursos) {
			Integer centro = c.centro();
			centros.add(centro);
		}
		return centros.size();
	}
	
	// Funcion que devuelve el numero maximo de centros
	public static Integer getMaxCentros() {
		return maxCentros;
	}
		
	// Funcion que devuelve el precio de la inscripcion del curso i
	public static Double getPrecioInscripcion(Integer i) {
		return cursos.get(i).coste();
	}
		
	// Funcion que devuelve las tematicas del curso i
	public static Set<Integer> getTematicasCurso(Integer i) {
		return cursos.get(i).tematicas();
	}
	
	// Funcion que devuelve el centro donde se imparte el curso i
	public static Integer getCentroCurso(Integer i) {
		return cursos.get(i).centro();
	}

	
	// TEST
	public static void main(String[] args) {
		System.out.println("* TEST DatosCursos *\n");
		iniDatos("ficheros/Ejercicio2DatosEntrada1.txt");
	}

}
