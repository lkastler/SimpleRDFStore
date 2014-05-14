package de.unikoblenz.west.lkastler.rdfsimplestore.impl;

import java.util.HashMap;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.EvaluationException;
import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.StorageException;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.BasicGraphPattern;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Query;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.TriplePattern;
import de.unikoblenz.west.lkastler.rdfsimplestore.storage.Storage;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Term;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Triple;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Variable;

/**
 * stores RDF in several tables. 
 * 
 * @author lkastler
 */
public class TableStorage implements Storage, QueryEngine {
	
	HashMap<Term, HashMap<Term, Term>> spo = new HashMap<Term, HashMap<Term, Term>>();
	HashMap<Term, HashMap<Term, Term>> sop = new HashMap<Term, HashMap<Term, Term>>();
	HashMap<Term, HashMap<Term, Term>> pso = new HashMap<Term, HashMap<Term, Term>>();
	HashMap<Term, HashMap<Term, Term>> pos = new HashMap<Term, HashMap<Term, Term>>();
	HashMap<Term, HashMap<Term, Term>> osp = new HashMap<Term, HashMap<Term, Term>>();
	HashMap<Term, HashMap<Term, Term>> ops = new HashMap<Term, HashMap<Term, Term>>();
	
	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.storage.Storage#add(de.unikoblenz.west.lkastler.rdfsimplestore.structure.Triple)
	 */
	public void add(Triple triple) throws StorageException {
		add(spo, triple.getSubject(), triple.getPredicate(), triple.getObject());
		add(sop, triple.getSubject(), triple.getObject(), triple.getPredicate());
		add(pso, triple.getPredicate(), triple.getSubject(), triple.getObject());
		add(pos, triple.getPredicate(), triple.getObject(), triple.getSubject());
		add(osp, triple.getObject(), triple.getSubject(), triple.getPredicate());
		add(ops, triple.getObject(), triple.getPredicate(), triple.getSubject());
	
	}

	/**
	 * adds value under the given keys to the given table.
	 * @param table - table to add value to.
	 * @param key1 - first key.
	 * @param key2 - second key.
	 * @param value - data.
	 */
	private void add(HashMap<Term, HashMap<Term, Term>> table, Term key1, Term key2, Term value) {
		if(!table.containsKey(key1)) {
			table.put(key1, new HashMap<Term,Term>());
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
			
			Mappings map = new MappingsImpl();
			
			for(TriplePattern p : bgp) {
				Mappings m = query(p);
				
				map = map.join(m);
				
				map.addAll(m);
			}
						
			
			return map;
			
		}
	
		throw new EvaluationException("not supported");
	}
	
	//
	private Mappings query(TriplePattern query) throws EvaluationException {
		Mappings result = new MappingsImpl();
		
		if(query.getSubject() instanceof Variable) {
			if(query.getPredicate() instanceof Variable) {
				if(query.getObject() instanceof Variable) {
					// ? ? ?
					for(Term _s : spo.keySet()) {
						for(Term _p : spo.get(_s).keySet()) {
							Mapping map = new MappingImpl();
							
							map.put((Variable) query.getSubject(), _s);
							map.put((Variable) query.getPredicate(), _p);
							
							map.put((Variable) query.getObject(), spo.get(_s).get(_p));
							
							result.add(map);
						}
					}
				}
				else {
					// ? ? o
					for(Term _p : ops.get(query.getObject()).keySet()) {
						Mapping map = new MappingImpl();
						
						map.put((Variable) query.getSubject(), ops.get(query.getObject()).get(_p));
						map.put((Variable) query.getPredicate(), _p);
													
						result.add(map);
					}
				}
			}
			else {
				// ? p ?
				if(query.getObject() instanceof Variable) {
					for(Term _s : pso.get(query.getPredicate()).keySet()) {
						Mapping map = new MappingImpl();
						
						map.put((Variable) query.getSubject(), _s);
						map.put((Variable) query.getObject(), pso.get(query.getPredicate()).get(_s));
													
						result.add(map);
					}
				}				
				else {
					// ? p o
					Mapping map = new MappingImpl();
					
					map.put((Variable) query.getSubject(), pso.get(query.getPredicate()).get(query.getObject()));
					
					result.add(map);
				}
			}
		}
		else {
			if(query.getPredicate() instanceof Variable) {
				if(query.getObject() instanceof Variable) {
					// s ? ?
					for(Term _p : spo.get(query.getSubject()).keySet()) {
						Mapping map = new MappingImpl();
						
						map.put((Variable) query.getObject(), ops.get(query.getSubject()).get(_p));
						map.put((Variable) query.getPredicate(), _p);
													
						result.add(map);
					}
				}
				else {
					// s ? o
					Mapping map = new MappingImpl();
					
					map.put((Variable) query.getPredicate(), sop.get(query.getSubject()).get(query.getObject()));
					
					result.add(map);
				}
			}
			else {
				if(query.getObject() instanceof Variable) {
					// s p ?
					Mapping map = new MappingImpl();
					
					map.put((Variable) query.getObject(), sop.get(query.getSubject()).get(query.getPredicate()));

					result.add(map);
				}
				else {
					// s p o
				}
			}
		}
		
		return result;
	}
	

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TableStorage [" + spo + "]";
	}

}
