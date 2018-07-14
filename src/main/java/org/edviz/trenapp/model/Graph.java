package org.edviz.trenapp.model;

import java.util.ArrayList;
import java.util.List;

import org.edviz.trenapp.criteria.PathCriteria;
import org.edviz.trenapp.exception.RouteException;

public class Graph {

    private List<Node> nodes = new ArrayList<Node>();

    public Graph() {
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
    	if(getNode(node.getName()) == null) {
    		nodes.add(node);
    	}
    }

    public Node getNode(String name) {
        for (final Node n: getNodes()) {
            if (n.getName().equals(name)) {
                return n;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                '}';
    }
    
    private void existNode(Node node) {
        if (getNode(node.getName()) == null) {
            throw new IllegalArgumentException("Node " + node.getName() + " does not exist");
        }
    }
    
    public List<Path> getAllPathsWithCriteria(Node sourceNode, Node finalNode, PathCriteria criteria) {
		existNode(sourceNode);
		existNode(finalNode);

        List<Path> paths = new ArrayList<Path>();
        
        for (Edge e : getNode(sourceNode.getName()).getEdges()) {
            Path path = Path.createEmptyPath();
            path.addEdge(e);
            paths.addAll(searchPathsWithCriteria(path, criteria, finalNode));
        }

        if (paths.isEmpty()) {
            throw new RouteException();
        }
        return paths;
    }
    
    // DFS 
	private List<Path> searchPathsWithCriteria(Path path, PathCriteria criteria, Node finalNode) {
		List<Path> paths = new ArrayList<Path>();
		if (criteria.passCriteria(path)) {
			if (isEndOfRoute(path, finalNode)) {
				paths.add(Path.copyPath(path));
			}
			for (Edge e : getNode(path.getLastNode().getName()).getEdges()) {
				path.addEdge(e);
				paths.addAll(searchPathsWithCriteria(path, criteria, finalNode));
			}

		}
		path.removeLastEdge();
		return paths;
	}
	
	private boolean isEndOfRoute(Path path, Node finalNode) {
        return path.getLastNode().getName().equals(finalNode.getName());
    }
 	
 	

}
