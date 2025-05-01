package com.example.utapCattle.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.time.format.DateTimeParseException;

public class DateUtilsTest {

    @Test
    public void testFormatToReadableDate() {
        // Test case 1: Date only (no time)
        String inputDate1 = "2025-04-08";
        String expectedOutput1 = "08-Apr-2025";
        assertEquals(expectedOutput1, DateUtils.formatToReadableDate(inputDate1));

        // Test case 2: Date with time
        String inputDate2 = "2025-04-08 18:43:52";
        String expectedOutput2 = "08-Apr-2025";
        assertEquals(expectedOutput2, DateUtils.formatToReadableDate(inputDate2));

        // Test case 3: Date with time and milliseconds
        String inputDate3 = "2025-04-08 14:29:05.000";
        String expectedOutput3 = "08-Apr-2025";
        assertEquals(expectedOutput3, DateUtils.formatToReadableDate(inputDate3));

        // Test case 4: Invalid date
        String invalidDate = "invalid-date";
        assertThrows(DateTimeParseException.class, () -> {
            DateUtils.formatToReadableDate(invalidDate);
        });
    }
}
