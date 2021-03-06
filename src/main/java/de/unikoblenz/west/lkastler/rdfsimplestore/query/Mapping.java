package de.unikoblenz.west.lkastler.rdfsimplestore.query;

import java.util.Map;
import java.util.Set;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.MergeException;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Term;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Variable;

/**
 * returns a possible result for a given query.
 * 
 * @author lkastler
 */
public interface Mapping extends Map<Variable, Term> {
	
	/**
	 * tests if given Mapping is compatible to this Mapping.
	 * @param other - Mapping to compare compatibility.
	 * @return <code>true</code> if both mappings are compatible, otherwise <code>false</code>.
	 */
	public boolean isCompatible(Mapping other);
	
	/**
	 * returns a merged Mapping if this and the other mapping are compatible.
	 * @param other - Mapping to merge with this one.
	 * @return merged Mapping or this Mapping.
	 * @throws MergeException - thrown if merge is not possible.
	 */
	public Mapping join(Mapping other) throws MergeException;

	/**
	 * creates a clone of this Mapping.
	 * @return a clone of this Mapping.
	 */
	public Mapping clone();
	
	/**
	 * returns the set of Variable where this mapping is defined on.
	 * @return the set of Variable where this mapping is defined on.
	 */
	public Set<Variable> getVariables();
	
	/**
	 * returns a Set of Query that this Mapping matches.
	 * @return a Set of Query that this Mapping matches.
	 */
	public Set<Query> getMatchingQueries();
}
