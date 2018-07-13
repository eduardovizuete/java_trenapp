package org.edviz.trenapp.service;

import java.util.ArrayList;
import java.util.List;

import org.edviz.trenapp.criteria.ContainsPathCriteria;
import org.edviz.trenapp.criteria.MaxStopsCriteria;
import org.edviz.trenapp.exception.RouteException;
import org.edviz.trenapp.model.Edge;
import org.edviz.trenapp.model.Graph;
import org.edviz.trenapp.model.Node;
import org.edviz.trenapp.model.Nodo;
import org.edviz.trenapp.model.Path;
import org.edviz.trenapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphServiceImpl implements GraphService {
	
	private static final Logger log = LoggerFactory.getLogger(GraphServiceImpl.class);
	
	private final Graph gd;

	public GraphServiceImpl(Graph gd) {
		super();
		this.gd = gd;
	}
	
	@Override
	public void createDirectedGraph(String dirGraph) {
		log.info("Directed graph: " + dirGraph);
		String[] strNodes = dirGraph.split(Constants.COMMA);
		
		for (String str : strNodes) {
			str = str.trim();	
			
			Node nodeFrom = gd.getNode(String.valueOf(str.charAt(0)));
			Node nodeTo = gd.getNode(String.valueOf(str.charAt(1)));
			
			nodeFrom = (nodeFrom == null)?  new Node(String.valueOf(str.charAt(0))) : nodeFrom;
			nodeTo = (nodeTo == null)?  new Node(String.valueOf(str.charAt(1))) : nodeTo;
			nodeFrom.addEdge(new Edge(nodeFrom, nodeTo, Integer.parseInt(String.valueOf(str.charAt(2)))));
			
			gd.addNode(nodeFrom);				
			gd.addNode(nodeTo);
		}
	}
	
//	@Override
//	public void createDirectedGraph(String dirGraph) {
//		log.info("Directed graph: " + dirGraph);
//		Node nodoA = new Node(Constants.NODE_A);
//		Node nodoB = new Node(Constants.NODE_B);
//		Node nodoC = new Node(Constants.NODE_C);
//		Node nodoD = new Node(Constants.NODE_D);
//		Node nodoE = new Node(Constants.NODE_E);
//		
//		nodoA.addEdge(new Edge(nodoA, nodoB, 5));
//		nodoB.addEdge(new Edge(nodoB, nodoC, 4));
//		nodoC.addEdge(new Edge(nodoC, nodoD, 8));
//		nodoD.addEdge(new Edge(nodoD, nodoC, 8));
//		nodoD.addEdge(new Edge(nodoD, nodoE, 6));
//		nodoA.addEdge(new Edge(nodoA, nodoD, 5));
//		nodoC.addEdge(new Edge(nodoC, nodoE, 2));
//		nodoE.addEdge(new Edge(nodoE, nodoB, 3));
//		nodoE.addEdge(new Edge(nodoE, nodoB, 3));
//		nodoA.addEdge(new Edge(nodoA, nodoE, 7));
//		
//		gd.addNode(nodoA);
//		gd.addNode(nodoB);
//		gd.addNode(nodoC);
//		gd.addNode(nodoD);
//		gd.addNode(nodoE);
//	}

	@Override
	public int distanceBetweenRoutes(String route) {
		try {
			List<Node> nodesInter = getIntermediateNodes(route);
			List<Node> nodes = getFromAndToNodes(route);
			Path finalPath = createPath(nodes.get(0), nodes.get(1), nodesInter);
	        List<Path> allPaths = gd.getAllPathsWithCriteria(nodes.get(0), nodes.get(1), new ContainsPathCriteria(finalPath));
	        log.info("Distance " + route + ": " + allPaths.get(0).getTotalDistance());
	        return allPaths.get(0).getTotalDistance();
		} catch(RouteException re) {
			log.info("Distance " + route + ": " + re.getMessage());
		}
		
		return 0;	
	}

	@Override
	public int numberOfTripsWithMaxStops(Node from, Node to, int stops) {
		int numPath = 0;
		try {
			numPath = gd.getAllPathsWithCriteria(from, to, new MaxStopsCriteria(stops)).size();			
			log.info("Trips with maximun stops: " 
					+ from.getName() + Constants.HYPHEN 
					+ to.getName() + Constants.HYPHEN
					+ stops + ": "
					+ numPath);
			return numPath;
		} catch(RouteException re) {
			log.info("Trips with maximun stops: " 
					+ from.getName() + Constants.HYPHEN 
					+ to.getName() + Constants.HYPHEN
					+ stops
					+ re.getMessage());
		}
		
		return numPath;
	}

	@Override
	public int numberOfTripsWithExactStops(Node from, Node to, int stops) {
		log.info("Trips with exacts stops: " 
				+ from.getName() + Constants.HYPHEN 
				+ to.getName() + Constants.HYPHEN
				+ stops);
		return 0;
	}

	@Override
	public int distanceShortestRoutes(Node from, Node to) {
		log.info("Distance shortest route: " 
				+ from.getName() + Constants.HYPHEN + to.getName());
		return 0;
	}

	@Override
	public int numberOfTripsWithLessDistance(Node from, Node to, int distance) {
		log.info("Trips with less distance: " 
				+ from.getName() + Constants.HYPHEN 
				+ to.getName() + Constants.HYPHEN
				+ distance);
		return 0;
	}

	@Override
	public void printGraph() {
		// TODO Auto-generated method stub
	}
	
	public List<Node> getIntermediateNodes(String strPath) {
		String[] strNodes = strPath.split("-");
		List<Node> nodesIn = new ArrayList<Node>();
        for (int i = 1; i < strNodes.length - 1; i++) {
        	nodesIn.add(new Node(strNodes[i]));
        }
        
        return nodesIn;
	}
	
	public List<Node> getFromAndToNodes(String strPath) {
		String[] strNodes = strPath.split("-");
		List<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(strNodes[0]));
        nodes.add(new Node(strNodes[strNodes.length - 1]));
        
        return nodes;
	}
	
	private Path createPath(Node sourceNode, Node finalNode, List<Node> nodesInter) {
        Path finalPath = Path.createEmptyPath();
        Node currentNode = sourceNode;
        if (nodesInter != null) {
            for (Node node : nodesInter) {
            	finalPath.addEdge(new Edge(currentNode, node, 0));
            	currentNode = node;
            }
        }
        finalPath.addEdge(new Edge(currentNode, finalNode, 0));
        return finalPath;
    }

}
