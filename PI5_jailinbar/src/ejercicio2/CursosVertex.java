package ejercicio2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import _datos.DatosCursos;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CursosVertex(Integer index, Set<Integer> tematicasRestantes, Set<Integer> centrosUtilizados) 
		implements VirtualVertex<CursosVertex, CursosEdge, Integer> {
	
	// Metodo de factoria
	public static CursosVertex of(Integer index, Set<Integer> tematicasRestantes, 
			Set<Integer> centrosUtilizados) {
		return new CursosVertex(index, tematicasRestantes, centrosUtilizados);
	}
	
	
	// Vertice inicial: indice sera 0, las tematicas restantes seran todas las tematicas
	// que hay en el problema y los cursos utilizados sera un conjunto vacio
	public static CursosVertex initial() {
		Set<Integer> tematicasRestantes = DatosCursos.getAllTematicas();
		Set<Integer> centrosUtilizados = new HashSet<>();
		return of(0, tematicasRestantes, centrosUtilizados);
	}
	
	
	// goal: predicado el cual es verdadero cuando ya hemos recorrido todos los cursos
	public static Predicate<CursosVertex> goal() {
		return x -> x.index() == DatosCursos.getNumCursos();
	}
	
	
	// goalHasSolution: predicado el cual es verdadero si no quedan tematicas restantes y
	// el numero de centros utilizado es menor o igual al maximo de centros psoibles
	public static Predicate<CursosVertex> goalHasSolution() {
		return x -> x.tematicasRestantes().isEmpty() && 
				x.centrosUtilizados().size() <= DatosCursos.getMaxCentros();
	}
	
	
	// Alternativas a las que podemos ir desde ese vertice, es decir, si podemos coger
	// el curso o no
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<>();
		
		// en caso de que se hayan recorrido todos los cursos, no habra alternativas
		if(index() < DatosCursos.getNumCursos()) {
			// en caso de que ya sea solucion, es decir, que no haya que cubrir tematicas y
			// que no se supera el numero maximo de centros, la alternativa sera no cogerlo
			// ya que nuestra intencion es minimizar el coste de los precios de inscripcion
			if(tematicasRestantes().isEmpty() && 
					centrosUtilizados().size() <= DatosCursos.getMaxCentros()) {
				alternativas = List2.of(0);
				
			} else {	// si no es solucion, miramos los siguientes casos
				// obtenemos el conjunto de tematicas que quedarian y los centros seleccionados 
				Set<Integer> restTematicas = Set2.difference(tematicasRestantes(), 
						DatosCursos.getTematicasCurso(index()));
				Set<Integer> centrosUsados = Set2.copy(centrosUtilizados());
				centrosUsados.add(DatosCursos.getCentroCurso(index()));
				
				// si estamos en el ultimo curso e incribiendonos en tal curso no es solucion,
				// no habra alternativas
				if(index() == DatosCursos.getNumCursos()-1) {
					if(restTematicas.isEmpty() && centrosUsados.size() <= DatosCursos.getMaxCentros()) {
						alternativas = List2.of(1);
					} else {
						alternativas = List2.empty();
					}
				} else {
					// si las tematicas restantes se quedan igual o el numero de centros es mayor al
					// maximo posible no se seleccionara el curso (alternativa: no inscribirse)
					if(restTematicas.equals(tematicasRestantes()) || 
							centrosUsados.size() > DatosCursos.getMaxCentros()) {
						alternativas = List2.of(0);
					} 
					// si no se cumple lo anterior, las alternativas son incribirse o
					// no inscribirse en el curso
					else {
						alternativas = List2.of(0, 1);
					}
				}
			}
		}

		return alternativas;
	}

	
	// Vertice vecino al que vamos, es decir, aumentar el indice, quitar las tematicas
	// que hayamos ya utilizado y añadir al conjunto de centros el centro seleccionado
	@Override
	public CursosVertex neighbor(Integer a) {
		Set<Integer> tematicasVecino = null;
		Set<Integer> centrosVecino = null;
		
		// si seleccionamos el curso quitamos las tematicas que hemos usado y añadimos el centro del curso seleccionado
		if(a != 0) {
			tematicasVecino = Set2.difference(tematicasRestantes(), 
					DatosCursos.getTematicasCurso(index()));
			centrosVecino = Set2.copy(centrosUtilizados());
			centrosVecino.add(DatosCursos.getCentroCurso(index()));
			
		} else {	// si no hemos seleccionado el cursos las tematicas y los centros se quedan igual
			tematicasVecino = Set2.copy(tematicasRestantes());
			centrosVecino = Set2.copy(centrosUtilizados());
		}
		
		return of(index()+1, tematicasVecino, centrosVecino);
	}

	
	// Arista que construimos, en este caso, la accion sera 1 o 0 dependiendo si hemos
	// seleccionado o no ese curso y el peso sera el precio de inscripcion del curso por
	// 0 o 1 (ya que si no se selecciona el peso sera 0)
	@Override
	public CursosEdge edge(Integer a) {
		return CursosEdge.of(this, neighbor(a), a);
	}


	// Metodo toString que define la representacion en forma de cadena de un vertice
	@Override
	public String toString() {
		return String.format("%d; %s; %s", this.index, this.tematicasRestantes, this.centrosUtilizados);
	}

}
