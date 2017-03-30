public class Course{
	private int category;
	private int credits;
	private String courseID;
	private String courseName;
	private String creditLayout;
	private String description;
	private String gradeMode;
	private String prerequisite;
	private String registrationInfo;
	private String restriction;
	private String alsoOfferedAs;
	private String courseFee;
	private String termOffered;

	public Course(int category,
				  String courseID,
				  String courseName,
				  int credits,
				  String creditLayout,
				  String description,
				  String gradeMode,
				  String prerequisite,
				  String registrationInfo,
				  String restriction,
				  String alsoOfferedAs,
				  String courseFee,
				  String termOffered)
	{
		this.category = category;
		this.credits = credits;
		this.courseID = courseID;
		this.courseName = courseName;
		this.creditLayout = creditLayout;
		this.description = description;
		this.gradeMode = gradeMode;
		this.prerequisite = prerequisite;
		this.registrationInfo = registrationInfo;
		this.restriction = restriction;
		this.alsoOfferedAs = alsoOfferedAs;
		this.courseFee = courseFee;
		this.termOffered = termOffered;
	}

	public int getCategory()			{ return category; }
	public String getCourseID() 		{ return courseID; }
	public String getCourseName() 		{ return courseName; }
	public int getCredits()				{ return credits; }
	public String getCreditLayout() 	{ return creditLayout; }
	public String getDescription() 		{ return description; }
	public String getPrerequisite() 	{ return prerequisite; }
	public String getGradeMode() 		{ return gradeMode; }
	public String getRegistrationInfo() { return registrationInfo; }
	public String getRestriction() 		{ return restriction; }
	public String getAlsoOfferedAs() 	{ return alsoOfferedAs;}
	public String getCourseFee() 		{ return courseFee; }
	public String getTermOffered() 		{ return termOffered;}

	public String toString(){
		return courseID;
	}
		
}
