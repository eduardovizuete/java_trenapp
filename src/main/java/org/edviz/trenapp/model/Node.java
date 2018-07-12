package org.edviz.trenapp.model;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private String name;
    private List<Edge> edges = new ArrayList<Edge>();

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void addEdge(Edge edge) {
        if (!edges.contains(edge)) {
            edges.add(edge);
        }
    }

    @Override
    public String toString() {
        return "\nNode{" +
                "name='" + name + '\'' +
                ", \n\tedges=" + edges +
                '}';
    }
}
