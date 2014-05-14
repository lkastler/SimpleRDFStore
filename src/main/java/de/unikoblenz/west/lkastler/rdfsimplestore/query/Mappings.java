package de.unikoblenz.west.lkastler.rdfsimplestore.query;

import java.util.List;

/**
 * container for the Mapping.
 */
public interface Mappings extends List<Mapping> {
	
	public Mappings join(Mappings other);
}
