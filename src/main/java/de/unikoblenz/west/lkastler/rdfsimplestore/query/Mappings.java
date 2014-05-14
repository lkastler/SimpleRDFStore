package de.unikoblenz.west.lkastler.rdfsimplestore.query;

import java.util.ArrayList;
import java.util.List;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.MergeException;

/**
 * container class for the Mapping class.
 * 
 * @author lkastler
 */
public class Mappings extends ArrayList<Mapping> {

	private static final long serialVersionUID = 1L;

	/**
	 * joins this Mappings with the other.
	 * @param other - the other Mappings to join.
	 * @return a joined Mapping.
	 */
	public Mappings join(List<Mapping> other) {
		
		Mappings map = new Mappings();
		
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
