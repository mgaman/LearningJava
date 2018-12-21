package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Chapter6 extends Application /*implements EventHandler<ActionEvent>*/{
	Button button;
	int count = 0;
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("First fx");
			button = new Button();
			button.setText("Click Me");
			button.setOnAction(e -> {
				boolean result = ConfirmBox.display("Alert title","Alert message");
				System.out.println(result);
			});
			StackPane layout = new StackPane();
			layout.getChildren().add(button);
			Scene scene = new Scene(layout,350,350);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
