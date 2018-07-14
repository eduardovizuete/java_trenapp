package org.edviz.trenapp.criteria;

import org.edviz.trenapp.model.Path;

public class MaxStopsCriteria implements PathCriteria {
	
	private final int maxStops;

	public MaxStopsCriteria(int maxStops) {
		this.maxStops = maxStops;
	}

	@Override
	public boolean passCriteria(Path path) {
		return path.getNumberOfStops() <= maxStops;
	}

}
