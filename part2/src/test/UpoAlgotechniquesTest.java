package test;

import static org.junit.jupiter.api.Assertions.*;  
import org.junit.jupiter.api.Test;

import upo.algotechniques.*;
import upo.graph.base.Edge;
import upo.graph.base.Vertex;
import upo.graph.base.WeightedGraph;
import incidList.*;
import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;

class UpoAlgotechniquesTest {

	@Test
	void getMooreMaxJobs() {	//jobs:{6,2,0,4,1,5,3,7}
		Integer [] duration = new Integer [] {7,6,4,6,1,8,3,10};
		Integer [] deadline = new Integer [] {28,9,6,20,8,25,11,35};
		Integer [] result = Greedy.getMooreMaxJobs(duration, deadline);
		
	   //Jobs ordinati:{0,1,2,3,4,5,6,7}
		assertEquals(0,result[0]); //Job 0 salvato in pos 0 
		assertEquals(1,result[1]); //Job 1 salvato in pos 1
		assertEquals(3,result[2]); //Job 2 salvato in pos 2 Job 3 rifiutato
		assertEquals(4,result[3]); //Job 4 salvato in pos 3
		assertEquals(6,result[4]); //Job 6 salvato in pos 4 Job 5 rifiutato
		assertEquals(7,result[5]); //Job 7 salvato in pos 5
		assertEquals(6,result.length);
	}
	
	

	@Test
	void getMSITest() {
		/*	GRAFO:
		 * Grafo:

			 10 - 5 - 15 - 20 - 2 - 30 - 40	risposta:[10,20,30]
			  
			 
		 */
		//costruzione grafo:
		IncidListUndir pippo = new IncidListUndir();
		
		Vertex mario = Vertex.getVertexByLabel("mario");
		Vertex pablo = Vertex.getVertexByLabel("pablo");
		Vertex che = Vertex.getVertexByLabel("che");
		Vertex loyd = Vertex.getVertexByLabel("loyd");
		Vertex clemenceau = Vertex.getVertexByLabel("clemenceau");
		Vertex kennedy = Vertex.getVertexByLabel("kennedy");
		Vertex mackensen = Vertex.getVertexByLabel("mackensen");

		pippo.addVertex(mario);
		pippo.addVertex(pablo);
		pippo.addVertex(che);
		pippo.addVertex(loyd);
		pippo.addVertex(clemenceau);
		pippo.addVertex(kennedy);
		pippo.addVertex(mackensen);


		Edge e1 = Edge.getEdgeByVertexes(mario, pablo);
		Edge e2 = Edge.getEdgeByVertexes(pablo, che);
		Edge e3 = Edge.getEdgeByVertexes(che, loyd);
		Edge e4 = Edge.getEdgeByVertexes(loyd, clemenceau);
		Edge e5 = Edge.getEdgeByVertexes(clemenceau, kennedy);
		Edge e6 = Edge.getEdgeByVertexes(kennedy, mackensen);


		pippo.addEdge(e1);
		pippo.addEdge(e2);
		pippo.addEdge(e3);
		pippo.addEdge(e4);
		pippo.addEdge(e5);
		pippo.addEdge(e6);

		
		HashMap<Vertex, Integer> vertexWeights = new HashMap<Vertex, Integer>();
		//inseriti volutamente in ordine sparso
		vertexWeights.put(mario, 10);
		vertexWeights.put(pablo, 5);
		vertexWeights.put(kennedy, 30);
		vertexWeights.put(loyd, 20);
		vertexWeights.put(che, 15);
		vertexWeights.put(clemenceau, 2);
		vertexWeights.put(mackensen, 40);
		
		
		
		Collection <Vertex> result = DynamicProgramming.getMSI(pippo, vertexWeights);
		assertEquals(3,result.size());
		assertTrue(result.contains(mario));
		assertTrue(result.contains(loyd));
		assertTrue(result.contains(mackensen));
		
		
		//vertice in piu' in grafo
		Assertions.assertThrows(IllegalArgumentException.class, ()->
		{
			Vertex errore = Vertex.getVertexByLabel("errore");
			pippo.addVertex(errore);
			Collection <Vertex> a = DynamicProgramming.getMSI(pippo, vertexWeights);
		});
		
		//vertice in piu' in vertexWeights
		Assertions.assertThrows(IllegalArgumentException.class, ()->
		{
			Vertex errore = Vertex.getVertexByLabel("errore");
			vertexWeights.put(errore, 1777);
			Collection <Vertex> a = DynamicProgramming.getMSI(pippo, vertexWeights);
		});
		//nodo con 3 archi
		Assertions.assertThrows(IllegalArgumentException.class, ()->
		{
			Vertex errore = Vertex.getVertexByLabel("errore");
			pippo.addVertex(errore);
			vertexWeights.put(errore, 1777);
			Edge sbagliato = Edge.getEdgeByVertexes(pablo, errore);
			Collection <Vertex> a = DynamicProgramming.getMSI(pippo, vertexWeights);
		});
		//nodo irraggiungibile
		Assertions.assertThrows(IllegalArgumentException.class, ()->
		{
			Vertex errore = Vertex.getVertexByLabel("errore");
			pippo.addVertex(errore);
			vertexWeights.put(errore, 1777);
			Collection <Vertex> a = DynamicProgramming.getMSI(pippo, vertexWeights);
		});
		
		//grafo ciclico
		Assertions.assertThrows(IllegalArgumentException.class, ()->
		{
			Vertex errore = Vertex.getVertexByLabel("errore");
			pippo.addVertex(errore);
			vertexWeights.put(errore, 1777);
			Edge sbagliato = Edge.getEdgeByVertexes(mario, errore);
			Edge sbagliato1 = Edge.getEdgeByVertexes(kennedy, errore);
			Collection <Vertex> a = DynamicProgramming.getMSI(pippo, vertexWeights);
		});
	}
	
	@Test
	void Prim() {
		IncidListUndirWeight grafo = new IncidListUndirWeight();
		Vertex a = Vertex.getVertexByLabel("a");
		Vertex b = Vertex.getVertexByLabel("b");
		Vertex c = Vertex.getVertexByLabel("c");
		Vertex d = Vertex.getVertexByLabel("d");
		Vertex e = Vertex.getVertexByLabel("e");
		
		grafo.addVertex(a);
		grafo.addVertex(b);
		grafo.addVertex(c);
		grafo.addVertex(d);
		grafo.addVertex(e);

		Edge e1 = Edge.getEdgeByVertexes(a, b);
		Edge e2 = Edge.getEdgeByVertexes(b, d);
		Edge e3 = Edge.getEdgeByVertexes(d, e);
		Edge e4 = Edge.getEdgeByVertexes(e, c);
		Edge e5 = Edge.getEdgeByVertexes(c, a);
		
		grafo.addEdge(e1);
		grafo.addEdge(e2);
		grafo.addEdge(e3);
		grafo.addEdge(e4);
		grafo.addEdge(e5);
		
		grafo.setEdgeWeight(e1, 2);
		grafo.setEdgeWeight(e2, 3);
		grafo.setEdgeWeight(e3, 2);
		grafo.setEdgeWeight(e4, 2);
		grafo.setEdgeWeight(e5, 4);


		WeightedGraph result = Approximate.getPrimMST(a, grafo);
		
		assertTrue(grafo.getVertices().equals(result.getVertices()));
		assertTrue(grafo.getEdges().size() > result.getEdges().size());
		assertEquals(4, result.getEdges().size());
		assertTrue(result.containsEdge(e1));
		assertTrue(result.containsEdge(e2));
		assertTrue(result.containsEdge(e3));
		assertTrue(result.containsEdge(e4));
		assertEquals(2, result.getEdgeWeight(e1));
		assertEquals(3, result.getEdgeWeight(e2));
		assertEquals(2, result.getEdgeWeight(e3));
		assertEquals(2, result.getEdgeWeight(e4));	
	}
	@Test
	void Prim2() {
		IncidListUndirWeight grafo = new IncidListUndirWeight();
		Vertex v1 = Vertex.getVertexByLabel("v1");
		Vertex v2 = Vertex.getVertexByLabel("v2");
		Vertex v3 = Vertex.getVertexByLabel("v3");
		Vertex v4= Vertex.getVertexByLabel("v4");
		Vertex v5 = Vertex.getVertexByLabel("v5");
		
		grafo.addVertex(v1);
		grafo.addVertex(v2);
		grafo.addVertex(v3);
		grafo.addVertex(v4);
		grafo.addVertex(v5);

		Edge e1 = Edge.getEdgeByVertexes(v1, v2);
		Edge e2 = Edge.getEdgeByVertexes(v1, v3);
		Edge e3 = Edge.getEdgeByVertexes(v1, v4);
		Edge e4 = Edge.getEdgeByVertexes(v1, v5);
		Edge e5 = Edge.getEdgeByVertexes(v2, v3);
		Edge e6 = Edge.getEdgeByVertexes(v2, v4);
		Edge e7 = Edge.getEdgeByVertexes(v2, v5);
		Edge e8 = Edge.getEdgeByVertexes(v3, v4);
		Edge e9 = Edge.getEdgeByVertexes(v3, v5);
		Edge e10 = Edge.getEdgeByVertexes(v4, v5);


		grafo.addEdge(e1);
		grafo.addEdge(e2);
		grafo.addEdge(e3);
		grafo.addEdge(e4);
		grafo.addEdge(e5);
		grafo.addEdge(e6);
		grafo.addEdge(e7);
		grafo.addEdge(e8);
		grafo.addEdge(e9);
		grafo.addEdge(e10);

		
		grafo.setEdgeWeight(e1, 5);
		grafo.setEdgeWeight(e2, 4);
		grafo.setEdgeWeight(e3, 3);
		grafo.setEdgeWeight(e4, 2);
		grafo.setEdgeWeight(e5, 2);
		grafo.setEdgeWeight(e6, 6);
		grafo.setEdgeWeight(e7, 3);
		grafo.setEdgeWeight(e8, 4);
		grafo.setEdgeWeight(e9, 2);
		grafo.setEdgeWeight(e10, 5);


		WeightedGraph result = Approximate.getPrimMST(v5, grafo);
		
		assertFalse(result.isCyclic());
		assertTrue(grafo.getVertices().equals(result.getVertices()));
		assertTrue(grafo.getEdges().size() > result.getEdges().size());
		assertEquals(4, result.getEdges().size());
		assertTrue(result.containsEdge(e4));
		assertTrue(result.containsEdge(e5));
		assertTrue(result.containsEdge(e9));
		assertTrue(result.containsEdge(e3));
		System.out.println(grafo.getEdgeWeight(e4));
		System.out.println(grafo.getEdgeWeight(e5));
		System.out.println(grafo.getEdgeWeight(e9));
		System.out.println(grafo.getEdgeWeight(e3));
		assertEquals(2, result.getEdgeWeight(e4));
		assertEquals(2, result.getEdgeWeight(e5));
		assertEquals(2, result.getEdgeWeight(e9));
		assertEquals(3, result.getEdgeWeight(e3));
	}
	
	
	@Test
	void approxTSPTest() {
		IncidListUndirWeight grafo = new IncidListUndirWeight();
		Vertex orlando = Vertex.getVertexByLabel("orlando");
		Vertex cadorna = Vertex.getVertexByLabel("cadorna");
		Vertex falkenhayn = Vertex.getVertexByLabel("falkenhayn");
		Vertex loyd = Vertex.getVertexByLabel("loyd");
		Vertex clemenceau = Vertex.getVertexByLabel("clemenceau");		

		grafo.addVertex(orlando);
		grafo.addVertex(cadorna);
		grafo.addVertex(falkenhayn);
		grafo.addVertex(loyd);
		grafo.addVertex(clemenceau);
		
		Edge e1 = Edge.getEdgeByVertexes(orlando, cadorna);
		Edge e2 = Edge.getEdgeByVertexes(orlando, falkenhayn);
		Edge e3 = Edge.getEdgeByVertexes(orlando, loyd);
		Edge e4 = Edge.getEdgeByVertexes(orlando, clemenceau);
		Edge e5 = Edge.getEdgeByVertexes(cadorna, falkenhayn);
		Edge e6 = Edge.getEdgeByVertexes(cadorna, loyd);
		Edge e7 = Edge.getEdgeByVertexes(cadorna, clemenceau);
		Edge e8 = Edge.getEdgeByVertexes(falkenhayn, loyd);
		Edge e9 = Edge.getEdgeByVertexes(falkenhayn, clemenceau);
		Edge e10 = Edge.getEdgeByVertexes(loyd, clemenceau);
		
		
		grafo.addEdge(e1);
		grafo.addEdge(e2);
		grafo.addEdge(e3);
		grafo.addEdge(e4);
		grafo.addEdge(e5);
		grafo.addEdge(e6);
		grafo.addEdge(e7);
		grafo.addEdge(e8);
		grafo.addEdge(e9);
		grafo.addEdge(e10);


		grafo.setEdgeWeight(e1, 5);
		grafo.setEdgeWeight(e2, 4);
		grafo.setEdgeWeight(e3, 3);
		grafo.setEdgeWeight(e4, 2);
		grafo.setEdgeWeight(e5, 2);
		grafo.setEdgeWeight(e6, 6);
		grafo.setEdgeWeight(e7, 3);
		grafo.setEdgeWeight(e8, 4);
		grafo.setEdgeWeight(e9, 2);
		grafo.setEdgeWeight(e10, 5);
		
		List<Vertex> result = Approximate.approxTSP(grafo);
		assertEquals(8, result.size());
		assertTrue(result.containsAll(grafo.getVertices()));
		assertTrue(result.contains(orlando));
		assertTrue(result.contains(cadorna));
		assertTrue(result.contains(falkenhayn));
		assertTrue(result.contains(loyd));
		assertTrue(result.contains(clemenceau));	
	}
		
	
	
	
}
