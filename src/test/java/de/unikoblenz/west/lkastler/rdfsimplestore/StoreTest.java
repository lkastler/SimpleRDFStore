package de.unikoblenz.west.lkastler.rdfsimplestore;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class StoreTest {

	static Logger log = LogManager.getLogger(); 
	
	@Test
	public void testAdd() throws Throwable {
		log.debug("Start");
	
		SimpleRDFStore store = new SimpleRDFStore();
		store.add("a b c");
	}
}
