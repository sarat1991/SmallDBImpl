package com.db.employee;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import com.db.DBRequest;
import com.db.DBResponse;
import com.db.DBSystem;

/**
 * @author saraths
 *
 */
public class DBEmployeeSystem implements DBSystem{
	
	private DBRequest dbRequestObject;
	
	private StorageEmployee storageForAccess;
	private Comparator<Employee> nameCompare = new Comparator<Employee>() {

		@Override
		public int compare(Employee o1, Employee o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
	
	private Comparator<Employee> idCompare = new Comparator<Employee>(){

		@Override
		public int compare(Employee o1, Employee o2) {
				return o1.getID()>o2.getID()?1:o1.getID()<o2.getID()?-1:0;
		}
		
	};
	
	private Comparator<Employee> salarayCompare = new Comparator<Employee>(){

		@Override
		public int compare(Employee o1, Employee o2) {
				return o1.getSalary()>o2.getSalary()?1:o1.getSalary()<o2.getSalary()?-1:0;
		}
		
	};
	
	
	 public DBEmployeeSystem(String FilePath) throws FileNotFoundException {
		//this.dbRequestObject = dbRequestObject;
		readAndStorinDB(FilePath);
	}
	 
	 public void setDbRequestObject(DBRequest dbRequestObject) {
			this.dbRequestObject = dbRequestObject;
		}
	 
	public DBResponse retrieveRecords(){
		
		ArrayList<Employee> temporaryResult = null;
		String[] reponseStrings;
		
		
		if(!dbRequestObject.getQuery().toLowerCase().matches("(.*)where(.*)")){
			temporaryResult = storageForAccess.getAllRecords();

		}
		else {
		
		
		String conditionalOperator = dbRequestObject.getConditionOperator();
		
		
		if(conditionalOperator!= null)
		{
			switch(conditionalOperator)
				{
				
					case "<":{
							 temporaryResult=storageForAccess.lessThan(dbRequestObject.getConditionColumn(),dbRequestObject.getConditionValue());
							 break;
					}
					case "<=":{
							 temporaryResult=storageForAccess.lessThanOrEqualTo(dbRequestObject.getConditionColumn(),dbRequestObject.getConditionValue());
								break;
					}
					case ">":{
							temporaryResult=storageForAccess.greaterThan(dbRequestObject.getConditionColumn(),dbRequestObject.getConditionValue());
							break;
					}
					case ">=":{
							temporaryResult=storageForAccess.greaterOrEqualTo(dbRequestObject.getConditionColumn(),dbRequestObject.getConditionValue());
							break;
					}
					case "=":{
							temporaryResult=storageForAccess.equalTo(dbRequestObject.getConditionColumn(),dbRequestObject.getConditionValue());
					        break;
					}
					case "!=":{
							temporaryResult=storageForAccess.notEqualTo(dbRequestObject.getConditionColumn(),dbRequestObject.getConditionValue());
							break;
					}
				}
			}
		
		
		
		}
	
		if(dbRequestObject.getOrderBy()!=null)
			 orderByQuery(temporaryResult, dbRequestObject.getOrderBy());
		
		if(!dbRequestObject.getOutputColumns()[0].equals("*")){
			reponseStrings = filterColumnsAndGenerateRecords(temporaryResult,dbRequestObject.getOutputColumns());
		}
		else
			reponseStrings = generateRecords(temporaryResult);
		
		
			
		
		return new DBResponse(reponseStrings);
		
	}
	
	private void orderByQuery(ArrayList<Employee> tempResult,String column){
		
			    
		if(column.toLowerCase().equals("name")){
	    	 
	    	 Collections.sort(tempResult,nameCompare);
	    	 
	     }
	     else if(column.toLowerCase().equals("id")){
	    	
	    	 Collections.sort(tempResult,idCompare);
	     }
	     else if(column.toLowerCase().equals("salary")){
	    	 
	    	 Collections.sort(tempResult,salarayCompare);
	     }
	     else {
	    	  tempResult.clear();
	     }
		
	}
	
	private String[] filterColumnsAndGenerateRecords(ArrayList<Employee> tempResult,String[] columns){
		
		HashSet<String> outputColumns = new HashSet<String>();
		String [] response = new String[tempResult.size()];
		for(String col: columns)
			outputColumns.add(col.toLowerCase());
		
		for(int i =0;i<response.length;i++){
			Employee emp = tempResult.get(i);
			response[i] ="";
		  if(outputColumns.contains("id"))
			  response[i] = "ID "+emp.getID();
		  if(outputColumns.contains("name"))
			  response[i] = response[i]+" Name "+emp.getName();
		  if(outputColumns.contains("salary"))
			  response[i] = response[i]+" Salary "+emp.getSalary();
		  if(response[i].equals(""))
		  {
			  response = new String[0];
			  break;
		  }
		}
		  
			return response;
		
	}
	
	private String[] generateRecords(ArrayList<Employee> tempResult){
		String [] response = new String[tempResult.size()];
		for(int i =0;i<response.length;i++){
			Employee emp = tempResult.get(i);
		  	  response[i] = "ID "+emp.getID()+" Name "+emp.getName()+" Salary "+emp.getSalary();
		}
		return response;
	}
	
	
	private void readAndStorinDB(String FilePath) throws FileNotFoundException{
		storageForAccess = new StorageEmployee();
		storageForAccess.retrieveDataFromFile(FilePath);	
	}
}
