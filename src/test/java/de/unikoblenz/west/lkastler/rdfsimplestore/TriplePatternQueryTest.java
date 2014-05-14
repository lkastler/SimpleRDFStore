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
		log.debug(store.query("? ? ?."));
	}
	@Test
	public void testPattern2() throws Throwable {
		
		log.debug(store.query("a ? ?."));
	}
	
	@Test
	public void testPattern3() throws Throwable {

		log.debug(store.query("? b ?."));
	}
	
	@Test
	public void testPattern4() throws Throwable {
	
		log.debug(store.query("? ? c."));
	}
	
	@Test
	public void testPattern5() throws Throwable {
	
		log.debug(store.query("a b ?."));
	}
	
	@Test
	public void testPattern6() throws Throwable {
	
		log.debug(store.query("? b c."));
	}
	
	@Test
	public void testPattern7() throws Throwable {
	
		log.debug(store.query("a ? c."));
	}
	
	@Test
	public void testPattern8() throws Throwable {
	
		log.debug(store.query("a b c."));
	}
	
	@Test
	public void testPattern9() throws Throwable {
	
		log.debug(store.query("a x ?."));
	}
	
	@Test
	public void testPattern10() throws Throwable {
	
		log.debug(store.query("? b x."));
	}
	
	@Test
	public void testPattern11() throws Throwable {
	
		log.debug(store.query("x ? c."));
	}
	
	@Test
	public void testPattern12() throws Throwable {
	
		log.debug(store.query("a b x."));
	}	
	
	@Test
	public void testPattern13() throws Throwable {
	
		log.debug(store.query("? x c."));
	}
	
	@Test
	public void testPattern14() throws Throwable {
	
		log.debug(store.query("a ? x."));
	}
	
	@Test
	public void testPattern15() throws Throwable {
	
		log.debug(store.query("x b c."));
	}	
}
