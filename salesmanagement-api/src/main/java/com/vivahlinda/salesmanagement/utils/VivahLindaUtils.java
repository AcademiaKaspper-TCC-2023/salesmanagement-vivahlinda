package com.vivahlinda.salesmanagement.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VivahLindaUtils {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public VivahLindaUtils() {
    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"mensagem\":\"" + responseMessage + "\"}", httpStatus);
    }

    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, dateFormatter);
    }
}
