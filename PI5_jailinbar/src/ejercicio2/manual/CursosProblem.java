package ejercicio2.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import _datos.DatosCursos;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record CursosProblem(Integer index, Set<Integer> tematicasRestantes, Set<Integer> centrosUtilizados) {
	
	// Metodo de factoria
	public static CursosProblem of(Integer index, Set<Integer> tematicasRestantes, 
			Set<Integer> centrosUtilizados) {
		return new CursosProblem(index, tematicasRestantes, centrosUtilizados);
	}
	
	
	// Vertice inicial: indice sera 0, las tematicas restantes seran todas las tematicas
	// que hay en el problema y los cursos utilizados sera un conjunto vacio
	public static CursosProblem initial() {
		Set<Integer> tematicasRestantes = DatosCursos.getAllTematicas();
		Set<Integer> centrosUtilizados = new HashSet<>();
		return of(0, tematicasRestantes, centrosUtilizados);
	}
	
	
	// Alternativas a las que podemos ir desde ese vertice, es decir, si podemos coger
	// el curso o no
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
	public CursosProblem neighbor(Integer a) {
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
	
	
	// Heuristica: lo mejor que puede pasar en un vertice es que cojamos el curso con menor
	// peso (si las tematicas restantes cambian y no se supera el numero maximo de centros utilizados)
	public Double heuristic() {
		Double res = 0.;
		List<Double> ls = new ArrayList<>();
		
		if(!(tematicasRestantes().isEmpty() && centrosUtilizados().size() <= DatosCursos.getMaxCentros())) {
			for (int i=index(); i<DatosCursos.getNumCursos(); i++) {
				// obtenemos el conjunto de tematicas que quedarian y los centros seleccionados
				Boolean tematicas = !List2.intersection(tematicasRestantes(), DatosCursos.getTematicasCurso(i))
						.isEmpty();
				Set<Integer> centrosUsados = Set2.copy(centrosUtilizados());
				centrosUsados.add(DatosCursos.getCentroCurso(i));
				// si las tematicas restantes cambian y no se supera el numero maximo de centros utilizados
				// entonces tendremos en cuenta el precio de la inscripcion de ese curso añadiendolo a la lista
				if(tematicas && centrosUsados.size() <= DatosCursos.getMaxCentros()) {
					ls.add(DatosCursos.getPrecioInscripcion(i));
				}
			}
			// devolvemos el precio de incripcion menor de los cursos seleccionados
			res = ls.stream().min(Comparator.naturalOrder()).orElse(100.);
		}
		
		return res;
	}
		

}
