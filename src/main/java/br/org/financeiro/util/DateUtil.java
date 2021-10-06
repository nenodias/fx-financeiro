package br.org.financeiro.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateUtil {

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }
    
    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(date);
    }
    
    public static Date parseDate(String dateString) {
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
            return sdf.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean validDate(String dateString) {
        return DateUtil.parse(dateString) != null;
    }
    
    public static Date toDate(LocalDate data) {
    	String texto = format(data);
    	return parseDate(texto);
    }

	public static LocalDate toLocalDate(Date data) {
		String texto = format(data);
    	return parse(texto);
	}
}