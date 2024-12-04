package org.example;

import java.util.Random;

public class Graph {
    private int[][] adjacencyMatrix;
    private int numberOfVertices;

    public Graph(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        adjacencyMatrix = new int[numberOfVertices][numberOfVertices];
    }

    public void addEdge(int source, int destination) {
        adjacencyMatrix[source][destination] = 1;
        adjacencyMatrix[destination][source] = 1; // Grafo não direcionado
    }

    public boolean isEdge(int source, int destination) {
        return adjacencyMatrix[source][destination] == 1;
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    public int getDegree(int vertex) {
        int degree = 0;
        for (int i = 0; i < numberOfVertices; i++) {
            degree += adjacencyMatrix[vertex][i];
        }
        return degree;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public static Graph createRandomGraph(int numberOfVertices, double edgeProbability) {
        Graph graph = new Graph(numberOfVertices);
        Random random = new Random();

        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = i + 1; j < numberOfVertices; j++) {
                if (random.nextDouble() < edgeProbability) {
                    graph.addEdge(i, j);
                }
            }
        }

        return graph;
    }

    public static Graph createCompleteGraph(int numberOfVertices) {
        Graph graph = new Graph(numberOfVertices);
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = i + 1; j < numberOfVertices; j++) {
                graph.addEdge(i, j);
            }
        }
        return graph;
    }

    public void printGraph() {
        System.out.println("Graph adjacency matrix:");
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}