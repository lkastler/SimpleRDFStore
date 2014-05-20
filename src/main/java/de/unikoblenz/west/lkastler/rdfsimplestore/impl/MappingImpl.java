package de.unikoblenz.west.lkastler.rdfsimplestore.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.MergeException;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.BasicGraphPattern;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Query;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Term;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Variable;

public class MappingImpl extends HashMap<Variable, Term> implements Mapping {

	static Logger log = LogManager.getLogger();
	
	private static final long serialVersionUID = 1L;

	private HashSet<Query> matchings;
	
	/**
	 * constructor for several matching queries.
	 * @param matchingQueries - queries this mapping is matching.
	 */
	public MappingImpl(Set<Query> matchingQueries) {
		this();
		
		matchings.addAll(matchingQueries);
	}
	
	/**
	 * constructor for one matching query.
	 * @param matchingQuery - the matching query.
	 */
	public MappingImpl(Query matchingQuery) {
		this();
		
		// FIXME: too much details
		if(matchingQuery instanceof BasicGraphPattern) {
			matchings.addAll((BasicGraphPattern)matchingQuery);
		}
		else {
			matchings.add(matchingQuery);
		}
	}
	
	/**
	 * default constructor
	 */
	private MappingImpl() {
		super();
		matchings = new HashSet<Query>();
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping#isCompartible(de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping)
	 */
	public boolean isCompatible(Mapping other) {
		log.debug("? " + this + " compatible with " + other + " ?");
		
		if(this.size() == 0) {
			log.debug("true");
			return true;
		}
		
		if(other == null || other.size() == 0) {
			log.debug("true");
			return true;
		}
		
		for(Variable t : other.keySet()) {
			if(containsKey(t)) {
				if(!other.get(t).equals(get(t))) {
					log.debug("false");
					return false;
				}
			}
		}
		
		log.debug("true");
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping#merge(de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping)
	 */
	public Mapping join(Mapping other) throws MergeException {
		
		log.debug("joining: " + this + " with " + other);
		
		if(isCompatible(other)) {
			HashSet<Query> combinedMatchings = new HashSet<Query>(matchings);
			combinedMatchings.addAll(other.getMatchingQueries());
			Mapping map = new MappingImpl(combinedMatchings);
			
			map.putAll(this);
			map.putAll(other);
			
			log.debug("joined: " + map);
			
			return map;
		}
		
		throw new MergeException("not compatible: " + this + "; " + other);
	}
	

	public Set<Variable> getVariables() {
		log.debug("variables: " + keySet());
		return keySet();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.HashMap#clone()
	 */
	public Mapping clone() {
		return (Mapping)super.clone();
	}
	
	/* (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping#getMatchingQueries()
	 */
	public Set<Query> getMatchingQueries() {
		return matchings;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MappingImpl [matchings=" + matchings + ", values=" + super.toString() +"]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((matchings == null) ? 0 : matchings.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MappingImpl other = (MappingImpl) obj;
		if (matchings == null) {
			if (other.matchings != null)
				return false;
		} else if (!matchings.equals(other.matchings))
			return false;
		return true;
	}
}
