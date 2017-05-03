import java.util.ArrayList;
import java.util.*;

public class Progress {
	private ArrayList<Course> fall;
	private ArrayList<Course> spring;
	private ArrayList<Course> summer;
	private String year;

	public Progress(String year){
		this.fall = new ArrayList<Course>();
		this.spring = new ArrayList<Course>();
		this.summer = new ArrayList<Course>();
		this.year = year;
	}

	public int countCredits(Category c){
		int count = 0;
		for(Course course : fall){
			if(c.getCategoryID() == course.getCategory()){
				count += course.getCredits();
			}
		}
		for(Course course : spring){
			if(c.getCategoryID() == course.getCategory()){
				count += course.getCredits();
			}	
		}
	
		for(Course course : summer){
			if(c.getCategoryID() == course.getCategory()){
				count += course.getCredits(); 
			}
		}
		return count;
	}

	public String getYear(){
		return year;
	}


	public Progress(String sem, Course course){
		fall = new ArrayList<Course>();
		spring = new ArrayList<Course>();
		summer = new ArrayList<Course>();
		this.insert(sem,course);
	}
	
	public void insert(String sem, Course course){
		getSem(sem).add(course);
	}
	
	public boolean remove(String courseName){
		boolean found = false;
		ArrayList<Course> removes = new ArrayList<Course>();
		for(Course c : fall){
			if(c.getCourseID().toLowerCase().equals(courseName.toLowerCase())){
				removes.add(c);
				found = true;
			}
		}

		for(Course c : removes){
			fall.remove(c);
		}
		
		removes.clear();

		for(Course c : spring){
			if(c.getCourseID().toLowerCase().equals(courseName.toLowerCase())){
				removes.add(c);
				found = true;
			}
		}
		
		for(Course c : removes){
			spring.remove(c);
		}

		removes.clear();
		
		for(Course c : summer){
			if(c.getCourseID().toLowerCase().equals(courseName.toLowerCase())){
				removes.add(c);
				found = true;
			}
		}

		for(Course c : removes){
			summer.remove(c);
		}

		removes.clear();
		return found;
	}

	public ArrayList<Course> getSem(String sem){
		if(sem.equals("fall")){
			return this.fall;
		}else if(sem.equals("spring")){
			return this.spring;
		}else if(sem.equals("summer")){
			return this.summer;	
		}else{
			System.out.println("Invalid Semster: " + sem);
			return null;
		}
		
	}



	public static void main(String[] args){
		Progress p1 = new Progress("2015-2016");
		Progress p2 = new Progress("2014-2016");
		Progress p3 = new Progress("2016-2017");
		
		TreeSet<Progress> a = new TreeSet<Progress>(new ProgressComp());	
	
		a.add(p1);
		a.add(p2);
		a.add(p3);
		
		System.out.println(a.contains(new Progress("2015-2017")));
		System.out.println(a.remove(new Progress("2015-2017")));

		System.out.println(a);	
		
		
	}

	public String toString(){
		return year + "\nfall:" + this.fall + "\nspring:" + this.spring + "\nsummer:" + this.summer + "\n";
	}
	
	public boolean equals(String s){
		return s.equals(this.getYear());
	}
}

class ProgressComp implements Comparator<Progress>{
	public int compare(Progress p1, Progress p2){	
		if(Integer.parseInt(p1.getYear().split("-")[0]) < Integer.parseInt(p2.getYear().split("-")[0])){
			return -1;
		}
		if(Integer.parseInt(p1.getYear().split("-")[0]) > Integer.parseInt(p2.getYear().split("-")[0])){
			return 1;
		}
		return 0;
	}
}
