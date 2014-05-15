package de.unikoblenz.west.lkastler.rdfsimplestore.impl;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.MergeException;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings;

/**
 * container class for the Mapping class.
 * 
 * @author lkastler
 */
public class MappingsImpl extends ArrayList<Mapping> implements Mappings {

	static Logger log = LogManager.getLogger();
	
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * @see de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings#join(de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings)
	 */
	public Mappings join(Mappings other) {
		MappingsImpl map = new MappingsImpl();
		
		for(Mapping here : this) {
			for(Mapping there : other) {
				try {
					map.add(here.join(there));
				} catch (MergeException e) {
					log.debug(e);
				}
			}
		}
		
		return map;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.AbstractList#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if(other != null && other instanceof Mappings) {
			Mappings o = (Mappings)other;
			if(o.size() == size()) {
				for(Mapping m : o) {
					if(!contains(m)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
}
