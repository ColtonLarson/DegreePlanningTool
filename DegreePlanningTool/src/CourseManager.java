import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;

public class CourseManager {
	
	private static ArrayList<Course> courseList;
	private static final String courseDataURL = "http://www.cs.colostate.edu/~pbivrell/courseData";
	
	public CourseManager(){
		System.out.println("Construction CourseManager");
		fillCourseList();
	}
	
	//Everything bellow here has to do with the CourseList
	//The following Methods are data accessors
	public static ArrayList<Course> searchByName(String searchText, int category, int credits){
		ArrayList<Course> result = new ArrayList<Course>();
		
		for(Course temp : courseList){
			if(searchText.length() > temp.getCourseID().length()){
				continue;
			}
			if(temp.getCourseID().substring(0,searchText.length()).toLowerCase().equals(searchText.toLowerCase())){
					result.add(temp);
			}
		}

		if(category != 0){ result = subSearchCategory(result,category); }

		if(credits != 0){ result = subSearchCredits(result, credits); }
		
		result.add(new Course(13,"","Build Your Own",-1,"",
		"This course is used to define course which are not listed here. If you add this course you will be prompted to fill out some information about it.",
						"","","","","","",""));
		
		return result;
	}

	public static Course getCourseByName(String name){
		for(Course c : courseList){
			if(c.getCourseID().equals(name)){
				return c;
			}
		}
		return null;
	}
	
	public static ArrayList<Course> searchByTitle(String searchText, int category, int credits){
		ArrayList<Course> result = new ArrayList<Course>();
		
		for(Course temp : courseList){
			if(searchText.length() > temp.getCourseName().length()){
				continue;
			}
			if(temp.getCourseName().substring(0,searchText.length()).toLowerCase().equals(searchText.toLowerCase())){
					result.add(temp);
			}
		}
		
		if(category != 0){ result = subSearchCategory(result, category); }

		if(credits != 0){ result = subSearchCredits(result, credits); }
		
		result.add(new Course(13,"","Build Your Own",-1,"",
		"This course is used to define course which are not listed here. If you add this course you will be prompted to fill out some information about it.",
						"","","","","","",""));
		
		return result;
	}

	private static ArrayList<Course> subSearchCategory(ArrayList<Course> list, int category){
		ArrayList<Course> result = new ArrayList<Course>();
		for(Course temp : list){
			if(temp.getCategory() == category){
				result.add(temp);
			}
		}
		return result;
	}
	
	private static ArrayList<Course> subSearchCredits(ArrayList<Course> list, int credits){
		ArrayList<Course> result = new ArrayList<Course>();
		for(Course temp : list){
			if(temp.getCredits() == credits){
				result.add(temp);
			}
		}
		return result;
	}


	//The following Methods are data mutators
	private static Course buildCourse(Scanner reader){
		int category = 0, credits = 0;
		String courseID = "",courseName = "",creditLayout = "",description = "",gradeMode = "",
			   prerequisite = "",registrationInfo = "",restriction = "",alsoOfferedAs = "",courseFee = "",termOffered = "";
		try{
			category = Integer.parseInt(reader.nextLine());
			courseID = reader.nextLine();
			courseName = reader.nextLine();
			credits = Integer.parseInt(reader.nextLine());
			creditLayout = reader.nextLine();
			description = reader.nextLine();
			gradeMode = reader.nextLine();
			prerequisite = reader.nextLine();
			registrationInfo = reader.nextLine();
			restriction = reader.nextLine();
			alsoOfferedAs = reader.nextLine();
			courseFee = reader.nextLine();
			termOffered= reader.nextLine();
		
		}catch(Exception e){
			ErrorLogger.error("" + e);
		}
		return new Course(category,courseID,courseName,credits,creditLayout,description,gradeMode,
						  prerequisite,registrationInfo,restriction,alsoOfferedAs,courseFee,termOffered);
	}

	private static void fillCourseList(){
		Scanner reader = null;  //Set to null for java type checker
		reader = DataManager.openURL(courseDataURL);	
		
		courseList = new ArrayList<Course>();		
	
		while(reader.hasNextLine()){
			Course temp = buildCourse(reader);
			if(temp != null)
			courseList.add(temp);
		}
	}


	//The following methods are for debugging proposes only
	private static void validateData(){
		Scanner reader = null; 
		reader = DataManager.openURL(courseDataURL);	
	
		while(reader.hasNextLine()){
			validateBlock(reader);
		}
	}
	
	private static void validateBlock(Scanner reader){
		int category = 0, credits = 0;
		String courseID = "",courseName = "",creditLayout = "",description = "",gradeMode = "",
			   prerequisite = "",registrationInfo = "",restriction = "",alsoOfferedAs = "",courseFee = "",termOffered = "";
		try{
			String s = reader.nextLine(); System.out.println("1: " + s);
			Integer.parseInt(s);
			s = reader.nextLine();System.out.println("2: " + s);
			s = reader.nextLine();System.out.println("3: " + s);
			s = reader.nextLine();System.out.println("4: " + s);
			Integer.parseInt(s);
			s = reader.nextLine();System.out.println("5: " + s);
			s = reader.nextLine();System.out.println("6: " + s);
			s = reader.nextLine();System.out.println("7: " + s);
			s = reader.nextLine();System.out.println("8: " + s);
			s = reader.nextLine();System.out.println("9: " + s);
			s = reader.nextLine();System.out.println("10: " +s);
			s = reader.nextLine();System.out.println("11: " + s);
			s = reader.nextLine();System.out.println("12: " + s);
			s = reader.nextLine();System.out.println("13: " + s);
		}catch(Exception e){
			ErrorLogger.error("" + e);
		}
	}
}
