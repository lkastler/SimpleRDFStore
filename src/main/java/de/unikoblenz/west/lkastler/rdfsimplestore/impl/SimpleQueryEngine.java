package de.unikoblenz.west.lkastler.rdfsimplestore.impl;

import java.util.ArrayList;
import java.util.List;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.EvaluationException;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Query;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings;
import de.unikoblenz.west.lkastler.rdfsimplestore.storage.Storage;

/**
 * defines a simple query engine.
 * @author lkastler
 *
 */
public class SimpleQueryEngine implements QueryEngine {

	private ArrayList<Storage> storages;
	
	public SimpleQueryEngine(List<Storage> storages) {
		this.storages = new ArrayList<Storage>(storages);
	}
	
	public Mappings query(Query query) throws EvaluationException {
		// TODO implement
		
		Mappings sol = new Mappings();
		
		for(Storage store : storages) {
			sol.addAll(store.query(query));
		}
		
		throw new EvaluationException("not implemented");
	}

}
