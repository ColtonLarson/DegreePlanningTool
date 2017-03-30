import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class dataManager{

	private static ArrayList<Course> courseList;
	private static Map categoryToCourse;
	private static int size = 0;	

	public static void main(String[] args){
		initCourseList();
		System.out.println(Arrays.toString(courseList.toArray()));
	
		ArrayList<Course> test = searchByName("C",0,4);
		System.out.println(Arrays.toString(test.toArray()));

		//test = searchByID("CS");
		//System.out.println(Arrays.toString(test.toArray()));
	}

	
	public static ArrayList<Course> searchByName(String searchText, int category, int credits){
		ArrayList<Course> result = new ArrayList<Course>(size);
		
		for(Course temp : courseList){
			if(temp.getCourseID().substring(0,searchText.length()).equals(searchText)){
					result.add(temp);
			}
		}

		if(category != 0){ result = subSearchCategory(result,category); }

		if(credits != 0){ result = subSearchCredits(result, credits); }
		
		return result;
	}
	
	public static ArrayList<Course> searchByTitle(String searchText, int category, int credits){
		ArrayList<Course> result = new ArrayList<Course>(size);
		
		for(Course temp : courseList){
			if(temp.getCourseName().substring(0,searchText.length()).equals(searchText)){
					result.add(temp);
			}
		}

		if(category != 0){ result = subSearchCategory(result, category); }

		if(credits != 0){ result = subSearchCredits(result, credits); }
		
		return result;
	}

	private static ArrayList<Course> subSearchCategory(ArrayList<Course> list, int category){
		ArrayList<Course> result = new ArrayList<Course>(size);
		for(Course temp : list){
			if(temp.getCategory() == category){
				result.add(temp);
			}
		}
		return result;
	}
	
	private static ArrayList<Course> subSearchCredits(ArrayList<Course> list, int credits){
		ArrayList<Course> result = new ArrayList<Course>(size);
		for(Course temp : list){
			if(temp.getCredits() == credits){
			//	System.out.println(temp.getCourseID() + ": " + credits + " = " + temp.getCredits());
				result.add(temp);
			}
		}
		//System.out.println("SHOULD : " + Arrays.toString(result.toArray()));
		return result;
	}


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

	private static Scanner openURL(){
		Scanner reader = null;
		try{
			URL url = new URL("http://www.cs.colostate.edu/~pbivrell/courseData");
			URLConnection con = url.openConnection();
			InputStream is =con.getInputStream();
			reader = new Scanner(is);
		}catch(Exception e){
			ErrorLogger.error("" + e);
		}

		return reader;
	}
	
	public static void initCourseList(){

		Scanner reader = null;
		reader = openURL();

		int lineCount = 0;	
		try{
			while(reader.hasNextLine()){
				reader.nextLine();
				lineCount++;
			}
		}catch(Exception e){
			ErrorLogger.error("" + e);
		}
		size=lineCount/13;
		courseList = new ArrayList<Course>(size);		
		fillArrayList();
	}
	
	private static void fillArrayList(){
		Scanner reader = null;  //Set to null for java type checker
		reader = openURL();	
	
		while(reader.hasNextLine()){
			Course temp = buildCourse(reader);
			if(temp != null)
			courseList.add(temp);
		}
	}


	public static void validateData(){
		Scanner reader = null;  //Set to null for java type checker
		reader = openURL();	
	
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
