package de.unikoblenz.west.lkastler.rdfsimplestore.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.ParsingException;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.BasicGraphPattern;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Query;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.TriplePattern;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Term;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Token;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Triple;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Variable;

/**
 * supplies different parsing methods for strings to data structures.
 * 
 * @author lkastler
 */
public abstract class Parser {
	
	/**
	 * allowed Term pattern
	 * TODO: currently only simple terms allowed!
	 */
	private static String TERM_PATTERN = "[a-zA-Z]+";
		
	/**
	 * allowed Triple pattern
	 */
	private static String TRIPLE_PATTERN = "^\\s*(" + TERM_PATTERN + "\\s*){3}$";
	
	/**
	 * parses a String representation of a BGP to a Query object.
	 * @param query - String representation of a BGP.
	 * @return a Query object corresponding to the given String representation of a BGP.
	 * @throws ParsingException - malformed query string.
	 */
	public static Query parseQuery(String query) throws ParsingException {
		String[] triplePatterns = query.trim().split("\\.");
		
		BasicGraphPattern bgp = new BasicGraphPattern();
		
		for(String t : triplePatterns) {
			String[] tokens = t.trim().split(" ");
			
			if(tokens.length != 3) {
				throw new ParsingException("not able to parse triple pattern: " + t);
			}
			
			Token s = tokens[0].startsWith("?") ? new Variable(tokens[0]) : new Term(tokens[0]);
			Token p = tokens[1].startsWith("?") ? new Variable(tokens[1]) : new Term(tokens[1]);
			Token o = tokens[2].startsWith("?") ? new Variable(tokens[2]) : new Term(tokens[2]);
			
			bgp.add(new TriplePattern(s, p, o));
		}
		return bgp;
	}
	
	/**
	 * parses a String representation of a triple and creates a Triple object.
	 * The representation has the following syntax: <code>"&lt;subject&gt; "&lt;predicate&gt; "&lt;object&gt;"</code>
	 * 
	 * @param triple - String representation of a Triple.
	 * @return the corresponding Triple to the given String representation.
	 * @throws ParsingException - thrown String representation could not be parsed.
	 */
	public static Triple parseTriple(String triple) throws ParsingException {
		Pattern triplePattern = Pattern.compile(TRIPLE_PATTERN, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		
		Matcher match = triplePattern.matcher(triple);
		
		if(!match.matches()) {
			throw new ParsingException("mal-formed triple: " + triple);
		}
		
		String[] tokens = triple.trim().split(" ");
		
		return (new Triple(tokens[0].trim(), tokens[1].trim(), tokens[2].trim()));
	}
}
