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
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Variable;

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
		
		if(query instanceof BasicGraphPattern) {
					
			for(QueryEngine store : engines) {
				Mappings s = store.query(query);
				
				log.debug("before store " + store.getId() + ": " + s);
				
				sol.addAll(sol.join(s));
				
				sol.addAll(s);
				
				log.debug("after store " + store.getId() + ": " + sol);
			}
			
			log.debug("uncleaned solution: " + sol);
			
			// remove incomplete matchings
			sol = removeIncompleteMappings(sol, (BasicGraphPattern)query);
			
			log.debug("cleand incomplete mappings solution: " + sol);
			
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
	
	//
	private Mappings select(Mappings map, Set<Variable> v) {
		Mappings result = new MappingsImpl();
		
		for(Mapping m : map) {
			if(m.getVariables().equals(v)) {
				result.add(m);
			}
		}
		
		return result;
	}
	
	//
	private Set<Variable> getVariables(BasicGraphPattern bgp) {
		HashSet<Variable> result = new HashSet<Variable>();
		
		for(TriplePattern tp : bgp) {
			result.addAll(getVariables(tp));
		}

		log.debug("variables: " + result);
		
		return result;
	}
	
	//
	private Set<Variable> getVariables(TriplePattern tp) {
		HashSet<Variable> result = new HashSet<Variable>();
		
		if(tp.getSubject() instanceof Variable) {
			result.add((Variable)tp.getSubject());
		}
		if(tp.getPredicate() instanceof Variable) {
			result.add((Variable)tp.getPredicate());
		}
		if(tp.getObject() instanceof Variable) {
			result.add((Variable)tp.getObject());
		}
		
		return result;
	}
	

	public long getId() {
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleQueryEngine";
	}
}
