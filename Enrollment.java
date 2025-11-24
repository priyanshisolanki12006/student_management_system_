package edu.ccrm.domain;

import java.time.LocalDate;


public class Enrollment {
	private Student student;
	private Course course;
	private Semester semester;
	private Grade grade;
	private LocalDate enrollmentDate;
	
	public Enrollment(Student student, Course course, Semester semester) {
		this.student = student;
		this.course = course;
		this.semester = semester;
		this.enrollmentDate = LocalDate.now();
	}
	
	public Student getStudent() {return student;}
	public Course getCourse() {return course;}
	public Semester getSemester() {return semester;}
	public LocalDate getEnrollmentDate() {return enrollmentDate;}
	public Grade getGrade() {return grade;}
	
	public void setGrade(Grade grade) {this.grade = grade;}
	
	@Override
	public String toString() {
		return String.format("Enrollment: %s in %s (%s) - Grade: %s",
			   student.getFullName(),
			   course.getTitle(),
			   semester.getLabel(),
			   grade != null? grade : "Not yet assigned");
	}

}
