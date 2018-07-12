package org.edviz.trenapp.command;

import org.edviz.trenapp.model.Node;
import org.edviz.trenapp.service.GraphService;

public class NumberOfTripsWithLessDistanceCommand implements Command {
	
	private GraphService g;
	private Node from, to;
	private int distance;

	public NumberOfTripsWithLessDistanceCommand(GraphService g, Node from, Node to, int distance) {
		super();
		this.g = g;
		this.from = from;
		this.to = to;
		this.distance = distance;
	}

	@Override
	public void execute() {
		g.numberOfTripsWithLessDistance(from, to, distance);
	}

}
