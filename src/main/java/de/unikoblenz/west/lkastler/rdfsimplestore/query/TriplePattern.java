package de.unikoblenz.west.lkastler.rdfsimplestore.query;

import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Token;

/**
 * TODO add comment
 * @author lkastler
 *
 */
public class TriplePattern implements Query {

	private Token subject;
	private Token predicate;
	private Token object;
	
	/**
	 * TODO add comment
	 * @param subject
	 * @param predicate
	 * @param object
	 */
	public TriplePattern(Token subject, Token predicate, Token object) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}
	/**
	 * @return the subject
	 */
	public Token getSubject() {
		return subject;
	}
	/**
	 * @return the predicate
	 */
	public Token getPredicate() {
		return predicate;
	}
	/**
	 * @return the object
	 */
	public Token getObject() {
		return object;
	}
	
	
}
