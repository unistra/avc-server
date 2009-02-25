package org.ulpmm.univrav.dao;

public class DaoException extends RuntimeException {

	private static final long serialVersionUID = -7841951573787188540L;

	/**
	 * @param message the error message to display
	 */
	public DaoException(String message) {
		super(message);
	}

}
