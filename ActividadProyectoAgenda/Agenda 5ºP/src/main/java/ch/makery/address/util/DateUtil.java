package ch.makery.address.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    //El patrón de fecha que se usa para la conversión.  Se cambia como se quiera.
    private static final String DATE_PATTERN = "dd.MM.yyyy";

     //El formateador de fechas.
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);


     //Devuelve la fecha dada como una cadena bien formateada. Lo anterior definido
     //@link DateUtil#DATE_PATTERN} usado.
     //@param fecha a fecha a devolver como cadena
     //@return formateado a string
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

     //Convierte una cadena en el formato definido {@link DateUtil#DATE_PATTERN}
     //a {@link LocalDate} objeto <p>
     //Returns null si el String no se pudo convertir.
     //@param dateString la fecha como String
     //@return fecha objecto o null si no se puede convertir
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean validDate(String dateString) {
        // Trate de analizar el String.
        return DateUtil.parse(dateString) != null;
    }
}
