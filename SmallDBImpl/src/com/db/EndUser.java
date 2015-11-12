package com.db;

import java.io.FileNotFoundException;
import java.util.Scanner;

import com.db.employee.DBEmployeeSystem;

/**
 * @author saraths
 *
 */
public class EndUser {

	
	/**
	 * @param args we
	 */
	public static void main(final String args[]){
		Scanner inputReader = new Scanner(System.in);
		String queryInput;
		String option = "n";
		DBEmployeeSystem dbSystem = null;
		do{
		try{
			System.out.println("Enter the Absolute File path of DB: ");
			String filePath = inputReader.nextLine();
			dbSystem = new DBEmployeeSystem(filePath);
		}catch(FileNotFoundException f){
			System.out.println("File not found for db. Kindly rename the file/ place the file in right location");
			System.out.println("Do you want to continue ?:(y/n) ");
			option = inputReader.nextLine();
		}
		}while(option.toLowerCase().equals("y"));
		
		do{
			System.out.println("Enter the select query you want to execute on DB...");
			queryInput = inputReader.nextLine();
			System.out.println("Response:");
			executeQuery(queryInput, dbSystem);
			System.out.println("Do you want to continue ?:(y/n) ");
			option = inputReader.nextLine();			
		}while(option.toLowerCase().equals("y"));
		System.out.println("Bye!!!");
		inputReader.close();
	}
	
	private static void executeQuery(String queryInput, DBEmployeeSystem dbSystem){
		DBRequest req = new DBRequest(queryInput);
		if(req.isValidRequestFormat()){
			
			
			dbSystem.setDbRequestObject(req);
			
			DBResponse response = dbSystem.retrieveRecords();
			String[] records = response.getResponseRecords();
			if(records.length == 0)
				System.out.println("No records to retrieve");
			else
			{
				for(int i=0; i<records.length;i++){
					System.out.println(records[i]);
				}
			}
			
		}	
		
		else
		{
			System.out.println("It is an invalid query");
		}
	}
}
