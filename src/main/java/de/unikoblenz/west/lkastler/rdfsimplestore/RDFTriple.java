package de.unikoblenz.west.lkastler.rdfsimplestore;

public class RDFTriple {

	private String subject = null;
	private String predicate = null;
	private String object = null;
	
	public RDFTriple(String subject, String predicate, String object) {
		super();
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}

	public String getSubject() {
		return subject;
	}

	public String getPredicate() {
		return predicate;
	}

	public String getObject() {
		return object;
	}

	@Override
	public String toString() {
		return subject + " " + predicate + " " + object + ".";
	}
	
	
}
