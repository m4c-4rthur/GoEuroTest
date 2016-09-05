/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goeuro.goeurotest.defines;

/**
 *
 * @author hesham.ibrahim
 */
public class Defines {
    public static final String API_URL = "http://api.goeuro.com/api/v2/position/suggest/en/$CITY_NAME$";
    public static final String NEW_LINE_SEPARATOR = "\n";
    public static final String[] FILE_HEADER = {"_id","name", "type", "longitude", "latitude"};
    public static final String FILE_NAME = "City_Result.csv";
    public static final String logPropertiesFileName = "log4j.properties";
    
}
