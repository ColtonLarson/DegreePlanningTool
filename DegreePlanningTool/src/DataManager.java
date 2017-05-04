import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DataManager{

	private static CourseManager courseManager;
	private static CategoryManager categoryManager;
	private static ProgressManager progressManager;
	private static boolean started = false;	

	public static void main(String[] args){
		initData();
	}

	public DataManager(){
		if(!started){
			initData();
			started = true;
		}
		
	}

	public static int getCategoryCredits(Category c, String year){
		return progressManager.getCategoryCredits(c,year);
	}

	public static ArrayList<Course> getCategoryClassesTaken(Category c, String year){
		return progressManager.getCategoryClassesTaken(c,year);
	}
	
	
	public static void initData(){
		System.out.println("Inializing Data");
		courseManager = new CourseManager();
		categoryManager = new CategoryManager();
		progressManager = new ProgressManager();
	}

	public static void addYear(String year){
		progressManager.add(year);
	}
    
    public static ArrayList<Course> getSem(String year, String sem){
        return progressManager.getSem(year,sem);
    }

    public static ArrayList getYears(){
        return progressManager.getYears();
    }

	public static void addCourse(String year, String sem, Course course){
        System.out.println(year + " " + sem + " " + " " + course);
        progressManager.insert(year,sem,course);
    }

	public static void deleteYear(String year){
		progressManager.delete(year);
	}

	public static boolean deleteCourse(String year, String courseName){
		return progressManager.remove(year,courseName);
	}

	public static boolean isYearsEmpty(){
		return progressManager.isEmpty();
	}

	public static void print(){
		System.out.println(progressManager.printer());
	}

	public static void save(File filename){
        progressManager.writeData(filename);
	}

	public static void load(File filename){
		progressManager.readData(filename);
	}
	
	public static Scanner openURL(String urlText){
		Scanner reader = null;
		try{
			URL url = new URL(urlText);
			URLConnection con = url.openConnection();
			InputStream is =con.getInputStream();
			reader = new Scanner(is);
		}catch(Exception e){
			ErrorLogger.error("" + e);
		}

		return reader;
	}

	public static Course getCourseByName(String name){
		return courseManager.getCourseByName(name);
	}

	public static Category getCategoryByID(int id){
		return categoryManager.getCategoryByID(id);
	}
	
	public static int getCategoryIDByName(String name){
		return categoryManager.getCategoryIDByName(name);
	}

	public static ArrayList<Category> getCategories(){
		return categoryManager.getCategories();
	}

	public static ArrayList<Course> searchByName(String searchText, int category, int credits){
		return courseManager.searchByName(searchText,category,credits);
	}
	
	public static ArrayList<Course> searchByTitle(String searchText, int category, int credits){
		return courseManager.searchByTitle(searchText,category,credits);
	}
}
