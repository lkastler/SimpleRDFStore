package de.unikoblenz.west.lkastler.rdfsimplestore.query;

import java.util.Set;

/**
 * container for the Mapping.
 */
public interface Mappings extends Set<Mapping> {
	
	/**
	 * joins all Mapping objects in this Mappings with the given Mapping object, if they are compatible.
	 * @param other - a Mapping to join.
	 * @return all joined Mapping objects that are compatible
	 */
	public Mappings join(Mapping other);
}
