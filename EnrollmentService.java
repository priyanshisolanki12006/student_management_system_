 package edu.ccrm.service;
 
 import edu.ccrm.domain.*;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;

import java.util.*;
 

public class EnrollmentService {
	private List<Enrollment> enrollments = new ArrayList<>();
	private static final int MAX_CREDITS_PER_SEMESTER = 20;
	
	public void enrollStudent(Student student, Course course, Semester semester) throws DuplicateEnrollmentException {
		for(Enrollment e : enrollments) {
			if(e.getStudent().equals(student) && e.getCourse().equals(course)) {
				throw new DuplicateEnrollmentException("Student already enrolled in course");
				
			}
		}
		
	    int totalCredits = enrollments.stream()
		         .filter(e->e.getStudent().equals(student) && e.getSemester() == semester)
		         .mapToInt(e->e.getCourse().getCredits())
		         .sum();
		if(totalCredits + course.getCredits()>MAX_CREDITS_PER_SEMESTER) {
			throw new MaxCreditLimitExceededException("Max credit limit reached for this semester");
		}
		
		enrollments.add(new Enrollment(student, course, semester));
		student.getEnrolledCourses().add(course);
	}
	public void assignGrade(Student student, Course course, Grade grade) {
		for(Enrollment e : enrollments) {
			if(e.getStudent().equals(student) && e.getCourse().equals(course)) {
				e.setGrade(grade);
				return;
			}
		}
		throw new IllegalArgumentException("Student not enrolled in course");
		
	}
	public double computeGPA(Student student) {
		int totalCredits = 0;
		int totalPoints = 0;
		
		for(Enrollment e : enrollments) {
			if(e.getStudent().equals(student) && e.getGrade() != null) {
				totalCredits += e.getCourse().getCredits();
				totalPoints += e.getCourse().getCredits() * e.getGrade().getGradePoints();
				
			}
		}
		return totalCredits == 0 ? 0.0 : (double) totalPoints / totalCredits;
		
	}
	public List<Enrollment> listEnrollments(){
		return enrollments;
	}

}
