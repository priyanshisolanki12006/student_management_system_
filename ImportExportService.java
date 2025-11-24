package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;

import java.io.*;
import java.nio.file.*;

public class ImportExportService {
	private StudentService studentService;
	private CourseService courseService;
	
	public ImportExportService(StudentService studentService,CourseService courseService) {
		this.studentService = studentService;
		this.courseService = courseService;
	}
	
	public void exportStudents(String filename)throws IOException{
		Path path = Paths.get(filename);
		try(BufferedWriter writer = Files.newBufferedWriter(path)){
			for(Student s : studentService.listStudents()) {
				writer.write(String.join(",",
						s.getId(),s.getFullName(),s.getEmail(),s.getRegNo(),String.valueOf(s.isActive())));
				writer.newLine();
			}
		}
	}
	public void exportCourses(String filename) throws IOException{
		Path path = Paths.get(filename);
		try(BufferedWriter writer = Files.newBufferedWriter(path)){
			for(Course c: courseService.listCourses()) {
				writer.write(String.join(",",
						c.getCode(),c.getTitle(),String.valueOf(c.getCredits()),c.getDepartment()));
				writer.newLine();
			}
			
		}
	}
	public void importStudents(String filename) throws IOException{
		Path path = Paths.get(filename);
		try(BufferedReader reader = Files.newBufferedReader(path)){
			String line;
			while((line=reader.readLine())!=null) {
				String[] parts = line.split(",");
				if(parts.length>=4) {
					Student s = new Student(parts[0],parts[1],parts[2],parts[3]);
					if(parts.length>4 && parts[4].equalsIgnoreCase("False")) {
						s.deactivate();
					}
					studentService.addStudent(s);
			}
		}
	}
}
	public void importCourses(String filename) throws IOException{
		Path path = Paths.get(filename);
		try(BufferedReader reader = Files.newBufferedReader(path)){
			String line;
			while((line=reader.readLine())!=null) {
				String [] parts = line.split(",");
				if(parts.length>=4) {
					Course c = new Course(parts[0],parts[1],Integer.parseInt(parts[2]),null,parts[3]);
					courseService.addCourse(c);
				
				}
			}
		}
	}
	

}
