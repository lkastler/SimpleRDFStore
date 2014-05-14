package de.unikoblenz.west.lkastler.rdfsimplestore.query;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.EvaluationException;

/**
 * defines an interface for query engines.
 * @author lkastler
 *
 */
public interface QueryEngine {
	
	public Mappings query(Query query) throws EvaluationException;
	
}
