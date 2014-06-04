package de.unikoblenz.west.lkastler.rdfsimplestore;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.ParsingException;
import de.unikoblenz.west.lkastler.rdfsimplestore.impl.MappingImpl;
import de.unikoblenz.west.lkastler.rdfsimplestore.impl.MappingsImpl;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Query;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Term;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Variable;

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
		store = new SimpleRDFStore(2);
		store.add("a b c", "a d e", "e f g");
	}
	
	@Test
	public void test1() throws Throwable {
		log.info("Test 1");
		
		Query q = SimpleRDFStore.parse("?x b c. ?x d e");
		
		Mappings map = new MappingsImpl();
		Mapping m = new MappingImpl(q);
		m.put(new Variable("?x"), new Term("a"));
		map.add(m);
		
		assertEquals(map, store.query(q));
	}
	
	@Test
	public void test2() throws Throwable {
		log.info("Test 2");
		
		Query q = SimpleRDFStore.parse("?x ?y c. ?x ?z e");
		
		Mappings map = new MappingsImpl();
		Mapping m = new MappingImpl(q);
		m.put(new Variable("?x"), new Term("a"));
		m.put(new Variable("?y"), new Term("b"));
		m.put(new Variable("?z"), new Term("d"));
		map.add(m);
		
		assertEquals(map, store.query(q));
	}
	
	@Test
	public void test3() throws Throwable {
		log.info("Test 3");
		
		Mappings map = new MappingsImpl();
		
		assertEquals(map, store.query("?x ?y d. ?x ?b e"));
	}
	
	@Test
	public void test4() throws Throwable {
		log.info("Test 4");
		
		Query q = SimpleRDFStore.parse("?x b ?y. ?x d ?z. ?z f ?p");
		
		Mappings map = new MappingsImpl();
		Mapping m = new MappingImpl(q);
		m.put(new Variable("?x"), new Term("a"));
		m.put(new Variable("?y"), new Term("c"));
		m.put(new Variable("?z"), new Term("e"));
		m.put(new Variable("?p"), new Term("g"));
		map.add(m);
				
		assertEquals(map, store.query(q));
	}
	
	@Test
	public void test5() throws Throwable {
		log.info("Test 5");
		
		Mappings map = new MappingsImpl();
				
		assertEquals(map, store.query("?x b ?y. ?y d ?z. ?z f ?p"));
	}
	
	@Test(expected=ParsingException.class)
	public void test6() throws Throwable {
		log.info("Test 6");
		
		store.query("?x.");
	}
}
