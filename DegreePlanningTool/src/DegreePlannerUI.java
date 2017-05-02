import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class DegreePlannerUI extends Application{
	
	private static LeftPanel leftPanel = new LeftPanel();
	private static RightPanel rightPanel = new RightPanel();
	private static CenterPanel centerPanel = new CenterPanel();
	
	private BorderPane borderPane = new BorderPane();
	private Stage window;
	private Scene scene;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		DataManager dm = new DataManager();
		window = primaryStage;
		window.setTitle("CS Degree Planner");
		//window.setMaximized(true);
		

		MenuBar menuBar = new MenuBar();

		Menu fileMenu = new Menu("File");
		MenuItem loadMenuItem = new MenuItem("Load");
   		MenuItem saveMenuItem = new MenuItem("Save");
    	loadMenuItem.setOnAction(actionEvent -> dm.load());
		saveMenuItem.setOnAction(actionEvent -> dm.save());	
	
		fileMenu.getItems().addAll(loadMenuItem, saveMenuItem);

		menuBar.getMenus().addAll(fileMenu);

		borderPane.setTop(menuBar);

		//LeftPanel Tests
	//	leftPanel.addYear("2017-2018");
		borderPane.setLeft(leftPanel.getPanel());
		
		//RightPanel Tests		
		borderPane.setRight(rightPanel.getPanel());
		
		//CenterPanel Tests
		borderPane.setCenter(centerPanel.getPanel());
		
		scene = new Scene(borderPane, 1600, 800);
		window.setScene(scene);
		window.show();
	}
	
	public static LeftPanel getLeftPanel(){
		return leftPanel;
	}
	
	public static RightPanel getRightPanel(){
		return rightPanel;
	}
}
