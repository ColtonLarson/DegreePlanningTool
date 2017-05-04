import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RightPanel extends Application{
	
	private BorderPane rightPanel = new BorderPane();
	private VBox InfoBox = new VBox();
	private VBox TreeBox = new VBox();
	private Text information = new Text();
	private Label title = new Label();
	private Text prereq = new Text();
	private Text restrictions = new Text();
	
	public RightPanel(){
		setUpPanel();
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public BorderPane getPanel(){
		return rightPanel;
	}
	
	private void setUpPanel(){
		rightPanel.setMaxSize(450, Double.MAX_VALUE);
		setUpInfoBox();
		rightPanel.setTop(InfoBox);
		//TODO: Add Tree box
		TreeBox.setStyle("-fx-background-color: #c0c0c0;");
		TreeBox.setMinWidth(450);
		TreeBox.setMinHeight(400);
		TreeBox.autosize();
		rightPanel.setCenter(TreeBox);
	}
	
	private void setUpInfoBox(){
		//first run purposes only
		InfoBox.setStyle("-fx-background-color: #367044;");
		InfoBox.setPadding(new Insets(10));
		InfoBox.setSpacing(10);
		InfoBox.setMinWidth(450);
		InfoBox.setMinHeight(400);
		InfoBox.autosize();
		title.setText("Course Information");
		title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		title.setTextFill(Color.WHITE);
		InfoBox.getChildren().add(title);
		information.setText("Information provided here...");
		information.setFill(Color.WHITE);
		InfoBox.getChildren().add(information);
		prereq.setFill(Color.WHITE);
		InfoBox.getChildren().add(prereq);
		restrictions.setFill(Color.WHITE);
		InfoBox.getChildren().add(restrictions);
	}
	
	public void updateInfoBox(Course course){
		title.setWrapText(true);
		title.setText(course.getCourseName() + " Information");
		information.setWrappingWidth(400);
		information.setText("Description: \n" + "\t" + (course.getDescription().isEmpty() ? "None" : course.getDescription()) + "\n");
		prereq.setWrappingWidth(400);
		prereq.setText("Prerequisite(s): " + course.getPrerequisite() + "\n");
		restrictions.setWrappingWidth(400);
		restrictions.setText("Restrictions: " + (course.getRestriction().isEmpty() ? "None" : course.getRestriction()));
	}
	
	public void updateInfoBox(Category category){
        title.setWrapText(true);
        title.setText(category.getCategoryName() + " Information");
        information.setWrappingWidth(400);
        information.setText("Description: \n" + "\t" + (category.getDescription().isEmpty() ? "None" : category.getDescription()) + "\n");
	}
}
