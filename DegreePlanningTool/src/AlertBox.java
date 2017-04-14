import java.util.ArrayList;
import java.util.Collections;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class AlertBox {
	
	private static TextField name;
    private static TextField title;
    private static Button add;
    private static Button cancel;
    private static TextField description;
    private static ComboBox credits;
    private static ComboBox category;
    private static boolean retType = false;
	
	public static void display(String title, String message){
		Stage window = new Stage();
		
		//User can't access the window behind this one
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(300);
		window.setMinHeight(150);
		
		Label label = new Label();
		label.setText(message);
		
		Button closeButton = new Button("Okay");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox();
		layout.setPrefSize(300, 150);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		//Makes user interact with the window
		window.showAndWait();
	}
	
	public static Course makeCourse(){
        //Call Constructor new Course(int category,String courseID, String courseName, int credits, String description); 
        
        Stage window = new Stage();
		
		//User can't access the window behind this one
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Build Course");
		window.setMinWidth(550);
		window.setMinHeight(300);
		
		BorderPane bPane = new BorderPane();
		bPane.setPrefSize(550, 300);
		bPane.setTop(getTop());
		bPane.setCenter(getCenter());
		
		
		add.setOnMouseClicked(e -> {
            setRet(true);
            window.close();
		});
		
		cancel.setOnMouseClicked(e -> {
            setRet(false);
            window.close();
		});
		
		Scene scene = new Scene(bPane);
		window.setScene(scene);
		//Makes user interact with the window
		window.showAndWait();
		if(retType){
            return new Course(category.getSelectionModel().selectedIndexProperty().getValue(), name.getText(), title.getText(), credits.getSelectionModel().selectedIndexProperty().getValue() + 1, description.getText());
		}
		else{
            return null;
		}
	}
	
	private static void setRet(boolean val){
        retType = val;
	}
	
	private static VBox getTop(){
        VBox top = new VBox(10);
        top.setPrefSize(560, 40);
        top.setAlignment(Pos.CENTER);
        Label label = new Label("Build a course that is not listed at CSU");
        top.getChildren().add(label);
        return top;
	}
	
	private static VBox getCenter(){
        VBox left = new VBox(10);
        left.setPrefSize(550, 400);
        left.setSpacing(8);
        left.setAlignment(Pos.CENTER);
        name = new TextField();
        name.setPromptText("Course Name");
        title = new TextField();
        title.setPromptText("Course Title");
        add = new Button("Add Course");
        
        ObservableList<Category> categories = FXCollections.observableArrayList();
        ArrayList<Category> cats = new ArrayList<Category>(DataManager.getCategories());
        for(Category c: cats){
            categories.add(c);
        }
        category = new ComboBox(categories);
        category.setValue(categories.get(0));
        
        ObservableList<Integer> credit = FXCollections.observableArrayList();
        for(int i = 1; i <= 18; i++){
            credit.add(i);
        }
        Label label = new Label("Credits:");
        credits = new ComboBox(credit);
        description = new TextField();
        description.setPromptText("Description");
        cancel = new Button("Cancel");
        left.getChildren().addAll(category, label, credits, name, title, description, add, cancel);
        return left;
	}
}
