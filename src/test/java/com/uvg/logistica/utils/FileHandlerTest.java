package com.uvg.logistica.utils;

import com.uvg.logistica.model.Graph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*; // Importación para Map y otras colecciones
public class FileHandlerTest {
    
    @TempDir
    Path tempDir;
    
    @Test
    public void testReadAndWriteGraph() throws IOException {
        // Crear archivo temporal para pruebas
        File tempFile = tempDir.resolve("testLogistica.txt").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("A B 5 8 10 15\n");
            writer.write("B C 3 4 6 9\n");
            writer.write("C D 2 3 5 8\n");
            writer.write("A D 15 20 25 30\n");
        }
        
        // Leer el grafo
        Graph graph = FileHandler.readGraphFromFile(tempFile.getPath());
        assertNotNull(graph);
        assertEquals(4, graph.getSize());
        
        // Verificar que las aristas existen
        double[][] normalMatrix = graph.getNormalMatrix();
        Map<String, Integer> indices = graph.getCityIndices();
        
        int aIndex = indices.get("A");
        int bIndex = indices.get("B");
        int cIndex = indices.get("C");
        int dIndex = indices.get("D");
        
        assertEquals(5.0, normalMatrix[aIndex][bIndex]);
        assertEquals(3.0, normalMatrix[bIndex][cIndex]);
        assertEquals(2.0, normalMatrix[cIndex][dIndex]);
        assertEquals(15.0, normalMatrix[aIndex][dIndex]);
    }
}
