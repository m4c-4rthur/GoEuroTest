/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goeuro.goeurotest.service;

/**
 *
 * @author hesham.ibrahim
 */
public class Validation {

    public static void validateInput(String cityName) throws Exception{
        try {
            if (cityName == null || cityName == "") {
                throw new Exception("City name Wasn't provided, please provide city name as an argument");
            }
            boolean numbersValidation = cityName.matches("\\D*");

            if (!numbersValidation) {
                throw new Exception("City name Wasn't provided, please provide city name as an argument");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

    }

}
