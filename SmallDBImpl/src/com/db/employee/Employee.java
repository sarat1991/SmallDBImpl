package com.db.employee;

public class Employee {
	int ID;
	String Name;
	int salary;
	
	public Employee() {
		super();
		
	}
	
	public Employee(int iD, String name, int salary) {
		super();
		ID = iD;
		Name = name;
		this.salary = salary;
	}

	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

}
