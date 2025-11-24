package edu.ccrm.domain;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
	private String regNo;
	private boolean active;
	private List<Course> enrolledCourse;
	
	public Student(String id, String fullName, String email, String regNo ) {
		super(id,fullName,email);
		assert id != null && !id.isBlank();
		assert regNo != null && !regNo.isBlank();
		this.regNo = regNo;
		this.active = true;
		this.enrolledCourse = new ArrayList<>();
		
	}
	public String getRegNo() {return regNo;}
	public boolean isActive() {return active;}
	public List<Course> getEnrolledCourses(){return enrolledCourse;}
	
	public void deactivate() {this.active = false;}
	
	@Override
	public String getRole() {
		return "Student";
	}
	

}
