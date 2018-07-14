package org.edviz.trenapp.command;

public class GraphServiceInvoker {
	
	private Command command;

	public GraphServiceInvoker(Command command) {
		this.command = command;
	}
	
	public void execute() {
		this.command.execute();
	}

}
