package br.com.rhribeiro25.baseprojectspringwebflux.utils;

import lombok.extern.log4j.Log4j2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class Date Utils
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Log4j2
public class DateUtils {
    public final static String convert_YYYY_MM_DD_To_DD_MM_YYYY(String stringDate) {
        try {
            Date oldDate = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(oldDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public final static String convert_DD_MM_YYYY_To_YYYY_MM_DD_(String stringDate) {
        try {
            Date oldDate = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(oldDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public final static boolean isAfterDate(String stringDate1, String stringDate2) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = dateFormat.parse(stringDate1);
            Date date2 = dateFormat.parse(stringDate2);
            if (date1.after(date2)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }
}
