package de.unikoblenz.west.lkastler.rdfsimplestore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * tests if SimpleRDFStore can handle basic graph pattern queries. 
 * @author lkastler
 *
 */
public class BasicGraphPatternQueryTest {

	static Logger log = LogManager.getLogger();
	
	SimpleRDFStore store; 
	
	@Before
	public void setUp() throws Throwable {
		store = new SimpleRDFStore();
		store.add("a b c", "a d e");
	}
	
	@Test
	public void test1() throws Throwable {
		store.query("?x b c. ?x d e");
	}
	
	@Test
	public void test2() throws Throwable {
		store.query("?x ?y c. ?x ?b e");
	}
}
