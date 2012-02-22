package org.integration.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;


public class DateUtils extends org.apache.commons.lang.time.DateUtils{
    private static final DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss ZZZZZ", Locale.US);
    
    /**
     * Parses a date/time returned by the Dropbox API. Returns null if it
     * cannot be parsed.
     *
     * @param date a date returned by the API.
     *
     * @return a {@link Date}.
     */
    public static Date parseEntryDate(String date) {
        if (StringUtils.isNotBlank(date)) {
            try {
                return dateFormat.parse(date);
            } catch (java.text.ParseException e) {
                return null;
            }
        }
        
        return null;
    }
}
