package com.example.utapCattle.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Utility class for handling date format conversions.
 * <p>
 * This class provides utility methods for converting date strings between
 * different formats.
 * </p>
 */
public class DateUtils {

    private static final DateTimeFormatter INPUT_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd")
                    .optionalStart()
                    .appendPattern(" HH:mm:ss")
                    .optionalEnd()
                    .toFormatter();

    private static final DateTimeFormatter DEFAULT_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    /**
     * Converts a date string from the format "yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss"
     * to a more human-readable format "dd-MMM-yyyy".
     * <p>
     * The method parses the input date string and returns it in the format "dd-MMM-yyyy"
     * (e.g., "08-Apr-2025").
     * </p>
     *
     * @param inputDate the input date string (e.g., "2025-04-08" or "2025-04-08 18:43:52")
     * @return formatted date string in the format "dd-MMM-yyyy" (e.g., "08-Apr-2025")
     */
    public static String formatToReadableDate(String dateString) {
        return formatToReadableDate(dateString, null);  // Use the default format if outputPattern is null
    }

    /**
     * Converts a date string from the format "yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss"
     * to a more human-readable format with a custom pattern.
     * <p>
     * The method parses the input date string and returns it in the specified format.
     * </p>
     *
     * @param inputDate   the input date string (e.g., "2025-04-08" or "2025-04-08 18:43:52")
     * @param outputPattern the desired output date pattern (e.g., "dd-MMM-yyyy")
     * @return formatted date string in the custom format (e.g., "08-Apr-2025")
     */
    public static String formatToReadableDate(String dateString, String outputPattern) {
        if (dateString == null || dateString.isEmpty()) {
            return dateString;
        }
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateString, INPUT_FORMATTER);

            // If outputPattern is null, use the default formatter
            DateTimeFormatter outputFormatter = (outputPattern != null && !outputPattern.isEmpty())
                    ? DateTimeFormatter.ofPattern(outputPattern, Locale.ENGLISH)
                    : DEFAULT_OUTPUT_FORMATTER;

            return dateTime.format(outputFormatter);
        } catch (DateTimeParseException e) {
            // Optionally log or rethrow the exception with a custom message
            return dateString;  // You can log the error here as well
        }
    }

    /**
     * Converts a date string to a LocalDateTime object.
     *
     * @param dateString the input date string (e.g., "2025-04-08 18:43:52")
     * @return LocalDateTime object parsed from the input string
     * @throws DateTimeParseException if the input string can't be parsed
     */
    public static LocalDateTime parseToDateTime(String dateString) {
        try {
            return LocalDateTime.parse(dateString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            // Optionally log or rethrow the exception with a custom message
            throw new DateTimeParseException("Invalid date format: " + dateString, dateString, e.getErrorIndex());
        }
    }
}
