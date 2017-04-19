import java.util.ArrayList;
import java.util.Hashtable;


public class ProgressManager {
	

	private Hashtable<String,Progress> years;

	public ProgressManager(){
     	years = new Hashtable<String, Progress>();
	}

	public void add(String year){
		years.put(year,new Progress());
	}

	public void insert(String year,String sem,Course course){	
		if(years.contains(year)){
			years.get(year).insert(sem,course);
		}else{
			System.err.println("Error adding Course: Progress Manager - Year: " + year + "sem: " + sem + "course: " + course);
		}
	}

	public void delete(String year,String sem,String courseName){
		if(years.contains(year)){
			years.get(year).delete(sem,courseName);
		}
	}

	private void readData(){
	
	}

	private void writeData(){
	
	}


}

