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
	
	public static void main(String[] args){
		initData();
	}
	
	public static void initData(){
		courseManager = new CourseManager();
		categoryManager = new CategoryManager();
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

	public static Category getCategoryByID(int id){
		return categoryManager.getCategoryByID(id);
	}

	public ArrayList<Category> getCategories(){
		return categoryManager.getCategories();
	}

	public static ArrayList<Course> searchByName(String searchText, int category, int credits){
		return courseManager.searchByName(searchText,category,credits);
	}
	
	public static ArrayList<Course> searchByTitle(String searchText, int category, int credits){
		return courseManager.searchByTitle(searchText,category,credits);
	}
}
