package currency_live;

import java.io.IOException;
import java.text.DecimalFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;


public class Converter {
	
	/*
	 * Description: The scraper function creates the web environment for scraping. The scraper will grab the necessary currency conversion rates and currency names.
	 * then it will return an object containing such data.
	 * 
	 * Parameters: 
	 * 		None
	 * 
	 * Returns:
	 * 		Scraped: An object that contains the currency and names
	 */
	public Object[] Scraper() {
		try {
			// Creates the web environment
			Document doc = Jsoup.connect("https://www.fx-exchange.com/currency-exchange-rates-list.html").userAgent("mozilla/61.0").get();
			
			Elements main = doc.getElementsByAttributeValue("class", "pure-u-1 fx-top");
			Elements rows = main.select("tr");
			Elements row_element = rows.select("td");
			String[] names = new String[94]; //Indexes country names
			String[] usd_currency = new String[94]; 
			
			for(int i = 0; i<=(row_element.size()-1); i++) {
				
			if (i == 0) {
					names[i] = row_element.get(2).text();				// Assigns names into array
				} else if (i > 0 ) {
					if (2+10*i+1*i<=row_element.size()) {
						names[i] = row_element.get(2+10*i+i*1).text(); 
					}
				}
			
			if (i == 0) {												// Assigns USD conversion rates to array
				usd_currency[i] = row_element.get(3).text();
			} else if (i > 0) {
				if (3+10*i+1*i<=row_element.size()) {
					usd_currency[i] = row_element.get(3+10*i+i*1).text(); 
				}
			}
			
		}
		
			Object[] scraped = new Object[] {names,usd_currency}; //creates object that bundles arrays together and returns one object
			
			return (scraped);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error has occured");
			e.printStackTrace();
			return null;
		}
		
	}
	
	/*
	 * Description: Calculates the conversion rates and returns a string that is displayed to the user.
	 * 
	 * Parameters:
	 * 		String Currency: Contains the currency name desired
	 * 		String Currency: Contains the name of the currency that the user wants to convert to.
	 * 		double usr_amount: Contains the amount entered by the user.
	 * 		String[] names: Contains all the names of the currencies.
	 * 		String[] rates: Contains the rates of each country.
	 * 
	 * Returns:
	 * 		String[] results: contains a string array
	 * 
	 */
	public String[] Currency_Conv (String currency,String currency_orig,double usr_amount,String[] names, String[] rates) {
		
		int index_1, index_2;
		index_1 = index_2 = 0;
		double final_amount,rate;
		String name_f, ans_1, ans_2;
		String[] results = new String[2];
		double[] rates_d = new double[rates.length];
		DecimalFormat num_format = new DecimalFormat("#.00");
		String BASE = "USD";
		
		currency = currency.trim().toUpperCase();
		currency_orig = currency_orig.trim().toUpperCase();
		
		for (int i = 0; i <=rates.length-1; i++) {
			rates_d[i] = Double.parseDouble(rates[i]); //converts the following string of rates to double type
		}
		
		for (int i = 0; i <= names.length-1;i++) {  //Finds the index of currency
			name_f = names[i].trim().toUpperCase(); 
			
			if(name_f.equals(currency)) {
				index_2 = i;
			}
			
			if (currency_orig.equals(BASE)) {
				if (name_f.equals(currency)) {
					index_1 = i;
				}
			} else {
				if (name_f.equals(currency_orig)) {
					index_1 = i;
				}
			}
		}
		// The following if statement takes care of different cases on the currency conversion. Once conversion is done relative to the base currency a string is returned to GUI
		if (BASE.equals(currency_orig)) { 	//Conversion if base equals original currency
			final_amount = 1/rates_d[index_1] * usr_amount;
			rate = 1/rates_d[index_1];
			ans_1 = "The currency desired is " + currency + " and the current exchange rate today for " + currency_orig + " to " + currency + " is " + num_format.format(rate) + currency;
			ans_2 = "The currency conversion is "+ num_format.format(final_amount)+ currency;
			results[0] = ans_1;
			results[1] = ans_2;
		} else if (BASE.equals(currency)) { //Conversion if base equals currency desired
			final_amount = rates_d[index_1] * usr_amount;
			rate = rates_d[index_1];
			ans_1 = "The currency desired is " + currency + " and the current exchange rate today for " + currency_orig + " to " + currency + " is " + num_format.format(rate) + currency;
			ans_2 = "The currency conversion is "+ num_format.format(final_amount)+ currency;
			results[0] = ans_1;
			results[1] = ans_2;
		} else { //currency if base does not equal either original or desired
			final_amount = rates_d[index_1] * usr_amount; //converts to base currency
			final_amount = 1/rates_d[index_2] * final_amount; // converts final amount from base to desired currency
			rate = rates_d[index_1];
			rate = 1/rates_d[index_2]*rate;
			ans_1 = "The currency desired is " + currency + " and the current exchange rate today for " + currency_orig + " to " + currency + " is " + num_format.format(rate) + currency;
			ans_2 = "The currency conversion is "+ num_format.format(final_amount)+ currency;
			results[0] = ans_1;
			results[1] = ans_2;
		}
		
		return(results);
	}
	
}
