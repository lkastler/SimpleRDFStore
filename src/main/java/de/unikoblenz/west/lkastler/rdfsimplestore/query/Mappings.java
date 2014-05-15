package de.unikoblenz.west.lkastler.rdfsimplestore.query;

import java.util.List;

/**
 * container for the Mapping.
 */
public interface Mappings extends List<Mapping> {
	
	/**
	 * joins all Mapping objects in this Mappings with all Mapping objects in the other Mappings, if they are compatible.
	 * @param other - other Mappings to join.
	 * @return all join Mapping objects that are compatible
	 */
	public Mappings join(Mappings other);
}
