package com.db;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author saraths
 *
 */
public interface Storage {
	/**
	 * The function is used to retrieve data from the db File from file system.
	 * @param FilePath Path of the file
	 * @throws FileNotFoundException When file is not present
	 */
	public void retrieveDataFromFile(String FilePath) throws FileNotFoundException;
	/**
	 * 
	 * Primary Key/ Unique key should be use this method to store in B+ tree data structure
	 */
	public void storingPrimaryKey();
	/**
	 *  Used for Less than operation 
	 * @param column column name
	 * @param value column value
	 * @return ArrayList of records
	 */
	public ArrayList<?> lessThan(String column, String value);
	/**
	 * Used for Less than operation 
	 * @param column column name
	 * @param value column value
	 * @return ArrayList of records
	 */
	public ArrayList<?> lessThanOrEqualTo(String column, String value);
	/**
	 * Used for greater than operation 
	 * @param column column name
	 * @param value column value
	 * @return ArrayList of records
	 */
	public ArrayList<?> greaterThan(String column, String value);
	/**
	 * Used for greater than or equal operation 
	 * @param column column name
	 * @param value column value
	 * @return ArrayList of records
	 */
	public ArrayList<?> greaterOrEqualTo(String column, String value);
	/**
	 * Used for not Equal to operation 
	 * @param column column name
	 * @param value column value
	 * @return ArrayList of records
	 */
	public ArrayList<?> notEqualTo(String column, String value);
	/**
	 * Used for Equal To operation 
	 * @param column column name
	 * @param value column value
	 * @return ArrayList of records
	 */
	public ArrayList<?> equalTo(String column, String value);
	/**
	 * gets all the records present in the table
	 * @return ArrayList of records
	 */
	public ArrayList<?> getAllRecords();
	
}
