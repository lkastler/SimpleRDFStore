package de.unikoblenz.west.lkastler.rdfsimplestore.query;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.EvaluationException;
import de.unikoblenz.west.lkastler.rdfsimplestore.impl.MappingsImpl;

/**
 * defines an interface for query engines.
 * @author lkastler
 *
 */
public interface QueryEngine {
	
	public MappingsImpl query(Query query) throws EvaluationException;
	
}
