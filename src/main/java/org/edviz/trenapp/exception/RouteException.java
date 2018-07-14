package org.edviz.trenapp.exception;

import org.edviz.trenapp.utils.Constants;

public class RouteException extends RuntimeException {

	private static final long serialVersionUID = 6401133136934937066L;
	
	public RouteException() {
		super(Constants.MSG_NO_SUCH_ROUTE);
	}

}
