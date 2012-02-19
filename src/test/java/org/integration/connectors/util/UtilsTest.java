package org.integration.connectors.util;

import java.util.Date;

import static org.junit.Assert.*;

import org.integration.util.DateUtils;
import org.junit.Test;

public class UtilsTest {
    public static String[] DATES = {"Sun, 05 Feb 2012 19:37:35 +0000"};
    
    @Test
    public void parseDates() {
        for (String date : DATES) {
            Date parsedDate = DateUtils.parseEntryDate(date);
            
            assertNotNull(parsedDate);
        }
    }
}
