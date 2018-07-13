package org.edviz.trenapp.model;

import java.util.ArrayList;
import java.util.List;

public class Path implements Comparable {
	
	private List<Edge> edges = new ArrayList<Edge>();
	private int totalDistance = 0;
	
	private Path() {}
	
	public static Path createEmptyPath() {
        return new Path();
    }
	
	public static Path copyPath(Path path) {
        return new Path(path);
    }
	
	private Path(Path path) {
		edges.addAll(path.getEdges());
        this.totalDistance = path.getTotalDistance();
    }

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}
	
	public void addEdge(Edge edge) {
		if (!isEdgeNext(edge)) {
            throw new IllegalArgumentException("The edge " + edge + " is not consecutive to the existing path");
        }
		edges.add(edge);
        totalDistance += edge.getDistance();
    }
	
	private boolean isEdgeNext(Edge edge) {
        Node lastNode = getLastNode();
        if (lastNode != null && !lastNode.equals(edge.getFrom())) {
            return false;
        }
        return true;
    }
	
	public Node getLastNode() {
		Node node = null;
        if (!edges.isEmpty()) {
            node = edges.get(edges.size() - 1).getTo();
        }
        return node;
    }
	
	public void removeLastEdge() {
        if (!edges.isEmpty()) {
            Edge lastEdge = edges.get(edges.size() - 1);
            this.totalDistance -= lastEdge.getDistance();
            edges.remove(edges.size() - 1);
        }
    }
	
	public boolean hasDuplicated() {
        for (int i = 0; i < edges.size(); i++) {
            for (int j = i + 1; j < edges.size(); j++) {
                if (edges.get(i).equals(edges.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }
	
	public boolean startWithPath(Path path) {
        List<Edge> partialPath = path.getEdges();
        List<Edge> fullPath = getEdges();
        for (int i = 0; i < partialPath.size(); i++) {
            if (i >= fullPath.size() || !partialPath.get(i).equals(fullPath.get(i))) {
                return false;
            }
        }
        return true;
    }
	
	public int getNumberOfStops() {
        return edges.size();
    }

	@Override
	public String toString() {
		return "Path (" + totalDistance + ") [edges=" + edges + "]";
	}

	@Override
	public int compareTo(Object o) {
		return this.getTotalDistance() - ((Path) o).getTotalDistance();
	}

}
