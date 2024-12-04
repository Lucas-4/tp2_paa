package org.example;

import java.util.List;

public class Clique {
    private List<Integer> vertices;

    public Clique(List<Integer> vertices) {
        this.vertices = vertices;
    }

    public List<Integer> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        return "Clique: " + vertices;
    }
}