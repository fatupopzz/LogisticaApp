package com.uvg.logistica.utils;

import com.uvg.logistica.model.Graph;
import java.io.*;
import java.util.*;

/**
 * Clase utilitaria para manejar la lectura y escritura de archivos
 */
public class FileHandler {
    
    /**
     * Lee un grafo desde un archivo
     * @param filename Nombre del archivo
     * @return Grafo cargado desde el archivo
     * @throws IOException Si hay un error de lectura
     */
    public static Graph readGraphFromFile(String filename) throws IOException {
        // Primera pasada: contar ciudades únicas
        Set<String> uniqueCities = new HashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            uniqueCities.add(parts[0]);
            uniqueCities.add(parts[1]);
        }
        reader.close();
        
        // Crear el grafo con el tamaño adecuado
        int size = uniqueCities.size();
        Graph graph = new Graph(size);
        
        // Asignar índices a las ciudades
        int index = 0;
        for (String city : uniqueCities) {
            graph.addVertex(city, index++);
        }
        
        // Segunda pasada: añadir las aristas
        reader = new BufferedReader(new FileReader(filename));
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            String fromCity = parts[0];
            String toCity = parts[1];
            double normalTime = Double.parseDouble(parts[2]);
            double rainTime = Double.parseDouble(parts[3]);
            double snowTime = Double.parseDouble(parts[4]);
            double stormTime = Double.parseDouble(parts[5]);
            
            graph.addEdge(fromCity, toCity, normalTime, rainTime, snowTime, stormTime);
        }
        reader.close();
        
        return graph;
    }
    
    /**
     * Escribe un grafo en un archivo
     * @param graph Grafo a escribir
     * @param filename Nombre del archivo
     * @throws IOException Si hay un error de escritura
     */
    public static void writeGraphToFile(Graph graph, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        String[] vertices = graph.getVertices();
        double[][] normalMatrix = graph.getNormalMatrix();
        
        for (int i = 0; i < graph.getSize(); i++) {
            for (int j = 0; j < graph.getSize(); j++) {
                if (i != j && normalMatrix[i][j] != Double.POSITIVE_INFINITY) {
                    writer.write(vertices[i] + " " + vertices[j] + " " + normalMatrix[i][j]);
                    // Aquí deberías agregar los otros tiempos para cada clima
                    writer.newLine();
                }
            }
        }
        
        writer.close();
    }
}
