package ejercicio3.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import _datos.DatosInvestigadores;
import us.lsi.common.List2;

public record InvestigadoresProblem(Integer index, List<Integer> days, List<List<Integer>> distribution) {
	
	// Metodo de factoria
	public static InvestigadoresProblem of(Integer index, List<Integer> days, List<List<Integer>> distribution) {
		return new InvestigadoresProblem(index, days, distribution);
	}

	
	// Vertice inicial: el indice sera 0, los dias seran los dias que tiene disponible cada
	// investigador inicialmente y la distribucion sera una lista con m lista de enteros donde
	// cada lista tendra los dias necesarios inicialmente de cada especialidad en ese trabajo
	public static InvestigadoresProblem initial() {
		Integer index = 0;

		List<Integer> days = new ArrayList<>();
		for (int i = 0; i < DatosInvestigadores.getNumInvestigadores(); i++) {
			Integer diasDisponibles = DatosInvestigadores.getDiasDisponiblesInvestigador(i);
			days.add(i, diasDisponibles);
		}

		List<List<Integer>> distribution = new ArrayList<>();
		for (int j = 0; j < DatosInvestigadores.getNumTrabajos(); j++) {
			List<Integer> distributionTrabajo = new ArrayList<>();
			for (int k = 0; k < DatosInvestigadores.getNumEspecialidades(); k++) {
				Integer diasEspecialidadTrabajo = DatosInvestigadores.getDiasNecesariosTrabajosEspecialidad(j, k);
				distributionTrabajo.add(k, diasEspecialidadTrabajo);
			}
			distribution.add(j, distributionTrabajo);
		}

		return of(index, days, distribution);
	}
	
	
	// Alternativas a las que podemos ir desde ese vertice, es decir, el rango de dias que
	// un investigador puede hacer en un trabajo determinado
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<>();
		Integer numVariables = DatosInvestigadores.getNumInvestigadores() * DatosInvestigadores.getNumTrabajos();

		// en caso de que ya hayamos recorrido todas las variables no habra alternativas
		if (index() < numVariables) {
			List<Integer> dias = new ArrayList<>();

			// obtenemos los dias restantes del investigador y lo añadimos a la lista de dias
			Integer indiceInvestigador = index() / DatosInvestigadores.getNumTrabajos();
			Integer diasRestantesInvestigador = days().get(indiceInvestigador);
			dias.add(diasRestantesInvestigador);

			// obtenemos los dias restantes de la especialidad del investigador en el trabajo y
			// lo añadimos a la lista de dias
			Integer especialidad = DatosInvestigadores.getInvestigadores().get(indiceInvestigador).especialidad();
			Integer indiceTrabajo = index() % DatosInvestigadores.getNumTrabajos();
			Integer diasRestantesEspecialidadTrabajo = distribution().get(indiceTrabajo).get(especialidad);
			dias.add(diasRestantesEspecialidadTrabajo);

			// cogemos el minimo de la lista dias que sera lo maximo que puede trabajar el
			// investigador en ese trabajo
			Integer maxDias = dias.stream().min(Comparator.naturalOrder()).orElse(0);

			// las alternativas seran desde 0 hasta el maximo de dias posibles
			alternativas = List2.rangeList(0, maxDias + 1); // +1 porque es rango abierto
		}

		return alternativas;
	}

	
	// Vertice vecino al que vamos, es decir, se incrementa el indice y si resulta que se le
	// dedican mas de 0 dias se los restamos de los dias disponibles del investigador y se lo
	// restamos a las horas necesarias de la especialidad de tal investigador en tal trabajo
	public InvestigadoresProblem neighbor(Integer a) {
		List<Integer> daysNeighbor = List2.copy(days());
		List<List<Integer>> distributionNeighbor = List2.copy(distribution());

		if (a > 0) {
			// le restamos a las que haya trabajado al investigador
			Integer indiceInvestigador = index() / DatosInvestigadores.getNumTrabajos();
			Integer horasInvestigadorVecino = days().get(indiceInvestigador) - a;
			daysNeighbor.set(indiceInvestigador, horasInvestigadorVecino);

			// le restamos a las horas necesarias para la especialidad del investigador
			// en tal trabajo
			Integer especialidad = DatosInvestigadores.getInvestigadores().get(indiceInvestigador).especialidad();
			Integer indiceTrabajo = index() % DatosInvestigadores.getNumTrabajos();
			List<Integer> diasEspecialidadTrabajo = List2.copy(distribution().get(indiceTrabajo));
			Integer horasTrabajoEspecialidadVecino = diasEspecialidadTrabajo.get(especialidad) - a;
			diasEspecialidadTrabajo.set(especialidad, horasTrabajoEspecialidadVecino);
			distributionNeighbor.set(indiceTrabajo, diasEspecialidadTrabajo);
		}

		return of(index() + 1, daysNeighbor, distributionNeighbor);
	}
	
	
	// Heuristica: lo mejor que puede pasar a partir de un vertice es que se cumplan todos los trabajos
	// restantes a partir de ese vertice (incluido el del vertice en el que estamos)
	public Double heuristic() {
		Double res = 0.;

		Integer indiceInvestigador = index() / DatosInvestigadores.getNumTrabajos();
		Integer ultimoInv = DatosInvestigadores.getNumInvestigadores() - 1;
		// si el investigador es el ultimo miramos por que trabajo vamos para solo sumar
		// a partir de ese
		if (indiceInvestigador == ultimoInv) {
			Integer indiceTrabajo = index() % DatosInvestigadores.getNumTrabajos();
			for (int j = indiceTrabajo; j < DatosInvestigadores.getNumTrabajos(); j++) {
				res += DatosInvestigadores.getCalidadTrabajo(j);
			}
		} else { // si el investigador no es el ultimo se deben sumar de todos los trabajos
			for (int j = 0; j < DatosInvestigadores.getNumTrabajos(); j++) {
				res += DatosInvestigadores.getCalidadTrabajo(j);
			}
		}

		return res;
	}

}
