package de.unikoblenz.west.lkastler.rdfsimplestore.exceptions;

/**
 * indicates an error during parsing of text.
 * 
 * @author lkastler
 */
public class ParsingException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * constructor with error message.
	 * @param msg - error message.
	 */
	public ParsingException(String msg) {
		super(msg);
	}
}
