package _datos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.common.Files2;

public class DatosInvestigadores {

	public static List<Investigador> investigadores;
	public static List<Trabajo> trabajos;
	
	// Funcion que realiza la lectura de fichero y crea un tipo DatosInvestigadores
	public static void iniDatos(String fichero) {
		List<Investigador> listaInvestigadores = new ArrayList<>();
		List<Trabajo> listaTrabajos = new ArrayList<>();
		
		List<String> filas = Files2.linesFromFile(fichero);
		List<String> investigadoresString = filas.subList(1, filas.indexOf("// TRABAJOS"));
		List<String> trabajosString = filas.subList(filas.indexOf("// TRABAJOS") + 1, filas.size());
		
		// Transformamos una linea en el tipo Investigador y lo añadimos a la lista de investigadores
		for(String linea: investigadoresString) {
			Investigador inv = Investigador.parseaInvestigador(linea);
			listaInvestigadores.add(inv);
		}
		investigadores = listaInvestigadores;
		
		// Transformamos una linea en el tipo Trabajo y lo añadimos a la lista de trabajos
		for(String linea: trabajosString) {
			Trabajo t = Trabajo.parseaTrabajo(linea);
			listaTrabajos.add(t);
		}
		trabajos = listaTrabajos;
				
		toConsole();
	}
	
	// Para mostar el resultado por pantalla
	private static void toConsole() {
		System.out.println("- Investigadores leidos: ");
		for(Investigador inv: investigadores) {
			System.out.println("  " + inv);
		}
		System.out.println("- Trabajos leidos: ");
		for(Trabajo t: trabajos) {
			System.out.println("  " + t);
		}
	}
	
	
	// Funcion que me devuelve los investigadores
	public static List<Investigador> getInvestigadores() {
		return investigadores;
	}
	
	// Funcion que me devuelve el numero de investigadores
	public static Integer getNumInvestigadores() {
		return investigadores.size();
	}
	
	// Funcion que me devuelve el numero de especialidades
	public static Integer getNumEspecialidades() {
		Set<Integer> especialidades = new HashSet<>();
		for(Investigador inv: investigadores) {
			Integer especialidad = inv.especialidad();
			especialidades.add(especialidad);
		}
		return especialidades.size();
	}
	
	// Funcion que me devuelve los trabajos
	public static List<Trabajo> getTrabajos() {
		return trabajos;
	}
	
	// Funcion que me devuelve el numero de trabajos
	public static Integer getNumTrabajos() {
		return trabajos.size();
	}
	
	// Funcion que me devuelve un 1 si el investigador i tiene la especialidad k, sino devuelve un 0
	public static Integer getInvestigadorTieneEspecialidad(Integer i, Integer k) {
		return investigadores.get(i).especialidad().equals(k)? 1:0;
	}
	
	// Funcion que me devuelve los dias disponibles del investigador i
	public static Integer getDiasDisponiblesInvestigador(Integer i) {
		return investigadores.get(i).capacidad();
	}
	
	// Funcion que me devuelve los dias necesarios para el trabajo j del investigador con especialidad k
	public static Integer getDiasNecesariosTrabajosEspecialidad(Integer j, Integer k) {
		return trabajos.get(j).repartos().stream()
				.filter(x -> x.especialidad().equals(k))
				.findFirst()
				.get()
				.dias();
	}
	
	// Funcion que me devuelve la calidad del trabajo j
	public static Integer getCalidadTrabajo(Integer j) {
		return trabajos.get(j).calidad();
	}
	
	
	// TEST
	public static void main(String[] args) {
		System.out.println("* TEST DatosInvestigadores *\n");
		iniDatos("ficheros/Ejercicio3DatosEntrada1.txt");
	}

}
