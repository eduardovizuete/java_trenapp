package org.edviz.trenapp.criteria;

import org.edviz.trenapp.model.Path;

public class ContainsPathCriteria implements PathCriteria {
	
	private final Path finalPath;

	public ContainsPathCriteria(Path finalPath) {
		this.finalPath = finalPath;
	}

	@Override
	public boolean passCriteria(Path path) {
		return finalPath.startWithPath(path); 
	}

}
