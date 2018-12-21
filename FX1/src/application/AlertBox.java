package application;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
//import javax.geometry;

public class AlertBox {
	public static void display(String title,String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		Label label1 = new Label(message);
		Button button = new Button("Close");
		button.setOnAction(e -> window.close());
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label1,button);
		layout.setAlignment(Pos.BASELINE_CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
	}
}
