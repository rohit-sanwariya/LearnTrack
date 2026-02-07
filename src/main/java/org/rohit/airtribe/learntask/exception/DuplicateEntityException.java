package org.rohit.airtribe.learntask.exception;

public class DuplicateEntityException extends Exception {
    private String entityType;
    private String identifier;

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String entityType, String identifier) {
        super(entityType + " with identifier '" + identifier + "' already exists.");
        this.entityType = entityType;
        this.identifier = identifier;
    }

    // Getters
    public String getEntityType() {
        return entityType;
    }

    public String getIdentifier() {
        return identifier;
    }
}