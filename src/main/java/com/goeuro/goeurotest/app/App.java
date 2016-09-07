/*
 * GoEuroTest By Hesham
 * Input: city name as argument
 * Output: CSV file with the result 
 * Description: This app simply query an API url with the city name as input variable 
 * and returns JSON data describes the city which was mentioned, then saving this data into CSV file 
 * 
 * 
 */
package com.goeuro.goeurotest.app;

import com.goeuro.goeurotest.defines.Defines;
import com.goeuro.goeurotest.service.Services;
import com.goeuro.goeurotest.service.Validation;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            String cityName = args[0].trim();
            Validation.validateInput(cityName);
            String url = Defines.API_URL.replace("$CITY_NAME$", cityName);
            System.out.println("Starting serving request with the following url " + url);
            Services service = new Services();
            String jsonResponse = service.getJsonResponse(url);
            List recordsList = service.parseJsonResponse(jsonResponse);
            service.writeCSV(recordsList);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
