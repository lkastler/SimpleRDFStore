package de.unikoblenz.west.lkastler.rdfsimplestore.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.StorageException;

import de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine;
import de.unikoblenz.west.lkastler.rdfsimplestore.storage.DistributedStorage;
import de.unikoblenz.west.lkastler.rdfsimplestore.storage.QueryableStorage;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Triple;

/**
 * TODO add comments
 * @author lkastler
 *
 */
public class DistributedStorageImpl implements DistributedStorage {

	static Logger log = LogManager.getLogger();
	
	HashMap<Integer, QueryableStorage> stores; 
	Random rand = new Random(0);
	
	
	public DistributedStorageImpl(int numberOfStores) {
		stores = new HashMap<Integer, QueryableStorage> ();
		
		for(int i = 0; i < numberOfStores; i++) {
			stores.put(i, new TableStorage(i));
		}
	}
	
	public void add(Triple triple) throws StorageException {
		int store = rand.nextInt(stores.size());
						
		log.debug(triple + " stored in " + store);
		
		stores.get(store).add(triple);
	}

	public List<QueryEngine> getQueryEngines() {
		return new ArrayList<QueryEngine>(stores.values());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DistributedStorageImpl []";
	}
}
