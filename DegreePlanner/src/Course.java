
public class Course {
	private String name;
	private int credit;
	
	public Course(String name, int credit){
		this.name = name;
		this.credit = credit;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCredit(){
		return credit;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setCredit(int credit){
		this.credit = credit;
	}
}
