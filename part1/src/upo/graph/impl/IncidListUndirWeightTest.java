package upo.graph.impl;

import static org.junit.jupiter.api.Assertions.*; 

import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;

import upo.graph.base.Edge;
import upo.graph.base.Vertex;
import upo.graph.base.VisitForest;

import org.junit.jupiter.api.Test;

class IncidListUndirWeightTest {

	IncidListUndirWeight pippo;
	@BeforeEach
	void constructor() {
		pippo = new IncidListUndirWeight();
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
		assertTrue(pippo.getEdgeWeight(e1)==1.0);
		assertTrue(pippo.getEdgeWeight(e1)==1.0);
		Edge e3 = Edge.getEdgeByVertexes(odoacre, gianni);
		assertFalse(pippo.containsEdge(e3));
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			Vertex mancante = Vertex.getVertexByLabel("mancante");
			Edge e4 = Edge.getEdgeByVertexes(odoacre, mancante);
			pippo.containsEdge(e4);
		 });
		
	}
/*
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
		NoSuchElementException nsee1 = assertThrows(NoSuchElementException.class, () -> {
			pippo.getEdgeWeight(e2);
		 });
	}
	
	@Test
	void getBFSTree() {
		UnsupportedOperationException uoe = assertThrows(UnsupportedOperationException.class, () -> {
			Vertex mario = Vertex.getVertexByLabel("mario");
			pippo.addVertex(mario);
			pippo.getBFSTree(mario);
		 });
	}
	
	@Test
	void getEdgeWeight() {
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
		
		pippo.setEdgeWeight(e1, 12);
		assertTrue(pippo.getEdgeWeight(e1)== 12.0);
		assertTrue(pippo.getEdgeWeight(e2)==1.0);
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			Vertex mancante = Vertex.getVertexByLabel("mancante");
			Edge e3 = Edge.getEdgeByVertexes(gianni, mancante);
			pippo.getEdgeWeight(e3);
		 });
		NoSuchElementException nsee = assertThrows(NoSuchElementException.class, () ->{
			Vertex staccato = Vertex.getVertexByLabel("staccato");
			pippo.addVertex(staccato);
			Edge e3 = Edge.getEdgeByVertexes(gianni, staccato );
			pippo.getEdgeWeight(e3);
		 });
	}
	@Test
	void setEdgeWeight() {
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
		pippo.setEdgeWeight(e1, 58);
		assertTrue(pippo.getEdgeWeight(e1)== 58);
		assertTrue(pippo.getEdgeWeight(e2)==1.0);
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> {
			Vertex mancante = Vertex.getVertexByLabel("mancante");
			Edge e3 = Edge.getEdgeByVertexes(gianni, mancante);
			pippo.setEdgeWeight(e3, -12);
		 });
		NoSuchElementException nsee = assertThrows(NoSuchElementException.class, () ->{
			Vertex staccato = Vertex.getVertexByLabel("staccato");
			pippo.addVertex(staccato);
			Edge e3 = Edge.getEdgeByVertexes(gianni, staccato );
			pippo.setEdgeWeight(e3, 9.56);
		 });
	}	*/
}
