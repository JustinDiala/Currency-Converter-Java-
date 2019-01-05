package currency_live;

import java.io.IOException;
import java.text.DecimalFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;


public class Converter {
	
	public Object[] Scraper() {
		try {
			Document doc = Jsoup.connect("https://www.fx-exchange.com/currency-exchange-rates-list.html").userAgent("mozilla/61.0").get();
			
			Elements main = doc.getElementsByAttributeValue("class", "pure-u-1 fx-top");
			Elements rows = main.select("tr");
			Elements row_element = rows.select("td");
			String[] names = new String[94]; //Indexes country names, NOTE PLEASE EDIT TO ARRAYLIST FOR FUTURE AND SORT NAMES INTO ALPHABETICAL ORDER
			String[] usd_currency = new String[94]; //FIX INDEXES CORRESPONDING TO ALPHABETICAL ORDER
			
			for(int i = 0; i<=(row_element.size()-1); i++) {

				//System.out.println(row_element.get(i).text());
				
			if (i == 0) {
					names[i] = row_element.get(2).text();				// Assigns names into array ARRAYLIST WILL BE USED LATER
				} else if (i > 0 ) {
					if (2+10*i+1*i<=row_element.size()) {
						names[i] = row_element.get(2+10*i+i*1).text(); // some formula i saw lmao
					}
				}
			
			if (i == 0) {												// Assigns usd conversion rates to array, convert to ARRAYLIST FOR OPTIMIZE
				usd_currency[i] = row_element.get(3).text();
			}else if (i>0) {
				if (3+10*i+1*i<=row_element.size()) {
					usd_currency[i] = row_element.get(3+10*i+i*1).text(); // some formula i saw lmao
				}
			}
			
		}
		
			Object[] scraped = new Object[] {names,usd_currency}; //creates object that bundles arrays together and returns one object
			
			return (scraped);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
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
			/*System.out.println(rates_d[i]);*/
		}
		
		for (int i = 0; i <= names.length-1;i++) { //Finds the index of currency
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
