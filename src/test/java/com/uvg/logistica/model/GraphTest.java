package com.uvg.logistica.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    
    private Graph graph;
    
    @BeforeEach
    public void setUp() {
        graph = new Graph(3);
        graph.addVertex("A", 0);
        graph.addVertex("B", 1);
        graph.addVertex("C", 2);
    }
    
    @Test
    public void testAddVertex() {
        assertEquals("A", graph.getVertices()[0]);
        assertEquals("B", graph.getVertices()[1]);
        assertEquals("C", graph.getVertices()[2]);
    }
    
    @Test
    public void testAddEdge() {
        graph.addEdge("A", "B", 10, 15, 20, 25);
        graph.addEdge("B", "C", 5, 8, 12, 15);
        
        double[][] normalMatrix = graph.getNormalMatrix();
        assertEquals(10.0, normalMatrix[0][1]);
        assertEquals(5.0, normalMatrix[1][2]);
        assertEquals(Double.POSITIVE_INFINITY, normalMatrix[0][2]); // No hay conexión directa
    }
    
    @Test
    public void testRemoveEdge() {
        graph.addEdge("A", "B", 10, 15, 20, 25);
        graph.removeEdge("A", "B");
        
        double[][] normalMatrix = graph.getNormalMatrix();
        assertEquals(Double.POSITIVE_INFINITY, normalMatrix[0][1]);
    }
    
    @Test
    public void testGetMatrixByWeather() {
        graph.addEdge("A", "B", 10, 15, 20, 25);
        
        assertEquals(10.0, graph.getMatrixByWeather("normal")[0][1]);
        assertEquals(15.0, graph.getMatrixByWeather("rain")[0][1]);
        assertEquals(20.0, graph.getMatrixByWeather("snow")[0][1]);
        assertEquals(25.0, graph.getMatrixByWeather("storm")[0][1]);
    }
}
