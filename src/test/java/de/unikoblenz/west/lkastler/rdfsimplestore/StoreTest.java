package de.unikoblenz.west.lkastler.rdfsimplestore;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.ParsingException;

/**
 * Test for the storage part of the SimpleRDFStore.
 * @author lkastler
 *
 */
public class StoreTest {

	static Logger log = LogManager.getLogger(); 
	
	@Test
	public void testAdd() throws Throwable {
		log.debug("testing: adding");
	
		SimpleRDFStore store = new SimpleRDFStore(1);
		store.add("a b c");
	}
	
	@Test
	public void testAddDublicate() throws Throwable {
		log.debug("testing: adding dublicate");
	
		SimpleRDFStore store = new SimpleRDFStore(1);
		store.add("a b c", "a b c");
	}
	
	@Test(expected=ParsingException.class)
	public void testAddingMalformed() throws Throwable {
		log.debug("testing: malformed triple");
		
		try {
			SimpleRDFStore store = new SimpleRDFStore(1);
			store.add("a b");
			
		} catch(ParsingException e) {
			log.debug(e);
			throw e;
		}
	}
	
	
	@Test(expected=ParsingException.class)
	public void testAddingMalformed2() throws Throwable {
		log.debug("testing: malformed triple");
		
		try {
			SimpleRDFStore store = new SimpleRDFStore(1);
			store.add("a b");
			
		} catch(ParsingException e) {
			log.debug(e);
			throw e;
		}
	}
	@Test(expected=ParsingException.class)
	public void testAddingMalformed3() throws Throwable {
		log.debug("testing: malformed triples");
		
		try {
			SimpleRDFStore store = new SimpleRDFStore(1);
			store.add("a b ?x"," a b c");
			
		} catch(ParsingException e) {
			log.debug(e);
			throw e;
		}
	}
}
