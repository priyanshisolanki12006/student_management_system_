package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.io.*;
import edu.ccrm.service.*;

import java.nio.file.Path;
import java.util.Scanner;

public class Main {
	private static final StudentService studentService = new StudentService();
	private static final CourseService courseService = new CourseService();
	private static final EnrollmentService enrollService = new EnrollmentService();
	private static final ImportExportService ioService = new ImportExportService(studentService,courseService);
	private static final BackupService backupservice = new BackupService("Backups");
	
	

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int choice;
		
		do {
			System.out.println("\n=== Campus Course & Records Manager(CCRM) ====");
			System.out.println("1. Manage Students");
			System.out.println("2. Manage Course");
			System.out.println("3. Enrollments & Grades");
			System.out.println("4. Reports");
			System.out.println("5. Import/Export Data");
			System.out.println("6. Backup Data");
			System.out.println("0.Exit");
			System.out.println("Enter your choice");
			
			choice = sc.nextInt();
			sc.nextLine();
			
			switch(choice) {
			case 1 -> manageStudents(sc);
			case 2 -> manageCourses(sc);
			case 3 -> manageEnrollments(sc);
			case 4 -> generateReports();
			case 5 -> manageImportExport(sc);
			case 6 -> manageBackup();
			case 0 -> System.out.println("Exiting");
			default -> System.out.println("Invalid choice,Enter a valic choice");
			
			
			}
		}while(choice != 0);
	
           sc.close();
	}
	private static void manageStudents(Scanner sc) {
		System.out.println("\n---Student Manager---");
		System.out.println("1. Add Student");
		System.out.println("2. List Students");
		System.out.println("Enter Choice:");
		int ch = sc.nextInt();
		sc.nextLine();
		
		switch(ch) {
		case 1 -> {
			System.out.println("Enter Id:");
			String id = sc.nextLine();
			System.out.println("Enter name:");
			String name = sc.nextLine();
			System.out.println("Enter email");
			String email = sc.nextLine();
			System.out.println("Enter RegNo:");
			String regNo = sc.nextLine();
			studentService.addStudent(new Student(id,name,email,regNo));
			
			System.out.println("Student Added!");
			
		}
		case 2 -> studentService.listStudents().forEach(System.out::println);
		default -> System.out.println("Enter a valid choice");
		
		}
	}
	private static void manageCourses(Scanner sc) {
		System.out.println("\n---Course Management");
		System.out.println("1. Add Course");
		System.out.println("2. List Courses");
		System.out.println("Entere Choice");
		int ch = sc.nextInt();
		sc.nextLine();
		
		switch(ch) {
		case 1 ->{
			System.out.println("Enter Code:");
			String code = sc.nextLine();
			System.out.println("Enter Title:");
			String title = sc.nextLine();
			System.out.println("Enter Credits:");
		    int credits = sc.nextInt();sc.nextLine();
		    
			System.out.println("Enter Department:");
			String dept = sc.nextLine();
			Course c = new Course(code,title,credits,null,dept);
			courseService.addCourse(c);
			System.out.println("Course Added");
		}
		case 2 -> courseService.listCourses().forEach(System.out::println);
		default -> System.out.println("Enter a valic choice");
		
		}
		
	}
	private static void manageEnrollments(Scanner sc) {
	   System.out.println("\n---Enrollment Management---");
	   System.out.println("Enter student ID");
	   String sid = sc.nextLine();
	   Student s = studentService.getStudent(sid);
	   if(s==null) {
		   System.out.println("student not found");
		   return;
	   }
	   System.out.println("Enter a course code;");
	   String code = sc.nextLine();
	   Course c = courseService.listCourses().stream()
			   .filter(course->course.getCode().equalsIgnoreCase(code))
			   .findFirst()
			   .orElse(null);
	   if (c==null) {
		   System.out.println("course not found");
		   return;
	   }
	   System.out.println("choose semester: 1.SPRING 2.SUMMER 3.FALL");
	   int semChoice = sc.nextInt();sc.nextLine();
	   Semester sem = switch(semChoice) {
	   case 1 -> Semester.SPRING;
	   case 2 -> Semester.SUMMER;
	   case 3 -> Semester.FALL;
	   default -> Semester.FALL;
	   };
	   
	   try {
		  
		   enrollService.enrollStudent(s,c,sem);
		   System.out.println("Enrollment Successful");
	
	   }
	   catch (DuplicateEnrollmentException e) {
	       System.out.println(e.getMessage());
	   }
	   catch(MaxCreditLimitExceededException e) {
		   System.out.println(e.getMessage());
	   }
	   
	}
	private static void generateReports() {
		System.out.println("\n----Reports----");
		for(Student s : studentService.listStudents()) {
			double gpa = enrollService.computeGPA(s);
			System.out.println(s.getFullName()+"(RegNo:"+s.getRegNo()+") -> GPA:" +gpa);
		}
	}
    private static void manageImportExport(Scanner sc) {
    	System.out.println("\n---Import/Export---");
    	System.out.println("1. Export Students");
    	System.out.println("2. Export Courses");
    	System.out.println("3. Import Students");
    	System.out.println("4. Import Courses");
    	System.out.println("Enter choice:");
    	int ch = sc.nextInt();sc.nextLine();
    	
    	try {
    		switch(ch) {
    		case 1 -> {
    			ioService.exportStudents("exports/students.csv");
    			System.out.println("Student exported");
    			
    		}
    		case 2 ->{
    			ioService.exportCourses("exports/courses.csv");
    			System.out.println("Courses exported");
    			
    		}
    		case 3 ->{
    			ioService.importStudents("exports/students.csv");
    			System.out.println("Student imported");
    			
    		}
    		case 4 ->{
    			ioService.importCourses("exports/courses.csv");
    			System.out.println("Courses imported");
    		}
    		default -> System.out.println("Invalid option");
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Error: "+e.getMessage());
    	}
    }
    
    private static void manageBackup() {
    	try {
    		Path backupFolder = backupservice.createBackup("exports");
    		long size = backupservice.computeFolderSize(backupFolder);
    		System.out.println("Backup created at :"+backupFolder);
    		System.out.println("Backup size:"+size + "bytes");
    	}
    	catch(Exception e) {
    		System.out.println("Backup failed :"+e.getMessage());
    		
    	}
    }
}
