package org.ulpmm.univrav.dao;

/**
 * To manage dao exceptions
 * 
 * @author morgan
 *
 */
public class DaoException extends RuntimeException {

	/** Serial version */
	private static final long serialVersionUID = -7841951573787188540L;

	/**
	 * To manage the error message to display
	 * 
	 * @param message the error message to display
	 */
	public DaoException(String message) {
		super(message);
	}

}
