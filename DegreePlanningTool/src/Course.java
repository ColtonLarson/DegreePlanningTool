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
	
	public Course(int category,
				  String courseID,
				  String courseName,
				  int credits,
				  String description)
	{
		this.category = category;
		this.credits = credits;
		this.courseID = courseID;
		this.courseName = courseName;
		this.creditLayout = "N/A";
		this.description = description + " [This was course built by the user]";
		this.gradeMode = "N/A";
		this.prerequisite = "N/A";
		this.registrationInfo = "N/A";
		this.restriction = "N/A";
		this.alsoOfferedAs = "N/A";
		this.courseFee = "N/A";
		this.termOffered = "N/A";
	}
	
	public Course(Course crs){
        this.category = crs.category;
        this.credits = crs.credits;
        this.courseID = crs.courseID;
        this.courseName = crs.courseName;
        this.creditLayout = crs.creditLayout;
        this.description = crs.description;
        this.gradeMode = crs.gradeMode;
        this.prerequisite = crs.prerequisite;
        this.registrationInfo = crs.registrationInfo;
        this.restriction = crs.restriction;
        this.alsoOfferedAs = crs.alsoOfferedAs;
        this.courseFee = crs.courseFee;
        this.termOffered = crs.termOffered;
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
