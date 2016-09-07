/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goeuro.goeurotest.service;

import com.goeuro.goeurotest.defines.Defines;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author hesham.ibrahim
 */
public class Services {

    /**
     * *
     * Query the input URL and return the result as a String
     *
     * @param url
     * @return jsonResponse
     * @throws Exception
     */
    public String getJsonResponse(String url) throws Exception {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String jsonResponse = "";
        String responseLine = "";
        try {
            URL servleturl = new URL(url);
            HttpURLConnection servletconnection = (HttpURLConnection) servleturl.openConnection();
            servletconnection.setRequestMethod("GET");
            servletconnection.setRequestProperty("Content-Type", "application/json");
            inputStream = servletconnection.getInputStream();
            InputStreamReader bin = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(bin);
            while ((responseLine = bufferedReader.readLine()) != null) {
                jsonResponse = jsonResponse + responseLine;
            }
            if (jsonResponse == null || jsonResponse == "") {
                
                throw new Exception("Json response is empty, there is no data to write in file");
            }
        } catch (MalformedURLException ex) {
            throw new Exception("MalformedURLException exception occured while trying to contect the API " + ex.getMessage());
        } catch (IOException ex) {
            throw new Exception("IOException exception occured while trying to contect the API " + ex.getMessage());
        }

        return jsonResponse;
    }

    /**
     * *
     * Parse the String as json array and extract the data to List of lists
     *
     * @param jsonResponse
     * @return mainlist
     */
    public List parseJsonResponse(String jsonResponse) {
        JSONArray jsonarray = null;
        List mainlist = null;
        List<String> list = null;
        list = new ArrayList<>();
        mainlist = new ArrayList<>();
        jsonarray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            list.add(String.valueOf(jsonobject.getInt("_id")));
            list.add(jsonobject.getString("name"));
            list.add(jsonobject.getString("type"));
            JSONObject jsonPositiont = jsonobject.getJSONObject("geo_position");
            if (jsonPositiont.has("longitude") && jsonPositiont.has("latitude")) {
                list.add(String.valueOf(jsonPositiont.getDouble("longitude")));
                list.add(String.valueOf(jsonPositiont.getDouble("latitude")));
            } else {
                list.add("N/A");
                list.add("N/A");
            }
            mainlist.add(list);
            list = new ArrayList<>();
        }
        return mainlist;
    }

    /**
     * Write CSV file using list of records and pre defined static header
     *
     * @param recordsList
     * @throws Exception
     */
    public void writeCSV(List recordsList) throws Exception {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        try {
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(Defines.NEW_LINE_SEPARATOR);
            fileWriter = new FileWriter(Defines.FILE_NAME);
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            csvFilePrinter.printRecord(Defines.FILE_HEADER);
            for (Object recordList : recordsList) {
                csvFilePrinter.printRecords(recordList);
            }
            fileWriter.flush();
            fileWriter.close();
            csvFilePrinter.close();
        } catch (IOException ex) {
            throw new Exception("IOException occured while writing CSV file " + ex.getMessage());
        }
    }

}
