import java.util.ArrayList;

public class Progress {
	private ArrayList<Course> fall;
	private ArrayList<Course> spring;
	private ArrayList<Course> summer;

	public Progress(){
		fall = new ArrayList<Course>();
		spring = new ArrayList<Course>();
		summer = new ArrayList<Course>();
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
	
	public void delete(String sem, String courseName){
		ArrayList<Course> semester = getSem(sem);
		for(Course c : semester){
			if(c.getCourseName().equals(courseName)){
				semester.remove(c);
				break;
			}
		}
	}

	private ArrayList<Course> getSem(String sem){
		if(sem.equals("fall")){
			return fall;
		}else if(sem.equals("spring")){
			return spring;
		}else if(sem.equals("summer")){
			return summer;	
		}else{
			System.out.println("Invalid Semster: " + sem);
			return null;
		}
		
	}


	public static void main(String[] args){
	}
}


