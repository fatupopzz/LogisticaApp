
package com.uvg.logistica.model;

import java.util.*;

/**
 * Implementación de grafo dirigido con matrices de adyacencia
 * para diferentes condiciones climáticas.
 * Fatima Navarrro
 * Emilio Chen
 */


public class Graph {
    private String[] vertices;
    private double[][] normalMatrix;
    private double[][] rainMatrix;
    private double[][] snowMatrix;
    private double[][] stormMatrix;
    private Map<String, Integer> cityIndices;
    
    /**
     * Constructor del grafo
     * @param size Número de vértices
     */
    public Graph(int size) {
        vertices = new String[size];
        normalMatrix = new double[size][size];
        rainMatrix = new double[size][size];
        snowMatrix = new double[size][size];
        stormMatrix = new double[size][size];
        cityIndices = new HashMap<>();
        
        // Inicializar matrices con valores infinitos
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                normalMatrix[i][j] = i == j ? 0 : Double.POSITIVE_INFINITY;
                rainMatrix[i][j] = i == j ? 0 : Double.POSITIVE_INFINITY;
                snowMatrix[i][j] = i == j ? 0 : Double.POSITIVE_INFINITY;
                stormMatrix[i][j] = i == j ? 0 : Double.POSITIVE_INFINITY;
            }
        }
    }
    
    /**
     * Agrega un vértice al grafo
     * @param city Nombre de la ciudad
     * @param index Índice del vértice
     */
    public void addVertex(String city, int index) {
        vertices[index] = city;
        cityIndices.put(city, index);
    }
    
    /**
     * Agrega una arista con tiempos para diferentes condiciones climáticas
     * @param fromCity Ciudad origen
     * @param toCity Ciudad destino
     * @param normalTime Tiempo en clima normal
     * @param rainTime Tiempo en lluvia
     * @param snowTime Tiempo en nieve
     * @param stormTime Tiempo en tormenta
     */
    public void addEdge(String fromCity, String toCity, double normalTime, double rainTime, double snowTime, double stormTime) {
        int from = cityIndices.get(fromCity);
        int to = cityIndices.get(toCity);
        
        normalMatrix[from][to] = normalTime;
        rainMatrix[from][to] = rainTime;
        snowMatrix[from][to] = snowTime;
        stormMatrix[from][to] = stormTime;
    }
    
    /**
     * Elimina una arista del grafo (simula interrupción de tráfico)
     * @param fromCity Ciudad origen
     * @param toCity Ciudad destino
     */
    public void removeEdge(String fromCity, String toCity) {
        int from = cityIndices.get(fromCity);
        int to = cityIndices.get(toCity);
        
        normalMatrix[from][to] = Double.POSITIVE_INFINITY;
        rainMatrix[from][to] = Double.POSITIVE_INFINITY;
        snowMatrix[from][to] = Double.POSITIVE_INFINITY;
        stormMatrix[from][to] = Double.POSITIVE_INFINITY;
    }
    
    /**
     * Obtiene la matriz de adyacencia según el clima
     * @param weatherType Tipo de clima (normal, rain, snow, storm)
     * @return Matriz de adyacencia para el clima especificado
     */
    public double[][] getMatrixByWeather(String weatherType) {
        switch (weatherType.toLowerCase()) {
            case "rain": return rainMatrix;
            case "snow": return snowMatrix;
            case "storm": return stormMatrix;
            default: return normalMatrix;
        }
    }
    
    /**
     * Getters
     */
    public String[] getVertices() {
        return vertices;
    }
    
    public double[][] getNormalMatrix() {
        return normalMatrix;
    }
    
    public Map<String, Integer> getCityIndices() {
        return cityIndices;
    }
    
    public int getSize() {
        return vertices.length;
    }
    
    /**
     * Imprime la matriz de adyacencia
     * @param matrix Matriz a imprimir
     */
    public void printMatrix(double[][] matrix) {
        System.out.printf("%15s", "");
        for (String vertex : vertices) {
            System.out.printf("%15s", vertex);
        }
        System.out.println();
        
        for (int i = 0; i < vertices.length; i++) {
            System.out.printf("%15s", vertices[i]);
            for (int j = 0; j < vertices.length; j++) {
                if (matrix[i][j] == Double.POSITIVE_INFINITY) {
                    System.out.printf("%15s", "∞");
                } else {
                    System.out.printf("%15.2f", matrix[i][j]);
                }
            }
            System.out.println();
        }
    }
}
