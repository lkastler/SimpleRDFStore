package de.unikoblenz.west.lkastler.rdfsimplestore;

/**
 * resembles an RDF triple
 * 
 * @author lkastler
 *
 */
public class Triple {

	private String subject = null;
	private String predicate = null;
	private String object = null;
	
	/**
	 * 
	 * @param subject
	 * @param predicate
	 * @param object
	 */
	public Triple(String subject, String predicate, String object) {
		super();
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}

	/**
	 * returns the subject of this RDF triple.
	 * @return the subject of this RDF triple.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * returns the predicate of this RDF triple.
	 * @return the predicate of this RDF triple.
	 */
	public String getPredicate() {
		return predicate;
	}

	/**
	 * returns the object of this RDF triple.
	 * @return the object of this RDF triple.
	 */
	public String getObject() {
		return object;
	}

	@Override
	public String toString() {
		return subject + " " + predicate + " " + object;
	}
	
	
}
