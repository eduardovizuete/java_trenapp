package org.edviz.trenapp.command;

import org.edviz.trenapp.model.Node;
import org.edviz.trenapp.service.GraphService;

public class NumberOfTripsWithMaxStopsCommand implements Command {
	
	private GraphService g;
	private Node from, to;
	private int stops;

	public NumberOfTripsWithMaxStopsCommand(GraphService g, Node from, Node to, int stops) {
		super();
		this.g = g;
		this.from = from;
		this.to = to;
		this.stops = stops;
	}

	@Override
	public void execute() {
		g.numberOfTripsWithMaxStops(from, to, stops);
	}

}
