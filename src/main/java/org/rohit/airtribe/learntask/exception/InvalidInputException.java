package org.rohit.airtribe.learntask.exception;

public class InvalidInputException extends Exception {
    private String inputName;
    private String inputValue;

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String inputName, String inputValue) {
        super("Invalid input for " + inputName + ": " + inputValue);
        this.inputName = inputName;
        this.inputValue = inputValue;
    }

    public InvalidInputException(String inputName, String inputValue, String expectedFormat) {
        super("Invalid input for " + inputName + ": " + inputValue + ". Expected: " + expectedFormat);
        this.inputName = inputName;
        this.inputValue = inputValue;
    }

    // Getters
    public String getInputName() {
        return inputName;
    }

    public String getInputValue() {
        return inputValue;
    }
}