package de.unikoblenz.west.lkastler.rdfsimplestore.impl;

import java.util.TreeMap;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.EvaluationException;
import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.StorageException;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.BasicGraphPattern;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Query;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.TriplePattern;
import de.unikoblenz.west.lkastler.rdfsimplestore.storage.Storage;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Term;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Triple;

/**
 * stores RDF in several tables. 
 * 
 * @author lkastler
 */
public class TableStorage implements Storage, QueryEngine {
	
	TreeMap<Term, TreeMap<Term, Term>> spo = new TreeMap<Term, TreeMap<Term, Term>>();
	TreeMap<Term, TreeMap<Term, Term>> sop = new TreeMap<Term, TreeMap<Term, Term>>();
	TreeMap<Term, TreeMap<Term, Term>> pso = new TreeMap<Term, TreeMap<Term, Term>>();
	TreeMap<Term, TreeMap<Term, Term>> pos = new TreeMap<Term, TreeMap<Term, Term>>();
	TreeMap<Term, TreeMap<Term, Term>> osp = new TreeMap<Term, TreeMap<Term, Term>>();
	TreeMap<Term, TreeMap<Term, Term>> ops = new TreeMap<Term, TreeMap<Term, Term>>();
	
	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.storage.Storage#add(de.unikoblenz.west.lkastler.rdfsimplestore.structure.Triple)
	 */
	public void add(Triple triple) throws StorageException {
		add(spo, triple.getSubject(), triple.getPredicate(), triple.getObject());
	}

	/**
	 * adds value under the given keys to the given table.
	 * @param table - table to add value to.
	 * @param key1 - first key.
	 * @param key2 - second key.
	 * @param value - data.
	 */
	private void add(TreeMap<Term, TreeMap<Term, Term>> table, Term key1, Term key2, Term value) {
		if(!table.containsKey(key1)) {
			table.put(key1, new TreeMap<Term,Term>());
		}
		
		table.get(key1).put(key2, value);
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine#query(de.unikoblenz.west.lkastler.rdfsimplestore.query.Query)
	 */
	public Mappings query(Query query) throws EvaluationException {
		if(query instanceof TriplePattern) {
			return query((TriplePattern)query);
		}
		
		if(query instanceof BasicGraphPattern) {
			BasicGraphPattern bgp = (BasicGraphPattern)query;
			
			Mappings map = new Mappings();
			
			for(TriplePattern p : bgp) {
				map.addAll(query(p));
			}
			
			return map;
			
		}
	
		throw new EvaluationException("not supported");
	}
	
	private Mappings query(TriplePattern query) throws EvaluationException {
		throw new EvaluationException("not supported");
	}
}
