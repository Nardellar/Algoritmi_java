package incidList;

import static org.junit.jupiter.api.Assertions.assertEquals;  
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import upo.graph.base.Edge;
import upo.graph.base.Vertex;
import upo.graph.base.VisitForest;
import upo.graph.base.VisitForest.Color;

public class IncidListUndirTest {
	IncidListUndir pippo;
	@BeforeEach
	void constructor() {
		pippo = new IncidListUndir();
	}
	@Test
	void addVertex() {
		assertEquals(0, pippo.addVertex(Vertex.getVertexByLabel("mario")));
		assertEquals(1, pippo.size());
		assertTrue(pippo.containsVertex(Vertex.getVertexByLabel("mario")));
	}
	@Test
	void getVertices() {
		pippo.addVertex(Vertex.getVertexByLabel("mario"));
		pippo.addVertex(Vertex.getVertexByLabel("gianni"));
		pippo.addVertex(Vertex.getVertexByLabel("odoacre"));
		assertTrue(pippo.getVertices().contains(Vertex.getVertexByLabel("mario")));
		assertTrue(pippo.getVertices().contains(Vertex.getVertexByLabel("gianni")));
		assertTrue(pippo.getVertices().size() == 3);
		assertTrue(pippo.getVertices().contains(Vertex.getVertexByLabel("odoacre")));
		assertFalse(pippo.getVertices().contains(Vertex.getVertexByLabel("fabio")));
	}
	@Test
	void getEdges() {
		Vertex mario = Vertex.getVertexByLabel("mario");
		Vertex gianni = Vertex.getVertexByLabel("gianni");
		Vertex odoacre = Vertex.getVertexByLabel("odoacre");
		pippo.addVertex(mario);
		pippo.addVertex(gianni);
		pippo.addVertex(odoacre);
		Edge e1 = Edge.getEdgeByVertexes(mario, gianni);
		Edge e2 = Edge.getEdgeByVertexes(gianni, mario);
		Edge e3 = Edge.getEdgeByVertexes(odoacre, mario);
		pippo.addEdge(e1);
		pippo.addEdge(e2);
		assertEquals(1, pippo.getEdges().size());
		assertTrue(pippo.getEdges().contains(e1));
		assertFalse(pippo.getEdges().contains(e2));
		assertFalse(pippo.getEdges().contains(e3));
	}
	@Test
	void containsVertex() {
		pippo.addVertex(Vertex.getVertexByLabel("mario"));
		pippo.addVertex(Vertex.getVertexByLabel("gianni"));
		pippo.addVertex(Vertex.getVertexByLabel("odoacre"));
		assertTrue(pippo.containsVertex(Vertex.getVertexByLabel("mario")));
		assertTrue(pippo.containsVertex(Vertex.getVertexByLabel("gianni")));
		assertTrue(pippo.containsVertex(Vertex.getVertexByLabel("odoacre")));
		assertFalse(pippo.containsVertex(Vertex.getVertexByLabel("fabio")));
	}
	@Test
	void removeVertex() {
		Vertex mario = Vertex.getVertexByLabel("mario");
		Vertex gianni = Vertex.getVertexByLabel("gianni");
		Vertex odoacre = Vertex.getVertexByLabel("odoacre");
		Vertex mancante = Vertex.getVertexByLabel("mancante");
		
		pippo.addVertex(mario);
		pippo.addVertex(gianni);
		pippo.addVertex(odoacre);
		pippo.removeVertex(mario);
		assertFalse(pippo.containsVertex(mario));
		NoSuchElementException nsee = assertThrows(NoSuchElementException.class, () -> {
			pippo.removeVertex(mancante);
		 });
	}
	@Test
	void addEdge() {
		Vertex mario = Vertex.getVertexByLabel("mario");
		Vertex gianni = Vertex.getVertexByLabel("gianni");
		Vertex odoacre = Vertex.getVertexByLabel("odoacre");
		pippo.addVertex(mario);
		pippo.addVertex(gianni);
		pippo.addVertex(odoacre);
		Edge e1 = Edge.getEdgeByVertexes(mario, gianni);
		Edge e2 = Edge.getEdgeByVertexes(mario, odoacre);
		pippo.addEdge(e1);
		pippo.addEdge(e2);
		assertTrue(pippo.containsEdge(e1));
		assertTrue(pippo.containsEdge(e2));
		Edge e3 = Edge.getEdgeByVertexes(odoacre, gianni);
		assertFalse(pippo.containsEdge(e3));
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			Vertex mancante = Vertex.getVertexByLabel("mancante");
			Edge e4 = Edge.getEdgeByVertexes(odoacre, mancante);
			pippo.containsEdge(e4);
		 });
		
	}
	@Test
	void containsEdge() {
		Vertex mario = Vertex.getVertexByLabel("mario");
		Vertex gianni = Vertex.getVertexByLabel("gianni");
		Vertex odoacre = Vertex.getVertexByLabel("odoacre");
		Vertex mancante = Vertex.getVertexByLabel("mancante");
		pippo.addVertex(mario);
		pippo.addVertex(gianni);
		pippo.addVertex(odoacre);
		
		Edge e1 = Edge.getEdgeByVertexes(mario, gianni);
		Edge e2 = Edge.getEdgeByVertexes(mario, odoacre);
		Edge inesistente = Edge.getEdgeByVertexes(gianni, odoacre);
		pippo.addEdge(e1);
		pippo.addEdge(e2);
		assertTrue(pippo.containsEdge(e1));
		Edge inversoE1 = Edge.getEdgeByVertexes(mario, gianni);
		assertTrue(pippo.containsEdge(inversoE1));
		assertTrue(pippo.containsEdge(e2));
		assertFalse(pippo.containsEdge(inesistente));
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			Edge e3 = Edge.getEdgeByVertexes(mancante, gianni);
			pippo.containsEdge(e3);
		 });

	}
	@Test
	void removeEdge() {
		Vertex mario = Vertex.getVertexByLabel("mario");
		Vertex gianni = Vertex.getVertexByLabel("gianni");
		Vertex odoacre = Vertex.getVertexByLabel("odoacre");
		Vertex mancante = Vertex.getVertexByLabel("mancante");
		pippo.addVertex(mario);
		pippo.addVertex(gianni);
		pippo.addVertex(odoacre);
		
		Edge e1 = Edge.getEdgeByVertexes(mario, gianni);
		Edge e2 = Edge.getEdgeByVertexes(mario, odoacre);
		Edge e3 = Edge.getEdgeByVertexes(mancante, gianni);
		pippo.addEdge(e1);
		pippo.addEdge(e2);
		pippo.removeEdge(e2);
		assertTrue(pippo.containsEdge(e1));
		assertFalse(pippo.containsEdge(e2));
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			pippo.removeEdge(e3);
		 });
		NoSuchElementException nsee = assertThrows(NoSuchElementException.class, () -> {
			pippo.removeEdge(e2);
		 });
	}
	@Test
	void getAdjacent() {
		Vertex mario = Vertex.getVertexByLabel("mario");
		Vertex gianni = Vertex.getVertexByLabel("gianni");
		Vertex odoacre = Vertex.getVertexByLabel("odoacre");
		Vertex pierpaolo = Vertex.getVertexByLabel("pierpaolo");
		
		pippo.addVertex(mario);
		pippo.addVertex(gianni);
		pippo.addVertex(odoacre);
		pippo.addVertex(pierpaolo);
		Edge e1 = Edge.getEdgeByVertexes(mario, gianni);
		Edge e2 = Edge.getEdgeByVertexes(mario, odoacre);
		Edge e3 = Edge.getEdgeByVertexes(gianni, pierpaolo);
		pippo.addEdge(e1);
		pippo.addEdge(e2);
		pippo.addEdge(e3);
		assertTrue(pippo.getAdjacent(mario).contains(odoacre));
		assertTrue(pippo.getAdjacent(odoacre).contains(mario));
		assertTrue(pippo.getAdjacent(mario).contains(gianni));
		assertFalse(pippo.getAdjacent(mario).contains(pierpaolo));
		assertTrue(pippo.getAdjacent(mario).size() == 2);
		NoSuchElementException nsee = assertThrows(NoSuchElementException.class, () ->{
			Vertex mancante = Vertex.getVertexByLabel("mancante");
			pippo.getAdjacent(mancante);
		});
	}
	@Test
	void isAdjacent() {
		Vertex mario = Vertex.getVertexByLabel("mario");
		Vertex gianni = Vertex.getVertexByLabel("gianni");
		Vertex odoacre = Vertex.getVertexByLabel("odoacre");
		Vertex pierpaolo = Vertex.getVertexByLabel("pierpaolo");
		
		pippo.addVertex(mario);
		pippo.addVertex(gianni);
		pippo.addVertex(odoacre);
		pippo.addVertex(pierpaolo);
		Edge e1 = Edge.getEdgeByVertexes(mario, gianni);
		Edge e2 = Edge.getEdgeByVertexes(mario, odoacre);
		Edge e3 = Edge.getEdgeByVertexes(gianni, pierpaolo);
		pippo.addEdge(e1); 
		pippo.addEdge(e2);
		pippo.addEdge(e3);
		assertTrue(pippo.isAdjacent(mario, gianni));
		assertTrue(pippo.isAdjacent(gianni, mario));
		assertTrue(pippo.isAdjacent(odoacre, mario));
		assertFalse(pippo.isAdjacent(mario, pierpaolo));
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			Vertex mancante = Vertex.getVertexByLabel("mancante");
			pippo.isAdjacent(gianni, mancante);
		 });
	}
	@Test
	void isCyclic() {
		Vertex mario = Vertex.getVertexByLabel("mario");
		Vertex gianni = Vertex.getVertexByLabel("gianni");
		Vertex odoacre = Vertex.getVertexByLabel("odoacre");
		Vertex pierpaolo = Vertex.getVertexByLabel("pierpaolo");
		
		pippo.addVertex(mario);
		pippo.addVertex(gianni);
		pippo.addVertex(odoacre);
		pippo.addVertex(pierpaolo);
		
		Edge e1 = Edge.getEdgeByVertexes(mario, gianni);
		Edge e2 = Edge.getEdgeByVertexes(mario, odoacre);
		Edge e3 = Edge.getEdgeByVertexes(gianni, pierpaolo);
		
		pippo.addEdge(e1); 
		pippo.addEdge(e2);
		pippo.addEdge(e3);
		
		assertFalse(pippo.isCyclic());
		Vertex staccato1 = Vertex.getVertexByLabel("staccato1");
		Vertex staccato2 = Vertex.getVertexByLabel("staccato2");
		Vertex staccato3 = Vertex.getVertexByLabel("staccato3");
		pippo.addVertex(staccato1);
		pippo.addVertex(staccato2);
		pippo.addVertex(staccato3);
		Edge n1 = Edge.getEdgeByVertexes(staccato1, staccato2);
		Edge n2 = Edge.getEdgeByVertexes(staccato2, staccato3);
		Edge n3 = Edge.getEdgeByVertexes(staccato3, staccato1);
		pippo.addEdge(n1);
		pippo.addEdge(n2);
		pippo.addEdge(n3);
		assertTrue(pippo.isCyclic());
	}
	@Test
	void getBFSTree() {
		Vertex A = Vertex.getVertexByLabel("A");
		Vertex B = Vertex.getVertexByLabel("B");
		Vertex C = Vertex.getVertexByLabel("C");
		Vertex D = Vertex.getVertexByLabel("D");
		Vertex E = Vertex.getVertexByLabel("E");
		Vertex F = Vertex.getVertexByLabel("F");
		Vertex G = Vertex.getVertexByLabel("G");
		Vertex H = Vertex.getVertexByLabel("H");
		
		pippo.addVertex(A);
		pippo.addVertex(B);
		pippo.addVertex(C);
		pippo.addVertex(D);
		pippo.addVertex(E);
		pippo.addVertex(F);
		pippo.addVertex(G);
		pippo.addVertex(H);
		Edge e1 = Edge.getEdgeByVertexes(A, B);
		Edge e2 = Edge.getEdgeByVertexes(A, D);
		Edge e3 = Edge.getEdgeByVertexes(B, C);
		Edge e4 = Edge.getEdgeByVertexes(B, F);
		Edge e5 = Edge.getEdgeByVertexes(C, D);
		Edge e6 = Edge.getEdgeByVertexes(C, E);
		Edge e7 = Edge.getEdgeByVertexes(D, H);
		Edge e8 = Edge.getEdgeByVertexes(E, F);
		Edge e9 = Edge.getEdgeByVertexes(E, H);
		Edge e10 = Edge.getEdgeByVertexes(F, G);
		Edge e11 = Edge.getEdgeByVertexes(G, H);
		pippo.addEdge(e1); 
		pippo.addEdge(e2);
		pippo.addEdge(e3);
		pippo.addEdge(e4); 
		pippo.addEdge(e5);
		pippo.addEdge(e6);
		pippo.addEdge(e7); 
		pippo.addEdge(e8);
		pippo.addEdge(e9);
		pippo.addEdge(e10);
		pippo.addEdge(e11);
		VisitForest result = pippo.getBFSTree(A);
		//NODO A
		assertTrue(result.getRoots().contains(A));
		assertTrue(result.getRoots().size()==1);
		assertTrue(result.getDistance(A)==0);
		assertTrue(result.getStartTime(A)==0);
		assertTrue(result.getEndTime(A)==0);
		assertTrue(result.getPartent(A)==null);
		//NODO B	
		assertTrue(result.getDistance(B)==1);
		assertTrue(result.getStartTime(B)==1);
		assertTrue(result.getEndTime(B)==1);
		assertTrue(result.getPartent(B)==A);
		//NODO C
		assertTrue(result.getDistance(C)==2);
		assertTrue(result.getStartTime(C)==3);
		assertTrue(result.getEndTime(C)==3);
		assertTrue(result.getPartent(C)==B);
		//NODO D
		assertTrue(result.getDistance(D)==1);
		assertTrue(result.getStartTime(D)==2);
		assertTrue(result.getEndTime(D)==2);
		assertTrue(result.getPartent(D)==A);
		//NODO E
		assertTrue(result.getDistance(E)==3);
		assertTrue(result.getStartTime(E)==6);
		assertTrue(result.getEndTime(E) == 6);
		assertTrue(result.getPartent(E)==C);
		//NODO F
		assertTrue(result.getDistance(F)==2);
		assertTrue(result.getStartTime(F)==4);
		assertTrue(result.getEndTime(F)==4);
		assertTrue(result.getPartent(F)==B);
		//NODO G
		assertTrue(result.getDistance(G)==3);
		assertTrue(result.getStartTime(G)==7);
		assertTrue(result.getEndTime(G)==7);
		assertTrue(result.getPartent(G)==F);
		//NODO H
		assertTrue(result.getDistance(H)==2);
		assertTrue(result.getStartTime(H)==5);
		assertTrue(result.getEndTime(H)==5);
		assertTrue(result.getPartent(H)==D);
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			Vertex mancante = Vertex.getVertexByLabel("mancante");
			pippo.getBFSTree(mancante);
		 });
	}
	@Test
	void getDFSTree() {
		Vertex A = Vertex.getVertexByLabel("A");
		Vertex B = Vertex.getVertexByLabel("B");
		Vertex C = Vertex.getVertexByLabel("C");
		Vertex D = Vertex.getVertexByLabel("D");
		Vertex E = Vertex.getVertexByLabel("E");
		Vertex F = Vertex.getVertexByLabel("F");
		Vertex G = Vertex.getVertexByLabel("G");
		Vertex H = Vertex.getVertexByLabel("H");
		
		pippo.addVertex(A);
		pippo.addVertex(B);
		pippo.addVertex(C);
		pippo.addVertex(D);
		pippo.addVertex(E);
		pippo.addVertex(F);
		pippo.addVertex(G);
		pippo.addVertex(H);
		Edge e1 = Edge.getEdgeByVertexes(A, B);
		Edge e2 = Edge.getEdgeByVertexes(A, G);
		Edge e3 = Edge.getEdgeByVertexes(B, H);
		Edge e4 = Edge.getEdgeByVertexes(B, C);
		Edge e5 = Edge.getEdgeByVertexes(C, D);
		Edge e6 = Edge.getEdgeByVertexes(C, G);
		Edge e7 = Edge.getEdgeByVertexes(D, H);
		Edge e8 = Edge.getEdgeByVertexes(D, F);
		Edge e9 = Edge.getEdgeByVertexes(E, H);
		Edge e10 = Edge.getEdgeByVertexes(E, F);
		Edge e11 = Edge.getEdgeByVertexes(F, G);
		pippo.addEdge(e1); 
		pippo.addEdge(e2);
		pippo.addEdge(e3);
		pippo.addEdge(e4); 
		pippo.addEdge(e5);
		pippo.addEdge(e6);
		pippo.addEdge(e7); 
		pippo.addEdge(e8);
		pippo.addEdge(e9);
		pippo.addEdge(e10);
		pippo.addEdge(e11);
		VisitForest result = pippo.getDFSTree(A);
		//NODO A
		assertTrue(result.getRoots().contains(A));
		assertTrue(result.getRoots().size()==1);
		assertEquals(0,result.getDistance(A));
		assertEquals(0, result.getStartTime(A));
		assertEquals(15, result.getEndTime(A));
		assertTrue(result.getPartent(A)==null);
		assertTrue(result.getColor(A) == Color.BLACK);
		//NODO B	
		assertTrue(result.getDistance(B)==1);
		assertEquals(1, result.getStartTime(B));
		assertTrue(result.getEndTime(B)==14);
		assertTrue(result.getPartent(B)==A);
		assertTrue(result.getColor(B) == Color.BLACK);
		//NODO C
		assertTrue(result.getDistance(C)==2);
		assertTrue(result.getStartTime(C)==2);
		assertTrue(result.getEndTime(C)==13);
		assertTrue(result.getPartent(C)==B);
		assertTrue(result.getColor(C) == Color.BLACK);
		//NODO D
		assertTrue(result.getDistance(D)==3);
		assertTrue(result.getStartTime(D)==3);
		assertTrue(result.getEndTime(D)==12);
		assertTrue(result.getPartent(D)==C);
		assertTrue(result.getColor(D) == Color.BLACK);
		//NODO E
		assertTrue(result.getDistance(E)==5);
		assertTrue(result.getStartTime(E)==5);
		assertEquals(8, result.getEndTime(E));
		assertTrue(result.getPartent(E)==F);
		assertTrue(result.getColor(E) == Color.BLACK);
		//NODO F
		assertTrue(result.getDistance(F)==4);
		assertTrue(result.getStartTime(F)==4);
		assertTrue(result.getEndTime(F)==11);
		assertTrue(result.getPartent(F)==D);
		assertTrue(result.getColor(F) == Color.BLACK);
		//NODO G
		assertTrue(result.getDistance(G)==5);
		assertTrue(result.getStartTime(G)==9);
		assertTrue(result.getEndTime(G)==10);
		assertTrue(result.getPartent(G)==F);
		assertTrue(result.getColor(G) == Color.BLACK);
		//NODO H
		assertTrue(result.getDistance(H)==6);
		assertTrue(result.getStartTime(H)==6);
		assertTrue(result.getEndTime(H)==7);
		assertTrue(result.getPartent(H)==E);
		assertTrue(result.getColor(H) == Color.BLACK);
		//TEST GIà SCRITTI BENE, CORREGGI CODICE;
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			Vertex mancante = Vertex.getVertexByLabel("mancante");
			pippo.getBFSTree(mancante);
		 });
	}
	
	@Test
	void getDFSTOTForest() {
		Vertex A = Vertex.getVertexByLabel("A");
		Vertex B = Vertex.getVertexByLabel("B");
		Vertex C = Vertex.getVertexByLabel("C");
		Vertex D = Vertex.getVertexByLabel("D");
		Vertex E = Vertex.getVertexByLabel("E");
		Vertex F = Vertex.getVertexByLabel("F");
		Vertex G = Vertex.getVertexByLabel("G");
		Vertex H = Vertex.getVertexByLabel("H");
		Vertex nuovo1 = Vertex.getVertexByLabel("nuovo1");
		Vertex nuovo2 = Vertex.getVertexByLabel("nuovo2");
		
		pippo.addVertex(A);
		pippo.addVertex(B);
		pippo.addVertex(C);
		pippo.addVertex(D);
		pippo.addVertex(E);
		pippo.addVertex(F);
		pippo.addVertex(G);
		pippo.addVertex(H);
		pippo.addVertex(nuovo1);
		pippo.addVertex(nuovo2);
		
		Edge e1 = Edge.getEdgeByVertexes(A, B);
		Edge e2 = Edge.getEdgeByVertexes(A, G);
		Edge e3 = Edge.getEdgeByVertexes(B, H);
		Edge e4 = Edge.getEdgeByVertexes(B, C);
		Edge e5 = Edge.getEdgeByVertexes(C, D);
		Edge e6 = Edge.getEdgeByVertexes(C, G);
		Edge e7 = Edge.getEdgeByVertexes(D, H);
		Edge e8 = Edge.getEdgeByVertexes(D, F);
		Edge e9 = Edge.getEdgeByVertexes(E, H);
		Edge e10 = Edge.getEdgeByVertexes(E, F);
		Edge e11 = Edge.getEdgeByVertexes(F, G);
		Edge eNuovo = Edge.getEdgeByVertexes(nuovo2, nuovo1);
		pippo.addEdge(e1); 
		pippo.addEdge(e2);
		pippo.addEdge(e3);
		pippo.addEdge(e4); 
		pippo.addEdge(e5);
		pippo.addEdge(e6);
		pippo.addEdge(e7); 
		pippo.addEdge(e8);
		pippo.addEdge(e9);
		pippo.addEdge(e10);
		pippo.addEdge(e11);
		pippo.addEdge(eNuovo);
		VisitForest result = pippo.getDFSTOTForest(A);
		//NODO A
		assertTrue(result.getRoots().contains(A));
		assertEquals(2, result.getRoots().size());
		assertEquals(0,result.getDistance(A));
		assertEquals(0, result.getStartTime(A));
		assertEquals(15, result.getEndTime(A));
		assertTrue(result.getPartent(A)==null);
		//NODO B	
		assertTrue(result.getDistance(B)==1);
		assertEquals(1, result.getStartTime(B));
		assertTrue(result.getEndTime(B)==14);
		assertTrue(result.getPartent(B)==A);
		assertTrue(result.getColor(B) == Color.BLACK);
		//NODO C
		assertTrue(result.getDistance(C)==2);
		assertTrue(result.getStartTime(C)==2);
		assertTrue(result.getEndTime(C)==13);
		assertTrue(result.getPartent(C)==B);
		assertTrue(result.getColor(C) == Color.BLACK);
		//NODO D
		assertTrue(result.getDistance(D)==3);
		assertTrue(result.getStartTime(D)==3);
		assertTrue(result.getEndTime(D)==12);
		assertTrue(result.getPartent(D)==C);
		assertTrue(result.getColor(D) == Color.BLACK);
		//NODO E
		assertTrue(result.getDistance(E)==5);
		assertTrue(result.getStartTime(E)==5);
		assertEquals(8, result.getEndTime(E));
		assertTrue(result.getPartent(E)==F);
		assertTrue(result.getColor(E) == Color.BLACK);
		//NODO F
		assertTrue(result.getDistance(F)==4);
		assertTrue(result.getStartTime(F)==4);
		assertTrue(result.getEndTime(F)==11);
		assertTrue(result.getPartent(F)==D);
		assertTrue(result.getColor(F) == Color.BLACK);
		//NODO G
		assertTrue(result.getDistance(G)==5);
		assertTrue(result.getStartTime(G)==9);
		assertTrue(result.getEndTime(G)==10);
		assertTrue(result.getPartent(G)==F);
		assertTrue(result.getColor(G) == Color.BLACK);
		//NODO H
		assertTrue(result.getDistance(H)==6);
		assertTrue(result.getStartTime(H)==6);
		assertTrue(result.getEndTime(H)==7);
		assertTrue(result.getPartent(H)==E);
		assertTrue(result.getColor(H) == Color.BLACK);
		//NODO nuovo1
		assertTrue(result.getRoots().contains(nuovo1));
		assertTrue(result.getRoots().size()==2);
		assertEquals(0,result.getDistance(nuovo1));
		assertEquals(16, result.getStartTime(nuovo1));
		assertEquals(19, result.getEndTime(nuovo1));
		assertTrue(result.getPartent(nuovo1)==null);
		//NODO nuovo2
		assertEquals(1,result.getDistance(nuovo2));
		assertEquals(17, result.getStartTime(nuovo2));
		assertEquals(18, result.getEndTime(nuovo2));
		assertTrue(result.getPartent(nuovo2)==nuovo1);
		
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			Vertex mancante = Vertex.getVertexByLabel("mancante");
			pippo.getBFSTree(mancante);
		 });
	}
	
	@Test
	void getDFSTOTForestVertexOrder() {
		Vertex A = Vertex.getVertexByLabel("A");
		Vertex B = Vertex.getVertexByLabel("B");
		Vertex C = Vertex.getVertexByLabel("C");
		Vertex D = Vertex.getVertexByLabel("D");
		Vertex E = Vertex.getVertexByLabel("E");
		Vertex F = Vertex.getVertexByLabel("F");
		Vertex G = Vertex.getVertexByLabel("G");
		Vertex H = Vertex.getVertexByLabel("H");
		Vertex nuovo1 = Vertex.getVertexByLabel("nuovo1");
		Vertex nuovo2 = Vertex.getVertexByLabel("nuovo2");
		
		pippo.addVertex(A);
		pippo.addVertex(B);
		pippo.addVertex(C);
		pippo.addVertex(D);
		pippo.addVertex(E);
		pippo.addVertex(F);
		pippo.addVertex(G);
		pippo.addVertex(H);
		pippo.addVertex(nuovo1);
		pippo.addVertex(nuovo2);
		
		Edge e1 = Edge.getEdgeByVertexes(A, B);
		Edge e2 = Edge.getEdgeByVertexes(A, G);
		Edge e3 = Edge.getEdgeByVertexes(B, H);
		Edge e4 = Edge.getEdgeByVertexes(B, C);
		Edge e5 = Edge.getEdgeByVertexes(C, D);
		Edge e6 = Edge.getEdgeByVertexes(C, G);
		Edge e7 = Edge.getEdgeByVertexes(D, H);
		Edge e8 = Edge.getEdgeByVertexes(D, F);
		Edge e9 = Edge.getEdgeByVertexes(E, H);
		Edge e10 = Edge.getEdgeByVertexes(E, F);
		Edge e11 = Edge.getEdgeByVertexes(F, G);
		Edge eNuovo = Edge.getEdgeByVertexes(nuovo2, nuovo1);
		pippo.addEdge(e1); 
		pippo.addEdge(e2);
		pippo.addEdge(e3);
		pippo.addEdge(e4); 
		pippo.addEdge(e5);
		pippo.addEdge(e6);
		pippo.addEdge(e7); 
		pippo.addEdge(e8);
		pippo.addEdge(e9);
		pippo.addEdge(e10);
		pippo.addEdge(e11);
		pippo.addEdge(eNuovo);
		Vertex [] order = {A,G,C,E,H, nuovo2};
		VisitForest result = pippo.getDFSTOTForest(order);
		//NODO A
		assertTrue(result.getRoots().contains(A));
		assertTrue(result.getRoots().size()==2);
		assertEquals(0,result.getDistance(A));
		assertEquals(0, result.getStartTime(A));
		assertEquals(15, result.getEndTime(A));
		assertTrue(result.getPartent(A)==null);
		assertTrue(result.getColor(A) == Color.BLACK);
		//NODO B	
		assertTrue(result.getDistance(B)==1);
		assertEquals(1, result.getStartTime(B));
		assertTrue(result.getEndTime(B)==14);
		assertTrue(result.getPartent(B)==A);
		assertTrue(result.getColor(B) == Color.BLACK);
		//NODO C
		assertTrue(result.getDistance(C)==2);
		assertTrue(result.getStartTime(C)==2);
		assertTrue(result.getEndTime(C)==13);
		assertTrue(result.getPartent(C)==B);
		assertTrue(result.getColor(C) == Color.BLACK);
		//NODO D
		assertTrue(result.getDistance(D)==3);
		assertTrue(result.getStartTime(D)==3);
		assertTrue(result.getEndTime(D)==12);
		assertTrue(result.getPartent(D)==C);
		assertTrue(result.getColor(D) == Color.BLACK);
		//NODO E
		assertTrue(result.getDistance(E)==5);
		assertTrue(result.getStartTime(E)==5);
		assertEquals(8, result.getEndTime(E));
		assertTrue(result.getPartent(E)==F);
		assertTrue(result.getColor(E) == Color.BLACK);
		//NODO F
		assertTrue(result.getDistance(F)==4);
		assertTrue(result.getStartTime(F)==4);
		assertTrue(result.getEndTime(F)==11);
		assertTrue(result.getPartent(F)==D);
		assertTrue(result.getColor(F) == Color.BLACK);
		//NODO G
		assertTrue(result.getDistance(G)==5);
		assertTrue(result.getStartTime(G)==9);
		assertTrue(result.getEndTime(G)==10);
		assertTrue(result.getPartent(G)==F);
		assertTrue(result.getColor(G) == Color.BLACK);
		//NODO H
		assertTrue(result.getDistance(H)==6);
		assertTrue(result.getStartTime(H)==6);
		assertTrue(result.getEndTime(H)==7);
		assertTrue(result.getPartent(H)==E);
		assertTrue(result.getColor(H) == Color.BLACK);
		//NODO nuovo1
		assertEquals(1,result.getDistance(nuovo1));
		assertEquals(17, result.getStartTime(nuovo1));
		assertEquals(18, result.getEndTime(nuovo1));
		assertTrue(result.getPartent(nuovo1)==nuovo2);
		//NODO nuovo2
		assertTrue(result.getRoots().contains(nuovo2));
		assertEquals(0,result.getDistance(nuovo2));
		assertEquals(16, result.getStartTime(nuovo2));
		assertEquals(19, result.getEndTime(nuovo2));
		assertTrue(result.getPartent(nuovo2)==null);
		
		
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			Vertex mancante = Vertex.getVertexByLabel("mancante");
			pippo.getBFSTree(mancante);
		 });
	}
	
	@Test
	void connectedComponents(){
		Vertex mario = Vertex.getVertexByLabel("mario");
		Vertex gianni = Vertex.getVertexByLabel("gianni");
		Vertex odoacre = Vertex.getVertexByLabel("odoacre");
		Vertex pierpaolo = Vertex.getVertexByLabel("pierpaolo");
		Vertex malpelo = Vertex.getVertexByLabel("malpelo");
		Vertex ermenegildo = Vertex.getVertexByLabel("ermenegildo");
		Vertex gianpancrazio = Vertex.getVertexByLabel("gianpancrazio");
		
		pippo.addVertex(mario);
		pippo.addVertex(gianni);
		pippo.addVertex(odoacre);
		pippo.addVertex(pierpaolo);
		pippo.addVertex(malpelo);
		pippo.addVertex(ermenegildo);
		pippo.addVertex(gianpancrazio);
		
		Edge e1 = Edge.getEdgeByVertexes(mario, gianni);
		Edge e2 = Edge.getEdgeByVertexes(mario, odoacre);
		Edge e3 = Edge.getEdgeByVertexes(gianni, pierpaolo);
		Edge e4 = Edge.getEdgeByVertexes(malpelo, ermenegildo);
		
		pippo.addEdge(e1); 
		pippo.addEdge(e2);
		pippo.addEdge(e3);
		pippo.addEdge(e4);
		
		Set<Set<Vertex>> result = pippo.connectedComponents();
		assertEquals(3,result.size());
		
		int [] dimensioni = {4,2,1};
		int i = 0;
		for(Set<Vertex> ele : result) {
			assertEquals(dimensioni[i], ele.size());
			i++;			
		}
	}
	

}
