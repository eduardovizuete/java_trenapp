package org.edviz.trenapp.service;

import org.edviz.trenapp.model.Node;

public interface GraphService {

    public void createDirectedGraph(String dirGraph);

    int distanceBetweenRoutes(String route);

    int numberOfTripsWithMaxStops(Node from, Node to, int stops);

    int numberOfTripsWithExactStops(Node from, Node to, int stops);

    int distanceShortestRoutes(Node from, Node to);

    int numberOfTripsWithLessDistance(Node from, Node to, int distance);

    public void printGraph();
}
