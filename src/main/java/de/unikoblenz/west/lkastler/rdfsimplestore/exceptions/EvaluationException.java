package de.unikoblenz.west.lkastler.rdfsimplestore.exceptions;

/**
 * indicates an issue during the evaluation of a query. 
 * 
 * @author lkastler
 */
public class EvaluationException extends Exception {

	/**
	 * constructor with message
	 * @param msg - error message.
	 */
	public EvaluationException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;
}
