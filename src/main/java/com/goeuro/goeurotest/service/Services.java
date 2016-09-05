/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goeuro.goeurotest.service;

import com.goeuro.goeurotest.app.App;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author hesham.ibrahim
 */
public class Services {

    public String getJsonResponse(String url) {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String urlResponse = "";
        try {
            URL servleturl = new URL(url);
            HttpURLConnection servletconnection = (HttpURLConnection) servleturl.openConnection();
            servletconnection.setRequestMethod("GET");
            servletconnection.setRequestProperty("Content-Type", "application/json");
            inputStream = servletconnection.getInputStream();
            InputStreamReader bin = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(bin);
        } catch (MalformedURLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        String responseLine = "";
        urlResponse = "";

        try {
            while ((responseLine = bufferedReader.readLine()) != null) {
                urlResponse = urlResponse + responseLine;
            }
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        return urlResponse;
    }

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
            list.add(String.valueOf(jsonPositiont.getDouble("longitude")));
            list.add(String.valueOf(jsonPositiont.getDouble("latitude")));
            mainlist.add(list);
            list = new ArrayList<>();
        }
        return mainlist;
    }

    public void writeCSV(List recordsList) {
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
            Logger.getLogger(Services.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
