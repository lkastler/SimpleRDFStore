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
import de.unikoblenz.west.lkastler.rdfsimplestore.storage.Storage;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Variable;

/**
 * defines a simple query engine.
 * @author lkastler
 *
 */
public class SimpleQueryEngine implements QueryEngine {

	static Logger log = LogManager.getLogger();
	
	private ArrayList<Storage> storages;
	
	/**
	 * constructor given a list of stores.
	 * @param storages.
	 */
	public SimpleQueryEngine(List<Storage> storages) {
		this.storages = new ArrayList<Storage>(storages);
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine#query(de.unikoblenz.west.lkastler.rdfsimplestore.query.Query)
	 */
	public Mappings query(Query query) throws EvaluationException {
		Mappings sol = new MappingsImpl();
		
		if(query instanceof TriplePattern) {
			for(Storage store : storages) {
				Mappings s = store.query(query);
				sol.addAll(s);
			}
			
			log.info("solution: " + sol);
			
			return sol;
		}
		
		if(query instanceof BasicGraphPattern) {
					
			for(Storage store : storages) {
				Mappings s = store.query(query);
				
				sol = sol.join(s);
				
				sol.addAll(s);
			}
			
			Mappings cleaned = select(sol, getVariables((BasicGraphPattern)query));
					
			log.info("solution: " + cleaned);
			
			return cleaned;
		}
		
		throw new EvaluationException("not supported");
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleQueryEngine";
	}

}
