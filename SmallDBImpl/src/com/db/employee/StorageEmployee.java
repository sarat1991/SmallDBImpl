package com.db.employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.db.BxTree;
import com.db.Storage;

/**
 * @author saraths
 *
 */
public class StorageEmployee implements Storage {

	ArrayList<Employee> employeeList;
	BxTree<Integer, Employee> idStorage = new BxTree<Integer,Employee>(4);
	HashMap<String, ArrayList<Employee>> nameStorage = new HashMap<>();
	BxTree<Integer, ArrayList<Employee>> salaryStorage = new BxTree<Integer, ArrayList<Employee>>(4);
	
	public void retrieveDataFromFile(String FilePath) throws FileNotFoundException{
		try{
			
			//URL dbFilePath = Storage.class.getResource("employees.txt");
			employeeList = DataInputReader.parseInputFile(new File(FilePath));
			
			storingPrimaryKey();
			storingNonPrimaryKey();
		}
		catch(Exception e){
			throw new FileNotFoundException();
		}
	}
	
	/* 
	/**
	 * Storing the primary key (i.e)EmployeeID in B+ tree
	 */
	public void storingPrimaryKey(){
		
		for(Employee employeeItem: employeeList){
			idStorage.insert(employeeItem.getID(), employeeItem);
		}
	}
	
	
	/*Storing the non primary key(i.e) EmployeeName and Salary in Treemap */ 
	void storingNonPrimaryKey(){
		
		for(Employee employeeItem: employeeList){
			String employeeName = employeeItem.getName();
			if(nameStorage.containsKey(employeeName)){				
			}
			else{
				nameStorage.put(employeeName, new ArrayList<Employee>());				
			}
			
			nameStorage.get(employeeName).add(employeeItem);
			
			Integer salary = employeeItem.getSalary();
			ArrayList<Employee> value;
			if((value = salaryStorage.find(salary)) != null){	
				value.add(employeeItem);
			}
			else{
				salaryStorage.insert(salary, new ArrayList<Employee>());
				salaryStorage.find(salary).add(employeeItem);
			}
		}
		
	}
	
	public ArrayList<Employee> lessThan(String column, String value){
		ArrayList<Employee> retrieveResult = new ArrayList<>();
		switch(column.toLowerCase()){
		
		case "id": 
				{
					retrieveResult = idStorage.findLess(Integer.parseInt(value.trim()));
					break;
				}
		case "salary":
				{
					ArrayList<ArrayList<Employee>> tempResult = salaryStorage.findLess(Integer.parseInt(value.trim()));
					for( ArrayList<Employee> entryArrayList :tempResult){
						
						for(Employee e : entryArrayList)
							retrieveResult.add(e);
						
					}
				}
		}
		return retrieveResult;
	}
	
	public ArrayList<Employee> lessThanOrEqualTo(String column, String value){
		ArrayList<Employee> retrieveResult = new ArrayList<>();
	switch(column.toLowerCase()){
		
		case "id": 
				{
					retrieveResult = idStorage.findLessthanEqual(Integer.parseInt(value.trim()));
					break;
				}
		case "salary":
				{
					ArrayList<ArrayList<Employee>> tempResult = salaryStorage.findLessthanEqual(Integer.parseInt(value.trim()));
					for( ArrayList<Employee> entryArrayList :tempResult){
						
						for(Employee e : entryArrayList)
							retrieveResult.add(e);
						
					}
				}
		}
		return retrieveResult;
	}
	
	public ArrayList<Employee> greaterThan(String column, String value){
		ArrayList<Employee> retrieveResult = new ArrayList<>();
	switch(column.toLowerCase()){
		
		case "id": 
				{
					retrieveResult = idStorage.findGreater(Integer.parseInt(value.trim()));
					break;
				}

		case "salary":
				{
					ArrayList<ArrayList<Employee>> tempResult = salaryStorage.findGreater(Integer.parseInt(value.trim()));
					for( ArrayList<Employee> entryArrayList :tempResult){
						
						for(Employee e : entryArrayList)
							retrieveResult.add(e);
						
					}
				}
		}
		return retrieveResult;
	}
	
	public ArrayList<Employee> greaterOrEqualTo(String column, String value){
		ArrayList<Employee> retrieveResult = new ArrayList<>();
	switch(column.toLowerCase()){
		
		case "id": 
				{
					retrieveResult = idStorage.findGreaterthanEqual(Integer.parseInt(value.trim()));
					break;
				}

		case "salary":
				{
					ArrayList<ArrayList<Employee>> tempResult = salaryStorage.findGreaterthanEqual(Integer.parseInt(value.trim()));
					for( ArrayList<Employee> entryArrayList :tempResult){
						
						for(Employee e : entryArrayList)
							retrieveResult.add(e);
						
					}
					break;
				}
		}
		return retrieveResult;
	}
	
	public ArrayList<Employee> notEqualTo(String column, String value){
		ArrayList<Employee> retrieveResult = new ArrayList<>();
	switch(column.toLowerCase()){
		
		case "id": 
				{
					retrieveResult = idStorage.findAllNotEqual(Integer.parseInt(value.trim()));
					break;
				}
		case "name":
				{
					for(Map.Entry<String, ArrayList<Employee>> nameList: nameStorage.entrySet()){
						if(!nameList.getKey().equals(value.trim())){
							for(Employee e: nameList.getValue()){
									retrieveResult.add(e);
							}
						}
					}
					break;
				}
		case "salary":
				{
					ArrayList<ArrayList<Employee>> tempResult = salaryStorage.findAllNotEqual(Integer.parseInt(value.trim()));
					for( ArrayList<Employee> entryArrayList :tempResult){
						
						for(Employee e : entryArrayList)
							retrieveResult.add(e);
						
					}
				}
		}
		return retrieveResult;
	}
	
	public ArrayList<Employee> equalTo(String column, String value){
		ArrayList<Employee> retrieveResult = new ArrayList<>();
	switch(column.toLowerCase()){
		
		case "id": 
				{
					retrieveResult.add(idStorage.find(Integer.parseInt(value.trim())));
					break;
				}
		case "name":
				{
					for(Map.Entry<String, ArrayList<Employee>> nameList: nameStorage.entrySet()){
						if(nameList.getKey().equals(value.trim())){
							for(Employee e: nameList.getValue()){
									retrieveResult.add(e);
							}
						}
					}
					break;
				}
		case "salary":
				{
					ArrayList<Employee> tempResult = salaryStorage.find(Integer.parseInt(value.trim()));
					for( Employee e :tempResult){
						retrieveResult.add(e);
						
					}
				}
		}
		return retrieveResult;
	}
	
	public ArrayList<Employee> getAllRecords(){
		ArrayList<Employee> retrieveResult = new ArrayList<>();
		for(Map.Entry<String, ArrayList<Employee>> nameList: nameStorage.entrySet()){			
			for(Employee e: nameList.getValue()){
				retrieveResult.add(e);
			}			
		}
		return retrieveResult;	
	}
	
	public void printPrimaryKeyValue(){
		System.out.println("id stored");
		idStorage.dump();
		System.out.println("Salary Dump");
		salaryStorage.dump();
	
	}
}

class DataInputReader{
	
	
	public static ArrayList<Employee> parseInputFile(File inputFile) throws FileNotFoundException{
		Scanner inputFileScan = new Scanner(inputFile);
		ArrayList<Employee> employeeArray = new ArrayList<>();
		if(inputFile.exists()){
			String content = inputFileScan.nextLine();
			if(content.equals("Employee")){
				String inputToken = inputFileScan.nextLine();
				while(inputFileScan.hasNextLine()){
					
					Employee employeeRec = new Employee();
					
					if(inputToken.equals(" ID")){
						employeeRec.setID(Integer.parseInt(inputFileScan.nextLine().trim())); 
						 if(inputFileScan.hasNextLine())
							 inputToken = inputFileScan.nextLine();
						if(inputToken.equals(" Name")){
							employeeRec.setName(inputFileScan.nextLine().trim());
							 if(inputFileScan.hasNextLine())
								 inputToken = inputFileScan.nextLine();
						}
						if(inputToken.equals(" Salary")){
							employeeRec.setSalary(Integer.parseInt(inputFileScan.nextLine().trim()));
							if(inputFileScan.hasNextLine())
								 inputToken = inputFileScan.nextLine();				
						}
						
						employeeArray.add(employeeRec);
					}
					
				}
			}
		}
		
		inputFileScan.close();
		return employeeArray;
	}
	
}