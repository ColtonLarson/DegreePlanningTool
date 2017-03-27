import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CenterPanel extends Application{
	
	private BorderPane centerPanel = new BorderPane();
	private HBox top = new HBox();
	private ScrollPane scrollPane = new ScrollPane();
	private HBox bottom = new HBox();
	private ArrayList<Label> quickProgress = new ArrayList<Label>();
	public CenterPanel(){
		centerPanel.setMaxSize(1600, Double.MAX_VALUE);
		setTopPanel();
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
		top.setStyle("-fx-background-color: #696969;");
		top.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		top.setPrefSize(450, 60);
		top.setAlignment(Pos.CENTER_LEFT);
		top.setPadding(new Insets(10));
		top.setSpacing(8);
		TextField search = new TextField();
		search.setPromptText("'Name', 'Course Name', or 'Category'");
		search.setPrefSize(300, 20);
		Button searchButton = new Button("Search");
		top.getChildren().addAll(searchButton, search);
	}
	
	private void setCenterPanel(){
		scrollPane.setPrefSize(450, 1600);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
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
}
