package ejercicio2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import _datos.DatosCursos;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public class CursosHeuristic {
	
	// Heuristica: lo mejor que puede pasar en un vertice es que cojamos el curso con menor
	// peso (si las tematicas restantes cambian y no se supera el numero maximo de centros utilizados)
	public static Double heuristic(CursosVertex v1, Predicate<CursosVertex> goal, CursosVertex v2) {
		Double res = 0.;
		List<Double> ls = new ArrayList<>();
		
		if(!(v1.tematicasRestantes().isEmpty() && v1.centrosUtilizados().size() <= DatosCursos.getMaxCentros())) {
			for (int i=v1.index(); i<DatosCursos.getNumCursos(); i++) {
				// obtenemos el conjunto de tematicas que quedarian y los centros seleccionados
				Boolean tematicas = !List2.intersection(v1.tematicasRestantes(), DatosCursos.getTematicasCurso(i))
						.isEmpty();
				Set<Integer> centrosUsados = Set2.copy(v1.centrosUtilizados());
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
