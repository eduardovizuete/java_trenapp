package org.edviz.trenapp.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.edviz.trenapp.command.Command;
import org.edviz.trenapp.command.CreateDirectedGraphCommand;
import org.edviz.trenapp.command.DistanceBetweenRoutesCommand;
import org.edviz.trenapp.command.DistanceShortestRoutesCommand;
import org.edviz.trenapp.command.GraphServiceInvoker;
import org.edviz.trenapp.command.NumberOfTripsWithExactStopsCommand;
import org.edviz.trenapp.command.NumberOfTripsWithLessDistanceCommand;
import org.edviz.trenapp.command.NumberOfTripsWithMaxStopsCommand;
import org.edviz.trenapp.model.Graph;
import org.edviz.trenapp.model.Node;
import org.edviz.trenapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphRunTask {
	
	private static final Logger log = LoggerFactory.getLogger(GraphRunTask.class);
	
	private String fileName;
	
	public GraphRunTask(String fileName) {
		this.fileName = fileName;
	}

	public void runTasks() {
		// create the receiver
		GraphService gs = new GraphServiceImpl(new Graph());
		// run commands
		createCommands(gs).forEach(command -> {
			if (command != null) {
				//Creating invoker and associating with Command
				GraphServiceInvoker invoker = new GraphServiceInvoker(command);
				//perform action on invoker object
				invoker.execute();	
			}		
		});
	}
	
	private List<Command> createCommands(final GraphService gs) {
		List<Command> commands = new ArrayList<>();
		try {
			Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
			Stream<String> lines = Files.lines(path);
			lines.forEach(line -> {
				commands.add(getCommandAndParams(gs, line));				
			});
		    lines.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return commands;
	}
	
	private Command getCommandAndParams(final GraphService gs, final String line) {
		String paramsLine[] = line.toUpperCase().split(Constants.COLON);
		String nameCommand = paramsLine[0];
		String fullCommand = paramsLine[1].trim();
		Command command = null;
		String params[];
		switch (nameCommand) {
			case Constants.START_WITH_GRAPH:
				//creating command, associating with receiver and add to list
				command = new CreateDirectedGraphCommand(gs, fullCommand);
				break;
			case Constants.START_WITH_DISTANCE:
				//creating command, associating with receiver and add to list
				command = new DistanceBetweenRoutesCommand(gs, fullCommand);
				break;
			case Constants.START_WITH_TRIPSMAXSTOPS:
				params = fullCommand.split(Constants.HYPHEN);
				//creating command, associating with receiver and add to list
				command = new NumberOfTripsWithMaxStopsCommand(gs, new Node(params[0]), new Node(params[1]), Integer.parseInt(params[2]));
				break;
			case Constants.START_WITH_TRIPSEXACTSTOPS:
				params = fullCommand.split(Constants.HYPHEN);
				//creating command, associating with receiver and add to list
				command = new NumberOfTripsWithExactStopsCommand(gs, new Node(params[0]), new Node(params[1]), Integer.parseInt(params[2]));
				break;
			case Constants.START_WITH_DISTANCESHORTESTROUTE:
				params = fullCommand.split(Constants.HYPHEN);
				//creating command, associating with receiver and add to list
				command = new DistanceShortestRoutesCommand(gs, new Node(params[0]), new Node(params[1]));
				break;
			case Constants.START_WITH_TRIPSLESSDISTANCE:
				params = fullCommand.split(Constants.HYPHEN);
				//creating command, associating with receiver and add to list
				command = new NumberOfTripsWithLessDistanceCommand(gs, new Node(params[0]), new Node(params[1]), Integer.parseInt(params[2]));
				break;
			default:
				break;
		}
		return command;
	}

}
