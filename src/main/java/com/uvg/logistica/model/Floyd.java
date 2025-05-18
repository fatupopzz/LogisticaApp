package com.uvg.logistica.model;

import java.util.*;

/**
 * Implementación del Algoritmo de Floyd para encontrar
 * caminos más cortos entre todos los pares de vértices de un grafo.
 * Fatima Navarro
 * Emilio Chen
 */


public class Floyd {
    private double[][] distances; // Matriz de distancias más cortas
    private int[][] next;         // Matriz para reconstruir caminos
    private String[] vertices;    // Nombres de los vértices
    
    /**
     * Constructor que inicializa y ejecuta el algoritmo de Floyd
     * @param adjacencyMatrix Matriz de adyacencia del grafo
     * @param vertices Nombres de los vértices
     */
    public Floyd(double[][] adjacencyMatrix, String[] vertices) {
        int n = adjacencyMatrix.length;
        this.vertices = vertices;
        this.distances = new double[n][n];
        this.next = new int[n][n];
        
        // Inicializar matrices
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distances[i][j] = adjacencyMatrix[i][j];
                
                // Inicializar matriz 'next' para reconstruir caminos
                if (adjacencyMatrix[i][j] != Double.POSITIVE_INFINITY && i != j) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }
        
        // Algoritmo de Floyd
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distances[i][k] != Double.POSITIVE_INFINITY && 
                        distances[k][j] != Double.POSITIVE_INFINITY &&
                        distances[i][k] + distances[k][j] < distances[i][j]) {
                        
                        distances[i][j] = distances[i][k] + distances[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }
    
    /**
     * Obtiene la ruta más corta entre dos ciudades
     * @param start Ciudad origen
     * @param end Ciudad destino
     * @param cityIndices Mapa de índices de ciudades
     * @return Lista de ciudades en la ruta
     */
    public List<String> getPath(String start, String end, Map<String, Integer> cityIndices) {
        int startIdx = cityIndices.get(start);
        int endIdx = cityIndices.get(end);
        
        if (next[startIdx][endIdx] == -1) {
            return new ArrayList<>(); // No hay ruta
        }
        
        List<String> path = new ArrayList<>();
        path.add(start);
        
        while (startIdx != endIdx) {
            startIdx = next[startIdx][endIdx];
            path.add(vertices[startIdx]);
        }
        
        return path;
    }
    
    /**
     * Obtiene la distancia más corta entre dos ciudades
     * @param start Ciudad origen
     * @param end Ciudad destino
     * @param cityIndices Mapa de índices de ciudades
     * @return Distancia más corta
     */
    public double getDistance(String start, String end, Map<String, Integer> cityIndices) {
        int startIdx = cityIndices.get(start);
        int endIdx = cityIndices.get(end);
        return distances[startIdx][endIdx];
    }
    
    /**
     * Calcula el centro del grafo (vértice con excentricidad mínima)
     * @return Nombre de la ciudad que es centro del grafo
     */
    public String findGraphCenter() {
        int n = distances.length;
        double[] eccentricity = new double[n];
        
        // Para cada vértice, encontrar la distancia máxima a cualquier otro vértice
        for (int i = 0; i < n; i++) {
            double maxDistance = 0;
            for (int j = 0; j < n; j++) {
                if (distances[i][j] != Double.POSITIVE_INFINITY && distances[i][j] > maxDistance) {
                    maxDistance = distances[i][j];
                }
            }
            eccentricity[i] = maxDistance;
        }
        
        // Encontrar el vértice con la menor excentricidad
        double minEccentricity = Double.POSITIVE_INFINITY;
        int centerIndex = -1;
        
        for (int i = 0; i < n; i++) {
            if (eccentricity[i] < minEccentricity) {
                minEccentricity = eccentricity[i];
                centerIndex = i;
            }
        }
        
        return vertices[centerIndex];
    }
    
    /**
     * Obtiene la matriz de distancias más cortas
     * @return Matriz de distancias más cortas
     */
    public double[][] getDistances() {
        return distances;
    }
}
