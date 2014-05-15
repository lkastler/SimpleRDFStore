package de.unikoblenz.west.lkastler.rdfsimplestore.impl;

import java.util.HashMap;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.MergeException;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Term;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Variable;

public class MappingImpl extends HashMap<Variable, Term> implements Mapping {

	static Logger log = LogManager.getLogger();
	
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping#isCompartible(de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping)
	 */
	public boolean isCompatible(Mapping other) {
		if(other == null) {
			return true;
		}
		for(Variable t : other.keySet()) {
			if(containsKey(t)) {
				if(!other.get(t).equals(get(t))) {
					return false;
				}
			}
		}
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping#merge(de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping)
	 */
	public Mapping join(Mapping other) throws MergeException {
		
		if(isCompatible(other)) {
			Mapping map = new MappingImpl();
			
			map.putAll(this);
			map.putAll(other);
			
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
	
	/*
	 * (non-Javadoc)
	 * @see java.util.AbstractMap#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {

		if(other != null && other instanceof Mapping) {
			Mapping o = (Mapping)other;
			if(o.size() == size()) {
				return isCompatible(o);
			}
		}
		
		return false;
	}
}
