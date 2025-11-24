package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.*;

public class StudentService {
	private Map<String, Student> students = new HashMap<>();
	
	public void addStudent(Student student) {
		if(students.containsKey(student.getId())) {
			throw new IllegalArgumentException("Student with Id already exists:"+ student.getId());
			
			
		}
		students.put(student.getId(), student);
	}
	public Student getStudent(String id) {
		return students.get(id);
	}
	public List<Student> listStudents(){
		return new ArrayList<>(students.values());
		
	}
	public void updateStudent(String id, String newName, String newEmail) {
		Student s = students.get(id);
		if(s != null) {
			s.setFullName(newName);
			s.setEmail(newEmail);
			
		}
	}
	public void deactivateStudent(String id) {
		Student s = students.get(id);
		if(s != null) {
			s.deactivate();
		}
	}

}
