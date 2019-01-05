package currency_live;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Currency_Live extends Application {

	static String currency, currency_orig;
	static double amount;
	static boolean caller;
	static String[] results;
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
	/*
	 * Description: Creates the basic GUI Environment using javafx. This places the basic objects on the GUI stage, once the objects are placed the GUI is shown
	 * to the user.
	 *
	 * Parameters:
	 * 		Stage pimaryStage: Contains the basic GUI container or stage
	 * 
	 * Returns:
	 * 		None
	 */
	public void start(Stage primaryStage) throws Exception {
		
		StackPane layout = new StackPane();
		Scene scene = new Scene(layout, 200, 130);
		layout.setPadding(new Insets(10,60,10,60));
		
		primaryStage.setTitle("Currency Converter");
		
		Button b_enter = new Button();
		TextField amount1 = new TextField();
		Label arrow = new Label("->");
		ChoiceBox<String> currencies1 = new ChoiceBox<>();
		ChoiceBox<String> currencies2 = new ChoiceBox<>();
		Converter obj1 = new Converter();
		Result obj2 = new Result();
		Object[] scraped;
		String[] names;
		
		scraped = obj1.Scraper();
		names = (String[]) scraped[0];
		
		b_enter.setText("Enter");
		
		//Positions textfield and choicebox on scene
		arrow.setTranslateY(-30);
		currencies1.setTranslateX(-50);
		currencies1.setTranslateY(-30);
		currencies2.setTranslateX(50);
		currencies2.setTranslateY(-30);
		amount1.setTranslateX(-40);
		amount1.setTranslateY(10);
		b_enter.setTranslateX(50);
		b_enter.setTranslateY(40);
		
		for(int i = 0; i <= names.length-1; i++) {
			currencies1.getItems().add(names[i]);
		}
		
		for(int i = 0; i <= names.length-1; i++) {
			currencies2.getItems().add(names[i]);
		}
		
		b_enter.setOnAction(e -> {
			Conversion(currencies1,currencies2,amount1);
			if (caller == true) {
				obj2.display(results);
			}
		});
		layout.getChildren().addAll(currencies1,currencies2,amount1,b_enter,arrow);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private void Conversion(ChoiceBox<String> currencies1, ChoiceBox<String> currencies2, TextField amount1 ) {

		Converter obj1 = new Converter();
		Object[] scraped;
		String[] names,rates;
		
		currency = currencies2.getValue();
		currency_orig = currencies1.getValue();
		amount = Double.parseDouble(amount1.getText());
		
		scraped = obj1.Scraper();
		names = (String[]) scraped[0];
		rates = (String[]) scraped[1];
		
		results = (String[]) obj1.Currency_Conv(currency,currency_orig,amount, names, rates);
		
		if (results[0] != null || results[1] != null) {
			caller = true;
		}
				
		
	}
	
}
