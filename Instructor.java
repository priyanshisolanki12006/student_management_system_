package edu.ccrm.domain;

public class Instructor extends Person{
	private String department;
	
	public Instructor(String Id, String fullName, String email, String department) {
		super(Id, fullName, email);
		this.department = department;
		
	}
	public String getDepartment() {return department;}
	public void setDepartment(String department) {this.department = department;}
	
	@Override
	public String getRole() {
		return "Instructor";
	}
}


