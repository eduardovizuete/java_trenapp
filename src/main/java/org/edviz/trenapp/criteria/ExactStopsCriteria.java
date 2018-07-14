package org.edviz.trenapp.criteria;

import org.edviz.trenapp.model.Path;

public class ExactStopsCriteria implements PathCriteria {
	
	private final int exactStops;

	public ExactStopsCriteria(int exactStops) {
		this.exactStops = exactStops;
	}

	@Override
	public boolean passCriteria(Path path) {
		return path.getNumberOfStops() == exactStops;
	}

}
