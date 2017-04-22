import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class ProgressManager {
	

	private TreeSet<Progress> years;

	public ProgressManager(){
     	years = new TreeSet<Progress>(new ProgressComp());
	}


	//Adds new Year
	public void add(String year){
		//System.out.println("Adding: " + year);
		years.add(new Progress(year));
	}

	//Adds new Course to a specific Year and Semster
	public void insert(String year,String sem,Course course){	
		if(years.contains(new Progress(year))){	
			for(Progress p : years){
				if(p.equals(year)){
					p.insert(sem,course);
				}
			}
		}
	}


	//Removes a year
	public void delete(String year){
		years.remove(new Progress(year));
	}
	
	//Removes a course from a specific Year and Semester
	public void remove(String year,String sem,String courseName){
		if(years.contains(new Progress(year))){
			for(Progress p : years){
				if(p.equals(year)){
					p.remove(sem,courseName);
					break;
				}
			}
		}
	}

	public void readData(){
	
		years.clear();
		Scanner s = null;
		try{
			s = new Scanner(new File("the-file-name.txt"));
		}catch(FileNotFoundException e){}
			
		while(s.hasNext()){
			String year = s.nextLine();
			readYear(year);
			readSemester(s.nextLine(),year);
			readSemester(s.nextLine(),year);
			readSemester(s.nextLine(),year);
		}
	}

	private void readYear(String year){
		this.add(year);
	}	

	private void readSemester(String sem, String year){
		String semster = sem.split(":")[0];
		String data = sem.split(":")[1];
		
		Course[] course = tokenize(data);
		
		for(Course c : course){
			this.insert(year,semster,c);	
		//	System.out.println("Creating Course: " + year + " - " + sem + " - " + c);
		}		
	}

	private Course[] tokenize(String data){
		//System.out.println("Tokenizing: " + data);
		int count = 0;
		for(char c : data.toCharArray()){
			if(c == '{'){
				count++;
			}
		}
		//System.out.println("Number of Course: " + count);
		Course[] ret = new Course[count];
		
		count = 0;
		for(int i = 0; i < data.length(); i++){
			String courseData = "";
			if(data.charAt(i) == '{'){
				i++;
				while(data.charAt(i) != '}' && i < data.length()){
					courseData += data.charAt(i++);
				}	
			}
			
			//System.out.println("Token: " + courseData);
			
			if(courseData.contains(",")){
				String[] udc = courseData.split(",");
				ret[count++] = new Course(Integer.parseInt(udc[0]),udc[1],udc[2],Integer.parseInt(udc[3]),udc[4],1);
				courseData = "";
			}else{
				if(!courseData.equals("")){
				//	System.out.println("Attempting to Construct DataManger");
					DataManager dm = new DataManager();
					ret[count++] = dm.getCourseByName(courseData);
					courseData = "";
				}
			}
		}
		
		return ret;
	}


	public void writeData(){
		try{
			PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
			writer.write(this.toString());	
			writer.close();
		} catch (IOException e) {
  			// do something
		}
	}

	public String toString(){
		String out = "";
		for(Progress p : years){
			out += p;
		}
		return out;
	}

	public static void main(String[] args){
		ProgressManager m = new ProgressManager();
		m.add("2013-2014");
		m.add("2015-2016");
		m.add("2016-2017");
		m.insert("2013-2014","spring",new Course(1,"CS200","Data Strucutures",1,"","","","","","","","",""));
		m.insert("2013-2014","spring",new Course(1,"CS300","Data Strucutures",1,""));
		m.insert("2015-2016","fall",new Course(1,"CS400","Data Strucutures",1,""));
		m.insert("2015-2016","spring",new Course(1,"CS400","Data Strucutures",1,""));
		m.insert("2015-2016","summer",new Course(1,"CS500","Data Strucutures",1,""));
		System.out.println(m);
		m.remove("2015-2016","summer","CS500");
		System.out.println(m);
		m.writeData();
		System.out.println("TEST");
		m.readData();
		System.out.println("TEST2");
		System.out.println(m);
		
	}


}

