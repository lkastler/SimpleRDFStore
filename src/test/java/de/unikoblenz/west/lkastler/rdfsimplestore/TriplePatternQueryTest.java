package de.unikoblenz.west.lkastler.rdfsimplestore;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import de.unikoblenz.west.lkastler.rdfsimplestore.impl.MappingImpl;
import de.unikoblenz.west.lkastler.rdfsimplestore.impl.MappingsImpl;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mapping;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.Mappings;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Term;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Variable;

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
		store.add("a b c", "a b d");
		log.debug(store.toString());
	}
	
	@Test
	public void testPattern1() throws Throwable {
		
		Mappings map = new MappingsImpl();
		Mapping m = new MappingImpl();
		m.put(new Variable("?x"), new Term("a"));
		m.put(new Variable("?y"), new Term("b"));
		m.put(new Variable("?z"), new Term("c"));
		map.add(m);
		
		m = new MappingImpl();
		m.put(new Variable("?x"), new Term("a"));
		m.put(new Variable("?y"), new Term("b"));
		m.put(new Variable("?z"), new Term("d"));
		map.add(m);
		
		assertEquals(map, store.query("?x ?y ?z."));
	}
	
	@Test
	public void testPattern10() throws Throwable {
	
		assertEquals(new MappingsImpl(), store.query("?x b t."));
	}
	
	@Test
	public void testPattern11() throws Throwable {
	
		assertEquals(new MappingsImpl(), store.query("t ?y c."));
	}
	
	@Test
	public void testPattern12() throws Throwable {
	
		assertEquals(new MappingsImpl(), store.query("a b t."));
	}
	
	@Test
	public void testPattern13() throws Throwable {
	
		assertEquals(new MappingsImpl(), store.query("?x t c."));
	}
	
	@Test
	public void testPattern14() throws Throwable {
	
		assertEquals(new MappingsImpl(), store.query("a ?y t."));
	}
	
	@Test
	public void testPattern15() throws Throwable {
	
		assertEquals(new MappingsImpl(), store.query("t b c."));
	}
	
	@Test
	public void testPattern16() throws Throwable {
		assertEquals(new MappingsImpl(), store.query("?x ?x ?x."));
	}
	
	@Test
	public void testPattern17() throws Throwable {
				
		assertEquals(new MappingsImpl(), store.query("?x t c."));
	}
	
	@Test
	public void testPattern18() throws Throwable {
	
		assertEquals(new MappingsImpl(), store.query("a t c."));
	}
		
	@Test
	public void testPattern19() throws Throwable {
		assertEquals(new MappingsImpl(), store.query("?x ?x t."));
	}
	
	@Test
	public void testPattern2() throws Throwable {
		
		Mappings map = new MappingsImpl();
		Mapping m = new MappingImpl();
		m.put(new Variable("?y"), new Term("b"));
		m.put(new Variable("?z"), new Term("c"));
		map.add(m);
		
		m = new MappingImpl();
		m.put(new Variable("?y"), new Term("b"));
		m.put(new Variable("?z"), new Term("d"));
		map.add(m);
		
		assertEquals(map, store.query("a ?y ?z."));
	}
	
	@Test
	public void testPattern20() throws Throwable {
		assertEquals(new MappingsImpl(), store.query("a ?x ?x."));
	}
	
	@Test
	public void testPattern21() throws Throwable {
		assertEquals(new MappingsImpl(), store.query("?x b ?x."));
	}
	
	@Test
	public void testPattern22() throws Throwable {
		assertEquals(new MappingsImpl(), store.query("?x ?x c."));
	}
	
	@Test
	public void testPattern23() throws Throwable {
		assertEquals(new MappingsImpl(), store.query("t ?x ?x."));
	}
	
	@Test
	public void testPattern24() throws Throwable {
		assertEquals(new MappingsImpl(), store.query("?x t ?x."));
	}
	
	@Test
	public void testPattern3() throws Throwable {

		Mappings map = new MappingsImpl();
		Mapping m = new MappingImpl();
		m.put(new Variable("?x"), new Term("a"));
		m.put(new Variable("?z"), new Term("c"));
		map.add(m);
		
		m = new MappingImpl();
		m.put(new Variable("?x"), new Term("a"));
		m.put(new Variable("?z"), new Term("d"));
		map.add(m);
		
		assertEquals(map, store.query("?x b ?z."));
	}
	
	@Test
	public void testPattern4() throws Throwable {
		
		Mappings map = new MappingsImpl();
		Mapping m = new MappingImpl();
		m.put(new Variable("?x"), new Term("a"));
		m.put(new Variable("?y"), new Term("b"));
		map.add(m);
		
		assertEquals(map, store.query("?x ?y c."));
	}	
	
	@Test
	public void testPattern5() throws Throwable {
		
		Mappings map = new MappingsImpl();
		Mapping m = new MappingImpl();
		m.put(new Variable("?z"), new Term("c"));
		map.add(m);
		
		m = new MappingImpl();
		m.put(new Variable("?z"), new Term("d"));
		map.add(m);
		
		assertEquals(map, store.query("a b ?z."));
	}	
	
	@Test
	public void testPattern6() throws Throwable {
	
		Mappings map = new MappingsImpl();
		Mapping m = new MappingImpl();
		m.put(new Variable("?x"), new Term("a"));
		map.add(m);
		
		assertEquals(map, store.query("?x b c."));
		
	}	
	
	@Test
	public void testPattern7() throws Throwable {
		
		Mappings map = new MappingsImpl();
		Mapping m = new MappingImpl();
		m.put(new Variable("?y"), new Term("b"));
		map.add(m);
		
		assertEquals(map, store.query("a ?y c."));
	}
	
	@Test
	public void testPattern8() throws Throwable {

		// how should be this be handled?
		
		Mappings map = new MappingsImpl();
		map.add(new MappingImpl());
		
		assertEquals(map, store.query("a b c."));
	}	
	
	@Test
	public void testPattern9() throws Throwable {
				
		assertEquals(new MappingsImpl(), store.query("a t ?z."));
	}
	
	
	
}
