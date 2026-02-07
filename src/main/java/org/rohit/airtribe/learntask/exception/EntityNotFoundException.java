package org.rohit.airtribe.learntask.exception;

public class EntityNotFoundException extends Exception {
    private String entityType;
    private int entityId;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityType, int entityId) {
        super(entityType + " with ID " + entityId + " not found.");
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public EntityNotFoundException(String entityType, int entityId, Throwable cause) {
        super(entityType + " with ID " + entityId + " not found.", cause);
        this.entityType = entityType;
        this.entityId = entityId;
    }

    // Getters
    public String getEntityType() {
        return entityType;
    }

    public int getEntityId() {
        return entityId;
    }
}