import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CenterPanel extends Application{
	
	private BorderPane centerPanel = new BorderPane();
	private BorderPane top = new BorderPane();
	private HBox topUpper = new HBox();
	
	private HBox topLower = new HBox();
	private ComboBox searchComboBox;
	private ComboBox categoryComboBox;
	private ComboBox creditComboBox;
	
	private ScrollPane scrollPane = new ScrollPane();
	private HBox bottom = new HBox();
	private ArrayList<Label> quickProgress = new ArrayList<Label>();
	private ArrayList<Course> searchDisplay = new ArrayList<Course>();
	private VBox selected = new VBox();
	public CenterPanel(){
		try {
			DataManager.initData();
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		centerPanel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		setTopPanel();
		top.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		top.setTop(topUpper);
		top.setBottom(topLower);
		centerPanel.setTop(top);
		setCenterPanel();
		centerPanel.setCenter(scrollPane);
		setBottomPanel();
		centerPanel.setBottom(bottom);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	private void setTopPanel(){
		setTopUpper();
		setTopLower();
	}
	
	private void setTopUpper(){
        topUpper.setStyle("-fx-background-color: #696969;");
		topUpper.setPrefSize(450, 40);
		topUpper.autosize();
		topUpper.setAlignment(Pos.CENTER);
		topUpper.setPadding(new Insets(10));
		topUpper.setSpacing(8);
		TextField search = new TextField();
		search.setPromptText("'Course Name' or 'Course Title'");
		search.setMaxSize(2000, 20);
		search.setPrefSize(450, 20);
		search.autosize();
		Button searchButton = new Button("Search");
		searchButton.setOnMouseClicked(e -> {
			switch(searchComboBox.getSelectionModel().selectedIndexProperty().getValue()){
			case 0:
				searchDisplay = DataManager.searchByName(search.getText(),0,0);
				UpdateCenterPanel();
				break;
			case 1:
				searchDisplay = DataManager.searchByTitle(search.getText(),0,0);
				UpdateCenterPanel();
				break;
			default:
				//TODO: Not found
				System.out.println(search.getText());
				break;
			};
		});
		Button progressButton = new Button("  Progress  ");
		topUpper.getChildren().addAll(searchButton, search, progressButton);
	}
	
	private void setTopLower(){
        topLower.setStyle("-fx-background-color: #696969;");
		topLower.setPrefSize(450, 40);
		topLower.autosize();
		topLower.setAlignment(Pos.CENTER);
		topLower.setPadding(new Insets(10));
		topLower.setSpacing(8);
		
		ObservableList<String> searchOptions = FXCollections.observableArrayList("Course Name", "Course Title");
		searchComboBox = new ComboBox(searchOptions);
		searchComboBox.setPromptText(searchOptions.get(0));
		searchComboBox.setValue(searchOptions.get(0));
		
		ObservableList<String> searchCategory = FXCollections.observableArrayList("Cat1", "Cat2");
		categoryComboBox = new ComboBox(searchCategory);
		categoryComboBox.setPromptText(searchCategory.get(0));
		categoryComboBox.setValue(searchCategory.get(0));
		
		ObservableList<String> searchCredit = FXCollections.observableArrayList("0", "1", "2", "3", "4", "5");
		creditComboBox = new ComboBox(searchCredit);
		creditComboBox.setPromptText(searchCredit.get(0));
		creditComboBox.setValue(searchCredit.get(0));
		
		topLower.getChildren().addAll(searchComboBox, categoryComboBox, creditComboBox);
	}
	
	private void setCenterPanel(){
		scrollPane.setPrefSize(800, 800);
		scrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		scrollPane.autosize();
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
	}

	private void setBottomPanel(){
		bottom.setPrefSize(450, 80);
		bottom.setStyle("-fx-background-color: #367044;");
		bottom.setAlignment(Pos.CENTER);
		bottom.setSpacing(50);
		bottom.setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		//Calculate total credits taken
		
		quickProgress.add(new Label("Completed Credits: "));
		quickProgress.add(new Label("Major: Computer Science"));
		//Get current year (TODO: update this)
		LocalDateTime now = LocalDateTime.now();
		String year = Integer.toString(now.getYear() - 1) + "-" + Integer.toString(now.getYear());
		quickProgress.add(new Label("Year: " + year));
		for(int i = 0; i < 3; i++){
			quickProgress.get(i).setFont(Font.font("Verdana", FontWeight.BOLD, 14));
			quickProgress.get(i).setTextFill(Color.WHITE);
			quickProgress.get(i).setAlignment(Pos.CENTER);
		}
		bottom.getChildren().addAll(quickProgress);
	}
	
	public BorderPane getPanel(){
		return centerPanel;
	}
	
	private void UpdateCenterPanel(){
		VBox searchDisplayPanes = new VBox();
		searchDisplayPanes.setPrefSize(1200, 200);
		searchDisplayPanes.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		searchDisplayPanes.autosize();
		for(int i = 0; i < searchDisplay.size(); i++){
			BorderPane bPane = new BorderPane();
			bPane.setPrefSize(400, 200);
			bPane.setBorder(new Border(new BorderStroke(Color.BLACK, 
		            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			bPane.setTop(getLeftCoursePane(i));
			bPane.setCenter(getRightCoursePane(i));
			searchDisplayPanes.getChildren().add(bPane);
		}
		scrollPane.setContent(searchDisplayPanes);
		
	}
	
	//Add another param for parent
	private VBox getLeftCoursePane(int i){
		Text name = new Text(searchDisplay.get(i).toString() + "\t" + searchDisplay.get(i).getCourseName());
		name.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		VBox panel = new VBox();
		panel.setStyle("-fx-background-color: #afafaf;");
		panel.setOnMouseClicked(e -> {
			if(!selected.equals(panel)){
				selected.setStyle("-fx-background-color: #afafaf;");
				selected = panel;
				selected.setStyle("-fx-background-color: #b38808;");
			}
			DegreePlannerUI.getRightPanel().updateInfoBox(searchDisplay.get(i));
		});
		
		panel.setPrefSize(450, 15);
		panel.setPadding(new Insets(15));
		//panel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		panel.autosize();
		panel.getChildren().addAll(name);
		return panel;
	}
	
	//Add another param for parent
	private HBox getRightCoursePane(int i){
		HBox pane = new HBox(10);
		pane.setOnMouseClicked(e -> {
			DegreePlannerUI.getRightPanel().updateInfoBox(searchDisplay.get(i));
		});
		pane.setPadding(new Insets(15));
		pane.setAlignment(Pos.CENTER_LEFT);
		Text addCourse = new Text("Add Course: ");
		addCourse.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
		Button addFall = new Button("Fall");
		addFall.setOnMouseClicked(e -> {
			if(searchDisplay.get(i).getCredits() == -1){
				//DegreePlannerUI.getLeftPanel().addCourse();
				//Call Constructor new Course(int category,String courseID, String courseName, int credits, String description); 
				System.out.println("ADDING BYO COURSE");
			}else{
				DegreePlannerUI.getLeftPanel().addCourse(DegreePlannerUI.getLeftPanel().getSelectedYear(), 0, searchDisplay.get(i));
			}
		});
		Button addSpring = new Button("Spring");
		addSpring.setOnMouseClicked(e -> {
			if(searchDisplay.get(i).getCredits() == -1){
				//DegreePlannerUI.getLeftPanel().addCourse();
				//Call Constructor new Course(int category,String courseID, String courseName, int credits, String description); 
				System.out.println("ADDING BYO COURSE");
			}else{
				DegreePlannerUI.getLeftPanel().addCourse(DegreePlannerUI.getLeftPanel().getSelectedYear(), 1, searchDisplay.get(i));
			}
		});
		Button addSummer = new Button("Summer");
		addSummer.setOnMouseClicked(e -> {
			if(searchDisplay.get(i).getCredits() == -1){
				//DegreePlannerUI.getLeftPanel().addCourse();
				//Call Constructor new Course(int category,String courseID, String courseName, int credits, String description); 
				System.out.println("ADDING BYO COURSE");
			}else{
				DegreePlannerUI.getLeftPanel().addCourse(DegreePlannerUI.getLeftPanel().getSelectedYear(), 2, searchDisplay.get(i));
			}
		});
		pane.getChildren().addAll(addCourse, addFall, addSpring, addSummer);
		return pane;
	}
}