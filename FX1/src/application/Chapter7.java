package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Chapter7 extends Application /*implements EventHandler<ActionEvent>*/{
	Button button;
	int count = 0;
	Stage window;
	@Override
	public void start(Stage primaryStage) {
		try {
			window = primaryStage;
			window.setTitle("First fx");
			window.setOnCloseRequest(e -> {
				e.consume();
				closeProgram();
			});
			button = new Button();
			button.setText("Click Me");
			button.setOnAction(e -> {
				closeProgram();
			});
			StackPane layout = new StackPane();
			layout.getChildren().add(button);
			Scene scene = new Scene(layout,350,350);
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	void closeProgram()
	{
		boolean result = ConfirmBox.display("Alert title","Do you want to exit");
		if (result)
			window.close();
	}
}
