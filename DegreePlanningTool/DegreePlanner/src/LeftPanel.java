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

		LocalDateTime now = LocalDateTime.now();
		String year = Integer.toString(now.getYear() - 1) + "-" + Integer.toString(now.getYear());// default
																									// year
		addYear(year);
		setTables(0);
		setPanel();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

	}

	public BorderPane getPanel() {
		return borderPane;
	}

	public void addCourse(int year, int semester, Course course) throws IndexOutOfBoundsException {
		if ((CourseData.size() < year || CourseData.isEmpty()) || (semester < 0 || semester > 2))
			throw new IndexOutOfBoundsException();
		CourseData.get(year * 3 + semester).add(course);
		// Update tables
		updateTables(year);
	}

	public Course removeCourse(int year, int semester, String name) throws IndexOutOfBoundsException {
		if ((CourseData.size() < year || CourseData.isEmpty()) || (semester < 0 || semester > 2))
			throw new IndexOutOfBoundsException();
		for (int i = 0; i < CourseData.get(year * 3 + semester).size(); ++i) {
			if (CourseData.get(year * 3 + semester).get(i).getCourseName().equals(name)) {
				return CourseData.get(year * 3 + semester).remove(i);
			}
		}
		return null;
	}

	private void setPanel() {
		panel.setStyle("-fx-background-color: #367044;");
		panel.setPadding(new Insets(10));
		panel.setSpacing(8);
		panel.setMinWidth(440);
		panel.setMaxHeight(975);
		panel.autosize();
		yearComboBox.setPrefSize(105, 20);
		yearComboBox.getSelectionModel().selectedIndexProperty()
				.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
					yearComboBox.setPromptText(
							yearComboBox.getSelectionModel().selectedIndexProperty().getValue().toString());
					updateTables(yearComboBox.getSelectionModel().selectedIndexProperty().getValue());
				});
		TextField yearTextField = new TextField();
		Button addYearButton = new Button("Add Year");
		Button removeButton = new Button("Remove Year");
		addYearButton.setOnMouseClicked(e -> {
			addYear(yearTextField.getText());
		});
		removeButton.setOnMouseClicked(e -> {
			removeYear(yearTextField.getText());
		});
		yearTextField.setPromptText("Ex: XXXX-XXXX");
		yearTextField.setMaxSize(150, 10);
		top.getChildren().addAll(yearComboBox, yearTextField, addYearButton, removeButton);
		TextField courseTextField = new TextField();
		courseTextField.setPromptText("Ex: BZ110");
		Button removeCourseButton = new Button("Remove Course");
		removeCourseButton.setOnMouseClicked(e -> {
			removeCourse(courseTextField.getText(),
					yearComboBox.getSelectionModel().selectedIndexProperty().getValue());
		});
		bottom.getChildren().addAll(courseTextField, removeCourseButton);
		for (int i = 0; i < 3; i++) {
			semesterLabels[i].setFont(Font.font("Verdana", FontWeight.BOLD, 14));
			semesterLabels[i].setTextFill(Color.WHITE);
			panel.getChildren().add(semesterLabels[i]);
			panel.getChildren().add(tables.get(i));
		}
	}

	private void setTables(int year) {
		for (int i = 0; i < 3; i++) {
			TableView<Course> table = new TableView<Course>();
			
			table.getSelectionModel().selectedIndexProperty()
			.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
				//TODO: Update the remove box based on selection
				DegreePlannerUI.getRightPanel().updateInfoBox(table.getItems().get(table.getSelectionModel().selectedIndexProperty().get()).getCourseName(), "Temp");
				//table.getItems().get(table.getSelectionModel().selectedIndexProperty().get()).getName();
			});
			table.setStyle("-fx-background-color: #a9a9a9;");
			table.setMaxSize(420, 300);
			TableColumn<Course, String> classCol = new TableColumn<>("Class");
			TableColumn<Course, String> creditCol = new TableColumn<>("Credits");
			creditCol.setStyle("-fx-alignment: CENTER;");
			classCol.prefWidthProperty().bind(table.widthProperty().multiply(0.75));
			classCol.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
			creditCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
			creditCol.setCellValueFactory(new PropertyValueFactory<Course, String>("credit"));
			table.getColumns().addAll(classCol, creditCol);
			ObservableList<Course> classes = FXCollections.observableArrayList();
			Collections.copy(classes, CourseData.get(year * 3 + i));
			table.setItems(classes);
			tables.add(table);
		}
	}

	private void updateTables(int year) {
		for (int i = 0; i < 3; i++) {
			ObservableList<Course> classes = FXCollections.observableArrayList(CourseData.get(year * 3 + i));
			int total = 0;
			for (int j = 0; j < CourseData.get(year * 3 + i).size(); j++) {
				total += CourseData.get(year * 3 + i).get(j).getCredits();
			}
			tables.get(i).getColumns().get(1).textProperty().set("Credit(" + total + ")");
			tables.get(i).setItems(classes);
		}
	}

	public void addYear(String year) {
		boolean goodYear = true;
		for(int i = 0; i < year.length(); i++){
			if(i != 4 && !Character.isDigit(year.charAt(i))){
				AlertBox.display("Add Year", "The year format is: 'XXXX-XXXX'.");
				goodYear = false;
			}
			else if(i == 4 && year.charAt(i) != '-'){
				AlertBox.display("Add Year", "The year format is: 'XXXX-XXXX'.");
				goodYear = false;
			}
		}
			
		if (year.equals("")) {
			AlertBox.display("Add Year", "The year format is: 'XXXX-XXXX'.");
		}
		else if(goodYear){
			yearComboBox.getItems().add(year);
			yearComboBox.setPromptText(year);
			for (int i = 0; i < 3; i++) {
				ObservableList<Course> courses = FXCollections.observableArrayList();
				CourseData.add(courses);
			}
			yearComboBox.getSelectionModel().selectLast();
		}
	}

	private void removeYear(String year) {
		boolean goodYear = true;
		for(int i = 0; i < year.length(); i++){
			if(i != 4 && !Character.isDigit(year.charAt(i))){
				AlertBox.display("Remove Year", "The year format is: 'XXXX-XXXX'.");
				goodYear = false;
			}
			else if(i == 4 && year.charAt(i) != '-'){
				AlertBox.display("Remove Year", "The year format is: 'XXXX-XXXX'.");
				goodYear = false;
			}
		}
		if(year.equals("")){
			AlertBox.display("Remove Year", "The year format is: 'XXXX-XXXX'.");
		}
		else if (yearComboBox.getItems().size() > 1) {
			for (int i = 0; i < yearComboBox.getItems().size(); i++) {
				if (yearComboBox.getItems().get(i).equals(year)) {
					yearComboBox.getItems().remove(i);
					for (int j = 0; j < 3; j++) {
						CourseData.remove(i * 3);
					}
				}

			}
		}
		else{
			AlertBox.display("Remove Year", "You must have at least one year.");
		}
	}

	private void removeCourse(String course, int year) {
		boolean found = false;
		if (course.equals("")) {
			AlertBox.display("Remove Course", "The course format to remove is: 'BZ110'.");
		}
		else{
			for (int i = year * 3; i < ((year * 3) + 3); i++) {
				for (int j = 0; j < CourseData.get(i).size(); j++) {
					if (CourseData.get(i).get(j).getCourseName().equals(course)) {
						CourseData.get(i).remove(j);
						found = true;
					}
				}
			}
			if(found)
				updateTables(year);
			else
				AlertBox.display("Remove Course", "The course '" + course + "' was not found in selected year.");
		}
	}
}
