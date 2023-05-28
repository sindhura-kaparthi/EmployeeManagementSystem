//package com.example.employeemanagementsystem.utility;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class CustomDateDeserializer extends JsonDeserializer<Date> {
//    private static final String DATE_FORMAT = "dd/MM/yyyy";
//
//    @Override
//    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//        String dateValue = jsonParser.getText();
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
//            dateFormat.setLenient(false);
//            return dateFormat.parse(dateValue);
//        } catch (ParseException e) {
//            throw new IllegalArgumentException("Invalid date format. Please use the format dd/MM/yyyy.");
//        }
//    }
//}
