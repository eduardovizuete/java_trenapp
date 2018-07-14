package org.edviz.trenapp.command;

import org.edviz.trenapp.service.GraphService;

public class DistanceBetweenRoutesCommand implements Command {
	
	private GraphService g;
	String route;

	public DistanceBetweenRoutesCommand(GraphService g, String route) {
		this.g = g;
		this.route = route;
	}

	@Override
	public void execute() {
		g.distanceBetweenRoutes(route);
	}

}
