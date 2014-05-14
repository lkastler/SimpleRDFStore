package de.unikoblenz.west.lkastler.rdfsimplestore.structure;

/**
 * 
 * 
 * @author lkastler
 */
public class Variable implements Token {

	private String name;

	/**
	 * create
	 * @param name
	 */
	public Variable(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
