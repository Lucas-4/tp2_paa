package org.example;

import java.util.ArrayList;
import java.util.List;

public class CliqueFinder {
    public enum AlgorithmType {
        BRUTE_FORCE, HEURISTIC
    }

    private Graph graph;
    private List<Clique> cliques;

    public CliqueFinder(Graph graph) {
        this.graph = graph;
        this.cliques = new ArrayList<>();
    }


    public boolean hasClique(int k, AlgorithmType algorithmType) {

        ArrayList<Integer> subset = new ArrayList<>();
        if (algorithmType == AlgorithmType.BRUTE_FORCE) {
            return hasCliqueUtil(subset, 0, k);
        } else if (algorithmType == AlgorithmType.HEURISTIC) {
            return hasCliqueHeuristic(k);
        }
        return false;

    }


    private boolean hasCliqueHeuristic(int k) {
        // Calcular e armazenar os graus dos vértices
        List<Integer> degrees = new ArrayList<>();
        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            degrees.add(graph.getDegree(i));
        }

        // Filtro Inicial Rápido
        List<Integer> possibleVertices = new ArrayList<>();
        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            if (degrees.get(i) >= k - 1) {
                possibleVertices.add(i);
            }
        }

        // Ordena os vértices restantes por grau decrescente
        possibleVertices.sort((a, b) -> Integer.compare(degrees.get(b), degrees.get(a)));

        // Tenta formar um clique com os vértices de maior grau
        for (int i = 0; i <= possibleVertices.size() - k; i++) {
            List<Integer> potentialClique = new ArrayList<>();
            potentialClique.add(possibleVertices.get(i));

            for (int j = i + 1; j < possibleVertices.size() && potentialClique.size() < k; j++) {
                int v = possibleVertices.get(j);
                potentialClique.add(v);

                if (potentialClique.size() == k && isClique(new ArrayList<>(potentialClique))) {
                    return true;
                }
            }
        }

        return false;
    }


    private boolean isClique(ArrayList<Integer> subset) {
        for (int i = 0; i < subset.size(); i++) {
            for (int j = i + 1; j < subset.size(); j++) {
                if (!graph.isEdge(subset.get(i), subset.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }


    private boolean hasCliqueUtil(ArrayList<Integer> subset, int start, int k) {
        if (subset.size() == k) {
            return isClique(subset);
        }

        for (int i = start; i < graph.getNumberOfVertices(); i++) {
            subset.add(i);
            if (hasCliqueUtil(subset, i + 1, k)) {
                return true;
            }
            subset.removeLast();
        }
        return false;
    }
}
