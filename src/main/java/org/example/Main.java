package org.example;

import org.example.CliqueFinder.AlgorithmType;

public class Main {
    public static void main(String[] args) {
        double[] densities = { 0.5, 0.9, 1.0 };
        String[] graphTypes = {"meio denso", "muito denso", "completo"};

        // Variação do número de vértices de 50 até 200
        for (int vertices = 50; vertices <= 200; vertices += 50) {
            // Variação do valor de k de 3 até 7
            for (int k = 3; k <= 7; k++) {
                for (int i = 0; i < densities.length; i++) {
                    Graph graph;
                    if (densities[i] == 1.0) {
                        graph = Graph.createCompleteGraph(vertices);
                    } else {
                        graph = Graph.createRandomGraph(vertices, densities[i]);
                    }

                    CliqueFinder cliqueFinder = new CliqueFinder(graph);

                    System.out.println("Grafo " + graphTypes[i] + ", Vértices: " + vertices + ", k: " + k + ":");

                    long startTimeHeuristic = System.nanoTime();
                    boolean resultHeuristic = cliqueFinder.hasClique(k, AlgorithmType.HEURISTIC);
                    long endTimeHeuristic = System.nanoTime();
                    long durationHeuristic = endTimeHeuristic - startTimeHeuristic;
                    double durationHeuristicMicros = durationHeuristic / 1_000.0;

                    System.out.println("     Algoritmo heurístico:");
                    System.out.println("     Possui clique: " + resultHeuristic);
                    System.out.println("     Tempo de execução: " + durationHeuristicMicros + " microssegundos");

                    long startTimeBruteForce = System.nanoTime();
                    boolean resultBruteForce = cliqueFinder.hasClique(k, AlgorithmType.BRUTE_FORCE);
                    long endTimeBruteForce = System.nanoTime();
                    long durationBruteForce = endTimeBruteForce - startTimeBruteForce;
                    double durationBruteForceMicros = durationBruteForce / 1_000.0;

                    System.out.println("     Algoritmo força bruta:");
                    System.out.println("     Possui clique: " + resultBruteForce);
                    System.out.println("     Tempo de execução: " + durationBruteForceMicros + " microssegundos");
                    System.out.println();
                }
            }
        }
    }
}