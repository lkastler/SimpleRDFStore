package de.unikoblenz.west.lkastler.rdfsimplestore.structure;


/**
 * 
 * 
 * @author lkastler
 */
public class Term implements Token {
	
	private String name;
	
	public Term(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
}
