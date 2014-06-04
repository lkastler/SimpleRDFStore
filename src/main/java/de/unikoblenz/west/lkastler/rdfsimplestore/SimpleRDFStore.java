package de.unikoblenz.west.lkastler.rdfsimplestore;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.EvaluationException;
import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.ParsingException;
import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.StorageException;
import de.unikoblenz.west.lkastler.rdfsimplestore.impl.DistributedStorageImpl;
import de.unikoblenz.west.lkastler.rdfsimplestore.impl.SimpleQueryEngine;
import de.unikoblenz.west.lkastler.rdfsimplestore.parser.Parser;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Query;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine;
import de.unikoblenz.west.lkastler.rdfsimplestore.storage.Storage;

/**
 * stores RDF and query it via Basic Graph Patterns. 
 * 
 * @author lkastler
 */
public class SimpleRDFStore {
	
	private Logger log =  LogManager.getLogger();
	
	private Storage store;
	private QueryEngine engine;
	

	/**
	 * constructor for a SimpleRDFStore that takes how many distributed stores should be generated. 
	 * @param numberOfStores - number of stores to generate
	 */
	public SimpleRDFStore(int numberOfStores) {
		store = new DistributedStorageImpl(numberOfStores);
				
		engine = new SimpleQueryEngine(store.getQueryEngines());
	}
	
	/**
	 * parses a String representation of a BGP to a Query object.
	 * @param query - String representation of a BGP.
	 * @return a Query object corresponding to the given String representation of a BGP.
	 * @throws ParsingException - malformed query string.
	 */
	public static Query parse(String query) throws ParsingException {
		return Parser.parseQuery(query);
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
			store.add(Parser.parseTriple(t));
		}
	}
	
	/**
	 * queries this SimpleRDFStore and receives a set of solutions.
	 * 
	 * @param query - String representation of an basic graph pattern.
	 * @return Solutions for the given query String.
	 * @throws EvaluationException - notifying if evaluation went wrong.
	 * @throws ParsingException - notifying if parsing went wrong.
	 */
	public Mappings query(String query) throws EvaluationException, ParsingException {
		
		return this.query(SimpleRDFStore.parse(query));
	}

	/**
	 * queries this SimpleRDFStore and receives a set of solutions.
	 * @param query - Query to query...
	 * @return Mappings that fulfills the query.
	 * @throws EvaluationException thrown if evaluation of given query was not possible.
	 * @throws ParsingException - thrown if given String representation of a query was not possible
	 */
	public Mappings query(Query query) throws EvaluationException, ParsingException {
		log.info("evaluating: " + query);
		
		return engine.query(query);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimpleRDFStore [store=" + store + ", engine=" + engine + "]";
	}
}
