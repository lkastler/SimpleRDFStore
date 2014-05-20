package de.unikoblenz.west.lkastler.rdfsimplestore.query;

import java.util.ArrayList;

/**
 * A basic graph pattern is a set of TriplePattern.
 * 
 * @author lkastler
 */
public class BasicGraphPattern extends ArrayList<TriplePattern> implements Query {

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BGP" + super.toString();
	}
}
