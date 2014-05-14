package de.unikoblenz.west.lkastler.rdfsimplestore.impl;

import java.util.HashMap;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.MergeException;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Term;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Token;

public class MappingImpl extends HashMap<Token, Term> implements Mapping {

	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping#isCompartible(de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping)
	 */
	public boolean isCompatible(Mapping other) {
		if(other == null) {
			return false;
		}
		for(Token t : other.keySet()) {
			if(containsKey(t)) {
				if(other.get(t) != get(t)) {
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
		
		throw new MergeException("not compatible");
	}
	
	
	

}
