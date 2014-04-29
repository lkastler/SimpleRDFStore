package de.unikoblenz.west.lkastler.rdfsimplestore;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleRDFStore {
	
	private Logger log =  LogManager.getLogger();
	
	RDFIndex spo = new RDFIndex();
	RDFIndex sop = new RDFIndex();
	RDFIndex pso = new RDFIndex();
	RDFIndex pos = new RDFIndex();
	RDFIndex osp = new RDFIndex();
	RDFIndex ops = new RDFIndex();
	
	public SimpleRDFStore() {}
	
	public void add(String subject, String predicate, String object) {
		synchronized (this) {
			spo.add(subject, predicate, object);
			sop.add(subject, object, predicate);
			pso.add(predicate, subject, object);
			pos.add(predicate, object, subject);
			osp.add(object, subject, predicate);
			ops.add(object, predicate, subject);
		}
	}
	
	public String query(String query) throws SparqlEvaluationException {
		log.debug("query=" + query);
		
		String[] triplePatterns = query.split("\\.");
				
		StringBuffer b = new StringBuffer();
		
		for(String r: triplePatterns) {
			log.debug(r);
			b.append(evaluateTriplePattern(r));
		}
		
		return b.toString();
	}
	
	private String evaluateBasicGraphPattern(String query) throws SparqlEvaluationException {
		throw new SparqlEvaluationException(query);
	}
	
	private String evaluateTriplePattern(String query) throws SparqlEvaluationException {
		String[] r = query.trim().split(" ");
		
		if(r.length == 3) {
						
			String s = r[0];
			String p = r[1];
			String o = r[2];
			
			log.debug("s=" + s +"; p=" + p + "; o=" + o);
			
			if(s.startsWith("?")) {
				if(p.startsWith("?")) {
					if(o.startsWith("?")) {
						// ? ? ?
						StringBuffer b = new StringBuffer();
						for(String _s : spo.keySet()) {
							for(String _p: spo.get(_s).keySet()) {
								b.append(tripleToString(_s, _p, spo.get(_s).get(_p)));
							}
						}
						return b.toString();
					}
					else {
						// ? ? o
						if(ops.containsKey(o)) {
							StringBuffer b = new StringBuffer();
							for(String _s: osp.get(o).keySet()) {
								b.append(tripleToString(_s, osp.get(o).get(_s), o));
							}
							return b.toString();
						}
					}
				}
				else {
					if(o.startsWith("?")) {
						// ? p ?
						if(pso.containsKey(p)) {
							StringBuffer b = new StringBuffer();
							for(String _s : pso.get(p).keySet()) {
								b.append(tripleToString(_s, p, pso.get(p).get(_s)));
							}
							return b.toString();
						}						
					}
					else {
						// ? p o
						if(pos.containsKey(p) && pos.get(p).containsKey(o)) {
							return tripleToString(pos.get(p).get(o), p, o);
						}
					}
				}
			}
			else {
				if(p.startsWith("?")) {
					if(o.startsWith("?")) {
						// s ? ?
						if(spo.containsKey(s)) {
							StringBuffer b = new StringBuffer();
							for(String _p : spo.get(s).keySet()) {
								b.append(tripleToString(s, _p, spo.get(s).get(_p)));
							}
							
							return b.toString();
						}
					}
					else {
						// s ? o
						if(spo.containsKey(s) && sop.get(s).containsKey(o)) {
							return tripleToString(s, sop.get(s).get(o), o);
						}
					}
				}
				else {
					if(o.startsWith("?")) {
						// s p ?
						if(spo.containsKey(s) && spo.get(s).containsKey(p)) {
							return tripleToString(s, p, spo.get(s).get(p));
						}
					}
					else {
						// s p o
						if(spo.containsKey(s) && spo.get(s).containsKey(p) && spo.get(s).get(p).equals(o)) {
							return tripleToString(s, p, o);
						}
						
					}
				}
			}
			return "";
		} else {
			throw new SparqlEvaluationException("triple pattern wrong size: " + Arrays.toString(r));
		}
	}
	
	
	@Override
	public String toString() {
		
		StringBuffer b = new StringBuffer("SimpleRDFStore[");
		
		for(String s : spo.keySet()) {
			for(String p: spo.get(s).keySet()) {
				b.append(tripleToString(s, p, spo.get(s).get(p)));
			}
		}
		
		return b.append("]").toString();
	}
	
	private String tripleToString(String subject, String predicate, String object) {
		return subject + " " + predicate + " " + object + ".";
	}
}
