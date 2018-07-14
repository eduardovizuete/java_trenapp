package org.edviz.trenapp.criteria;

import org.edviz.trenapp.model.Path;

public class LessDistanceCriteria implements PathCriteria {
	
	private final int distance;

	public LessDistanceCriteria(int distance) {
		this.distance = distance;
	}

	@Override
	public boolean passCriteria(Path path) {
		return path.getTotalDistance() < distance;
	}

}
