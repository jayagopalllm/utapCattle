package com.example.utapCattle.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Locale;

/**
 * Utility class for handling date format conversions.
 * <p>
 * This class provides utility methods for converting date strings between
 * different formats.
 * </p>
 */
public class DateUtils {

    private static final DateTimeFormatter INPUT_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd")
            .optionalStart()
            .appendPattern(" HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, true)
            .optionalEnd()
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
            .toFormatter();

    private static final DateTimeFormatter DATE_ONLY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, true)
            .optionalEnd()
            .toFormatter();

    private static final DateTimeFormatter DEFAULT_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy",
            Locale.ENGLISH);

    /**
     * Converts a date string from the format "yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss"
     * to a more human-readable format "dd-MMM-yyyy".
     * <p>
     * The method parses the input date string and returns it in the format
     * "dd-MMM-yyyy"
     * (e.g., "08-Apr-2025").
     * </p>
     *
     * @param inputDate the input date string (e.g., "2025-04-08" or "2025-04-08
     *                  18:43:52")
     * @return formatted date string in the format "dd-MMM-yyyy" (e.g.,
     *         "08-Apr-2025")
     */
    public static String formatToReadableDate(String dateString) {
        return formatToReadableDate(dateString, null); // Use the default format if outputPattern is null
    }

    /**
     * Converts a date string from the format "yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss"
     * to a more human-readable format with a custom pattern.
     * <p>
     * The method parses the input date string and returns it in the specified
     * format.
     * </p>
     *
     * @param inputDate     the input date string (e.g., "2025-04-08" or "2025-04-08
     *                      18:43:52")
     * @param outputPattern the desired output date pattern (e.g., "dd-MMM-yyyy")
     * @return formatted date string in the custom format (e.g., "08-Apr-2025")
     */
    public static String formatToReadableDate(String dateString, String outputPattern) {
        if (dateString == null || dateString.isEmpty()) {
            return dateString;
        }
        try {
            // If outputPattern is null, use the default formatter
            DateTimeFormatter outputFormatter = (outputPattern != null && !outputPattern.isEmpty())
                    ? DateTimeFormatter.ofPattern(outputPattern, Locale.ENGLISH)
                    : DEFAULT_OUTPUT_FORMATTER;

            return parseDate(dateString).format(outputFormatter);
        } catch (DateTimeParseException e) {
            throw e;
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
        if (dateString == null || dateString.isEmpty()) {
            throw new DateTimeParseException("Date string cannot be null or empty", dateString, 0);
        }
        try {
            return LocalDateTime.parse(dateString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date format: " + dateString, dateString, e.getErrorIndex());
        }
    }

    /**
     * Converts a date string to a LocalDate object.
     *
     * @param dateString the input date string (e.g., "2025-04-08 18:43:52")
     * @return LocalDate object parsed from the input string
     * @throws DateTimeParseException if the input string can't be parsed
     */
    public static LocalDate parseToDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            throw new DateTimeParseException("Date string cannot be null or empty", dateString, 0);
        }
        try {
            return LocalDate.parse(dateString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date format: " + dateString, dateString, e.getErrorIndex());
        }
    }

    public static LocalDateTime parseDate(String dateString) {
        if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            // Date-only string
            return LocalDate.parse(dateString, DATE_ONLY_FORMATTER).atStartOfDay();
        } else {
            // Date-time string
            return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
        }
    }

}
