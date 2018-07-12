package org.edviz.trenapp.model;

import java.util.Objects;

public class Edge {

    private Node from;
    private Node to;
    private int distance;

    public Edge(Node from, Node to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(from.getName(), edge.from.getName()) &&
                Objects.equals(to.getName(), edge.to.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from.getName() +
                ", to=" + to.getName() +
                ", distance=" + distance +
                '}';
    }
    
}
