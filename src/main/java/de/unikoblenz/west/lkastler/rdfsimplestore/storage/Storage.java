package de.unikoblenz.west.lkastler.rdfsimplestore.storage;

import de.unikoblenz.west.lkastler.rdfsimplestore.exceptions.StorageException;
import de.unikoblenz.west.lkastler.rdfsimplestore.query.QueryEngine;
import de.unikoblenz.west.lkastler.rdfsimplestore.structure.Triple;

/**
 * storage abstraction.
 * @author lkastler
 *
 */
public interface Storage extends QueryEngine {

	/**
	 * adds given Triple to the Storage.
	 * @param triple - Triple to add.
	 * @throws StorageException - thrown if triple could not be stored. 
	 */
	void add(Triple triple) throws StorageException;

}
