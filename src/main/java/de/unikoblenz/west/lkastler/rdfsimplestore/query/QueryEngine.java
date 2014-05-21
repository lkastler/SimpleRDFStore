package de.unikoblenz.west.lkastler.rdfsimplestore.query;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.EvaluationException;

/**
 * defines an interface for query engines.
 * @author lkastler
 *
 */
public interface QueryEngine {
	
	/**
	 * evaluates given query and returns a set of Mapping that fulfill it. 
	 * @param query - query to evaluate
	 * @return a Mappings object that fulfill the query.
	 * @throws EvaluationException - thrown if the query evaluation went wrong.
	 */
	public Mappings query(Query query) throws EvaluationException;
	
	/**
	 * returns the identifier of this QueryEngine.
	 * @return the identifier of this QueryEngine.
	 */
	public long getId();
	
}
