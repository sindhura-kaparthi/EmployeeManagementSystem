//package com.example.employeemanagementsystem.utility;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//
//public class DateFormatValidator implements ConstraintValidator<DateFormat, Date> {
//    private static final String DATE_FORMAT = "dd/MM/yyyy";
//
//    @Override
//    public void initialize(DateFormat constraintAnnotation) {
//    }
//
//    @Override
//    public boolean isValid(Date value, ConstraintValidatorContext context) {
//        if (value == null) {
//            return true;
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//        sdf.setLenient(false);
//        try {
//            String dateString = sdf.format(value);
//            Date parsedDate = sdf.parse(dateString);
//            return value.equals(parsedDate);
//        } catch (ParseException e) {
//            return false;
//        }
//    }
//}
