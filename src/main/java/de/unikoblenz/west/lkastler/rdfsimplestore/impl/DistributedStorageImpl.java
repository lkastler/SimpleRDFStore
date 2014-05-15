package de.unikoblenz.west.lkastler.rdfsimplestore.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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

	HashMap<Integer, QueryableStorage> stores; 
	Random rand = new Random(System.currentTimeMillis());
	
	
	public DistributedStorageImpl() {
		stores = new HashMap<Integer, QueryableStorage> ();
		stores.put(0, new TableStorage());
		stores.put(1, new TableStorage());
	}
	
	public void add(Triple triple) throws StorageException {
		stores.get(rand.nextInt(stores.size())).add(triple);
	}

	public List<QueryEngine> getQueryEngines() {
		return new ArrayList<QueryEngine>(stores.values());
	}
}
