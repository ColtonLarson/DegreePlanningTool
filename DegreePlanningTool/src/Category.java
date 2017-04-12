public class Category{
	private String categoryName;
	private int categoryID;
	private int creditsRequired;
	private String description;

	public Category(String categoryName, int categoryID,
					int creditsRequired, String description)
	{
		this.categoryName = categoryName;
		this.categoryID = categoryID;
		this.creditsRequired = creditsRequired;
		this.description = description;
	}

	public String getCategoryName(){ return categoryName; }
	public int getCategoryID(){ return categoryID; }
	public int getCreditsRequired() { return creditsRequired; }
	public String getDescription(){ return description; }


	public String toString(){
		return categoryName;
	}		
}
