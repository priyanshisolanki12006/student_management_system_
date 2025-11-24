package edu.ccrm.domain;

public class Course {
	private String code;
	private String title;
	private int credits;
	private Instructor instructor;
	public String department;
	
	public Course(String code, String title, int credits, Instructor instructor, String department) {
		this.code = code;
		this.title = title;
		this.credits = credits;
		this.instructor = instructor;
		this.department = department;
	}
	public String getCode() {return code;}
	public String getTitle() {return title;}
	public int getCredits() {return credits;}
	public Instructor getInstructor() {return instructor;}
	public String getDepartment() {return department;}
	
	@Override
	public String toString() {
		return String.format("%s - %s (%d credits, Dept:%s)", code, title, credits, department);
		
	}

}
