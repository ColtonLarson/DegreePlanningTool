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
	private ArrayList<Category> progressDisplay = new ArrayList<Category>();
	private ArrayList<VBox> selectionableVBoxs = new ArrayList<VBox>();
	private ArrayList<VBox> selectableCatVBoxs = new ArrayList<VBox>();
	private VBox selected = new VBox();
	private VBox selectedCat = new VBox();
	private ArrayList<Category> copy;
	private ArrayList<Category> catList;
	private ComboBox<String> yearComboBox;
	
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
            if(search.getText().length() != 0){//EMPTY TextField
                switch(searchComboBox.getSelectionModel().selectedIndexProperty().getValue()){
                case 0:
                    searchDisplay = DataManager.searchByName(search.getText(),DataManager.getCategoryIDByName(copy.get(categoryComboBox.getSelectionModel().selectedIndexProperty().getValue()).getCategoryName()), creditComboBox.getSelectionModel().selectedIndexProperty().getValue());
                    UpdateCenterPanel();
                    break;
                case 1:
                    searchDisplay = DataManager.searchByTitle(search.getText(), DataManager.getCategoryIDByName(copy.get(categoryComboBox.getSelectionModel().selectedIndexProperty().getValue()).getCategoryName()), creditComboBox.getSelectionModel().selectedIndexProperty().getValue());
                    UpdateCenterPanel();
                    break;
                default:
                    //TODO: Not found
                    System.out.println(search.getText());
                    break;
                };
			}
		});
		Button progressButton = new Button("  Progress  ");
		progressButton.setOnMouseClicked(e -> {
            changeToProgress();
		});
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
		
		ObservableList<String> searchCategory = FXCollections.observableArrayList();
		copy = new ArrayList<Category>(DataManager.getCategories());
		copy.add(0, new Category("Any", -1, -1, ""));
		for(int i = 0; i < copy.size(); i++){
            searchCategory.add(copy.get(i).getCategoryName());
		}
		categoryComboBox = new ComboBox(searchCategory);
		categoryComboBox.setPromptText(searchCategory.get(0));
		categoryComboBox.setValue(searchCategory.get(0));
		
		ObservableList<String> searchCredit = FXCollections.observableArrayList();
		for(int i = 0; i <= 18; i++){
            if(i == 0)
                searchCredit.add("Any");
            else
                searchCredit.add(Integer.toString(i));
		}
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
		selectionableVBoxs.clear();
		for(int i = 0; i < searchDisplay.size(); i++){
			BorderPane bPane = new BorderPane();
			bPane.setPrefSize(400, 100);
			bPane.setBorder(new Border(new BorderStroke(Color.BLACK, 
		            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			bPane.setTop(getTopCoursePane(i));
			bPane.setCenter(getBottomCoursePane(i));
			searchDisplayPanes.getChildren().add(bPane);
		}
		scrollPane.setContent(searchDisplayPanes);
		
	}
	
	private VBox getTopCoursePane(int i){
		Text name = new Text(searchDisplay.get(i).getCourseID() + "\t" + searchDisplay.get(i).getCourseName());
		name.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
		VBox panel = new VBox();
		selectionableVBoxs.add(panel);
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
	
	private HBox getBottomCoursePane(int i){
		HBox pane = new HBox(10);
		pane.setPrefSize(450,25);
		pane.setOnMouseClicked(e -> {
			DegreePlannerUI.getRightPanel().updateInfoBox(searchDisplay.get(i));
			if(!selected.equals(selectionableVBoxs.get(i))){
				selected.setStyle("-fx-background-color: #afafaf;");
				selected = selectionableVBoxs.get(i);
				selected.setStyle("-fx-background-color: #b38808;");
			}
		});
		pane.setPadding(new Insets(15));
		pane.setAlignment(Pos.CENTER_LEFT);
		Text addCourse = new Text("Add Course: ");
		addCourse.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
		Button addFall = new Button("Fall");
		addFall.setOnMouseClicked(e -> {
			if(searchDisplay.get(i).getCredits() == -1){
				Course toAdd = new Course(AlertBox.makeCourse());
				if(toAdd != null){
                    DegreePlannerUI.getLeftPanel().addCourse("fall",toAdd);
				}
			}else{
				DegreePlannerUI.getLeftPanel().addCourse("fall", searchDisplay.get(i));
			}
		});
		Button addSpring = new Button("Spring");
		addSpring.setOnMouseClicked(e -> {
			if(searchDisplay.get(i).getCredits() == -1){
				Course toAdd = new Course(AlertBox.makeCourse());
				if(toAdd != null){
                    DegreePlannerUI.getLeftPanel().addCourse("spring",toAdd);
				}
			}else{
				DegreePlannerUI.getLeftPanel().addCourse("spring", searchDisplay.get(i));
			}
		});
		Button addSummer = new Button("Summer");
		addSummer.setOnMouseClicked(e -> {
			if(searchDisplay.get(i).getCredits() == -1){
				Course toAdd = new Course(AlertBox.makeCourse());
				if(toAdd != null){
                    DegreePlannerUI.getLeftPanel().addCourse("summer",toAdd);
				}
			}else{
				DegreePlannerUI.getLeftPanel().addCourse("summer", searchDisplay.get(i));
			}
		});
		pane.getChildren().addAll(addCourse, addFall, addSpring, addSummer);
		return pane;
	}

	private void changeToProgress(){
        //Should have a top HBox with Search Button and ComboBox
        //Should have a Center ScrollPane
        //Maybe have a At a Glance HBox(like search)
        HBox topProgress = new HBox(10);
        topProgress.setAlignment(Pos.CENTER);
        topProgress.setPrefSize(450, 50);
        topProgress.setSpacing(8);
        topProgress.setStyle("-fx-background-color: #696969;");
        topProgress.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Text progressLabel = new Text("Progress");
        progressLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        //FIXME:
        yearComboBox = new ComboBox<String>(DegreePlannerUI.getLeftPanel().getYears());
        Button search = new Button("  Search  ");
        search.setOnMouseClicked(e -> {
            centerPanel.setTop(top);
            centerPanel.setCenter(scrollPane);
            centerPanel.setBottom(bottom);
        });
        topProgress.getChildren().addAll(progressLabel, yearComboBox, search);
        ScrollPane scrollProgress = new ScrollPane();
        VBox catProgress = new VBox();
        catProgress.setPrefSize(1200, 200);
        catList = new ArrayList<Category>(DataManager.getCategories());
        selectableCatVBoxs.clear();
        for(int i = 0; i < catList.size(); i++){
            BorderPane bPane = new BorderPane();
			bPane.setPrefSize(400, 100);
			bPane.setBorder(new Border(new BorderStroke(Color.BLACK, 
		            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			bPane.setTop(getTopCategoryPane(i));
			bPane.setCenter(getCenterCategoryPane(i));
			bPane.setBottom(getBottomCategoryPane(i));
			catProgress.getChildren().add(bPane);
        }
        scrollProgress.setContent(catProgress);
        centerPanel.setTop(topProgress);
        centerPanel.setCenter(scrollProgress);
	}
	
	private VBox getTopCategoryPane(int i){
        VBox catTop = new VBox(10);
        selectableCatVBoxs.add(catTop);
        catTop.setPrefSize(450, 50);
        catTop.setSpacing(8);
        catTop.setAlignment(Pos.CENTER_LEFT);
        catTop.setPadding(new Insets(15));
        catTop.setStyle("-fx-background-color: #afafaf;");
        catTop.setOnMouseClicked(e -> {
            DegreePlannerUI.getRightPanel().updateInfoBox(catList.get(i));
			if(!selectedCat.equals(catTop)){
				selectedCat.setStyle("-fx-background-color: #afafaf;");
				selectedCat = catTop;
				selectedCat.setStyle("-fx-background-color: #b38808;");
			}
			//FIXME: overload updateInfoBox for categories
			//DegreePlannerUI.getRightPanel().updateInfoBox(progressDisplay.get(i));
		});
        
        Text catName = new Text(catList.get(i).getCategoryName());
        catName.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
        catTop.getChildren().addAll(catName);
        return catTop;
	}
	
	private HBox getCenterCategoryPane(int i){
        HBox catCenter = new HBox(10);
        catCenter.setOnMouseClicked(e -> {
			DegreePlannerUI.getRightPanel().updateInfoBox(catList.get(i));
			if(!selectedCat.equals(selectableCatVBoxs.get(i))){
				selectedCat.setStyle("-fx-background-color: #afafaf;");
				selectedCat = selectableCatVBoxs.get(i);
				selectedCat.setStyle("-fx-background-color: #b38808;");
			}
		});
        catCenter.setPrefSize(450, 50);
        catCenter.setSpacing(8);
        catCenter.setAlignment(Pos.CENTER_LEFT);
        catCenter.setPadding(new Insets(15));
        Text creditTitle = new Text("Completed Credits: " + ProgressManager.getCategoryCredits(catList.get(i), yearComboBox.getSelectionModel().selectedIndexProperty().getValue().toString()) + "/" + catList.get(i).getCreditsRequired());
        
        catCenter.getChildren().addAll(creditTitle);
        return catCenter;
	}
	
	private HBox getBottomCategoryPane(int i){
        HBox catBottom = new HBox(10);
        catBottom.setOnMouseClicked(e -> {
			DegreePlannerUI.getRightPanel().updateInfoBox(catList.get(i));
			if(!selectedCat.equals(selectableCatVBoxs.get(i))){
				selectedCat.setStyle("-fx-background-color: #afafaf;");
				selectedCat = selectableCatVBoxs.get(i);
				selectedCat.setStyle("-fx-background-color: #b38808;");
			}
		});
        catBottom.setPrefSize(450, 50);
        catBottom.setSpacing(8);
        catBottom.setAlignment(Pos.CENTER_LEFT);
        catBottom.setPadding(new Insets(15));
        Text courseTitle = new Text("Completed Courses: ");
        
        catBottom.getChildren().addAll(courseTitle);
        return catBottom;
	}
}

    
