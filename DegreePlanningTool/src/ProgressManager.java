import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.collections.ObservableList;

public class ProgressManager {
	

	private static TreeSet<Progress> years;

	public ProgressManager(){
     	years = new TreeSet<Progress>(new ProgressComp());
	}

	public static int getCategoryCredits(Category c, String year){
	    int total = 0;
        for(Progress p : years){
            total += p.countCredits(c);
            if(p.getYear().equals(year)){
			    return total;
            }
		}
		return 0;
	}

    public static ArrayList<Course> getCategoryClassesTaken(Category c, String year){
        ArrayList<Course> list = new ArrayList<Course>();
        for(Progress p : years){
           for(Course co : p.getClasses(c)){
                list.add(co);
           } 
           if(p.getYear().equals(year)){
                return list;   
           }
        }
        return list;
    }


	public static boolean isEmpty(){
		return years.isEmpty();
	}

    public static ArrayList<Course> getSem(String year, String sem){
        for(Progress p : years){
            if(p.equals(year)){
                return p.getSem(sem);
            } 
        }
        return null;
    }

	public static ArrayList<String> getYears(){
		ArrayList<String> yearList = new ArrayList<String>();
        for(Progress p : years){
            yearList.add(p.getYear());
        }
        return yearList;
	}

	//Adds new Year
	public static void add(String year){
		//System.out.println("Adding: " + year);
		years.add(new Progress(year));
	}

	//Adds new Course to a specific Year and Semster
	public static void insert(String year,String sem,Course course){	
		if(!years.contains(new Progress(year)))
            add(year);
       
		for(Progress p : years){
			if(p.equals(year)){
				p.insert(sem,course);
				System.out.println("Inserting: " + year + " " + sem + " " + course);
			}
		}
	}


	//Removes a year
	public static void delete(String year){
		years.remove(new Progress(year));
	}
	
	//Removes all instances of a course from a specific Year
	public static boolean remove(String year,String courseName){
		boolean found = false;

		System.out.println("Removing: " + year + " " + courseName);		
		
		if(years.contains(new Progress(year))){
			for(Progress p : years){
				if(p.equals(year)){
					found = p.remove(courseName);
					
				}
			}
		}
		return found;
	}

	public static void readData(File filename){
		//We are here now
		System.out.println("We are here now");
		TreeSet<Progress> save = new TreeSet<Progress>(years);


		years.clear();
		Scanner s = null;
		try{
			s = new Scanner(filename);
		}catch(FileNotFoundException e){
			AlertBox.display("File Error", "File could not be opened:\n" + e);
		}
			
		while(s.hasNext()){
			try{
			String year = s.nextLine();
			readYear(year);
			readSemester(s.nextLine(),year);
			readSemester(s.nextLine(),year);
			readSemester(s.nextLine(),year);
			}catch(Exception e){
				AlertBox.display("Read File", "Input file was invalid");
				years = save;
				return;
			}
		}
	}

	private static void readYear(String year){
		add(year);
	}	

	private static void readSemester(String sem, String year){
		String semster = sem.split(":")[0];
		String data = sem.split(":")[1];
		
		Course[] course = tokenize(data);

		for(Course c : course){
			insert(year,semster,c);	
			System.out.println("Creating Course: " + year + " - " + sem + " - " + c);
		}		
	}

	private static Course[] tokenize(String data){
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


	public static void writeData(File filename){
		try{
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			writer.write(printer());	
			writer.close();
		} catch (IOException e) {
			AlertBox.display("Write Error", "Could not write to file: \n" + e);
		}
	}

	public static String printer(){
		String out = "";
		for(Progress p : years){
			out += p;
		}
		return out;
	}

	public static void main(String[] args){
		
	}


}

