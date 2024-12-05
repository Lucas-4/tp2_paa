package org.example;

import org.example.CliqueFinder.AlgorithmType;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMenu de Testes:");
            System.out.println("[0] Sair");
            System.out.println("[1] Teste de Grafos Densos (Heurístico)");
            System.out.println("[2] Teste de Grafos Densos (Força Bruta)");
            System.out.println("[3] Teste de Grafos Pouco Densos (Heurístico)");
            System.out.println("[4] Teste de Grafos Pouco Densos (Força Bruta)");
            System.out.println("[5] Teste de Precisão Geral");
            System.out.println("[6] Precisão em Grafos Densos");
            System.out.println("[7] Precisão em Grafos Pouco Densos");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 0:
                    System.out.println("Saindo...");
                    running = false;
                    break;
                case 1:
                    System.out.println("Executando Teste de Grafos Densos (Heurístico):");
                    testGraph(0.8, 100, 5, 30, AlgorithmType.HEURISTIC);
                    break;
                case 2:
                    System.out.println("Executando Teste de Grafos Densos (Força Bruta):");
                    testGraph(0.8, 100, 5, 30, AlgorithmType.BRUTE_FORCE);
                    break;
                case 3:
                    System.out.println("Executando Teste de Grafos Pouco Densos (Heurístico):");
                    testGraph(0.2, 100, 5, 30, AlgorithmType.HEURISTIC);
                    break;
                case 4:
                    System.out.println("Executando Teste de Grafos Pouco Densos (Força Bruta):");
                    testGraph(0.2, 100, 5, 30, AlgorithmType.BRUTE_FORCE);
                    break;
                case 5:
                    System.out.println("Calculando Precisão Geral:");
                    calculateGeneralAccuracy();
                    break;
                case 6:
                    System.out.println("Calculando Precisão para Grafos Densos:");
                    calculateDensitySpecificAccuracy(0.9);
                    break;
                case 7:
                    System.out.println("Calculando Precisão para Grafos Pouco Densos:");
                    calculateDensitySpecificAccuracy(0.2);
                    break;
                default:
                    System.out.println("Opção Inválida! Tente novamente.");
            }
        }

        scanner.close();
    }

    public static void testGraph(double density, int vertices, int kStart, int kEnd, AlgorithmType algorithmType) {
        System.out.println("n      k       Tempo (" + algorithmType + ") (ms)");
        for (int k = kStart; k <= kEnd; k++) {
            Graph graph = Graph.createRandomGraphWithClique(vertices, density, k);
            double duration = calculateDuration(graph, k, algorithmType);
            System.out.printf("%-8d%-8d%-24.3f%n", vertices, k, duration);
        }
    }

    public static double calculateDuration(Graph graph, int k, AlgorithmType algorithmType) {
        CliqueFinder cliqueFinder = new CliqueFinder(graph);

        long startTime = System.nanoTime();
        cliqueFinder.hasClique(k, algorithmType);
        long endTime = System.nanoTime();

        return (endTime - startTime) / 1_000_000.0;
    }

    public static double calculateGeneralAccuracy() {
        return calculateAccuracyAcrossGraphs(100, 1000, 10, 10, 0.2, 1, 0.2);
    }

    public static void calculateDensitySpecificAccuracy(double density) {
        calculateAccuracyAcrossGraphs(100, 1000, 10, 100, density, density, 0);
    }

    public static double calculateAccuracyAcrossGraphs(int graphSizeStart, int graphSizeEnd, int kStart, int kStep, double edgeProbStart, double edgeProbEnd, double edgeProbStep) {
        Graph graph;
        int total = 0;
        int correct = 0;
        for (int graphSize = graphSizeStart; graphSize <= graphSizeEnd; graphSize += 100) {
            for (int k = kStart; k < graphSize / 2; k += kStep) {
                for (double edgeProb = edgeProbStart; edgeProb <= edgeProbEnd; edgeProb += Math.max(edgeProbStep, 0.01)) {
                    graph = Graph.createRandomGraphWithClique(graphSize, edgeProb, k);
                    CliqueFinder cf = new CliqueFinder(graph);
                    if (cf.hasClique(k, AlgorithmType.HEURISTIC)) {
                        correct++;
                    }
                    total++;
                }
            }
        }
        double accuracy = (double) correct / total;
        System.out.printf("Precisão calculada: %.2f%%%n", accuracy * 100);
        return accuracy;
    }
}