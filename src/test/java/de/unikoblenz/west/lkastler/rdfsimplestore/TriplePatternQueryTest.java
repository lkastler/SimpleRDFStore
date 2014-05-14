package de.unikoblenz.west.lkastler.rdfsimplestore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the triple pattern query interface.
 * 
 * @author lkastler
 */
public class TriplePatternQueryTest {

	static Logger log = LogManager.getLogger();
	
	private SimpleRDFStore store;
	
	@Before
	public void setUp() throws Throwable {
		store = new SimpleRDFStore();
		store.add("a b c");
		log.debug(store.toString());
	}
	
	@Test
	public void testPattern1() throws Throwable {
		log.debug(store.query("?x ?y ?z."));
	}
	
	@Test
	public void testPattern2() throws Throwable {
		
		log.debug(store.query("a ?y ?z."));
	}
	
	@Test
	public void testPattern3() throws Throwable {

		log.debug(store.query("?x b ?z."));
	}
	
	@Test
	public void testPattern4() throws Throwable {
	
		log.debug(store.query("?x ?y c."));
	}
	
	@Test
	public void testPattern5() throws Throwable {
	
		log.debug(store.query("a b ?z."));
	}
	
	@Test
	public void testPattern6() throws Throwable {
	
		log.debug(store.query("?x b c."));
	}
	
	@Test
	public void testPattern7() throws Throwable {
	
		log.debug(store.query("a ?y c."));
	}
	
	@Test
	public void testPattern8() throws Throwable {
	
		log.debug(store.query("a b c."));
	}
	
	@Test
	public void testPattern9() throws Throwable {
	
		log.debug(store.query("a t ?z."));
	}
	
	@Test
	public void testPattern10() throws Throwable {
	
		log.debug(store.query("?x b t."));
	}
	
	@Test
	public void testPattern11() throws Throwable {
	
		log.debug(store.query("t ?y c."));
	}
	
	@Test
	public void testPattern12() throws Throwable {
	
		log.debug(store.query("a b t."));
	}	
	
	@Test
	public void testPattern13() throws Throwable {
	
		log.debug(store.query("?x t c."));
	}
	
	@Test
	public void testPattern14() throws Throwable {
	
		log.debug(store.query("a ?y t."));
	}
	
	@Test
	public void testPattern15() throws Throwable {
	
		log.debug(store.query("t b c."));
	}	
	
	@Test
	public void testPattern16() throws Throwable {
		log.debug(store.query("?x ?x ?x."));
	}
	
}
