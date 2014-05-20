package de.unikoblenz.west.lkastler.rdfsimplestore.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.EvaluationException;
import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.StorageException;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.BasicGraphPattern;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Query;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.TriplePattern;
import de.unikoblenz.west.lkastler.rdfsimplestore.storage.QueryableStorage;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Term;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Triple;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Variable;

/**
 * stores RDF in several tables. 
 * 
 * @author lkastler
 */
public class TableStorage implements QueryableStorage {
	
	private Logger log;
	private long id;
	
	private HashMap<Term, HashMap<Term, HashSet<Term>>> spo = new HashMap<Term, HashMap<Term, HashSet<Term>>>();
	private HashMap<Term, HashMap<Term, HashSet<Term>>> sop = new HashMap<Term, HashMap<Term, HashSet<Term>>>();
	private HashMap<Term, HashMap<Term, HashSet<Term>>> pso = new HashMap<Term, HashMap<Term, HashSet<Term>>>();
	private HashMap<Term, HashMap<Term, HashSet<Term>>> pos = new HashMap<Term, HashMap<Term, HashSet<Term>>>();
	private HashMap<Term, HashMap<Term, HashSet<Term>>> osp = new HashMap<Term, HashMap<Term, HashSet<Term>>>();
	private HashMap<Term, HashMap<Term, HashSet<Term>>> ops = new HashMap<Term, HashMap<Term, HashSet<Term>>>();
	
	public TableStorage(long id) {
		super();
		this.id = id;
		log = LogManager.getLogger(TableStorage.class.toString() + " " + Long.toString(id));
		
		log.debug("created");
	}
	
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
	private void add(HashMap<Term, HashMap<Term, HashSet<Term>>> table, Term key1, Term key2, Term value) {
		if(!table.containsKey(key1)) {
			table.put(key1, new HashMap<Term,HashSet<Term>>());
		}
		
		HashMap<Term, HashSet<Term>> row = table.get(key1);
		
		if(!row.containsKey(key2)) {
			row.put(key2, new HashSet<Term>());
		}
		
		row.get(key2).add(value);
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine#query(de.unikoblenz.west.lkastler.rdfsimplestore.query.Query)
	 */
	public Mappings query(Query query) throws EvaluationException {
		log.debug("query: " + query);
		
		if(query instanceof TriplePattern) {
			return query((TriplePattern)query);
		}
		
		if(query instanceof BasicGraphPattern) {
			BasicGraphPattern bgp = (BasicGraphPattern)query;
			
			Mappings map = new MappingsImpl();
			
			for(TriplePattern p : bgp) {
				Mappings m = query(p);
				
				map.addAll(map.join(m));
				
				map.addAll(m);
			}
						
			return map;
		}
	
		throw new EvaluationException("not supported");
	}
	
	//
	private Mappings query(TriplePattern q) throws EvaluationException {
		Mappings result = new MappingsImpl();
		
		if(q.getSubject() instanceof Variable) {
			if(q.getPredicate() instanceof Variable) {
				if(q.getObject() instanceof Variable) {
					// ? ? ?
					for(Term _s : spo.keySet()) {
						for(Term _p : spo.get(_s).keySet()) {
							Mapping map = new MappingImpl(q);
							
							map.put((Variable) q.getSubject(), _s);
							
							Term test = map.put((Variable) q.getPredicate(), _p);
							
							if(test != null && test !=  _p) {
								continue;
							}
							
							for(Term _o : spo.get(_s).get(_p)) {
								
								Mapping mapping = map.clone(); 
										
								mapping.put((Variable)q.getObject(), _o);
								
								result.add(mapping);
							}
						}
					}
				}
				else {
					// ? ? o
					if(ops.containsKey(q.getObject())) {
						for(Term _p : ops.get(q.getObject()).keySet()) {
							
							Mapping map = new MappingImpl(q);
							
							Term test = map.put((Variable) q.getPredicate(), _p);
							
							if(test != null && test !=  _p) {
								continue;
							}
							
							for(Term _s : ops.get(q.getObject()).get(_p)) {
								
								Mapping mapping = map.clone(); 
										
								Term test_s = mapping.put((Variable)q.getSubject(), _s);
								
								if(test_s != null && test_s !=  _s) {
									continue;
								}
								
								result.add(mapping);
							}
						}
					}
				}
			}
			else {
				
				if(q.getObject() instanceof Variable) {
					// ? p ?
					if(pso.containsKey(q.getPredicate())) {
						for(Term _s : pso.get(q.getPredicate()).keySet()) {
							Mapping map = new MappingImpl(q);
							
							Term test = map.put((Variable) q.getSubject(), _s);
							
							if(test != null && test !=  _s) {
								continue;
							}
							
							for(Term _o : pso.get(q.getPredicate()).get(_s)) {
								
								Mapping mapping = map.clone(); 
										
								Term test_o = mapping.put((Variable)q.getObject(), _o);
								
								if(test_o != null && test_o !=  _o) {
									continue;
								}
								
								result.add(mapping);
							}
						}
					}
				}				
				else {
					// ? p o
					if(pos.containsKey(q.getPredicate()) && pos.get(q.getPredicate()).containsKey(q.getObject())) {
						Mapping map = new MappingImpl(q);
						
						for(Term _s : pos.get(q.getPredicate()).get(q.getObject())) {
							
							Mapping mapping = map.clone(); 
									
							Term test_s = mapping.put((Variable)q.getSubject(), _s);
							
							if(test_s != null && test_s !=  _s) {
								continue;
							}
							
							result.add(mapping);
						}
					}
				}
			}
		}
		else {
			if(q.getPredicate() instanceof Variable) {
				if(q.getObject() instanceof Variable) {
					// s ? ?
					if(spo.containsKey(q.getSubject())) {
						for(Term _p : spo.get(q.getSubject()).keySet()) {
							Mapping map = new MappingImpl(q);
							
							Term test = map.put((Variable) q.getPredicate(), _p);
							
							if(test != null && test !=  _p) {
								continue;
							}
							
							for(Term _o : spo.get(q.getSubject()).get(_p)) {
								
								Mapping mapping = map.clone(); 
										
								Term test_o = mapping.put((Variable)q.getObject(), _o);
								
								if(test_o != null && test_o !=  _o) {
									continue;
								}
								
								result.add(mapping);
							}
					
						}
					}
				}
				else {
					// s ? o
					if(sop.containsKey(q.getSubject()) && sop.get(q.getSubject()).containsKey(q.getObject())) {
						Mapping map = new MappingImpl(q);
						
						for(Term _p : sop.get(q.getSubject()).get(q.getObject())) {
							
							Mapping mapping = map.clone(); 
									
							Term test_p = mapping.put((Variable)q.getPredicate(), _p);
							
									if(test_p != null && test_p !=  _p) {
								continue;
							}
							
							result.add(mapping);
						}
					}
				}
			}
			else {
				if(q.getObject() instanceof Variable) {
					// s p ?
					if(spo.containsKey(q.getSubject()) && spo.get(q.getSubject()).containsKey(q.getPredicate())) {
						Mapping map = new MappingImpl(q);
						
						for(Term _o : spo.get(q.getSubject()).get(q.getPredicate())) {
							
							Mapping mapping = map.clone(); 
									
							Term test_o = mapping.put((Variable)q.getObject(), _o);
							
							if(test_o != null && test_o !=  _o) {
								continue;
							}
							
							result.add(mapping);
						}
					}
					
				}
				else {
					// s p o
					if(spo.containsKey(q.getSubject()) 
							&& spo.get(q.getSubject()).containsKey(q.getPredicate()) 
							&& spo.get(q.getSubject()).get(q.getPredicate()).contains(q.getObject())) {
						result.add(new MappingImpl(q));
					}
				}
			}
		}
		
		log.debug(q + " solution: " + result);
		
		return result;
	}
	

	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TableStorage [" + spo + "]";
	}

	public List<QueryEngine> getQueryEngines() {
		ArrayList<QueryEngine> engines = new ArrayList<QueryEngine>();
		engines.add(this);
		return engines;
	}
}
