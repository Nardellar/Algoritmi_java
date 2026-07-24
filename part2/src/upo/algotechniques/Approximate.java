package upo.algotechniques;

import upo.graph.base.Edge;
import upo.graph.base.Graph;
import upo.graph.base.Vertex;
import upo.graph.base.VisitForest;
import upo.graph.base.VisitForest.VisitType;
import upo.graph.base.WeightedGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

import incidList.IncidListUndirWeight;

public class Approximate {
	
    /** Calcola un ciclo Hamiltoniano di peso al più 2 volte quello di un ciclo Hamiltoniano minimi
     * utilizzando l'algoritmo <strong>approssimato</strong> visto a lezione.
     *
     * @param graph il grafo sul quale applicare l'algoritmo  
     * @return una lista di vertici che rappresenta il ciclo Hamiltoniano calcolato (es. <A, D, C, A>)
     * @throws IllegalArgumentException nel caso in cui il grafo non sia completo o sia orientato (evitate,
     * per semplicità, di verificare la disuguaglianza triangolare su tutte le coppie di archi)
     */
	
	
	
	
	
	
	static public WeightedGraph getPrimMST(Vertex startingVertex, IncidListUndirWeight graph) throws UnsupportedOperationException, IllegalArgumentException {

	    class VertexPrim {
	        Vertex placeholder;
	        Double distance = Double.POSITIVE_INFINITY;
	        boolean def = false;

	        public VertexPrim(Vertex v1) {
	            placeholder = v1;
	        }

	        Vertex getVertex() {
	            return placeholder;
	        }

	        VertexPrim setDistance(Double newDistance) {
	            distance = newDistance;
	            return this;
	        }

	        Double getDistance() {
	            return distance;
	        }

	        void setDef(boolean bol) {
	            def = bol;
	        }

	        boolean isDef() {
	            return def;
	        }

	        @Override
	        public boolean equals(Object obj) {
	            if (this == obj) return true;
	            if (obj == null || getClass() != obj.getClass()) return false;
	            VertexPrim that = (VertexPrim) obj;
	            return Objects.equals(placeholder, that.placeholder);
	        }

	        @Override
	        public int hashCode() {
	            return Objects.hash(placeholder);
	        }
	    }

	    class DistanceComparator implements Comparator<VertexPrim> {
	        @Override
	        public int compare(VertexPrim o1, VertexPrim o2) {
	            return Double.compare(o1.getDistance(), o2.getDistance());
	        }
	    }

	    PriorityQueue<VertexPrim> pQueue = new PriorityQueue<>(new DistanceComparator());
	    Map<Vertex, VertexPrim> vertexMap = new HashMap<>();

	    for (Vertex element : graph.getVertices()) {
	        VertexPrim vp = new VertexPrim(element);
	        if (element.equals(startingVertex)) {
	            vp.setDistance(0d);
	        }
	        pQueue.add(vp);
	        vertexMap.put(element, vp);
	    }

	    VisitForest albero = new VisitForest(graph, VisitType.OTHER);

	    while (!pQueue.isEmpty()) {
	        VertexPrim u = pQueue.poll();
	        u.setDef(true);
	        for (Vertex v : graph.getAdjacent(u.getVertex())) {
	            VertexPrim vPrim = vertexMap.get(v);
	            double edgeWeight = graph.getEdgeWeight(u.getVertex(), vPrim.getVertex());
	            if (!vPrim.isDef() && vPrim.getDistance() > edgeWeight) {
	                pQueue.remove(vPrim);  
	                vPrim.setDistance(edgeWeight);
	                pQueue.add(vPrim);  
	                albero.setParent(vPrim.getVertex(), u.getVertex());
	            }
	        }
	    }

	    IncidListUndirWeight result = new IncidListUndirWeight();
	    for (Vertex element : graph.getVertices()) {
	        result.addVertex(element);
	    }
	    for (Vertex element : graph.getVertices()) {
	        Vertex padre = albero.getPartent(element);
	        if (padre != null) {
	        	Edge mario = graph.findEdge(element, padre);
	            
	            result.addEdge(mario);
	            result.setEdgeWeight(mario, vertexMap.get(element).getDistance());
	        }
	    }
	    return result;
	}
	static private List<Vertex> DFS_ric_INIZIO(Graph graph, Vertex startingVertex){
		VisitForest DFS = graph.getDFSTree(startingVertex);
		List<Vertex> result = new ArrayList<Vertex>();
		result.addAll(Collections.<Vertex>nCopies(graph.getVertices().size()+2,null));
		result.add(startingVertex);
		for(Vertex element : graph.getVertices()) {
			System.out.println(DFS.getStartTime(element));
			result.set(DFS.getStartTime(element), element);
		}
		return result;
	}
	
	
		
				
		
	
    public static List<Vertex> approxTSP(WeightedGraph graph) throws IllegalArgumentException {
    	if(graph.isDirected()) {
    		throw new IllegalArgumentException("grafo orientato");
    	}
    	for(Vertex element : graph.getVertices()) {
			Set<Vertex> altriVertici = graph.getVertices();
			//altriVertici.remove(element);
			if(graph.getAdjacent(element)== null || graph.getAdjacent(element).equals(altriVertici)) {
				throw new IllegalArgumentException("grafo non completo");
			}
			
		}
    	
    	Vertex r = (Vertex) graph.getVertices().toArray()[0];
    	
    	WeightedGraph MAR = getPrimMST(r, (IncidListUndirWeight)graph );
    	List<Vertex> ord = DFS_ric_INIZIO(MAR, r);
    	return ord;	
	}
}
