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
		store.add("a", "b", "c");
		
		log.debug(store.toString());
		
		log.debug(store.query("? ? ?."));
		
		log.debug(store.query("a ? ?."));
		
		log.debug(store.query("? b ?."));
		
		log.debug(store.query("? ? c."));
		
		log.debug(store.query("a b ?."));
		
		log.debug(store.query("? b c."));
		
		log.debug(store.query("a ? c."));
		
		log.debug(store.query("a b c."));
		
		log.debug(store.query("a x ?."));
		
		log.debug(store.query("? b x."));
		
		log.debug(store.query("x ? c."));
		
		log.debug(store.query("a b x."));
		
		log.debug(store.query("? x c."));
		
		log.debug(store.query("a ? x."));
		
		log.debug(store.query("x b c."));
		
		log.debug("End");
	}
}
