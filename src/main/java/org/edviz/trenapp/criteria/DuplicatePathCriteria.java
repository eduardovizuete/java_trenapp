package org.edviz.trenapp.criteria;

import org.edviz.trenapp.model.Path;

public class DuplicatePathCriteria implements PathCriteria {

	@Override
	public boolean passCriteria(Path path) {
		return !path.hasDuplicated();
	}

}
