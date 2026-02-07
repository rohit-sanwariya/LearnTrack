package org.rohit.airtribe.learntask;

import org.rohit.airtribe.learntask.ui.ConsoleUI;

public class Main {
    static void main() {
        try {
            ConsoleUI consoleUI = new ConsoleUI();
            consoleUI.start();
        } catch (Exception e) {
            System.err.println("A critical error occurred: " + e.getMessage());
            System.err.println("Please restart the application.");
        }
    }
}