package com.uvg.logistica.app;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.uvg.logistica.model.Floyd;
import com.uvg.logistica.model.Graph;
import com.uvg.logistica.utils.FileHandler;

/**
 * Programa principal para la aplicación de logística
 * Fatima Navarro
 * Emilio Chen 
 */
public class LogisticaApp {
    private Graph graph;
    private Floyd floyd;
    private Scanner scanner;
    private String currentWeather = "normal";
    
    /**
     * Constructor de la aplicación
     */
    public LogisticaApp() {
        scanner = new Scanner(System.in);
    }
    
    /**
     * Método principal que ejecuta la aplicación
     */
    public void run() {
        System.out.println("\n=== Sistema de Logística - Grafos y Algoritmo de Floyd ===\n");
        
        try {
            System.out.print("Ingrese el nombre del archivo (por defecto: logistica.txt): ");
            String filename = scanner.nextLine().trim();
            
            if (filename.isEmpty()) {
                filename = "logistica.txt";
            }
            
            System.out.println("\nCargando datos desde " + filename + "...");
            graph = FileHandler.readGraphFromFile(filename);
            updateFloyd();
            System.out.println("Datos cargados exitosamente.");
            
            boolean running = true;
            while (running) {
                System.out.println("\n=== Menú Principal ===");
                System.out.println("1. Consultar ruta más corta entre ciudades");
                System.out.println("2. Mostrar centro del grafo");
                System.out.println("3. Modificar el grafo");
                System.out.println("4. Mostrar matriz de adyacencia");
                System.out.println("5. Cambiar condición climática (Actual: " + currentWeather + ")");
                System.out.println("6. Salir");
                System.out.print("\nSeleccione una opción: ");
                
                int option = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                
                switch (option) {
                    case 1:
                        findShortestPath();
                        break;
                    case 2:
                        showGraphCenter();
                        break;
                    case 3:
                        modifyGraph();
                        break;
                    case 4:
                        showAdjacencyMatrix();
                        break;
                    case 5:
                        changeWeatherCondition();
                        break;
                    case 6:
                        running = false;
                        System.out.println("\n¡Gracias por usar el Sistema de Logística!");
                        break;
                    default:
                        System.out.println("\nOpción inválida. Intente de nuevo.");
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Actualiza el algoritmo de Floyd con la matriz según el clima actual
     */
    private void updateFloyd() {
        floyd = new Floyd(graph.getMatrixByWeather(currentWeather), graph.getVertices());
        System.out.println("Algoritmo de Floyd actualizado para clima: " + currentWeather);
    }
    
    /**
     * Busca y muestra la ruta más corta entre dos ciudades
     */
    private void findShortestPath() {
        System.out.println("\n=== Consulta de Ruta Más Corta (" + currentWeather + ") ===");
        
        // Mostrar ciudades disponibles
        System.out.println("\nCiudades disponibles:");
        String[] cities = graph.getVertices();
        for (int i = 0; i < cities.length; i++) {
            System.out.println((i + 1) + ". " + cities[i]);
        }
        
        System.out.print("\nIngrese el nombre de la ciudad origen: ");
        String origin = scanner.nextLine();
        
        System.out.print("Ingrese el nombre de la ciudad destino: ");
        String destination = scanner.nextLine();
        
        try {
            double distance = floyd.getDistance(origin, destination, graph.getCityIndices());
            List<String> path = floyd.getPath(origin, destination, graph.getCityIndices());
            
            if (path.isEmpty() || distance == Double.POSITIVE_INFINITY) {
                System.out.println("\n¡No existe ruta entre " + origin + " y " + destination + "!");
            } else {
                System.out.println("\n=== Ruta encontrada ===");
                System.out.println("Ruta más corta: " + String.join(" -> ", path));
                System.out.println("Tiempo total: " + distance + " horas");
            }
        } catch (Exception e) {
            System.out.println("\nError: Verifique que las ciudades existen en el grafo.");
        }
    }
    
    /**
     * Muestra el centro del grafo
     */
    private void showGraphCenter() {
        String center = floyd.findGraphCenter();
        System.out.println("\n=== Centro del Grafo ===");
        System.out.println("El centro del grafo es: " + center);
        System.out.println("(Ciudad con la menor excentricidad)");
    }
    
    /**
     * Modifica el grafo (agregar/eliminar conexiones)
     */
    private void modifyGraph() {
        System.out.println("\n=== Modificar Grafo ===");
        System.out.println("1. Interrupción de tráfico entre ciudades");
        System.out.println("2. Establecer nueva conexión entre ciudades");
        System.out.print("\nSeleccione una opción: ");
        
        int option = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        // Mostrar ciudades disponibles
        System.out.println("\nCiudades disponibles:");
        String[] cities = graph.getVertices();
        for (int i = 0; i < cities.length; i++) {
            System.out.println((i + 1) + ". " + cities[i]);
        }
        
        switch (option) {
            case 1:
                System.out.print("\nIngrese el nombre de la ciudad origen: ");
                String from = scanner.nextLine();
                
                System.out.print("Ingrese el nombre de la ciudad destino: ");
                String to = scanner.nextLine();
                
                try {
                    graph.removeEdge(from, to);
                    System.out.println("\nConexión eliminada correctamente.");
                    updateFloyd();
                } catch (Exception e) {
                    System.out.println("\nError: " + e.getMessage());
                }
                break;
                
            case 2:
                System.out.print("\nIngrese el nombre de la ciudad origen: ");
                String fromCity = scanner.nextLine();
                
                System.out.print("Ingrese el nombre de la ciudad destino: ");
                String toCity = scanner.nextLine();
                
                System.out.print("Tiempo normal (horas): ");
                double normalTime = scanner.nextDouble();
                
                System.out.print("Tiempo con lluvia (horas): ");
                double rainTime = scanner.nextDouble();
                
                System.out.print("Tiempo con nieve (horas): ");
                double snowTime = scanner.nextDouble();
                
                System.out.print("Tiempo con tormenta (horas): ");
                double stormTime = scanner.nextDouble();
                
                scanner.nextLine(); // Consumir el salto de línea
                
                try {
                    graph.addEdge(fromCity, toCity, normalTime, rainTime, snowTime, stormTime);
                    System.out.println("\nConexión agregada correctamente.");
                    updateFloyd();
                } catch (Exception e) {
                    System.out.println("\nError: " + e.getMessage());
                }
                break;
                
            default:
                System.out.println("\nOpción inválida.");
        }
    }
    
    /**
     * Muestra la matriz de adyacencia
     */
    private void showAdjacencyMatrix() {
        System.out.println("\n=== Matriz de Adyacencia (Clima: " + currentWeather + ") ===");
        graph.printMatrix(graph.getMatrixByWeather(currentWeather));
    }
    
    /**
     * Cambia la condición climática para los cálculos
     */
    private void changeWeatherCondition() {
        System.out.println("\n=== Cambiar Condición Climática ===");
        System.out.println("1. Normal");
        System.out.println("2. Lluvia");
        System.out.println("3. Nieve");
        System.out.println("4. Tormenta");
        System.out.print("\nSeleccione el clima: ");
        
        int option = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        
        switch (option) {
            case 1:
                currentWeather = "normal";
                break;
            case 2:
                currentWeather = "rain";
                break;
            case 3:
                currentWeather = "snow";
                break;
            case 4:
                currentWeather = "storm";
                break;
            default:
                System.out.println("\nOpción inválida.");
                return;
        }
        
        updateFloyd();
        System.out.println("\nCondición climática cambiada a: " + currentWeather);
    }
    
    /**
     * Método principal
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        LogisticaApp app = new LogisticaApp();
        app.run();
    }
}
