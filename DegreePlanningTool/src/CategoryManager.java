import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;

public class CategoryManager{
	private static ArrayList<Category> categoryList;
	private static final String categoryToCourseURL = "http://www.cs.colostate.edu/~pbivrell/categories";
	
	public CategoryManager(){
		initCategory();
	}

	public static Category getCategoryByID(int id){
		for(Category temp : categoryList){
			if(temp.getCategoryID() == id){
				return temp;
			}
		}
		return categoryList.get(categoryList.size()-1);
	}

	public ArrayList<Category> getCategories(){
		return categoryList;
	}
	
	public int getCategoryIDByName(String name){
        for(Category cat : categoryList){
            if(cat.getCategoryName().equals(name)){
                return cat.getCategoryID();
            }
        }
        return 0;
    }

	

	//The following Methods are data mutators
	private static void initCategory(){
		Scanner reader = null;  
		reader = DataManager.openURL(categoryToCourseURL);	
		
		categoryList = new ArrayList<Category>();		
	
		while(reader.hasNextLine()){
			Category temp = buildCategory(reader);
			if(temp != null)
			categoryList.add(temp);
		}
	}
	
	private static Category buildCategory(Scanner reader){
		int creditsRequired = 0, categoryID = 0;
		String categoryName = "", description = "";	
	
		try{
			categoryName = reader.nextLine();
			categoryID = Integer.parseInt(reader.nextLine());
			creditsRequired = Integer.parseInt(reader.nextLine());
			description = reader.nextLine();
		
		}catch(Exception e){
			ErrorLogger.error("" + e);
		}
		return new Category(categoryName,categoryID,creditsRequired,description);
	}


}
