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
import upo.graph.base.Graph;
import upo.graph.base.Vertex;
import upo.graph.base.VisitForest;
import upo.graph.base.VisitForest.Color;
import upo.graph.base.VisitForest.VisitType;

public class IncidListUndir implements Graph {
	//ho scelto linked list perche' almeno mantengo l'ordine dei dati per aggiunta (avevo problemi nella funzione "connectedConponents").
		LinkedHashMap<Vertex,List<Edge>> listaIncid;
		
		public IncidListUndir(){
			listaIncid = new LinkedHashMap<>();
		}
		/*MIA AGGIUNTA DATO CHE L'EQUALS SUGLI ARCHI DI DEFAULT NON RICONOSCE DUE ARCHI CON "source" e "target"
		INVERTITI COME UGUALI
		*/
		private Edge invertEdge(Edge edge) {
			return Edge.getEdgeByVertexes(edge.getTarget(), edge.getSource());
		}
		
		@Override
		public int addVertex(Vertex vertex) {
			listaIncid.put(vertex, new ArrayList<Edge>());
			return listaIncid.size()-1;
		}
		
		@Override
		public Set<Vertex> getVertices() {
			 return listaIncid.keySet();	
		}
		
		@Override
		public Set<Edge> getEdges() {
			Set<Edge> result = new HashSet<>();
			
			listaIncid.forEach((key, value) -> {
				for(Edge elemento : value) {
					result.add(elemento);
				}
			});
			return result;
		}
		
		@Override
		public boolean containsVertex(Vertex vertex) {
			return listaIncid.containsKey(vertex);
		}
		
		@Override
		public void removeVertex(Vertex vertex) throws NoSuchElementException {
			if (listaIncid.remove(vertex) == null) {
				throw new NoSuchElementException();
			}
		}
		
		@Override
		public void addEdge(Edge edge) throws IllegalArgumentException {
			List<Edge> x = listaIncid.get(edge.getSource());
			List<Edge> y = listaIncid.get(edge.getTarget());
			if (x == null || y == null) {
				throw new IllegalArgumentException();
			}
			if(!(x.contains(edge) || x.contains(invertEdge(edge)) || y.contains(edge) || y.contains(invertEdge(edge)))) {
				x.add(edge);
				y.add(edge);
			}
		}
		
		@Override
		public boolean containsEdge(Edge edge) throws IllegalArgumentException {
			Set <Edge> p = getEdges();
			if(!(containsVertex(edge.getSource()) && containsVertex(edge.getTarget()))){
				throw new IllegalArgumentException();
			}
			return p.contains(edge) || p.contains(invertEdge(edge));
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
		}

		@Override
		public Set<Vertex> getAdjacent(Vertex vertex) throws NoSuchElementException {
			if(!containsVertex(vertex)) {
				throw new NoSuchElementException();
			}
			Set<Vertex> result = new HashSet<>();
			for(Edge elemento : listaIncid.get(vertex)) {
				if(elemento.getSource().equals(vertex)) {
					result.add(elemento.getTarget());
				}
				else {
					result.add(elemento.getSource());
				}
			}
			return result;
		}

		@Override
		public boolean isAdjacent(Vertex targetVertex, Vertex sourceVertex) throws IllegalArgumentException {
			if(!(containsVertex(targetVertex) && containsVertex(sourceVertex))) {
				throw new IllegalArgumentException();
			}
			if(getAdjacent(sourceVertex).contains(targetVertex)) {
				return true;
			}
			return false;
		}

		@Override
		public int size() {
			return listaIncid.size();
		}

		@Override
		public boolean isDirected() {
			return false;
		}
		
		private boolean VisitaRicCiclo(VisitForest tree,Vertex u) {
			tree.setColor(u, Color.GRAY);
			for(Vertex element : getAdjacent(u)) {
				if(tree.getColor(element) == Color.WHITE) {
					tree.setParent(element, u);
					if(VisitaRicCiclo(tree,element)) {
						return true;
					}
				}
				else if(tree.getPartent(u) != element) {
					return true;
				}
			}
			tree.setColor(u, Color.BLACK);
			return false;
		}

		@Override
		public boolean isCyclic() {
			VisitForest tree = new VisitForest(this, VisitType.DFS_TOT);
			for(Vertex element : getVertices()) {
				if(tree.getColor(element) == Color.WHITE && VisitaRicCiclo(tree,element)) {
					return true;
				}
			}
			return false;
		}
		

		@Override
		public boolean isDAG() {
			return false;
		}
			
		@Override
		public VisitForest getBFSTree(Vertex startingVertex) throws UnsupportedOperationException, IllegalArgumentException {
			if(!containsVertex(startingVertex)) {
				throw new IllegalArgumentException();
			}
			VisitForest result = new VisitForest(this, VisitType.BFS);
			ArrayList<Vertex> queue = new ArrayList<>();
			Set <Vertex> adjacents = new HashSet<>();
			int ciclo = 0;
			ArrayList<Vertex> trovati = new ArrayList<>();
			
			result.setColor(startingVertex, Color.GRAY);
			result.setDistance(startingVertex, 0);
			queue.add(startingVertex);
			
			while(!queue.isEmpty()) {
				result.setStartTime(queue.get(0), ciclo);
				adjacents = getAdjacent(queue.get(0));
				adjacents.forEach(element -> {
					if(result.getColor(element) == Color.WHITE) {
						result.setColor(element, Color.GRAY);
						result.setParent(element, queue.get(0));
						trovati.add(element);
						queue.add(element);
					}
				});
				for(Vertex elemento : trovati) {
					result.setDistance(elemento, result.getDistance(queue.get(0))+1);
				}
				trovati.clear();
				result.setEndTime(queue.get(0), ciclo);	
				ciclo++;
				result.setColor(queue.get(0), Color.BLACK);
				queue.remove(0);
			}
			return result;
		}
		
		@Override
		public VisitForest getDFSTree(Vertex startingVertex)throws UnsupportedOperationException, IllegalArgumentException {
			if(!containsVertex(startingVertex)) {
				throw new IllegalArgumentException();
			}
			Stack<Vertex> stack = new Stack<Vertex>();
			VisitForest result = new VisitForest(this, VisitType.DFS);
			Set <Vertex> adjacents = new HashSet<>();
			int ciclo = 0;
			result.setColor(startingVertex, Color.GRAY);
			stack.push(startingVertex);
			boolean ifExecuted = false;
			while(!stack.isEmpty()) {
				Vertex subject = stack.peek();
				if(result.getStartTime(subject) == Integer.MAX_VALUE) {
					result.setStartTime(subject, ciclo);
				}
				adjacents = getAdjacent(subject);
				for(Vertex element : adjacents) {
					if(result.getColor(element) == Color.WHITE) {
						result.setColor(element, Color.GRAY);
						result.setParent(element, subject);
						stack.push(element);
						ifExecuted = true;
						break;
					}
				}
				if(!ifExecuted) {
					result.setColor(subject, Color.BLACK);
					result.setEndTime(subject, ciclo+1);
					stack.pop();
					result.setDistance(subject, stack.size());
				}
				ifExecuted = false;
				ciclo++;
			}
			return result;
		}
		//uguale a "getDFSTree" ma richiede un albero di ricerca già creato (in modo da non sovrascrivere i dati già presenti in esso)
		private VisitForest getDFSSameTree(Vertex startingVertex, VisitForest forest, int time)throws IllegalArgumentException {
			if(!containsVertex(startingVertex)) {
				throw new IllegalArgumentException();
			}
			Stack<Vertex> stack = new Stack<Vertex>();
			Set <Vertex> adjacents = new HashSet<>();
			forest.setColor(startingVertex, Color.GRAY);
			stack.push(startingVertex);
			boolean ifExecuted = false;
			while(!stack.isEmpty()) {
				Vertex subject = stack.peek();
				if(forest.getStartTime(subject) == Integer.MAX_VALUE) {
					forest.setStartTime(subject, time);
				}
				adjacents = getAdjacent(subject);
				for(Vertex element : adjacents) {
					if(forest.getColor(element) == Color.WHITE) {
						forest.setColor(element, Color.GRAY);
						forest.setParent(element, subject);
						stack.push(element);
						ifExecuted = true;
						break;
					}
				}
				if(!ifExecuted) {
					forest.setColor(subject, Color.BLACK);
					forest.setEndTime(subject, time+1);
					stack.pop();
					forest.setDistance(subject, stack.size());
				}
				ifExecuted = false;
				time++;
			}
			return forest;
			
		}
		
		@Override
		public VisitForest getDFSTOTForest(Vertex startingVertex) {
			if(!containsVertex(startingVertex)) {
				throw new IllegalArgumentException();
			}
			VisitForest result = new VisitForest(this, VisitType.DFS_TOT);
			result = getDFSTree(startingVertex);
			Set <Vertex> tutti  = getVertices();
			int tempo = result.getEndTime(startingVertex);
			for(Vertex element : tutti) {
				if(result.getColor(element) == Color.WHITE) {
					result = getDFSSameTree(element, result, tempo + 1 );
					tempo = result.getEndTime(element);
				}
			}
			return result;
		}
		
		@Override
		public VisitForest getDFSTOTForest(Vertex[] vertexOrdering) throws UnsupportedOperationException, IllegalArgumentException {
			for(Vertex element : vertexOrdering) {
				if(!containsVertex(element)) {
					throw new IllegalArgumentException();
				}
			}
			VisitForest result = new VisitForest(this, VisitType.DFS_TOT);
			result = getDFSTree(vertexOrdering[0]);
			int tempo = result.getEndTime(vertexOrdering[0]);
			for(Vertex element : vertexOrdering) {
				if(result.getColor(element) == Color.WHITE) {
					result = getDFSSameTree(element, result, tempo + 1);
					tempo = result.getEndTime(element);
				}
			}
			Set <Vertex> tutti  = getVertices();
			for(Vertex element : tutti) {
				if(result.getColor(element) == Color.WHITE) {
					result = getDFSSameTree(element, result, tempo + 1 );
					tempo = result.getEndTime(element);
				}
			}
			return result;
		}
		@Override
		public Vertex[] topologicalSort() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
	
		@Override
		public Set<Set<Vertex>> stronglyConnectedComponents() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
	
		@Override
		public Set<Set<Vertex>> connectedComponents() throws UnsupportedOperationException {
			Set<Set<Vertex>> result = new HashSet<>();
			VisitForest tree = new VisitForest(this, VisitType.DFS);
			for(Vertex element : getVertices()) {
				if(tree.getColor(element) == Color.WHITE) {
					VisitForest CC = getDFSTree(element);
					Set <Vertex> CCVert = new HashSet<>();
					result.add(CCVert);
					for(Vertex vertice : getVertices()) {
						if(CC.getColor(vertice) == Color.BLACK) {
							CCVert.add(vertice);
							tree.setColor(vertice, Color.BLACK);
						}
					}
				}
			}
			return result;
		}
}