package org.rohit.airtribe.learntask.util;

import org.rohit.airtribe.learntask.exception.InvalidInputException;

public class InputValidator {

    // Private constructor - utility class
    private InputValidator() {}

    // Validate integer input
    public static int validateInteger(String input, String fieldName) throws InvalidInputException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName, input, "a valid integer number");
        }
    }

    // Validate positive integer
    public static int validatePositiveInteger(String input, String fieldName) throws InvalidInputException {
        int value = validateInteger(input, fieldName);
        if (value <= 0) {
            throw new InvalidInputException(fieldName, input, "a positive integer greater than 0");
        }
        return value;
    }

    // Validate string is not empty
    public static String validateNonEmpty(String input, String fieldName) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName, input, "non-empty text");
        }
        return input.trim();
    }

    // Validate email format (basic validation)
    public static String validateEmail(String email) throws InvalidInputException {
        String trimmedEmail = validateNonEmpty(email, "Email");
        if (!trimmedEmail.contains("@") || !trimmedEmail.contains(".")) {
            throw new InvalidInputException("Email", email, "valid email format (user@domain.com)");
        }
        return trimmedEmail;
    }

    // Validate date format (basic YYYY-MM-DD)
    public static String validateDate(String date) throws InvalidInputException {
        String trimmedDate = validateNonEmpty(date, "Date");
        if (!trimmedDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new InvalidInputException("Date", date, "YYYY-MM-DD format");
        }
        return trimmedDate;
    }

    // Validate status is one of allowed values
    public static String validateStatus(String status) throws InvalidInputException {
        String trimmedStatus = validateNonEmpty(status, "Status");
        if (!trimmedStatus.equals("ACTIVE") &&
                !trimmedStatus.equals("COMPLETED") &&
                !trimmedStatus.equals("CANCELLED")) {
            throw new InvalidInputException("Status", status, "ACTIVE, COMPLETED, or CANCELLED");
        }
        return trimmedStatus;
    }
}