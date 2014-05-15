package de.unikoblenz.west.lkastler.rdfsimplestore;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.EvaluationException;
import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.ParsingException;
import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.StorageException;
import de.unikoblenz.west.lkastler.rdfsimplestore.impl.DistributedStorageImpl;
import de.unikoblenz.west.lkastler.rdfsimplestore.impl.SimpleQueryEngine;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.BasicGraphPattern;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.TriplePattern;
import de.unikoblenz.west.lkastler.rdfsimplestore.storage.Storage;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Term;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Token;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Triple;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Variable;

/**
 * stores RDF and query it via Basic Graph Patterns. 
 * 
 * @author lkastler
 */
public class SimpleRDFStore {
	
	private Logger log =  LogManager.getLogger();
	
	private Storage store;
	private QueryEngine engine;
	
	public SimpleRDFStore() {
		store = new DistributedStorageImpl();
				
		engine = new SimpleQueryEngine(store.getQueryEngines());
	}
	
	/**
	 * adds String represented RDF triples to this SimpleRDFStore.
	 * 
	 * @param triples - Strings of RDF triples.
	 * @throws StorageException - thrown if Triple could not be stored.
	 * @throws ParsingException - thrown if String represented RDF triples could not be parsed.
	 */
	public void add(String... triples) throws StorageException, ParsingException {
		log.info("storing: " + Arrays.toString(triples));
		for(String t : triples) {
			String[] tokens = t.trim().split(" ");
			
			if(tokens.length == 3) {
				store.add(new Triple(tokens[0].trim(), tokens[1].trim(), tokens[2].trim()));
			}
			else {
				throw new ParsingException("triple could not be parsed: " + t);
			}
		}
	}
	
	/**
	 * queries this SimpleRDFStore and recieves a set of solutions.
	 * 
	 * @param query - String representation of an basic graph pattern.
	 * @return Solutions for the given query String.
	 * @throws EvaluationException - notifying if evaluation went wrong.
	 * @throws ParsingException - notifying if parsing went wrong.
	 */
	public Mappings query(String query) throws EvaluationException, ParsingException {
		log.info("evaluating: " + query);
		
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
		
		return engine.query(bgp);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleRDFStore [store=" + store + ", engine=" + engine + "]";
	}
}
