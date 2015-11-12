package com.db;

/**
 * @author saraths
 *
 */
public interface DBSystem {

	 /**
	  * 
	  * Sets the DB request object
	 * @param dbRequest Db request object
	 */
	public void setDbRequestObject(DBRequest dbRequest) ;
	 
	 /**
	  * retrieves the records from the table
	 * @return DBResponse Object
	 */
	public DBResponse retrieveRecords();
}
