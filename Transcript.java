package edu.ccrm.domain;

import java.util.*;

public class Transcript {
	private final Student student;
	private final Map<Course,Grade> grades;
	private final double gpa;
	
	private Transcript(Builder builder) {
		this.student = builder.student;
		this.grades = builder.grades;
		this.gpa = builder.gpa;
		
	}
	
	public Student getStudent() {return student;}
	public Map<Course,Grade> getGrades(){return grades;}
	public double getGpa() {return gpa;}
	
	@Override
	public String toString() {
		return "Transcript for"+ student.getFullName()+"(GPA: "+gpa+")";
		
	}
	public static class Builder{
		private final Student student;
		private final Map<Course,Grade> grades=new HashMap<>();
		private double gpa;
		
		public Builder(Student student) {
			this.student=student;
		}
		
		public Builder addGrade(Course course, Grade grade) {
			grades.put(course, grade);
			return this;
		}
		public Builder gpa(double gpa) {
			this.gpa = gpa;
			return this;
		}
		
		public Transcript build() {
			return new Transcript(this);
		}
	}

}
