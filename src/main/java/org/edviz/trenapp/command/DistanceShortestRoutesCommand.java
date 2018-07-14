package org.edviz.trenapp.command;

import org.edviz.trenapp.model.Node;
import org.edviz.trenapp.service.GraphService;

public class DistanceShortestRoutesCommand implements Command {

	private GraphService g;
	private Node from, to;
	
	public DistanceShortestRoutesCommand(GraphService g, Node from, Node to) {
		super();
		this.g = g;
		this.from = from;
		this.to = to;
	}


	@Override
	public void execute() {
		g.distanceShortestRoutes(from, to);

	}

}
