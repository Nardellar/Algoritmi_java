package upo.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

import upo.graph.base.Edge;
import upo.graph.base.Vertex;
import upo.graph.base.VisitForest;

import upo.graph.base.WeightedGraph;
import upo.graph.base.VisitForest.Color;
import upo.graph.base.VisitForest.VisitType;

public class IncidListUndirWeight extends IncidListUndir implements upo.graph.base.WeightedGraph {
	//ho scelto limked list perche' almeno mantengo l'ordine dei dati per aggiunta (avevo problemi nella funzione "connectedConponents");.
	HashMap <Edge, Double> listaPeso;
	
	/*MIA AGGIUNTA DATO CHE L'EQUALS SUGLI ARCHI DI DEFAULT NON RICONOSCE DUE ARCHI CON "source" e "target"
	INVERTITI COME UGUALI
	*/
	public Edge invertEdge(Edge edge) {
		return Edge.getEdgeByVertexes(edge.getTarget(), edge.getSource());
	}
	
	public IncidListUndirWeight(){
		listaIncid = new LinkedHashMap<>();
		listaPeso = new HashMap<>();
	}

	@Override
	public void addEdge(Edge edge) throws IllegalArgumentException {
		List<Edge> x = listaIncid.get(edge.getSource());
		List<Edge> y = listaIncid.get(edge.getTarget());
		if (x == null || y == null) {
			System.out.println(x + "  " + y + listaIncid.size());
			throw new IllegalArgumentException();
		}
		if(!(x.contains(edge) || x.contains(invertEdge(edge)) || y.contains(edge) || y.contains(invertEdge(edge)))) {
			x.add(edge);
			y.add(edge);
			listaPeso.put(edge, defaultEdgeWeight);	
		}
		
	}

	@Override
	public void removeEdge(Edge edge) throws IllegalArgumentException, NoSuchElementException {
		if(!(containsVertex(edge.getSource())) && containsVertex(edge.getTarget())){
			throw new IllegalArgumentException();
		}
		if(!(containsEdge(edge) || containsEdge(invertEdge(edge)))) {
			throw new NoSuchElementException();
		}
		if (!listaIncid.get(edge.getSource()).remove(edge)) {
			listaIncid.get(edge.getSource()).remove(invertEdge(edge));
		}
		if (!listaIncid.get(edge.getTarget()).remove(edge)) {
			listaIncid.get(edge.getTarget()).remove(invertEdge(edge));
		}
		if (listaPeso.remove(edge) == null) {
			listaPeso.remove(invertEdge(edge));
		}
	}
	
	@Override
	public double getEdgeWeight(Edge edge) throws IllegalArgumentException, NoSuchElementException {
		if(!containsEdge(edge)) {
			throw new NoSuchElementException();
		}
		return listaPeso.get(edge) ;
	}

	@Override
	public void setEdgeWeight(Edge edge, double weight) throws IllegalArgumentException, NoSuchElementException {
		if(!containsEdge(edge)) {
			throw new NoSuchElementException();
		}
		listaPeso.put(edge, weight);
	}
	
	//NON IMPLEMENTARE
	@Override
	public WeightedGraph getBellmanFordShortestPaths(Vertex startingVertex)throws UnsupportedOperationException, IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	//NON IMPLEMENTARE
	@Override
	public WeightedGraph getDijkstraShortestPaths(Vertex startingVertex)throws UnsupportedOperationException, IllegalArgumentException {
		throw new UnsupportedOperationException();
	}
	//NON IMPLEMENTARE
	@Override
	public WeightedGraph getPrimMST(Vertex startingVertex)throws UnsupportedOperationException, IllegalArgumentException {
		throw new UnsupportedOperationException();
	}
	//NON IMPLEMENTARE
	@Override
	public WeightedGraph getKruskalMST() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	//NON IMPLEMENTARE
	@Override
	public WeightedGraph getFloydWarshallShortestPaths() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	
}
