import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LeftPanel extends Application {

	private BorderPane borderPane = new BorderPane();
	private HBox top = new HBox();
	private HBox bottom = new HBox();
	private ScrollPane scrollPane = new ScrollPane();
	private VBox panel = new VBox();
	private ArrayList<ObservableList<Course>> CourseData = new ArrayList<ObservableList<Course>>();
	private ArrayList<TableView<Course>> tables = new ArrayList<TableView<Course>>();
	private ComboBox<String> yearComboBox = new ComboBox<String>();
	private Label semesterLabels[] = new Label[] { new Label("Fall"), new Label("Spring"), new Label("Summer") };

	public LeftPanel() {
		top.setMaxSize(450, 30);
		top.setStyle("-fx-background-color: #696969;");
		top.setPadding(new Insets(10));
		top.setSpacing(8);
		bottom.setMaxSize(450, 30);
		bottom.setStyle("-fx-background-color: #696969;");
		bottom.setPadding(new Insets(10));
		bottom.setSpacing(8);
		borderPane.setTop(top);
		borderPane.setBottom(bottom);
		scrollPane.setPrefSize(450, 1000);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.autosize();
		scrollPane.setContent(panel);
		borderPane.setCenter(scrollPane);
		borderPane.setMaxSize(450, Double.MAX_VALUE);

		//LocalDateTime now = LocalDateTime.now();
		//String year = Integer.toString(now.getYear() - 1) + "-" + Integer.toString(now.getYear());// default
	    
            // year
		//addYear(year);
		setTables();
		setPanel();
		yearComboBox.setDisable(true);
		//updateTables();
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}

	public BorderPane getPanel() {
		return borderPane;
	}

	public void addCourse(String sem, Course course) throws IndexOutOfBoundsException {
        if(currentYear() == null){
			AlertBox.display("Add Year", "You must add a year first!");
			return;
		}
		DataManager.addCourse(currentYear(),sem,course);
	    updateTables();
    }

	private void setPanel() {
		panel.setStyle("-fx-background-color: #367044;");
		panel.setPadding(new Insets(10));
		panel.setSpacing(8);
		panel.setMinWidth(440);
		panel.setMaxHeight(975);
		panel.autosize();
		yearComboBox.setPrefSize(125, 20);
		yearComboBox.getSelectionModel().selectedIndexProperty()
				.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
					yearComboBox.setPromptText(
							yearComboBox.getSelectionModel().selectedIndexProperty().getValue().toString());
					updateTables();
				});
		TextField yearTextField = new TextField();
		Button addYearButton = new Button("Add Year");
		Button removeButton = new Button("Remove Year");
		addYearButton.setOnMouseClicked(e -> {
			addYear(yearTextField.getText());
			updateTables();
		});
		removeButton.setOnMouseClicked(e -> {
			removeYear(yearTextField.getText(),yearTextField);
		});
		yearTextField.setPromptText("####-####");
		yearTextField.setMaxSize(100, 10);
		top.getChildren().addAll(yearComboBox, yearTextField, addYearButton, removeButton);
		TextField courseTextField = new TextField();
		courseTextField.setPromptText("Ex: BZ110");
		Button removeCourseButton = new Button("Remove Course");
		removeCourseButton.setOnMouseClicked(e -> {
			removeCourse(courseTextField.getText());
		});
		bottom.getChildren().addAll(courseTextField, removeCourseButton);
		for (int i = 0; i < 3; i++) {
			semesterLabels[i].setFont(Font.font("Verdana", FontWeight.BOLD, 14));
			semesterLabels[i].setTextFill(Color.WHITE);
			panel.getChildren().add(semesterLabels[i]);
			panel.getChildren().add(tables.get(i));
		}
	}

	private void setTables() {
		for (int i = 0; i < 3; i++) {
			TableView<Course> table = new TableView<Course>();
			table.getSelectionModel().selectedIndexProperty()
			.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
				//TODO: Update the remove box based on selection
				DegreePlannerUI.getRightPanel().updateInfoBox(table.getItems().get(table.getSelectionModel().selectedIndexProperty().get()));
				
			});
			table.setStyle("-fx-background-color: #a9a9a9;");
			table.setMaxSize(420, 300);
			TableColumn<Course, String> classCol = new TableColumn<>("Class");
			TableColumn<Course, String> creditCol = new TableColumn<>("Credits");
			creditCol.setStyle("-fx-alignment: CENTER;");
			classCol.prefWidthProperty().bind(table.widthProperty().multiply(0.75));
			classCol.setCellValueFactory(new PropertyValueFactory<Course, String>("courseID"));
			creditCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
			creditCol.setCellValueFactory(new PropertyValueFactory<Course, String>("credits"));
			table.getColumns().addAll(classCol, creditCol);
                
			tables.add(table);
			
			
		}
	}

	public void updateTables() {
		if(currentYear() == null){
			return;
		}
        String year = currentYear();
        ArrayList<Course> fall = DataManager.getSem(year,"fall");
        ArrayList<Course> spring = DataManager.getSem(year,"spring");
        ArrayList<Course> summer = DataManager.getSem(year,"summer");
        
        updateTable(fall,0);
        updateTable(spring,1);
        updateTable(summer,2);
        
		if(!DataManager.isYearsEmpty()){
			yearComboBox.setDisable(false);
		}
        //update QuickAccess information
        DegreePlannerUI.getCenterPanel().updateQuickProgress();
	}

    private void updateTable(ArrayList<Course> sem, int i){
        ObservableList<Course> classes = FXCollections.observableArrayList(sem);
        int total = 0;
        for(Course c : sem){
            total += c.getCredits();
        }
		tables.get(i).getColumns().get(1).textProperty().set("Credits (" + total + ")");
		tables.get(i).setItems(classes);
    }

	public void addYear(String year) {	
		if(!validYear(year)){
			AlertBox.display("Add Year", "The year format is: 'XXXX-XXXX'");
		    return;
        }
		yearComboBox.setDisable(false);

        DataManager.addYear(year);
        ObservableList<String> list = FXCollections.observableArrayList(DataManager.getYears());
		yearComboBox.setItems(list);
		yearComboBox.getSelectionModel().selectLast();
	}

	public void initYear(){
        ObservableList<String> list = FXCollections.observableArrayList(DataManager.getYears());
		yearComboBox.setItems(list);
		yearComboBox.getSelectionModel().selectLast();
	}

	private boolean validYear(String year){
		return year.matches("^\\d{4}-\\d{4}$");
	}

	private void removeYear(String year, TextField ytf) {
        if(year.equals("")){
			if(currentYear() == null){
				return;
			}
			ytf.setText(currentYear());
			return;
		}else if(!validYear(year)){
			AlertBox.display("Remove Year", "Invalid year formatt should be ####-####\n");
            return;
        }

        for (int i = 0; i < yearComboBox.getItems().size(); i++) 
            if (yearComboBox.getItems().get(i).equals(year))
                yearComboBox.getItems().remove(i);
        DataManager.deleteYear(year);
        if(DataManager.isYearsEmpty()){
			yearComboBox.setDisable(true);
		}
		ytf.setText("");
		updateTables();
        yearComboBox.getSelectionModel().selectLast();
    }

	private void removeCourse(String course) {
		boolean found = false;
		if (course.equals("")) {
			AlertBox.display("Remove Course", "The course format to remove is: 'BZ110'.");
		}
	    
		if(currentYear() == null){
			AlertBox.display("Add Year", "You must add a year first!");
			return;
		}
        found = DataManager.deleteCourse(currentYear(),course);

		if(found){
			updateTables();
		}else{
			AlertBox.display("Remove Course", "The course '" + course + "' was not found in selected year.");
		}
	}
	
	private String currentYear(){
		try{
			getYears().get(getSelectedYear());
		}catch(Exception e){
			return null;
		}
		
		return getYears().get(getSelectedYear());
	}

	public int getSelectedYear(){
		return yearComboBox.getSelectionModel().getSelectedIndex();
	}
	
	public ObservableList<String> getYears(){
        return yearComboBox.getItems();
	}
	
	public ComboBox<String> getYearsComboBox(){
        return yearComboBox;
	}
}
