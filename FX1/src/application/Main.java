package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Main extends Application /*implements EventHandler<ActionEvent>*/{
	Button button;
	int count = 0;
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("First fx");
			button = new Button();
			button.setText("Click Me");
			button.setOnAction(e -> button.setText("Clicked " + count++));
/*			button.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					if (event.getSource()==button)
					{
						button.setText("Clicked " + count++);
					}
				}
			});
*///			button.setOnAction(this);
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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

/*	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource()==button)
		{
			button.setText("Clicked " + count++);
		}
	}
*/
}
