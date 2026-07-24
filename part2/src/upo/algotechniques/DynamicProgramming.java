package upo.algotechniques;

import upo.graph.base.Graph; 
import upo.graph.base.Vertex;
import upo.graph.base.VisitForest;
import upo.graph.base.VisitForest.VisitType;

import java.util.ArrayList;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DynamicProgramming {	
	/** Calcola il Massimo Sottoinsieme Indipendente con l'algoritmo di programmazione dinamica
	 * visto a lezione.
	 * </br>NOTA: nonostante l'algoritmo possa essere applicato solo a grafi non orientati, qui per
	 * semplicità vi viene chiesto di applicarlo anche a grafi orientati. Nel caso in cui il vostro grafo
	 * sia orientato, considerate <u,v> equivalente ad (u,v). Prima di applicare l'algoritmo, preprocessate
	 * il vostro grafo: se sono presenti sia l'arco <u,v> che l'arco <v,u>, eliminate uno dei 2.
	 *
	 * @param graph il grafo sul quale applicare l'algoritmo
	 * @param vertexWeights mappa ogni vertice del grafo con il suo peso
	 * @return una collezione di vertici che rappresenta il MSI del grafo dato
	 * @throws IllegalArgumentException se il grafo dato non è lineare (eventualmente non considerando la direzione
	 * degli archi) o se c'è discrepanza tra i vertici di graph e vertexWeights.
	 */
	public static Collection<Vertex> getMSI(Graph graph, Map<Vertex, Integer> vertexWeights) throws IllegalArgumentException {

		//controllo coerenza tra le due tabelle
		if(!vertexWeights.keySet().equals(graph.getVertices())) {
			throw new IllegalArgumentException();
		}
		//controllo che il grafo non abbia piu' componenti e che non sia ciclico
		if(graph.isCyclic() || graph.connectedComponents().size()!=1) {
			throw new IllegalArgumentException();
		}
		//tutti i nodi devono avere 1 o 2 adiacenti (1 per gli estremi e 2 per quelli interni)
		//non serve controllare che ne abbiano 0, lo vedo gia' con connectedComponents
		for(Vertex element : graph.getVertices()) {
			if(graph.getAdjacent(element).size()>=3) {
				throw new IllegalArgumentException();
			}
		}
		 ArrayList<Set<Vertex>> A = new  ArrayList<Set<Vertex>>();
		return MSI(graph.getVertices().size(), A, graph, vertexWeights );
		
	}
	private static Collection<Vertex> MSI(int n, ArrayList<Set<Vertex>> A, Graph graph,  Map<Vertex, Integer> vertexWeights){
		//inizializzo ogni cella dell'arraylist fino alla dimensione corretta
		for (int i = 0; i < n+1; i++) {
		   A.add(new HashSet<>());
		}
		//i vertici potrebbero essere inseriti in ordine sparso, li ordino
		Vertex [] lista = getLinearOrder(graph);
		//in pos 0 non inizializzo perche' vuoto
		A.get(1).add(lista[0]);
		
		for(int i = 2;i<n+1;i++) {
			//non posso inserire questa linea di codice gia' nel set sotto perche' .add resitusice un boolean
			A.get(i-2).add(lista[i-1]);
			A.set(i, maxPeso(A.get(i-1), A.get(i-2),vertexWeights));
		}
		return A.get(n);
	}
	
	private static Vertex[] getLinearOrder(Graph graph) {
		//trovo inizio e fine del grafo lineare
		Vertex inizio = null;
		Vertex ultimo = null;
		Boolean executed = false;
		for(Vertex element : graph.getVertices()) {
			if(graph.getAdjacent(element).size()==1) {
				if(!executed) {
				inizio = element;
				executed = true;
				}
				else {
					ultimo = element;
				}
			}
		}
		//faccio visita in modo da scoprire l'oridne del corpo del grafo
		int numVertici = graph.getVertices().size();
		Vertex [] result = new Vertex[numVertici];
		result[numVertici-1] = ultimo;
		VisitForest visita = new VisitForest(graph, VisitType.DFS);
		visita = graph.getBFSTree(inizio);
		
		Vertex precedente = ultimo;
		for(int i=numVertici-2; i>=0;i--) {
			result[i] = visita.getPartent(precedente);
			precedente = result[i];
		}
		return result;
		
	}
	private static Set<Vertex> maxPeso(Set<Vertex> opzione1, Set<Vertex> opzione2, Map<Vertex, Integer> vertexWeights) {
		int peso1 = 0;
		int peso2 = 0;
		for(Vertex element : opzione1) {
			peso1 += vertexWeights.get(element);
		}
		for(Vertex element : opzione2) {
			peso2 += vertexWeights.get(element);
		}
		
		Set<Vertex> copy;
		if(peso1>=peso2) {
			copy = new HashSet<Vertex>(opzione1);
		}
		else {
			copy = new HashSet<Vertex>(opzione2);
		}
		return copy;
	}
}
