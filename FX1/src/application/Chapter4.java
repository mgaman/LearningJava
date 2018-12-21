package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Chapter4 extends Application /*implements EventHandler<ActionEvent>*/{
	Stage window;
	Scene scene1,scene2;
	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		Label label1 = new Label("Welcome to scene1");
		Button button1 = new Button("Go to scene2");
		button1.setOnAction(e -> window.setScene(scene2));
		// layout 1 in vertical column
		VBox layout1 = new VBox(20);
		layout1.getChildren().addAll(label1,button1);
		scene1 = new Scene(layout1,200,200);
		
		Button button2 = new Button("Back to scene1");
		button2.setOnAction(e -> window.setScene(scene1));
		
		// layout 2 simpler
		StackPane layout2 = new StackPane();
		layout2.getChildren().add(button2);
		scene2 = new Scene(layout2,600,300);
		
		// set initial scene
		window.setScene(scene1);
		window.setTitle("Title here");
		window.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
