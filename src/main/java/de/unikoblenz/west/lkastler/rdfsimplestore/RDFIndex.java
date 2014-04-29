package de.unikoblenz.west.lkastler.rdfsimplestore;

import java.util.HashMap;

public class RDFIndex extends HashMap<String, HashMap<String, String>> {

	private static final long serialVersionUID = 1L;

	
	public void add(String a, String b, String c) {
		if(get(a) == null) {
			put(a, new HashMap<String, String>());
		}
		
		get(a).put(b, c);
	}
}
