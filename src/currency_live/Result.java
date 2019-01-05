package currency_live;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.*;

public class Result {
	
	/*
	 * Description: Once the user presses "Enter" button the secondary GUI is called containing the results. This GUI displays the results to the user.
	 * With the appropriate string associated with the action.
	 * 
	 */
	public static void display(String[] result) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Result");
		window.setMinWidth(300);
		window.setMinHeight(220);
		
		Label label1 = new Label();
		Label label2 = new Label();
		
		label1.setText(result[0]);
		label2.setText(result[1]);
		
		Button close_b = new Button("Close/Convert Again");
		close_b.setOnAction(e -> window.close());	//"Once Close/Convert Again" button is pressed the lambda expression closes the window.
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label1,label2,close_b);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

		
	}
	
	
}
