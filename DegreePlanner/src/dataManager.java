import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;

public class dataManager{

	private static ArrayList<Course> courseList;
	private static Map categoryToCourse;
	public static ErrorLogger errorBuilder;
	private static int size = 0;	

	public static void main(String[] args){
		setCourseList();
		System.out.println(Arrays.toString(courseList.toArray()));
	
		ArrayList<Course> test = searchByID("C");
		System.out.println(Arrays.toString(test.toArray()));

		test = searchByID("CS");
		System.out.println(Arrays.toString(test.toArray()));
		
		test = searchByName("C");
		System.out.println(Arrays.toString(test.toArray()));
		
		test = searchByName("CS");
		System.out.println(Arrays.toString(test.toArray()));
		
	}

	public static void setCourseList(){
		initArrayList("data/Courses");
	}


	private static Course getCourse(Scanner reader){
		int category = 0, credits = 0;
		String courseID = "",courseName = "",creditLayout = "",additionalInfo = "",description = "",gradeMode = "",
			   prerequisite = "",registrationInfo = "",restriction = "",alsoOfferedAs = "",courseFee = "",termOffered = "";
		try{
			category = Integer.parseInt(reader.nextLine());
			courseID = reader.nextLine();
			courseName = reader.nextLine();
			credits = Integer.parseInt(reader.nextLine());
			creditLayout = reader.nextLine();
			additionalInfo = reader.nextLine();
			description = reader.nextLine();
			gradeMode = reader.nextLine();
			prerequisite = reader.nextLine();
			registrationInfo = reader.nextLine();
			restriction = reader.nextLine();
			alsoOfferedAs= reader.nextLine();
			courseFee = reader.nextLine();
			termOffered= reader.nextLine();
		}catch(Exception e){
			//errorBuilder.error("" + e);
		}
		return new Course(category,credits,courseID,courseName,creditLayout,additionalInfo,description,gradeMode,
						  prerequisite,registrationInfo,restriction,alsoOfferedAs,courseFee,termOffered);
	}

	private static void initArrayList(String filePath){
		Scanner reader = null;  //Set to null for java type checker

		int lineCount = 0;	
		try{
			reader = new Scanner(new File(filePath));
			while(reader.hasNextLine()){
				reader.nextLine();
				lineCount++;
			}
		}catch(Exception e){
			errorBuilder.error("" + e);
		}
		size=lineCount/14;
		courseList = new ArrayList<Course>(size);		
		fillArrayList(filePath);
	}
	
	private static void fillArrayList(String filePath){
		Scanner reader = null;  //Set to null for java type checker
		try{
			reader = new Scanner(new File(filePath));
		}catch(Exception e){
			errorBuilder.error("" + e);
		}
		
		while(reader.hasNextLine()){
			Course temp = getCourse(reader);
			if(temp != null)
			courseList.add(temp);
		}
	}

	public static ArrayList<Course> searchByID(String search){
		ArrayList<Course> searched = new ArrayList<Course>(size);
		for(Course temp : courseList)
			if(temp.getCourseID().substring(0,search.length()).equals(search))
				searched.add(temp);
		return searched;
	}
	
	public static ArrayList<Course> searchByName(String search){
		ArrayList<Course> searched = new ArrayList<Course>(size);
		for(Course temp : courseList)
			if(temp.getCourseName().substring(0,search.length()).equals(search))
				searched.add(temp);
		return searched;
	}
	
	public static ArrayList<Course> searchByCategory(String search){
		ArrayList<Course> searched = new ArrayList<Course>(size);
		for(Course temp : courseList)
			if(temp.getCourseID().substring(0,search.length()).equals(search))
				searched.add(temp);
		return searched;
	}
}
