package org.edviz.trenapp.command;

import org.edviz.trenapp.service.GraphService;

public class CreateDirectedGraphCommand implements Command {
	
	private GraphService g;
	private String dirGraph;

	public CreateDirectedGraphCommand(GraphService g, String dirGraph) {
		super();
		this.g = g;
		this.dirGraph = dirGraph;
	}

	@Override
	public void execute() {
		g.createDirectedGraph(dirGraph);
	}

}
