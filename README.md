# Sistema de Logística con Algoritmo de Floyd

Este proyecto implementa un sistema de logística que utiliza grafos y el algoritmo de Floyd para encontrar las rutas más cortas entre ciudades, considerando diferentes condiciones climáticas.

## Contenido
1. [Descripción](#descripción)
2. [Funcionalidades](#funcionalidades)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Instalación y Ejecución](#instalación-y-ejecución)
5. [Uso del Programa](#uso-del-programa)
6. [Implementación del Algoritmo de Floyd](#implementación-del-algoritmo-de-floyd)
7. [Cálculo del Centro del Grafo](#cálculo-del-centro-del-grafo)
8. [Autores](#autores)

## Descripción

El sistema permite a una empresa de logística optimizar sus rutas de entrega considerando diferentes factores como la distancia entre almacenes y las condiciones climáticas. Mediante el algoritmo de Floyd, el sistema calcula la ruta más corta entre cualquier par de ciudades y determina el centro logístico óptimo.

## Funcionalidades

- **Consulta de rutas óptimas**: Encuentra la ruta más corta entre dos ciudades.
- **Identificación del centro del grafo**: Determina cuál es la ciudad central para optimizar la ubicación de almacenes.
- **Simulación de condiciones climáticas**: Calcula rutas considerando diferentes condiciones (normal, lluvia, nieve, tormenta).
- **Modificación del grafo**: Permite simular interrupciones de tráfico o añadir nuevas conexiones.
- **Visualización de la matriz de adyacencia**: Muestra las conexiones directas entre ciudades.

## Estructura del Proyecto

```
LogisticaApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/uvg/logistica/
│   │   │       ├── app/
│   │   │       │   └── LogisticaApp.java
│   │   │       ├── model/
│   │   │       │   ├── Graph.java
│   │   │       │   └── Floyd.java
│   │   │       └── utils/
│   │   │           └── FileHandler.java
│   │   └── resources/
│   │       └── logistica.txt
│   └── test/
│       └── java/
│           └── com/uvg/logistica/
│               ├── model/
│               │   ├── GraphTest.java
│               │   └── FloydTest.java
│               └── utils/
│                   └── FileHandlerTest.java
└── pom.xml
```

## Instalación y Ejecución

### Requisitos previos
- Java JDK 11 o superior
- Maven (opcional, para compilación)

### Compilación
```bash
# Usando Maven
mvn clean package

# Compilación manual
mkdir -p bin
javac -d bin $(find main/java -name "*.java")
cp main/resources/logistica.txt bin/
```

### Ejecución
```bash
# Usando el JAR de Maven
java -jar target/logistica-1.0-SNAPSHOT-jar-with-dependencies.jar

# Ejecución directa de las clases compiladas
cd bin
java com.uvg.logistica.app.LogisticaApp
```

## Uso del Programa

Al ejecutar el programa, se mostrará un menú con las siguientes opciones:

1. **Consultar ruta más corta entre ciudades**
   - Ingresa el nombre completo de la ciudad origen (ej. "Lima")
   - Ingresa el nombre completo de la ciudad destino (ej. "BuenosAires")

2. **Mostrar centro del grafo**
   - Muestra la ciudad que constituye el centro del grafo

3. **Modificar el grafo**
   - Interrumpir tráfico entre ciudades
   - Establecer nueva conexión entre ciudades

4. **Mostrar matriz de adyacencia**
   - Visualiza la matriz de conexiones entre ciudades

5. **Cambiar condición climática**
   - Selecciona una opción numérica (1-4) para cambiar entre:
     1. Normal
     2. Lluvia
     3. Nieve
     4. Tormenta

6. **Salir**
   - Finaliza el programa

### Formato del Archivo logistica.txt

El programa lee un archivo con el siguiente formato:
```
CiudadOrigen CiudadDestino TiempoNormal TiempoLluvia TiempoNieve TiempoTormenta
```

Ejemplo:
```
BuenosAires SaoPaulo 10 15 20 50
Lima Quito 10 12 15 20
```

## Implementación del Algoritmo de Floyd

El algoritmo de Floyd permite encontrar el camino más corto entre todos los pares de vértices de un grafo. Este proyecto implementa el algoritmo con las siguientes características:

1. Inicializa matrices de distancias y reconstrucción de caminos
2. Para cada trio de vértices (i, j, k), verifica si pasar por k reduce la distancia desde i hasta j
3. Si es así, actualiza la distancia y el camino
4. Mantiene una matriz para reconstruir las rutas completas

## Cálculo del Centro del Grafo

El centro del grafo es el vértice con mínima excentricidad, es decir, la ciudad desde la cual la distancia máxima a cualquier otra ciudad es la menor posible. El proceso es:

1. Calcular la excentricidad de cada vértice (distancia máxima a cualquier otro vértice)
2. Encontrar el vértice con la menor excentricidad
3. Este vértice representa la ubicación óptima para un centro de distribución

## Autores

- Fatima Navarro
- Emilio Chen

*Universidad del Valle de Guatemala*  
*Facultad de Ingeniería - CC2016 Algoritmos y Estructura de Datos*  
*Semestre I - 2025*

## Licencia

Este proyecto está disponible bajo la Licencia MIT. Ver el archivo LICENSE para más detalles.
