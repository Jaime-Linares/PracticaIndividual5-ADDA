package _soluciones;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;

import _datos.DatosInvestigadores;
import ejercicio3.InvestigadoresEdge;
import ejercicio3.InvestigadoresVertex;
import us.lsi.common.List2;

public class SolucionInvestigadores {

	// Propiedades
	private Map<String, List<Integer>> solucion;
	private Integer goal;
	private List<Integer> path;
	
	
	// getter del goal
	public Integer getGoal() {
		return goal;
	}
		
	
	// Metodo de factoria
	public static SolucionInvestigadores of(List<Integer> ls) {
		return new SolucionInvestigadores(ls);
	}

	public static SolucionInvestigadores of(GraphPath<InvestigadoresVertex, InvestigadoresEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(x -> x.action()).toList();
		SolucionInvestigadores res = of(ls);
		res.path = ls;		
		return res;		
	}
	
		
	// Constructor
	public SolucionInvestigadores(List<Integer> ls) {
		List<Integer> lsCopia = List2.copy(ls);
		// Hallamos la suma de las calidades de los trabajos que se realizan
		Integer calidades = 0;
		for(int j=0; j<DatosInvestigadores.getNumTrabajos(); j++) {
			Boolean realiza = true;
			for(int k=0; k<DatosInvestigadores.getNumEspecialidades(); k++) {
				Integer diasPorEspecialidad = 0;
				for(int i=0; i<ls.size(); i++) {
					Integer numInvestigador = i/DatosInvestigadores.getNumTrabajos();
					Integer numTrabajo = i%DatosInvestigadores.getNumTrabajos();
					if(numTrabajo.equals(j) && 
							DatosInvestigadores.getInvestigadorTieneEspecialidad(numInvestigador, k).equals(1)) {
						diasPorEspecialidad += ls.get(i);
					}
				}
				if(diasPorEspecialidad != DatosInvestigadores.getDiasNecesariosTrabajosEspecialidad(j, k)) {
					realiza = false;
				}
			}
			if(realiza) {
				calidades += DatosInvestigadores.getCalidadTrabajo(j);
			}
		}
		
		// Hallamos las horas que cada investigador dedica a cada trabajo
		Map<String, List<Integer>> mp = new HashMap<>();
		for(int i=0; i<DatosInvestigadores.getNumInvestigadores(); i++) {
			// Dividimos el cromosoma por investigadores
			Integer inicio = i*DatosInvestigadores.getNumTrabajos();
			List<Integer> valoresInvestigador = lsCopia.subList(inicio, inicio+DatosInvestigadores.getNumTrabajos());
			mp.put("INV"+(i+1), valoresInvestigador);
		}
		
		solucion = mp;
		goal = calidades;
	}
		
	// Metodo toString que devuelve una cadena con lo que cada investigador dedica a cada trabajo y la calidad obtenida
	@Override
	public String toString() {
		String res = String.format("Reparto obtenido = %s;\n- Suma de las calidades de los trabajos realizados = %d", 
				this.solucion, this.goal);
		return path==null? res: String.format("%s\nPath de la solucion = %s", res, this.path);
	}
	
}
