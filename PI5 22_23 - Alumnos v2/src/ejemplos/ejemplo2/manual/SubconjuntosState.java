package ejemplos.ejemplo2.manual;

import java.util.List;

import _datos.DatosSubconjuntos;
import _soluciones.SolucionSubconjuntos;
import us.lsi.common.List2;

public class SubconjuntosState {

	SubconjuntosProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<SubconjuntosProblem> anteriores;
	
	private SubconjuntosState(SubconjuntosProblem p, Double a, 
		List<Integer> ls1, List<SubconjuntosProblem> ls2) {
		// TODO Inicializar las propiedades individuales
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores = ls2;
	}

	public static SubconjuntosState initial() {
		// TODO Crear el estado inicial
		SubconjuntosProblem pi = SubconjuntosProblem.initial();
		return of(pi, 0., List2.empty(), List2.empty());
	}

	public static SubconjuntosState of(SubconjuntosProblem prob, Double acum, List<Integer> lsa,
			List<SubconjuntosProblem> lsp) {
		return new SubconjuntosState(prob, acum, lsa, lsp);
	}

	public void forward(Integer a) {		
		// TODO Avanzar un estado segun la alternativa a
		acumulado += a * DatosSubconjuntos.getPeso(actual.index());
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
	}

	public void back() {
		// TODO Retroceder al estado anterior
		int last = acciones.size()-1;
		var prob_ant = anteriores.get(last);
		
		acumulado -= acciones.get(last) * DatosSubconjuntos.getPeso(prob_ant.index());
		acciones.remove(last);
		anteriores.remove(last);
		actual = prob_ant;
	}

	public List<Integer> alternativas() {
		// TODO Alternativas segun el actual
		return actual.actions();
	}

	public Double cota(Integer a) {
		// TODO Cota = acumulado + func(a, actual) + h(vecino(actual, a))
		Double weight = a>0 ? DatosSubconjuntos.getPeso(actual.index()) : 0;
		return acumulado + weight + actual.neighbor(a).heuristic();
	}

	public Boolean esSolucion() {
		// TODO Cuando todos los elementos del universo se han cubierto
		return actual.remaining().isEmpty();
	}

	public Boolean esTerminal() {
		// TODO Cuando se han recorrido todos los subconjuntos
		return actual.index() == DatosSubconjuntos.getNumSubconjuntos();
	}

	public SolucionSubconjuntos getSolucion() {
		// TODO Aprovechamos lo hecho en la PI4
		return SolucionSubconjuntos.of(acciones);
	}

}
