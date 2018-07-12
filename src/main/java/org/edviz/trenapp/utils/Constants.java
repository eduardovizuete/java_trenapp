package org.edviz.trenapp.utils;

public final class Constants {
	
	public static final String NODE_A = "A";
	public static final String NODE_B = "B";
	public static final String NODE_C = "C";
	public static final String NODE_D = "D";
	public static final String NODE_E = "E";
	
	public static final String COLON = ":";
	public static final String HYPHEN = "-";
	
	public static final String START_WITH_GRAPH = "GRAPH";
	public static final String START_WITH_DISTANCE = "DISTANCE";
	public static final String START_WITH_TRIPSMAXSTOPS = "TRIPSMAXSTOPS";
	public static final String START_WITH_TRIPSEXACTSTOPS = "TRIPSEXACTSTOPS";
	public static final String START_WITH_DISTANCESHORTESTROUTE = "DISTANCESHORTESTROUTE";
	public static final String START_WITH_TRIPSLESSDISTANCE = "TRIPSLESSDISTANCE";
	public static final String COMMA = ",";
	
	public static final String filename = "input.txt";
	public static final String MSG_NO_SUCH_ROUTE = "NO SUCH ROUTE";

	private Constants() {
		throw new AssertionError();
	}

}
