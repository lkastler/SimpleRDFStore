package de.unikoblenz.west.lkastler.rdfsimplestore.structure;

/**
 * resembles an RDF triple
 * 
 * @author lkastler
 *
 */
public class Triple {

	private Term subject = null;
	private Term predicate = null;
	private Term object = null;
	
	/**
	 * 
	 * @param subject
	 * @param predicate
	 * @param object
	 */
	public Triple(String subject, String predicate, String object) {
		super();
		this.subject = new Term(subject);
		this.predicate = new Term(predicate);
		this.object = new Term(object);
	}

	/**
	 * returns the subject of this RDF triple.
	 * @return the subject of this RDF triple.
	 */
	public Term getSubject() {
		return subject;
	}

	/**
	 * returns the predicate of this RDF triple.
	 * @return the predicate of this RDF triple.
	 */
	public Term getPredicate() {
		return predicate;
	}

	/**
	 * returns the object of this RDF triple.
	 * @return the object of this RDF triple.
	 */
	public Term getObject() {
		return object;
	}

	@Override
	public String toString() {
		return subject + " " + predicate + " " + object;
	}
	
	
}
