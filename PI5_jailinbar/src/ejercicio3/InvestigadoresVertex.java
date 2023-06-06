package ejercicio3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import _datos.DatosInvestigadores;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record InvestigadoresVertex(Integer index, List<Integer> days, List<List<Integer>> distribution) 
		implements VirtualVertex<InvestigadoresVertex, InvestigadoresEdge, Integer> {
	
	// Metodo de factoria
	public static InvestigadoresVertex of(Integer index, List<Integer> days, 
			List<List<Integer>> distribution) {
		return new InvestigadoresVertex(index, days, distribution);
	}
	
	
	// Vertice inicial: el indice sera 0, los dias seran los dias que tiene disponible cada
	// investigador inicialmente y la distribucion sera una lista con m lista de enteros donde
	// cada lista tendra los dias necesarios inicialmente de cada especialidad en ese trabajo
	public static InvestigadoresVertex initial() {
		Integer index = 0;
		
		List<Integer> days = new ArrayList<>();
		for(int i=0; i<DatosInvestigadores.getNumInvestigadores(); i++) {
			Integer diasDisponibles = DatosInvestigadores.getDiasDisponiblesInvestigador(i);
			days.add(i, diasDisponibles);
		}
		
		List<List<Integer>> distribution = new ArrayList<>();
		for(int j=0; j<DatosInvestigadores.getNumTrabajos(); j++) {
			List<Integer> distributionTrabajo = new ArrayList<>();
			for(int k=0; k<DatosInvestigadores.getNumEspecialidades(); k++) {
				Integer diasEspecialidadTrabajo = DatosInvestigadores.getDiasNecesariosTrabajosEspecialidad(j, k);
				distributionTrabajo.add(k, diasEspecialidadTrabajo);
			}
			distribution.add(j, distributionTrabajo);
		}

		return of(index, days, distribution);
	}
	
	
	// goal: predicado el cual es verdadero cuando ya hemos recorrido todas los variables, es decir,
	// la n*m variables
	public static Predicate<InvestigadoresVertex> goal() {
		return x -> x.index() == DatosInvestigadores.getNumInvestigadores()*DatosInvestigadores.getNumTrabajos();
	}
	
	
	// goalHasSolution: predicado el cual es verdadero cuando ya hemos recorrido todas los variables,
	//  es decir, la n*m variables
	public static Predicate<InvestigadoresVertex> goalHasSolution() {
		return x -> x.index() == DatosInvestigadores.getNumInvestigadores()*DatosInvestigadores.getNumTrabajos();
	}
	
	
	// Alternativas a las que podemos ir desde ese vertice, es decir, el rango de dias que
	// un investigador puede hacer en un trabajo determinado
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<>();
		Integer numVariables = DatosInvestigadores.getNumInvestigadores()*DatosInvestigadores.getNumTrabajos();
		
		// en caso de que ya hayamos recorrido todas las variables no habra alternativas
		if(index() < numVariables) {
			List<Integer> dias = new ArrayList<>();
			
			// obtenemos los dias restantes del investigador y lo añadimos a la lista de dias
			Integer indiceInvestigador = index()/DatosInvestigadores.getNumTrabajos();
			Integer diasRestantesInvestigador = days().get(indiceInvestigador);
			dias.add(diasRestantesInvestigador);
			
			// obtenemos los dias restantes de la especialidad del investigador en el trabajo y 
			// lo añadimos a la lista de dias
			Integer especialidad = DatosInvestigadores.getInvestigadores()
					.get(indiceInvestigador).especialidad();
			Integer indiceTrabajo = index()%DatosInvestigadores.getNumTrabajos();
			Integer diasRestantesEspecialidadTrabajo = distribution().get(indiceTrabajo).get(especialidad);
			dias.add(diasRestantesEspecialidadTrabajo);
			
			// cogemos el minimo de la lista dias que sera lo maximo que puede trabajar el
			// investigador en ese trabajo
			Integer maxDias = dias.stream().min(Comparator.naturalOrder()).orElse(0);
			
			// las alternativas seran desde 0 hasta el maximo de dias posibles
			alternativas = List2.rangeList(0, maxDias+1);	// +1 porque es rango abierto			
		}
		
		return alternativas;
	}
	
	
	// Vertice vecino al que vamos, es decir, se incrementa el indice y si resulta que se le
	// dedican mas de 0 dias se los restamos de los dias disponibles del investigador y se lo
	// restamos a las horas necesarias de la especialidad de tal investigador en tal trabajo
	@Override
	public InvestigadoresVertex neighbor(Integer a) {
		List<Integer> daysNeighbor = List2.copy(days());
		List<List<Integer>> distributionNeighbor = new ArrayList<>();
		for(int j=0; j<distribution().size(); j++) {
			List<Integer> trabajoNeighbor = List2.copy(distribution().get(j));
			distributionNeighbor.add(trabajoNeighbor);
		}
		
		if(a > 0) {
			// le restamos a las que haya trabajado al investigador
			Integer indiceInvestigador = index()/DatosInvestigadores.getNumTrabajos();
			Integer horasInvestigadorVecino = days().get(indiceInvestigador) - a;
			daysNeighbor.set(indiceInvestigador, horasInvestigadorVecino);
			
			// le restamos a las horas necesarias para la especialidad del investigador 
			// en tal trabajo
			Integer especialidad = DatosInvestigadores.getInvestigadores()
					.get(indiceInvestigador).especialidad();
			Integer indiceTrabajo = index()%DatosInvestigadores.getNumTrabajos();
			List<Integer> diasEspecialidadTrabajo = distributionNeighbor.get(indiceTrabajo);
			Integer horasTrabajoEspecialidadVecino = diasEspecialidadTrabajo.get(especialidad) - a;
			diasEspecialidadTrabajo.set(especialidad, horasTrabajoEspecialidadVecino);
			distributionNeighbor.set(indiceTrabajo, diasEspecialidadTrabajo);			
		}
		
		return of(index()+1, daysNeighbor, distributionNeighbor);
	}

	
	// Arista que construimos, en este caso, la accion sera el numero de horas que el investigador
	// le ha dedicado al trabajo y el peso sera 0 (si no se completa un trabajo entre un vertice 
	// y el otro) o la calidad del trabajo (si entre un vertice y el otro si que se completa el trabajo)
	@Override
	public InvestigadoresEdge edge(Integer a) {
		return InvestigadoresEdge.of(this, neighbor(a), a);
	}
	
	
	// Metodo toString que define la representacion en forma de cadena de un vertice
	@Override
	public String toString() {
		return String.format("%d; %s; %s", this.index, this.days, this.distribution);
	}

}
