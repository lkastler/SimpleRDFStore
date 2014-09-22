package de.unikoblenz.west.lkastler.rdfsimplestore.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.EvaluationException;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.BasicGraphPattern;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Query;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.TriplePattern;

/**
 * defines a simple query engine.
 * @author lkastler
 *
 */
public class SimpleQueryEngine implements QueryEngine {

	static Logger log = LogManager.getLogger();
	
	private ArrayList<QueryEngine> engines;
	
	/**
	 * constructor given a list of stores.
	 * @param storages.
	 */
	public SimpleQueryEngine(List<QueryEngine> storages) {
		this.engines = new ArrayList<QueryEngine>(storages);
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine#query(de.unikoblenz.west.lkastler.rdfsimplestore.query.Query)
	 */
	public Mappings query(Query query) throws EvaluationException {
		Mappings sol = new MappingsImpl();
		
		if(query instanceof TriplePattern) {
			for(QueryEngine store : engines) {
				Mappings s = store.query(query);
				sol.addAll(s);
			}
			
			log.info("solution: " + sol);
			
			return sol;
		}
		
		else if(query instanceof BasicGraphPattern) {
			Mappings buffer = new MappingsImpl();
			
			for(QueryEngine store : engines) {
				Mappings s = store.query(query);
				
				log.debug("before store " + store.getId() + ": " + s);
				
				buffer.addAll(s);
								
				log.debug("after store " + store.getId() + ": " + sol);
			}
			
			log.debug("all solutions from distr. stores: " + buffer);
			
			for(Mapping m : buffer) {
				sol.addAll(sol.join(m));
				sol.add(m);
			}
			
			log.debug("uncleaned solution: " + sol);
			
			// remove incomplete matchings
			sol = removeIncompleteMappings(sol, (BasicGraphPattern)query);
			
			log.debug("cleaned incomplete mappings solution: " + sol);
			
			// select only mappings that use all variables.
			//sol = select(sol, getVariables((BasicGraphPattern)query));
					
			log.info("solution: " + sol);
			
			return sol;
		}
		
		throw new EvaluationException("not supported");
	}

	
	private Mappings removeIncompleteMappings(Mappings map, BasicGraphPattern bgp) {
		Mappings result = new MappingsImpl();
		
		Set<Query> neededMatchings = new HashSet<Query>(bgp);
		
		for(Mapping m : map) {
			if(m.getMatchingQueries().equals(neededMatchings)) {
				result.add(m);
			}
		}
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine#getId()
	 */
	public long getId() {
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleQueryEngine[" + Long.toString(getId()) + "]";
	}
}
