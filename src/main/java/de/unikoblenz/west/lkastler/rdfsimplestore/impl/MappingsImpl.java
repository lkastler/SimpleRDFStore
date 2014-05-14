package de.unikoblenz.west.lkastler.rdfsimplestore.impl;

import java.util.ArrayList;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.MergeException;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings;

/**
 * container class for the Mapping class.
 * 
 * @author lkastler
 */
public class MappingsImpl extends ArrayList<Mapping> implements Mappings {

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
				} catch (MergeException e) {}
			}
		}
		
		return map;
	}
}
